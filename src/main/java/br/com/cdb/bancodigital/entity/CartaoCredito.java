package br.com.cdb.bancodigital.entity;


import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Table(name = "cartao_credito")
@PrimaryKeyJoinColumn(name = "cartao_id")
public class CartaoCredito extends Cartao{

    @Column(name = "limite_mensal", nullable = false)
    private BigDecimal limiteMensal = BigDecimal.valueOf(2100.00);

    public CartaoCredito(){}

    public CartaoCredito(String nomeDono, String codigoSeguranca, String senha, LocalDate dataCriacao,
                         boolean ativo, Conta conta, BigDecimal limiteMensal) {
        super(nomeDono, codigoSeguranca, senha, dataCriacao, ativo, conta);
        this.limiteMensal = limiteMensal;
    }

    public CartaoCredito(String nomeDono, String senha, boolean ativo){
        setNomeDono(nomeDono);
        setSenha(senha);
        setAtivo(ativo);
    }

    public TipoCartao getTipo(){
        return TipoCartao.CREDITO;
    }

    public BigDecimal getLimite() {
        return limiteMensal;
    }

    public void setLimite(BigDecimal limiteMensal){
        this.limiteMensal = limiteMensal.setScale(2, RoundingMode.DOWN);
    }

    public BigDecimal getLimiteMensal() {
        return limiteMensal;
    }

    public void setLimiteMensal(BigDecimal limite) {
        this.limiteMensal = limite.setScale(2, RoundingMode.DOWN);
    }
}
