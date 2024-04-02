package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.dto.conta.ContaDTO;
import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.entity.TaxaConta;
import br.com.cdb.bancodigital.entity.enums.TipoConta;
import br.com.cdb.bancodigital.entity.enums.TipoTaxaConta;
import br.com.cdb.bancodigital.repository.ClienteRepository;
import br.com.cdb.bancodigital.repository.ContaRepository;
import br.com.cdb.bancodigital.repository.TaxaContaRepository;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import br.com.cdb.bancodigital.service.exception.OperacaoProibidaException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    ContaRepository repository;

    @Autowired
    TaxaContaRepository taxaContaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public ContaDTO findById(Long id){
        Optional<Conta> contaOPT = repository.findById(id);
        Conta conta = contaOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Conta informada não encontrada")
        );
        return new ContaDTO(conta);
    }

    @Transactional(readOnly = true)
    public Page<ContaDTO> findAllPaged(PageRequest page) {
        Page<Conta> lista = repository.findAllPaged(page);
        return lista.map(conta -> new ContaDTO(conta));
    }

    @Transactional(readOnly = true)
    public ContaDTO addSaldo(Long id, BigDecimal saldoAdicional){
        Optional<Conta> contaOPT = repository.findById(id);
        Conta conta = contaOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Conta informada não encontrada")
        );
        BigDecimal saldoAntigo = conta.getSaldo();
        BigDecimal novoSaldo = saldoAntigo.add(saldoAdicional).setScale( 2, RoundingMode.DOWN);
        conta.setSaldo(novoSaldo);

        conta = repository.save(conta);
        return new ContaDTO(conta);
    }

    @Transactional(readOnly = true)
    public ContaDTO applyMensalidade(Long id){
        Optional<Conta> contaOPT = repository.findById(id);
        Conta conta = contaOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Conta informada não encontrada")
        );

        if(conta.getTipo().compareTo(TipoConta.CORRENTE) != 0)
            throw new OperacaoProibidaException("Apenas uma conta corrente está sujeita à taxa de mensalidade");

        conta.aplicarTaxa();
        conta = repository.save(conta);
        return new ContaDTO(conta);

    }

    @Transactional(readOnly = true)
    public ContaDTO applyRendimento(Long id){
        Optional<Conta> contaOPT = repository.findById(id);
        Conta conta = contaOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Conta informada não encontrada")
        );

        if(conta.getTipo().compareTo(TipoConta.POUPANCA) != 0)
            throw new OperacaoProibidaException("Apenas uma conta poupança está sujeita à taxa de rendimento");

        conta.aplicarTaxa();
        conta = repository.save(conta);
        return new ContaDTO(conta);

    }

//    @Transactional
//    public ContaDTO update(Long id, Conta conta) {
//        try {
//            repository.updateAlter(id,conta.getTipo().getCodigo(),conta.getSaldo());
//            Conta contaAtualizada = repository.findById(id).get();
//            return new ContaDTO(contaAtualizada);
//
//        } catch (EntityNotFoundException e) {
//            throw new EntidadeNaoEncontradaException("Não existe conta com o id=" + id);
//        }
//    }

    @Transactional
    public ContaDTO create(Conta novaConta, Long donoId){

        Optional<Cliente> clienteOPT = clienteRepository.findById(donoId);
        Cliente cliente = clienteOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cliente dono da conta não encontrada")
        );
        novaConta.setDono(cliente);

        TaxaConta taxa;

        if(novaConta.getTipo().equals(TipoConta.CORRENTE)) {
            taxa = taxaContaRepository.findByTipo(TipoTaxaConta.MENSALIDADE_PADRAO);
        }
        else {
            taxa = taxaContaRepository.findByTipo(TipoTaxaConta.RENDIMENTO_PADRAO);
        }

        if(taxa == null) {
            throw new EntidadeNaoEncontradaException("Taxa aplicada na conta não foi encontrada");
        }
        novaConta.setTaxa(taxa);

        Conta ultimaConta = repository.findLastConta();
        if(ultimaConta == null)
            novaConta.setNumero("000000001");
        else {
            String numeroNovaConta = gerarNumeroNovaConta(ultimaConta.getNumero());
            novaConta.setNumero(numeroNovaConta);
        }

        novaConta = repository.save(novaConta);
        return new ContaDTO(novaConta);
    }

    private String gerarNumeroNovaConta(String numeroUltimaConta){
        int num = Integer.parseInt(numeroUltimaConta);
        num++;

        StringBuilder numeroNovaConta = new StringBuilder(Integer.toString(num));
        int adicionarZeros = Conta.getNumeroLenght() - numeroNovaConta.length();

        while(adicionarZeros > 0){
            numeroNovaConta.insert(0, "0");
            adicionarZeros--;
        }
        return numeroNovaConta.toString();
    }
}
