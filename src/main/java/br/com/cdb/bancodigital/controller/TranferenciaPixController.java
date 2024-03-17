package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.dto.transferenciaPix.TransferenciaPixCreateDTO;
import br.com.cdb.bancodigital.dto.transferenciaPix.TransferenciaPixDTO;
import br.com.cdb.bancodigital.service.TranferenciaPixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transferenciaPix")
public class TranferenciaPixController {

    @Autowired
    TranferenciaPixService service;

    @PostMapping
    public ResponseEntity<TransferenciaPixDTO> create(@RequestBody TransferenciaPixCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransferenciaPixDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
}
