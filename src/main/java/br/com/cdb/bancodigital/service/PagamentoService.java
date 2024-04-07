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
import java.util.HashMap;
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


        boolean isSaldoPositivo = cartao.realizarPagamento(valor);
        if(!isSaldoPositivo && cartao.getTipo().compareTo(TipoCartao.DEBITO) == 0)
            throw new PagamentoInvalidoException("Saldo insuficiente na conta para realizar pagamento");

        LocalDateTime dateNow = LocalDateTime.now(ZoneId.of("Brazil/East"));

        //Verifica limite do cartao e insere o pagamento no banco
        if( cartao instanceof CartaoDebito){
            BigDecimal limiteDiario = cartao.getLimite();
            List<Pagamento> pagamentosDia = repository.getPagamentosCartaoHoje(cartao.getId());

            HashMap<String, Object> map = isLimiteCartaoUtrapassado(pagamentosDia, valor, limiteDiario);
            boolean isLimiteUtrapassado = (boolean) map.get("isLimiteUtrapassado");

            if(isLimiteUtrapassado) {
                BigDecimal valorRestante = (BigDecimal) map.get("valorRestante");
                throw new PagamentoInvalidoException("Limite diário do cartão foi ultrapassado", valorRestante);
            }

            Pagamento novoPagamento = new Pagamento(remetente,valor,null,null, dateNow, cartao);
            repository.save(novoPagamento);

        }else if(cartao instanceof CartaoCredito){
            BigDecimal limiteMensal = cartao.getLimite();

            List<LocalDate> list = cartao.getPeriodoPagamentoAtual();
            LocalDate inicioMes = list.get(0);
            LocalDate fimMes = list.get(1);

            //Lista de pagamentos do cartao nos ultimos 30 dias
            List<Pagamento> pagamentosMensais = repository.getPagamentosCartaoMensal(cartao.getId(), inicioMes, fimMes);

            HashMap<String, Object> map = isLimiteCartaoUtrapassado(pagamentosMensais, valor, limiteMensal);
            boolean isLimiteUtrapassado = (boolean) map.get("isLimiteUtrapassado");

            if(isLimiteUtrapassado) {
                BigDecimal valorRestante = (BigDecimal) map.get("valorRestante");
                throw new PagamentoInvalidoException("Limite mensal do cartão foi ultrapassado",
                        valorRestante, inicioMes, fimMes);
            }

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

    private HashMap<String, Object> isLimiteCartaoUtrapassado(List<Pagamento> pagamentosAntigos, BigDecimal valorNovoPagamento,
                                              BigDecimal limite){

        HashMap<String, Object> result = new HashMap<String, Object>();

        final BigDecimal[] somaPagamentosAntigos = {BigDecimal.ZERO};
        pagamentosAntigos.forEach( pa -> {
            somaPagamentosAntigos[0] = somaPagamentosAntigos[0].add(pa.getValor());
        });

        result.put("valorRestante", limite.subtract(somaPagamentosAntigos[0]));
        result.put("isLimiteUtrapassado", somaPagamentosAntigos[0].add(valorNovoPagamento).compareTo(limite) > 0);

        return result;
    }
}
