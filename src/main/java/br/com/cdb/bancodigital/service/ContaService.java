package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.dto.conta.ContaDTO;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.repository.ContaRepository;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    ContaRepository repository;

    @Transactional(readOnly = true)
    public ContaDTO findById(Long id){
        Optional<Conta> contaOPT = repository.findById(id);
        Conta conta = contaOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Conta não encontrada")
        );
        return new ContaDTO(conta);
    }

    public ContaDTO create(Conta conta){
        Conta novaConta = repository.save(conta);
        return new ContaDTO(novaConta);
    }
}
