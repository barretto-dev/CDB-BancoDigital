package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.dto.apolice.ApoliceCreateDTO;
import br.com.cdb.bancodigital.dto.apolice.ApoliceDTO;
import br.com.cdb.bancodigital.entity.Apolice;
import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.Seguro;
import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import br.com.cdb.bancodigital.repository.ApoliceRepository;
import br.com.cdb.bancodigital.repository.CartaoRepository;
import br.com.cdb.bancodigital.repository.SeguroRepository;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import br.com.cdb.bancodigital.service.exception.OperacaoProibidaException;
import br.com.cdb.bancodigital.service.exception.PagamentoInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ApoliceService {

    @Autowired
    ApoliceRepository repository;

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    SeguroRepository seguroRepository;

    @Transactional(readOnly = true)
    public ApoliceDTO findByNumero(String numero){
        Apolice apolice = repository.findApoliceByNumero(numero);
        if(apolice == null)
            throw new EntidadeNaoEncontradaException("Apolice informada não foi encontrada");

        return new ApoliceDTO(apolice);
    }

    @Transactional(readOnly = true)
    public Page<ApoliceDTO> findApolicesByCartao(Long cartaoId, PageRequest pageRequest){
        Optional<Cartao> cartaoOPT = cartaoRepository.findById(cartaoId);
        Cartao cartao = cartaoOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cartão informado não encontrado")
        );

        if(cartao.getTipo().compareTo(TipoCartao.CREDITO) != 0)
            throw new OperacaoProibidaException("Apenas cartão de crédito possui uma lista de apólices");

        Page<Apolice> page = repository.findApolicesByCartao(cartaoId,pageRequest);
        return page.map( apolice -> new ApoliceDTO(apolice));
    }

    @Transactional()
    public ApoliceDTO create(ApoliceCreateDTO dto){

        Optional<Cartao> cartaoOPT = cartaoRepository.findById(dto.getCartaoId());
        Cartao cartao = cartaoOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cartão informado não encontrado")
        );

        Optional<Seguro> seguroOPT = seguroRepository.findById(dto.getSeguroId());
        Seguro seguro = seguroOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Seguro informado não encontrado")
        );

        if(!cartao.isAtivo())
            throw new OperacaoProibidaException("Cartão informado está inativo");

        if(!cartao.isValido()){
            throw new OperacaoProibidaException("Cartão informado está com a validade expirada");
        }

        if(!cartao.getTipo().equals(TipoCartao.CREDITO)){
            throw new OperacaoProibidaException("Apenas cartão de crédito pode fazer um contrato de seguro");
        }

        Apolice apoliceJaExistente = repository.findByCartaoAndSeguro(dto.getCartaoId(), dto.getSeguroId());
        if(apoliceJaExistente != null)
            throw new OperacaoProibidaException("Este cartão já possui contrato para um seguro deste tipo");

        String numero = "";
        Apolice ultimaApolice = repository.findLastApolice();
        if(ultimaApolice == null)
            numero = "000000000001";
        else
            numero = gerarNumeroNovaApolice(ultimaApolice.getNumero());

        Apolice novaApolice = new Apolice((CartaoCredito) cartao,seguro,numero);
        novaApolice = repository.save(novaApolice);
        ((CartaoCredito) cartao).pagarApolice(novaApolice);

        return new ApoliceDTO(novaApolice);

    }

    private String gerarNumeroNovaApolice(String numeroUltimaApolice){
        int num = Integer.parseInt(numeroUltimaApolice);
        num++;

        StringBuilder numeroNovaApolice = new StringBuilder(Integer.toString(num));
        int adicionarZeros = Apolice.getNumeroLength() - numeroNovaApolice.length();

        while(adicionarZeros > 0){
            numeroNovaApolice.insert(0, "0");
            adicionarZeros--;
        }
        return numeroNovaApolice.toString();
    }
}
