package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.dto.cartao.PagamentoDTO;
import br.com.cdb.bancodigital.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pagamento")
public class PagamentoController {

    @Autowired
    PagamentoService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody PagamentoDTO dto){
        boolean isPago = service.create(dto);
        return ResponseEntity.status(204).body("");
    }


}
