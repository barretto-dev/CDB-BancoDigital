package br.com.cdb.bancodigital.dto.cartao;

import br.com.cdb.bancodigital.dto.formatters.NumeroCartaoFormatter;
import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.Conta;
import jakarta.persistence.Column;

import java.math.BigDecimal;

public class CartaoMinDTO {

    private Long id;
    private String numero;

    private String nomeDono;

    private String codigoSeguranca;

    private String tipo;

    private BigDecimal limite;

    private Long contaId;

    public CartaoMinDTO(){}

    public CartaoMinDTO(Cartao cartao){
        this.id = cartao.getId();
        this.numero = NumeroCartaoFormatter.formatar(cartao);
        this.nomeDono = cartao.getNomeDono();
        this.codigoSeguranca = cartao.getCodigoSeguranca();
        this.tipo = cartao.getTipo().name();
        this.limite = cartao.getLimite();
        this.contaId = cartao.getConta().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomeDono() {
        return nomeDono;
    }

    public void setNomeDono(String nomeDono) {
        this.nomeDono = nomeDono;
    }

    public String getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public void setCodigoSeguranca(String codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }
}
