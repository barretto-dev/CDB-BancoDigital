package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.annotations.ValoresPermitidos;
import br.com.cdb.bancodigital.dto.transferenciaPix.TransferenciaPixCreateDTO;
import br.com.cdb.bancodigital.dto.transferenciaPix.TransferenciaPixDTO;
import br.com.cdb.bancodigital.service.TranferenciaPixService;
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
@RequestMapping(value = "/transferenciaPix")
public class TranferenciaPixController {

    @Autowired
    TranferenciaPixService service;

    @PostMapping
    public ResponseEntity<TransferenciaPixDTO> create(@Valid @RequestBody TransferenciaPixCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransferenciaPixDTO> findById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(value = "conta/{contaId}")
    public ResponseEntity<Page<TransferenciaPixDTO>> findAllByCartaoId(
            @PathVariable @Min(1) Long contaId,
            @RequestParam(value = "numeroPagina", defaultValue = "0") @Min(0) Integer numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "12") @Min(1) Integer tamanhoPagina,
            @RequestParam(value = "ordem", defaultValue = "DESC")
                @ValoresPermitidos(propName = "ordem", values = {"ASC", "DESC"}) String ordem,
            @RequestParam(value = "ordenarPor", defaultValue = "data")
                @ValoresPermitidos(propName = "ordenarPor", values = {"id", "destinatario", "valor", "data"}) String ordenarPor
            ) {

        PageRequest pageRequest = PageRequest.of(
                numeroPagina, tamanhoPagina, Sort.Direction.valueOf(ordem), ordenarPor );

        Page<TransferenciaPixDTO> lista = service.findAllByCartaoId(contaId, pageRequest);
        return ResponseEntity.ok().body(lista);


    }
}
