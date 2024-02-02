package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.dto.conta.ContaDTO;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.repository.ContaRepository;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Transactional(readOnly = true)
    public Page<ContaDTO> findAllPaged(PageRequest page) {
        Page<Conta> lista = repository.findAll(page);
        return lista.map(conta -> new ContaDTO(conta));
    }

    @Transactional
    public ContaDTO create(Conta conta){
        Conta novaConta = repository.save(conta);
        return new ContaDTO(novaConta);
    }

    @Transactional
    public ContaDTO update(Long id, Conta conta) {
        try {
            repository.updateAlter(id,conta.getTipo().getCodigo(),conta.getSaldo());
            Conta contaAtualizada = repository.findById(id).get();
            return new ContaDTO(contaAtualizada);

        } catch (EntityNotFoundException e) {
            throw new EntidadeNaoEncontradaException("Não existe conta com o id=" + id);
        }
    }
}
