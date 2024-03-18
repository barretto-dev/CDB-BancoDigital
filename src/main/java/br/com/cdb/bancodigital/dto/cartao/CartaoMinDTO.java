package br.com.cdb.bancodigital.dto.cartao;

import br.com.cdb.bancodigital.dto.formatters.NumeroCartaoFormatter;
import br.com.cdb.bancodigital.dto.formatters.YearMonthFormatter;
import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.Conta;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.YearMonth;

public class CartaoMinDTO {

    private Long id;
    private String numero;

    private String nomeDono;

    private String validade;

    private String codigoSeguranca;

    private String tipo;

    private BigDecimal limite;

    private boolean ativo;

    private Long contaId;

    public CartaoMinDTO(){}

    public CartaoMinDTO(Cartao cartao){
        this.id = cartao.getId();
        this.numero = NumeroCartaoFormatter.formatar(cartao);
        this.nomeDono = cartao.getNomeDono();
        this.validade = YearMonthFormatter.formatar(cartao.getValidade());
        this.codigoSeguranca = cartao.getCodigoSeguranca();
        this.tipo = cartao.getTipo().name();
        this.limite = cartao.getLimite();
        this.ativo = cartao.isAtivo();
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

    public String getValidade() { return validade; }

    public void setValidade(YearMonth validade) { this.validade = YearMonthFormatter.formatar(validade); }

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

    public boolean isAtivo() { return ativo; }

    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }
}
