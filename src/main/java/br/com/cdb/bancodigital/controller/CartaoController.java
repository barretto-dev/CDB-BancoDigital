package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.dto.cartao.*;
import br.com.cdb.bancodigital.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cartao")
public class CartaoController {

    @Autowired
    CartaoService service;

    @PostMapping
    public ResponseEntity<CartaoMinDTO> create(@RequestBody CartaoCreateDTO dto){
        CartaoMinDTO result = service.create(dto.toCartao(), dto.getContaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CartaoMinDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping(value = "/{id}/mudarLimite")
    public ResponseEntity<CartaoMinDTO> updateLimite(@PathVariable Long id, @RequestBody CartaoUpdateLimiteDTO dto){
        return ResponseEntity.ok(service.updateLimite(id, dto.getLimite()));
    }

    @PutMapping(value = "/{id}/mudarSenha")
    public ResponseEntity<String> updateSenha(@PathVariable Long id, @RequestBody CartaoUpdateSenhaDTO dto){
        service.updateSenha(id, dto.getSenhaAtual(), dto.getSenhaNova());
        return ResponseEntity.ok("");
    }

    @PutMapping(value = "/{id}/mudarAtividade")
    public ResponseEntity<CartaoMinDTO> updateLimite(@PathVariable Long id, @RequestBody CartaoUpdateAtivoDTO dto){
        return ResponseEntity.ok(service.updateAtividade(id, dto.isAtivo()));
    }
}
