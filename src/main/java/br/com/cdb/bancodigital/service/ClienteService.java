package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.Endereco;
import br.com.cdb.bancodigital.repository.ClienteRepository;
import br.com.cdb.bancodigital.repository.EnderecoRepository;
import br.com.cdb.bancodigital.service.exception.BancoDeDadosException;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import br.com.cdb.bancodigital.dto.cliente.ClienteDTO;
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

    @Autowired
    EnderecoRepository enderecoRepository;

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
        try {
            Cliente novoCliente = clienteRepository.save(cliente);
            return new ClienteDTO(novoCliente);
        }catch (DataIntegrityViolationException e ){
            throw new BancoDeDadosException(
                    "Cpf informado já está sendo usado por outro cliente"
            );
        }


    }

    @Transactional
    public ClienteDTO update(Long id, Cliente cliente) {

        Optional<Cliente> clienteOPT = clienteRepository.findById(id);
        Cliente clienteToUpdate = clienteOPT.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cliente não encontrado")
        );

        Optional<Cliente> clienteCpf = clienteRepository.findByCpf(cliente.getCpf());
        if(clienteCpf.isPresent() && clienteCpf.get().getId() != id){
            throw new BancoDeDadosException( "Cpf informado já está sendo usado por outro cliente" );
        }


        Endereco enderecoToUpdate = enderecoRepository.findById(clienteToUpdate.getEndereco().getId()).get();
        Endereco novoEndereco = cliente.getEndereco();

        enderecoToUpdate.setCep(novoEndereco.getCep());
        enderecoToUpdate.setUnidadeFederativa(novoEndereco.getUnidadeFederativa());
        enderecoToUpdate.setCidade(novoEndereco.getCidade());
        enderecoToUpdate.setBairro(novoEndereco.getBairro());
        enderecoToUpdate.setLogradouro(novoEndereco.getLogradouro());
        enderecoToUpdate.setNumero(novoEndereco.getNumero());
        enderecoToUpdate.setComplemento(novoEndereco.getComplemento());

        Endereco enderecoAtualizado = enderecoRepository.save(enderecoToUpdate);

        clienteToUpdate.setCpf(cliente.getCpf());
        clienteToUpdate.setNome(cliente.getNome());
        clienteToUpdate.setEndereco(cliente.getEndereco());
        clienteToUpdate.setDataNascimento(cliente.getDataNascimento());
        clienteToUpdate.setTipo(cliente.getTipo());
        clienteToUpdate.setEndereco(enderecoAtualizado);

        clienteToUpdate = clienteRepository.save(clienteToUpdate);

        return new ClienteDTO(clienteToUpdate);

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
