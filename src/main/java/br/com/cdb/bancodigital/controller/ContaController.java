package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.annotations.ValoresPermitidos;
import br.com.cdb.bancodigital.dto.conta.ContaCreateDTO;
import br.com.cdb.bancodigital.dto.conta.ContaDTO;
import br.com.cdb.bancodigital.dto.conta.ContaUpdateSaldoDTO;
import br.com.cdb.bancodigital.service.ContaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;

@RestController
@Validated
@RequestMapping(value = "/conta")
public class ContaController {

    @Autowired
    ContaService service;

    @GetMapping
    public ResponseEntity<Page<ContaDTO>> findAll(
            @RequestParam(value = "numeroPagina", defaultValue = "0") @Min(0) Integer numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "12") @Min(1) Integer tamanhoPagina,
            @RequestParam(value = "ordem", defaultValue = "ASC")
                @ValoresPermitidos(propName = "ordem", values = {"ASC", "DESC"}) String ordem,
            @RequestParam(value = "ordenarPor", defaultValue = "id")
                @ValoresPermitidos(propName = "ordenarPor", values = {"id", "numero", "agencia", "saldo", "tipo"}) String ordenarPor
    ) {
        PageRequest pageRequest = PageRequest.of(
                numeroPagina, tamanhoPagina, Sort.Direction.valueOf(ordem), ordenarPor );

        Page<ContaDTO> lista = service.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContaDTO> findById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }
    @GetMapping(value = "/{id}/mostrarSaldo")
    public ResponseEntity<HashMap<String,BigDecimal>> getSaldo(@PathVariable @Min(1) Long id){
        ContaDTO contaDTO = service.findById(id);

        HashMap<String, BigDecimal> result = new HashMap<String, BigDecimal>();
        result.put("Saldo", contaDTO.getSaldo());
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ContaDTO> create(@Valid @RequestBody ContaCreateDTO dto){
        ContaDTO novaConta = service.create(dto.toConta(), dto.getDonoId());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novaConta.getId()).toUri();

        return ResponseEntity.created(uri).body(novaConta);

    }
    @PutMapping(value = "/{id}/adicionarSaldo")
    public ResponseEntity<HashMap<String,BigDecimal>> addSaldo(@PathVariable @Min(1) Long id,
                                                               @Valid @RequestBody ContaUpdateSaldoDTO dto) {
        ContaDTO contaAtualizada = service.addSaldo(id,dto.getSaldo());

        HashMap<String, BigDecimal> result = new HashMap<String, BigDecimal>();
        result.put("Saldo", contaAtualizada.getSaldo());

        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/{id}/aplicarTaxa")
    public ResponseEntity<HashMap<String,BigDecimal>> applyTaxa(@PathVariable Long id) {
        ContaDTO contaAtualizada = service.applyTaxa(id);

        HashMap<String, BigDecimal> result = new HashMap<String, BigDecimal>();
        result.put("Saldo", contaAtualizada.getSaldo());

        return ResponseEntity.ok(result);
    }


}
