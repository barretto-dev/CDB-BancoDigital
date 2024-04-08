package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.dto.cartao.CartaoMinDTO;
import br.com.cdb.bancodigital.dto.formatters.LocalDateFormatter;
import br.com.cdb.bancodigital.entity.*;
import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import br.com.cdb.bancodigital.repository.CartaoRepository;
import br.com.cdb.bancodigital.repository.ContaRepository;
import br.com.cdb.bancodigital.repository.PagamentoRepository;
import br.com.cdb.bancodigital.service.encrypt.PasswordEncoder;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import br.com.cdb.bancodigital.service.exception.OperacaoProibidaException;
import br.com.cdb.bancodigital.service.exception.PagamentoInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import java.security.SecureRandom;

@Service
public class CartaoService {

    @Autowired
    CartaoRepository repository;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Transactional(readOnly = true)
    public CartaoMinDTO findById(Long id){
        Optional<Cartao> cartaoOPT = repository.findById(id);
        Cartao cartao = cartaoOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cartão informado não encontrado")
        );
        return new CartaoMinDTO(cartao);
    }

    @Transactional(readOnly = true)
    public Page<CartaoMinDTO> findByConta(Long contaId, PageRequest pageRequest){
        Optional<Conta> contaOPT = contaRepository.findById(contaId);
        Conta conta = contaOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Conta informada não encontrada")
        );

        Page<Cartao> page = repository.findByConta(contaId,pageRequest);
        return page.map( cartao -> new CartaoMinDTO(cartao));
    }

    @Transactional()
    public CartaoMinDTO updateLimite(Long id, BigDecimal limite, String senha){
        Optional<Cartao> cartaoOPT = repository.findById(id);
        Cartao cartao = cartaoOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cartão informado não encontrado")
        );

        if(!cartao.getTipo().equals(TipoCartao.DEBITO))
            throw new OperacaoProibidaException("Apenas cartões de debito podem alterar o limite");

        if( !PasswordEncoder.matches(senha, cartao.getSenha()) )
            throw new OperacaoProibidaException("A senha informada está incorreta");

        if(!cartao.isAtivo())
            throw new OperacaoProibidaException("Cartão informado está inativo");

        if(!cartao.isValido())
            throw new PagamentoInvalidoException("Cartão informado está com a validade expirada");

        cartao.setLimite(limite);

        cartao = repository.save(cartao);
        return new CartaoMinDTO(cartao);
    }

    @Transactional()
    public void updateSenha(Long id, String senhaAtual, String senhaNova){
        Optional<Cartao> cartaoOPT = repository.findById(id);
        Cartao cartao = cartaoOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cartão informado não encontrado")
        );

        if(!cartao.isAtivo())
            throw new OperacaoProibidaException("Cartão informado está inativo");

        if(!cartao.isValido())
            throw new PagamentoInvalidoException("Cartão informado está com a validade expirada");

        if( !PasswordEncoder.matches(senhaAtual, cartao.getSenha()) )
            throw new OperacaoProibidaException("A senha atual informada está incorreta");

        cartao.setSenha(PasswordEncoder.encrypt(senhaNova));
        repository.save(cartao);
    }

    @Transactional()
    public CartaoMinDTO updateAtividade(Long id, boolean ativo, String senha){
        Optional<Cartao> cartaoOPT = repository.findById(id);
        Cartao cartao = cartaoOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cartão informado não encontrada")
        );

        if( !PasswordEncoder.matches(senha, cartao.getSenha()) )
            throw new OperacaoProibidaException("A senha informada está incorreta");

        if(!cartao.isValido() && ativo)
            throw new PagamentoInvalidoException("Cartão informado está com a validade expirada, logo não pode ser reativado");

        cartao.setAtivo(ativo);
        cartao = repository.save(cartao);
        return new CartaoMinDTO(cartao);

    }

    public void aplicarTaxaDeUso(Long id){
        Optional<Cartao> cartaoOPT = repository.findById(id);
        Cartao cartao = cartaoOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cartão informado não encontrada")
        );

        if(cartao instanceof CartaoDebito)
            throw new OperacaoProibidaException("Apenas cartões de credito estão sujeitos à taxas de pagamentos");

        CartaoCredito cartaoCredito = (CartaoCredito) cartao;

        List<LocalDate> periodoPagamento = cartaoCredito.getPeriodoPagamentoAtual();
        LocalDate inicioPeriodo = periodoPagamento.get(0);
        LocalDate finalPeriodo = periodoPagamento.get(1);

        LocalDate dataAtual = LocalDate.now(ZoneId.of("Brazil/East"));
        LocalDate dataAplicacaoTaxa = finalPeriodo.plusDays(1);

        if( dataAtual.compareTo(dataAplicacaoTaxa) != 0) {
            throw new OperacaoProibidaException("A data para aplicação da taxa de uso deste cartao é "
                    + LocalDateFormatter.formatar(dataAplicacaoTaxa));

        }

        List<Pagamento> pagamentosMesAtual = pagamentoRepository.getPagamentosCartaoMensal(id, inicioPeriodo,finalPeriodo);

        final BigDecimal[] somaPagamentos = {BigDecimal.ZERO};
        pagamentosMesAtual.forEach( pa -> {
            somaPagamentos[0] = somaPagamentos[0].add(pa.getValor());
        });

        cartaoCredito.aplicarTaxaDeUso(somaPagamentos[0]);
        repository.save(cartao);
    }

    @Transactional
    public CartaoMinDTO create(Cartao cartao, Long contaId){

        //Aqui se verificar se existir uma conta com o id informado
        Optional<Conta> contaOPT = contaRepository.findById(contaId);
        Conta conta = contaOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Conta informada não encontrada")
        );

        LocalDate dataCriacao = LocalDate.now(ZoneId.of("Brazil/East"));
        YearMonth validade = YearMonth.from(dataCriacao.plusYears(Cartao.getValidadeEmAnos()));

        cartao.setConta(conta);
        cartao.setDataCriacao(dataCriacao);
        cartao.setValidade(validade);

        //Aqui é gerado o numero do novo cartao
        Cartao ultimoCartao = repository.findLastCartao();
        if( ultimoCartao == null){
            cartao.setNumero("0000000000000001");
        }
        else{
            String novoNumeroCartao = gerarNumeroNovoCartao(ultimoCartao.getNumero());
            cartao.setNumero(novoNumeroCartao);
        }

        //Aqui é gerado o codigo de segurança do novo cartão
        String novoCodigoSeguranca = gerarCodigoSegurancaNovoCartao();
        cartao.setCodigoSeguranca( novoCodigoSeguranca );

        String senhaCripto = PasswordEncoder.encrypt(cartao.getSenha());
        cartao.setSenha(senhaCripto);

        cartao = repository.save(cartao);
        return new CartaoMinDTO(cartao);

    }



    private String gerarNumeroNovoCartao(String numeroUltimoCartao){
        int num = Integer.parseInt(numeroUltimoCartao);
        num++;

        StringBuilder numeroNovoCartao = new StringBuilder(Integer.toString(num));
        int adicionarZeros = Cartao.getNumeroLength() - numeroNovoCartao.length();

        while(adicionarZeros > 0){
            numeroNovoCartao.insert(0, "0");
            adicionarZeros--;
        }
        return numeroNovoCartao.toString();
    }

    private String gerarCodigoSegurancaNovoCartao(){
        SecureRandom rand = new SecureRandom();
        int upperbound = (int) Math.pow( 10, Cartao.getCodigoSegurancaLength());
        int num = rand.nextInt(upperbound);

        StringBuilder novoCodigoSeguranca = new StringBuilder(Integer.toString(num));
        int adicionarZeros = Cartao.getCodigoSegurancaLength() - novoCodigoSeguranca.length();

        while(adicionarZeros > 0){
            novoCodigoSeguranca.insert(0, "0");
            adicionarZeros--;
        }

        return novoCodigoSeguranca.toString();
    }
}
