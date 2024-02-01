package br.com.cdb.bancodigital.dto.conta;

import br.com.cdb.bancodigital.entity.Conta;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ContaDTO {

    private Long id;
    private BigDecimal saldo;
    private String tipo;

    public ContaDTO (Conta conta){
        this.id = conta.getId();
        this.saldo = conta.getSaldo();
        this.tipo = conta.getTipo().getDescricao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo.setScale(2, RoundingMode.UP);
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo.setScale(2, RoundingMode.UP);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
