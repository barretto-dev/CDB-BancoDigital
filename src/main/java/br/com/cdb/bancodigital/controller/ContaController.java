package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.dto.cliente.ClienteDTO;
import br.com.cdb.bancodigital.dto.conta.ContaDTO;
import br.com.cdb.bancodigital.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/conta")
public class ContaController {

    @Autowired
    ContaService service;

    @GetMapping
    public ResponseEntity<Page<ContaDTO>> findAll(
            @RequestParam(value = "numeroPagina", defaultValue = "0") Integer numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "12") Integer tamanhoPagina,
            @RequestParam(value = "ordem", defaultValue = "ASC") String ordem,
            @RequestParam(value = "ordenarPor", defaultValue = "nome") String ordenarPor
    ) {
        PageRequest pageRequest = PageRequest.of(
                numeroPagina, tamanhoPagina, Sort.Direction.valueOf(ordem), ordenarPor );

        Page<ContaDTO> lista = service.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(lista);
    }

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
    @PutMapping(value = "/{id}")
    public ResponseEntity<ContaDTO> update(@PathVariable Long id, @RequestBody ContaDTO dto) {
        ContaDTO contaAtualizada = service.update(id,dto.toConta());
        return ResponseEntity.status(204).body(contaAtualizada);
    }


}
