package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.dto.pagamento.PagamentoDTO;
import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.CartaoDebito;
import br.com.cdb.bancodigital.entity.Pagamento;
import br.com.cdb.bancodigital.repository.CartaoRepository;
import br.com.cdb.bancodigital.repository.PagamentoRepository;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import br.com.cdb.bancodigital.service.exception.PagamentoInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    PagamentoRepository repository;
    @Autowired
    CartaoRepository cartaoRepository;

    @Transactional
    public boolean create(PagamentoDTO dto){

        String remetente = dto.getRemetente();
        BigDecimal valor = dto.getValor();
        Integer qtdParcelas = dto.getQtdParcelas();
        Long cartaoId = dto.getCartaoId();

        Optional<Cartao> cartaoOPT = cartaoRepository.findById(cartaoId);
        Cartao cartao = cartaoOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cartão não encontrado")
        );

        if( cartao instanceof CartaoDebito && qtdParcelas > 1){
            throw new PagamentoInvalidoException("Cartão de débito só pode ter uma parcela");
        }

        //Verificar se a conta do cartao possui saldo para pagamento
        boolean podeSerPago = cartao.realizarPagamento(valor);
        if(!podeSerPago)
            throw new PagamentoInvalidoException("Saldo insuficiente na conta para realizar pagamento");

        LocalDateTime dateNow = LocalDateTime.now(ZoneId.of("Brazil/East"));

        //Verifica limite do cartao e insere o pagamento no banco
        if( cartao instanceof CartaoDebito){
            BigDecimal limiteDiario = ((CartaoDebito) cartao).getLimiteDiario();
            List<Pagamento> pagamentosDia = repository.getPagamentosCartaoHoje(cartao.getId());

            boolean isLimiteUtrapassado = isLimiteCartaoUtrapassado(pagamentosDia, valor, limiteDiario);
            if(isLimiteUtrapassado)
                throw new PagamentoInvalidoException("Limite diário do cartão foi ultrapassado");

            Pagamento novoPagamento = new Pagamento(remetente,valor,null,null, dateNow, cartao);
            repository.save(novoPagamento);

        }else if(cartao instanceof CartaoCredito){
            BigDecimal limiteMensal = ((CartaoCredito) cartao).getLimiteMensal();

            List<LocalDate> list = getDataInicioFimMesCartao(cartao);
            LocalDate inicioMes = list.get(0);
            LocalDate fimMes = list.get(1);

            //Lista de pagamentos do cartao nos ultimos 30 dias
            List<Pagamento> pagamentosMensais = repository.getPagamentosCartaoMensal(cartao.getId(), inicioMes, fimMes);

            boolean isLimiteUtrapassado = isLimiteCartaoUtrapassado(pagamentosMensais, valor, limiteMensal);
            if(isLimiteUtrapassado)
                throw new PagamentoInvalidoException("Limite mensal do cartão foi ultrapassado. " +
                        "Foram consideradas todas os pagamentos de "+inicioMes+" até "+fimMes);

            BigDecimal valorParcela = valor.divide(BigDecimal.valueOf(qtdParcelas),2, RoundingMode.DOWN);

            //Armazena a diferença entre valor total da compra com soma das parcelas
            //Caso não seja 0, significa que existe uma margem de error que deve ser corrigida
            BigDecimal margemErro = valor.subtract(valorParcela.multiply(BigDecimal.valueOf(qtdParcelas)));

            int parcelaAtual = 1;
            Pagamento parcela = new Pagamento(remetente,valorParcela, parcelaAtual, qtdParcelas, dateNow, cartao);

            repository.save(parcela);
            parcelaAtual++;

            while (parcelaAtual <= qtdParcelas){
                dateNow = dateNow.plusMonths(1);

                //caso exista margem de error, a ultima parcela terá um acrescimo
                if(margemErro.compareTo(BigDecimal.ZERO) > 0 && parcelaAtual == qtdParcelas) {
                    repository.save(new Pagamento(remetente, valorParcela.add(margemErro),
                            parcelaAtual, qtdParcelas, dateNow, cartao));
                }else
                    repository.save(new Pagamento(remetente,valorParcela, parcelaAtual, qtdParcelas, dateNow, cartao));

                parcelaAtual++;
            }
        }

        cartaoRepository.save(cartao);

        return true;
    }

    /*
    Retorna uma lista contendo o inicio e o fim do mês do cartão

    EX:
        SE o cartão foi cadastrado em 12-02-2024 então:
            1° mes -> 12-02-2024 até 12-03-2024
            2° mes -> 13-03-2024 até 11-04-2024
    */
    private List<LocalDate> getDataInicioFimMesCartao(Cartao cartao){
        LocalDate dataCriacao = cartao.getDataCriacao();

        LocalDate dataAtual = LocalDate.now(ZoneId.of("Brazil/East"));
        Long quantidadeDias = ChronoUnit.DAYS.between( dataCriacao, dataAtual ) + 1;

        LocalDate dataInicio;
        LocalDate dataFim;

        if(quantidadeDias <= 30){
            dataInicio = dataCriacao;
            dataFim = dataInicio.plusDays(29);
        }else if(quantidadeDias % 30 == 0){
            dataFim = dataAtual;
            dataInicio = dataFim.minusDays(29);
        }else {
            //Diz em qual posição a data atual está em relação ao mês do cartao
            long posicaoNoMes = quantidadeDias % 30;

            dataInicio = dataAtual.minusDays(posicaoNoMes - 1);
            dataFim = dataInicio.plusDays(29);
        }


        List<LocalDate> result = new ArrayList<>();
        result.add(dataInicio);
        result.add(dataFim);
        return  result;
    }

    private boolean isLimiteCartaoUtrapassado(List<Pagamento> pagamentosAntigos, BigDecimal valorNovoPagamento,
                                              BigDecimal limite){

        final BigDecimal[] valorTotal = {valorNovoPagamento};
        pagamentosAntigos.forEach( pa -> {
            valorTotal[0] = valorTotal[0].add(pa.getValor());
        });

        return valorTotal[0].compareTo(limite) > 0;
    }
}
