package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.dto.transferenciaPix.TransferenciaPixCreateDTO;
import br.com.cdb.bancodigital.dto.transferenciaPix.TransferenciaPixDTO;
import br.com.cdb.bancodigital.service.TranferenciaPixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transferenciaPix")
public class TranferenciaPixController {

    @Autowired
    TranferenciaPixService service;

    @PostMapping
    public ResponseEntity<TransferenciaPixDTO> create(@RequestBody TransferenciaPixCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }
}
