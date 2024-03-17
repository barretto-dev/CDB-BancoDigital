package br.com.cdb.bancodigital.entity;


import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Entity
@Table(name = "cartao_credito")
@PrimaryKeyJoinColumn(name = "cartao_id")
public class CartaoCredito extends Cartao{

    @Column(name = "limite_mensal", nullable = false)
    private BigDecimal limiteMensal = BigDecimal.valueOf(2100.00);

    @OneToMany(mappedBy = "cartaoCredito")
    private List<Apolice> apolices;

    public CartaoCredito(){}

    public CartaoCredito(String nomeDono, String codigoSeguranca, String senha, YearMonth validade,
                         LocalDate dataCriacao, boolean ativo, Conta conta, BigDecimal limiteMensal) {
        super(nomeDono, codigoSeguranca, senha, validade, dataCriacao, ativo, conta);
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

    public List<Apolice> getApolices() {
        return apolices;
    }
}
