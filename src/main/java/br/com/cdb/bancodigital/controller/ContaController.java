package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.dto.conta.ContaDTO;
import br.com.cdb.bancodigital.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/conta")
public class ContaController {

    @Autowired
    ContaService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContaDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ContaDTO> create(@RequestBody ContaDTO dto){
        ContaDTO novaConta = service.create(dto.toConta());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novaConta.getId()).toUri();

        return ResponseEntity.created(uri).body(novaConta);

    }


}
