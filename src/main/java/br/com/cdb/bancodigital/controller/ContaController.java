package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.dto.cliente.ClienteDTO;
import br.com.cdb.bancodigital.dto.conta.ContaCreateDTO;
import br.com.cdb.bancodigital.dto.conta.ContaDTO;
import br.com.cdb.bancodigital.dto.conta.ContaUpdateSaldoDTO;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.service.ClienteService;
import br.com.cdb.bancodigital.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;

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
    @GetMapping(value = "/{id}/mostrarSaldo")
    public ResponseEntity<HashMap<String,BigDecimal>> getSaldo(@PathVariable Long id){
        ContaDTO contaDTO = service.findById(id);

        HashMap<String, BigDecimal> result = new HashMap<String, BigDecimal>();
        result.put("Saldo", contaDTO.getSaldo());
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ContaDTO> create(@RequestBody ContaCreateDTO dto){
        ContaDTO novaConta = service.create(dto.toConta(), dto.getDonoId());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novaConta.getId()).toUri();

        return ResponseEntity.created(uri).body(novaConta);

    }
    @PutMapping(value = "/{id}/adicionarSaldo")
    public ResponseEntity<HashMap<String,BigDecimal>> addSaldo(@PathVariable Long id, @RequestBody ContaUpdateSaldoDTO dto) {
        ContaDTO contaAtualizada = service.addSaldo(id,dto.getSaldo());

        HashMap<String, BigDecimal> result = new HashMap<String, BigDecimal>();
        result.put("Saldo", contaAtualizada.getSaldo());

        return ResponseEntity.ok(result);
    }


}
