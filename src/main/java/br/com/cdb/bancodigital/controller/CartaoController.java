package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.dto.cartao.CartaoCreateDTO;
import br.com.cdb.bancodigital.dto.cartao.CartaoMinDTO;
import br.com.cdb.bancodigital.dto.cartao.CartaoUpdateLimiteDTO;
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
    public ResponseEntity<CartaoMinDTO> create(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CartaoMinDTO> updateLimite(@PathVariable Long id, @RequestBody CartaoUpdateLimiteDTO dto){
        return ResponseEntity.ok(service.updateLimite(id, dto.getLimite()));
    }
}
