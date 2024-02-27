package br.com.cdb.bancodigital.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "cartao_credito")
@PrimaryKeyJoinColumn(name = "cartao_id")
public class CartaoCredito extends Cartao{

    @Column(name = "limite_mensal", nullable = false)
    private BigDecimal limiteMensal;

    public CartaoCredito(String nomeDono, int codigoSeguranca, String senha, boolean ativo,
                         Conta conta, BigDecimal limiteMensal) {
        super(nomeDono, codigoSeguranca, senha, ativo, conta);
        this.limiteMensal = limiteMensal;
    }

    public BigDecimal getLimiteMensal() {
        return limiteMensal;
    }

    public void setLimiteMensal(BigDecimal limiteMensal) {
        this.limiteMensal = limiteMensal;
    }
}
