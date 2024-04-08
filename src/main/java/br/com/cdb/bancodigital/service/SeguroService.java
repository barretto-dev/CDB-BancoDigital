package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.dto.seguro.SeguroDTO;
import br.com.cdb.bancodigital.entity.Seguro;
import br.com.cdb.bancodigital.repository.SeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeguroService {

    @Autowired
    SeguroRepository seguroRepository;

    @Transactional(readOnly = true)
    public Page<SeguroDTO> findAll(PageRequest pageRequest){
        Page<Seguro> lista = seguroRepository.findAll(pageRequest);
        return lista.map( seguro -> new SeguroDTO(seguro));
    }
}
