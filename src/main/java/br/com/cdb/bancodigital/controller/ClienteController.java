package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.annotations.ValoresPermitidos;
import br.com.cdb.bancodigital.dto.cep.CepResultDTO;
import br.com.cdb.bancodigital.dto.cliente.ClienteCreateDTO;
import br.com.cdb.bancodigital.service.ClienteService;
import br.com.cdb.bancodigital.dto.cliente.ClienteDTO;
import br.com.cdb.bancodigital.service.exception.EntidadeNaoEncontradaException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@Validated
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> findAll(
            @RequestParam(value = "numeroPagina", defaultValue = "0") @Min(0) Integer numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "12") @Min(1) Integer tamanhoPagina,
            @RequestParam(value = "ordem", defaultValue = "ASC")
                @ValoresPermitidos(propName = "ordem", values = {"ASC", "DESC"}) String ordem,
            @RequestParam(value = "ordenarPor", defaultValue = "nome")
                @ValoresPermitidos(propName = "ordem", values = {"id", "nome", "cpf", "endereco", "dataNascimento", "tipo"}) String ordenarPor
    ) {
        PageRequest pageRequest = PageRequest.of(
                numeroPagina, tamanhoPagina, Sort.Direction.valueOf(ordem), ordenarPor );

        Page<ClienteDTO> lista = clienteService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable("id") @Min(1) Long id) {
        return ResponseEntity.ok().body(clienteService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteCreateDTO dto) {

        RestTemplate restTemplate = new RestTemplate();
        CepResultDTO cepResultDTO = restTemplate.getForEntity(
                                String.format("https://viacep.com.br/ws/%s/json", dto.getEndereco().getCep()),
                                CepResultDTO.class).getBody();

        if(cepResultDTO.getCep() == null)
            throw new EntidadeNaoEncontradaException("Número do cep não foi encontrado");

        ClienteDTO novoCliente = clienteService.create(dto.toCliente(cepResultDTO));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoCliente.getId()).toUri();

        return ResponseEntity.created(uri).body(novoCliente);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable @Min(1) Long id, @Valid @RequestBody ClienteCreateDTO dto) {

        RestTemplate restTemplate = new RestTemplate();
        CepResultDTO cepResultDTO = restTemplate.getForEntity(
                String.format("https://viacep.com.br/ws/%s/json", dto.getEndereco().getCep()),
                CepResultDTO.class).getBody();

        if(cepResultDTO.getCep() == null)
            throw new EntidadeNaoEncontradaException("Número do cep não foi encontrado");

        ClienteDTO clienteAtualizado = clienteService.update(id,dto.toCliente(cepResultDTO));
        return ResponseEntity.status(204).body(clienteAtualizado);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
