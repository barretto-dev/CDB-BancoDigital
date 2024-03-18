package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.dto.cliente.ClienteCreateDTO;
import br.com.cdb.bancodigital.service.ClienteService;
import br.com.cdb.bancodigital.dto.cliente.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> findAll(
            @RequestParam(value = "numeroPagina", defaultValue = "0") Integer numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "12") Integer tamanhoPagina,
            @RequestParam(value = "ordem", defaultValue = "ASC") String ordem,
            @RequestParam(value = "ordenarPor", defaultValue = "nome") String ordenarPor
    ) {
        PageRequest pageRequest = PageRequest.of(
                numeroPagina, tamanhoPagina, Sort.Direction.valueOf(ordem), ordenarPor );

        Page<ClienteDTO> lista = clienteService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(clienteService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<ClienteDTO> create(@RequestBody ClienteCreateDTO dto) {
        ClienteDTO novoCliente = clienteService.create(dto.toCliente());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoCliente.getId()).toUri();

        return ResponseEntity.created(uri).body(novoCliente);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody ClienteCreateDTO dto) {
        ClienteDTO clienteAtualizado = clienteService.update(id,dto.toCliente());
        return ResponseEntity.status(204).body(clienteAtualizado);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
