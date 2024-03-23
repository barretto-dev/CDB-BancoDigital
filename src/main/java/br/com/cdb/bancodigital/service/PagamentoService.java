package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.dto.pagamento.PagamentoCreateDTO;
import br.com.cdb.bancodigital.dto.pagamento.PagamentoDTO;
import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.CartaoDebito;
import br.com.cdb.bancodigital.entity.Pagamento;
import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import br.com.cdb.bancodigital.repository.CartaoRepository;
import br.com.cdb.bancodigital.repository.PagamentoRepository;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import br.com.cdb.bancodigital.service.exception.PagamentoInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Transactional(readOnly = true)
    public Page<PagamentoDTO> findAllByCartaoId(Long cartaoId, LocalDate dataInicio, LocalDate dataFim, PageRequest pageRequest){
        Optional<Cartao> cartaoOPT = cartaoRepository.findById(cartaoId);
        Cartao cartao = cartaoOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cartão infomado não encontrado")
        );
        Page<Pagamento> lista = repository.getPagamentosCartaoMensal(cartaoId,dataInicio,dataFim,pageRequest);
        return lista.map( pagamento -> new PagamentoDTO(pagamento));


    }

    @Transactional
    public boolean create(PagamentoCreateDTO dto){

        String remetente = dto.getDestinatario();
        BigDecimal valor = dto.getValor();
        Integer qtdParcelas = dto.getQtdParcelas();
        Long cartaoId = dto.getCartaoId();

        Optional<Cartao> cartaoOPT = cartaoRepository.findById(cartaoId);
        Cartao cartao = cartaoOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cartão infomado não encontrado")
        );

        if(!cartao.isAtivo())
            throw new PagamentoInvalidoException("Cartão informado está inativo");

        if(!cartao.isValido())
            throw new PagamentoInvalidoException("Cartão informado está com a validade expirada");

        if( cartao instanceof CartaoDebito && qtdParcelas > 1){
            throw new PagamentoInvalidoException("Cartão de débito só pode ter uma parcela");
        }


        boolean isSaldoNegativo = cartao.realizarPagamento(valor);
        if(!isSaldoNegativo && cartao.getTipo().compareTo(TipoCartao.DEBITO) == 0)
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

            List<LocalDate> list = cartao.getDataInicioFimMesCartao();
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

    private boolean isLimiteCartaoUtrapassado(List<Pagamento> pagamentosAntigos, BigDecimal valorNovoPagamento,
                                              BigDecimal limite){

        final BigDecimal[] valorTotal = {valorNovoPagamento};
        pagamentosAntigos.forEach( pa -> {
            valorTotal[0] = valorTotal[0].add(pa.getValor());
        });

        return valorTotal[0].compareTo(limite) > 0;
    }
}
