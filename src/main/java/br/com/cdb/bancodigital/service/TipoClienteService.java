package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.entity.TipoCliente;
import br.com.cdb.bancodigital.repository.TipoClienteRepository;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TipoClienteService {

    @Autowired
    TipoClienteRepository tipoClienteRepository;

    @Transactional(readOnly = true)
    public TipoCliente findByTipo(String tipo){
        Optional<TipoCliente> tipoOpt = tipoClienteRepository.findByNome(tipo);
        TipoCliente tipoCliente = tipoOpt.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Tipo do cliente informado não encontrado")
        );
        return tipoCliente;
    }
}
