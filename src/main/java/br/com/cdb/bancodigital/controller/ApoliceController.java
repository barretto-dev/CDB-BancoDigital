package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.annotations.ValoresPermitidos;
import br.com.cdb.bancodigital.dto.apolice.ApoliceCreateDTO;
import br.com.cdb.bancodigital.dto.apolice.ApoliceDTO;
import br.com.cdb.bancodigital.service.ApoliceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(value = "/apolice")
public class ApoliceController {

    @Autowired
    ApoliceService service;

    @PostMapping
    public ResponseEntity<ApoliceDTO> create(@Valid @RequestBody ApoliceCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping(value = "/{numero}")
    public ResponseEntity<ApoliceDTO> findByNumero(@PathVariable @Min(1) String numero){
        return ResponseEntity.ok().body(service.findByNumero(numero));
    }

    @GetMapping(value = "cartao/{cartaoId}")
    public ResponseEntity<Page<ApoliceDTO>> findByCartao(
            @PathVariable @Min(1) Long cartaoId,
            @RequestParam(value = "numeroPagina", defaultValue = "0") @Min(0) Integer numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "12") @Min(1) Integer tamanhoPagina,
            @RequestParam(value = "ordem", defaultValue = "ASC")
                @ValoresPermitidos(propName = "ordem", values = {"ASC", "DESC"}) String ordem,
            @RequestParam(value = "ordenarPor", defaultValue = "numero")
                @ValoresPermitidos(propName = "ordenarPor", values = {"numero", "data", "valor"}) String ordenarPor
    ){
        PageRequest pageRequest = PageRequest.of(
                numeroPagina, tamanhoPagina, Sort.Direction.valueOf(ordem), ordenarPor );
        return ResponseEntity.ok(service.findApolicesByCartao(cartaoId,pageRequest));
    }

}
