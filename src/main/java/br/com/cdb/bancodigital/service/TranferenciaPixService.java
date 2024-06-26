package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.dto.transferenciaPix.TransferenciaPixCreateDTO;
import br.com.cdb.bancodigital.dto.transferenciaPix.TransferenciaPixDTO;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.entity.TransferenciaPix;
import br.com.cdb.bancodigital.repository.ContaRepository;
import br.com.cdb.bancodigital.repository.TransferenciaPixRepository;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import br.com.cdb.bancodigital.service.exception.TransferenciaPixInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TranferenciaPixService {

    @Autowired
    TransferenciaPixRepository repository;

    @Autowired
    ContaRepository contaRepository;

    @Transactional
    public TransferenciaPixDTO create(TransferenciaPixCreateDTO dto){
        Optional<Conta> contaOpt = contaRepository.findById(dto.getContaId());
        Conta conta = contaOpt.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Conta informada não foi encontrada")
        );

        boolean podeTranferir = conta.transferirPix(dto.getValor());
        if(!podeTranferir){
            throw new TransferenciaPixInvalidaException("Conta possui saldo insuficiente para a transferência");
        }

        TransferenciaPix transferencia = new TransferenciaPix(dto.getDestinatario(), dto.getValor(), conta);
        transferencia = repository.save(transferencia);
        return new TransferenciaPixDTO(transferencia);

    }

    @Transactional(readOnly = true)
    public TransferenciaPixDTO findById(Long id){
        Optional<TransferenciaPix> transferenciaPixOpt = repository.findById(id);
        TransferenciaPix transferenciaPix = transferenciaPixOpt.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Transferência via pix informada não foi encontrada")
        );
        return new TransferenciaPixDTO(transferenciaPix);
    }

    @Transactional(readOnly = true)
    public Page<TransferenciaPixDTO> findAllByCartaoId(Long contaId, PageRequest pageRequest) {

        Optional<Conta> contaOpt = contaRepository.findById(contaId);
        Conta conta = contaOpt.orElseThrow(
                () -> new EntidadeNaoEncontradaException("Conta informada não foi encontrada")
        );

        Page<TransferenciaPix> lista = repository.findAllByCartaoId( contaId, pageRequest);
        return lista.map( transferenciaPix -> new TransferenciaPixDTO(transferenciaPix));
    }
}
