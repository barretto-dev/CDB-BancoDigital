package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.dto.transferenciaPix.TransferenciaPixCreateDTO;
import br.com.cdb.bancodigital.dto.transferenciaPix.TransferenciaPixDTO;
import br.com.cdb.bancodigital.service.TranferenciaPixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @GetMapping(value = "conta/{contaId}")
    public ResponseEntity<Page<TransferenciaPixDTO>> findAllByCartaoId(
            @PathVariable Long contaId,
            @RequestParam(value = "numeroPagina", defaultValue = "0") Integer numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "12") Integer tamanhoPagina,
            @RequestParam(value = "ordem", defaultValue = "DESC") String ordem,
            @RequestParam(value = "ordenarPor", defaultValue = "data") String ordenarPor
            ) {

        PageRequest pageRequest = PageRequest.of(
                numeroPagina, tamanhoPagina, Sort.Direction.valueOf(ordem), ordenarPor );

        Page<TransferenciaPixDTO> lista = service.findAllByCartaoId(contaId, pageRequest);
        return ResponseEntity.ok().body(lista);


    }
}
