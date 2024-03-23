package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.dto.cartao.CartaoMinDTO;
import br.com.cdb.bancodigital.entity.*;
import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import br.com.cdb.bancodigital.entity.enums.TipoTaxaCartao;
import br.com.cdb.bancodigital.repository.CartaoRepository;
import br.com.cdb.bancodigital.repository.ContaRepository;
import br.com.cdb.bancodigital.repository.PagamentoRepository;
import br.com.cdb.bancodigital.repository.TaxaCartaoRepository;
import br.com.cdb.bancodigital.service.encrypt.PasswordEncoder;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import br.com.cdb.bancodigital.service.exception.OperacaoProibidaException;
import br.com.cdb.bancodigital.service.exception.PagamentoInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.security.SecureRandom;

@Service
public class CartaoService {

    @Autowired
    CartaoRepository repository;

    @Autowired
    TaxaCartaoRepository taxaCartaoRepository;

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
    public CartaoMinDTO updateLimite(Long id, BigDecimal limite){
        Optional<Cartao> cartaoOPT = repository.findById(id);
        Cartao cartao = cartaoOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cartão informado não encontrado")
        );

        if(!cartao.getTipo().equals(TipoCartao.DEBITO))
            throw new OperacaoProibidaException("Apenas cartões de debito podem alterar o limite");

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
    public CartaoMinDTO updateAtividade(Long id, boolean ativo){
        Optional<Cartao> cartaoOPT = repository.findById(id);
        Cartao cartao = cartaoOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cartão informado não encontrada")
        );

        if(!cartao.isValido() && ativo)
            throw new PagamentoInvalidoException("Cartão informado está com a validade expirada, logo não pode ser reativado");

        cartao.setAtivo(ativo);
        cartao = repository.save(cartao);
        return new CartaoMinDTO(cartao);

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

        //Retorna a taxa adequada para o cartao ou um null
        TaxaCartao taxaCartao = encontrarTaxaCartao(cartao);
        if(taxaCartao == null)
            throw new EntidadeNaoEncontradaException("Taxa para o cartao não foi encontrada");

        cartao.setTaxa(taxaCartao);

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

    private TaxaCartao encontrarTaxaCartao(Cartao cartao){

        TaxaCartao taxaCartao = null;

        if(cartao instanceof CartaoDebito){
            taxaCartao = taxaCartaoRepository.findByTipo(TipoTaxaCartao.DEBITO);

        }
        else if (cartao instanceof CartaoCredito){
            taxaCartao = taxaCartaoRepository.findByTipo(TipoTaxaCartao.CREDITO);
        }

        return taxaCartao;
    }
}
