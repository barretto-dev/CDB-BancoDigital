package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.annotations.ValoresPermitidos;
import br.com.cdb.bancodigital.dto.cartao.CartaoMinDTO;
import br.com.cdb.bancodigital.dto.pagamento.PagamentoCreateDTO;
import br.com.cdb.bancodigital.dto.pagamento.PagamentoDTO;
import br.com.cdb.bancodigital.dto.pagamento.PaginaPagamentoDTO;
import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.service.CartaoService;
import br.com.cdb.bancodigital.service.PagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/pagamento")
public class PagamentoController {

    @Autowired
    PagamentoService service;

    @Autowired
    CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody PagamentoCreateDTO dto){
        boolean isPago = service.create(dto);
        return ResponseEntity.status(204).body("");
    }


    @GetMapping(value = "/cartao/{cartaoId}")
    public ResponseEntity<PaginaPagamentoDTO> findAllByCartaoId(
            @PathVariable @Min(1) Long cartaoId,
            @RequestParam(value = "numeroPagina", defaultValue = "0") @Min(0) Integer numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "12") @Min(1) Integer tamanhoPagina,
            @RequestParam(value = "ordem", defaultValue = "DESC")
                @ValoresPermitidos(propName = "ordem", values = {"ASC", "DESC"}) String ordem,
            @RequestParam(value = "ordenarPor", defaultValue = "data_pagamento")
                @ValoresPermitidos(propName = "ordenarPor", values = {"id", "destinatario", "valor", "data_pagamento"})String ordenarPor,
            @RequestParam(value = "dataInicio", defaultValue = "1111-01-11") LocalDate dataInicio,
            @RequestParam(value = "dataFinal", defaultValue = "1111-01-11") LocalDate dataFinal
    ){
        CartaoMinDTO dto = cartaoService.findById(cartaoId);
        Cartao cartao = new CartaoCredito();
        cartao.setDataCriacao(dto.getDataCriacao());

        //Lista que contem o inicio e o final do mês do cartao
        List<LocalDate> inicioFimMesCartao = cartao.getDataInicioFimMesCartao();

        if(dataInicio.equals(LocalDate.parse("1111-01-11")) && dataFinal.equals(LocalDate.parse("1111-01-11"))){
            dataInicio = inicioFimMesCartao.get(0);
            dataFinal = inicioFimMesCartao.get(1);
        }

        PageRequest pageRequest = PageRequest.of(
                numeroPagina, tamanhoPagina, Sort.Direction.valueOf(ordem), ordenarPor );

        Page<PagamentoDTO> pagina = service.findAllByCartaoId(cartaoId, dataInicio, dataFinal, pageRequest);

        return ResponseEntity.ok(new PaginaPagamentoDTO(dataInicio,dataFinal,pagina));
    }


}
