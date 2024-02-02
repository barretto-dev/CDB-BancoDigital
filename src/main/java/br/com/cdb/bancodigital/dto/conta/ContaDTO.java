package br.com.cdb.bancodigital.dto.conta;

import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.entity.ContaCorrente;
import br.com.cdb.bancodigital.entity.ContaPoupanca;
import br.com.cdb.bancodigital.entity.enums.TipoConta;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ContaDTO {

    private Long id;
    private BigDecimal saldo;
    @NotNull
    private TipoConta tipo;

    public ContaDTO (Long id, BigDecimal saldo, TipoConta tipo){
        this.id = id;
        this.saldo = saldo.setScale(2,RoundingMode.UP);
        this.tipo = tipo;
    }
    public ContaDTO (Conta conta){
        this.id = conta.getId();
        this.saldo = conta.getSaldo();
        this.tipo = conta.getTipo();
    }

    public Conta toConta(){
        if(tipo.equals(TipoConta.POUPANCA)){
            return new ContaPoupanca(saldo);
        }
        return new ContaCorrente(saldo);
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

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }
}
