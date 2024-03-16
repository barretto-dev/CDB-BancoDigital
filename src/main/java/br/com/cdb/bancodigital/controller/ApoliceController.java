package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.dto.apolice.ApoliceCreateDTO;
import br.com.cdb.bancodigital.dto.apolice.ApoliceDTO;
import br.com.cdb.bancodigital.service.ApoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/apolice")
public class ApoliceController {

    @Autowired
    ApoliceService service;

    @PostMapping
    public ResponseEntity<ApoliceDTO> create(@RequestBody ApoliceCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping(value = "/{numero}")
    public ResponseEntity<ApoliceDTO> findByNumero(@PathVariable String numero){
        return ResponseEntity.ok().body(service.findByNumero(numero));
    }
}
