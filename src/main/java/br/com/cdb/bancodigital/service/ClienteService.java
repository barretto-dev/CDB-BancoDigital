package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.repository.ClienteRepository;
import br.com.cdb.bancodigital.service.exception.BancoDeDadosException;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import br.com.cdb.bancodigital.dto.cliente.ClienteDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public ClienteDTO findById(long id) {
        Optional<Cliente> clienteOPT = clienteRepository.findById(id);
        Cliente cliente = clienteOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cliente não encontrado")
        );
        return new ClienteDTO(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
        List<Cliente> lista = clienteRepository.findAll();
        List<ClienteDTO> listaDTO = lista.stream().map(cliente -> new ClienteDTO(cliente)).toList();
        return listaDTO;
    }

    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAllPaged(PageRequest page) {
        Page<Cliente> lista = clienteRepository.findAll(page);
        return lista.map(cliente -> new ClienteDTO(cliente));
    }

    @Transactional
    public ClienteDTO create(Cliente cliente) {
        Cliente novoCliente = clienteRepository.save(cliente);
        return new ClienteDTO(novoCliente);
    }

    @Transactional
    public ClienteDTO update(Long id, Cliente cliente) {
        try {
            Cliente clienteAtualizado = clienteRepository.getReferenceById(id);

            clienteAtualizado.setCpf(cliente.getCpf());
            clienteAtualizado.setNome(cliente.getNome());
            clienteAtualizado.setEndereco(cliente.getEndereco());
            clienteAtualizado.setDataNascimento(cliente.getDataNascimento());

            clienteAtualizado = clienteRepository.save(clienteAtualizado);
            return new ClienteDTO(clienteAtualizado);
        } catch (EntityNotFoundException e) {
            throw new EntidadeNaoEncontradaException("Não existe cliente com o id=" + id);
        }
    }

    //Não usar @transactonal aqui pq senão as exceções EntidadeNaoEncontradaException e
    //DataIntegrityViolationException não seram lançadas quando necessário
    public void delete(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Não existe cliente com o id=" + id);
        } catch (DataIntegrityViolationException e) {
            throw new BancoDeDadosException(
                    "Cliente não pode ser excluido, poís feriria a integridade do banco"
            );
        }
    }
}
