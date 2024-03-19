package br.com.cdb.bancodigital.dto.conta;

import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.entity.ContaCorrente;
import br.com.cdb.bancodigital.entity.ContaPoupanca;
import br.com.cdb.bancodigital.entity.enums.TipoConta;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ContaCreateDTO {

    private String agencia;
    private BigDecimal saldo;
    @NotNull
    private TipoConta tipo;
    private Long donoId;

    public ContaCreateDTO (BigDecimal saldo, TipoConta tipo, Long donoId){
        this.saldo = saldo.setScale(2, RoundingMode.UP);
        this.tipo = tipo;
        this.donoId = donoId;
    }

    public Conta toConta(){
        if(tipo.equals(TipoConta.POUPANCA)){
            return new ContaPoupanca(agencia,saldo);
        }
        return new ContaCorrente(agencia,saldo);
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public Long getDonoId() {
        return donoId;
    }

    public void setDonoId(Long donoId) {
        this.donoId = donoId;
    }
}
