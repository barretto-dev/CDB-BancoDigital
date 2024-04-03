package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.annotations.ValoresPermitidos;
import br.com.cdb.bancodigital.dto.cartao.*;
import br.com.cdb.bancodigital.service.CartaoService;
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
@RequestMapping(value = "/cartao")
public class CartaoController {

    @Autowired
    CartaoService service;

    @PostMapping
    public ResponseEntity<CartaoMinDTO> create(@Valid @RequestBody CartaoCreateDTO dto){
        CartaoMinDTO result = service.create(dto.toCartao(), dto.getContaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CartaoMinDTO> findById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(value = "conta/{contaId}")
    public ResponseEntity<Page<CartaoMinDTO>> findByConta(
            @PathVariable Long contaId,
            @RequestParam(value = "numeroPagina", defaultValue = "0") @Min(0) Integer numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "12") @Min(1) Integer tamanhoPagina,
            @RequestParam(value = "ordem", defaultValue = "ASC")
                @ValoresPermitidos(propName = "ordem", values = {"ASC", "DESC"}) String ordem,
            @RequestParam(value = "ordenarPor", defaultValue = "id")
                @ValoresPermitidos(propName = "ordenarPor", values = {"id", "numero", "nomeDono", "validade", "ativo"}) String ordenarPor
    ){
        PageRequest pageRequest = PageRequest.of(
                numeroPagina, tamanhoPagina, Sort.Direction.valueOf(ordem), ordenarPor );

        return ResponseEntity.ok(service.findByConta(contaId,pageRequest));
    }

    @PutMapping(value = "/{id}/mudarLimite")
    public ResponseEntity<CartaoMinDTO> updateLimite(@PathVariable @Min(1) Long id, @Valid @RequestBody CartaoUpdateLimiteDTO dto){
        return ResponseEntity.ok(service.updateLimite(id, dto.getLimite(), dto.getSenha()));
    }

    @PutMapping(value = "/{id}/mudarSenha")
    public ResponseEntity<String> updateSenha(@PathVariable @Min(1) Long id, @Valid @RequestBody CartaoUpdateSenhaDTO dto){
        service.updateSenha(id, dto.getSenhaAtual(), dto.getSenhaNova());
        return ResponseEntity.ok("");
    }

    @PutMapping(value = "/{id}/mudarAtividade")
    public ResponseEntity<CartaoMinDTO> updateAtivo(@PathVariable @Min(1) Long id, @Valid @RequestBody CartaoUpdateAtivoDTO dto){
        return ResponseEntity.ok(service.updateAtividade(id, dto.isAtivo(), dto.getSenha()));
    }
}
