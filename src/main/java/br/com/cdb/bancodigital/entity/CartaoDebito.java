package br.com.cdb.bancodigital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "cartao_debito")
@PrimaryKeyJoinColumn(name = "cartao_id")
public class CartaoDebito extends Cartao {

    @Column(name = "limite_diario", nullable = false)
    private BigDecimal limiteDiario;

    public CartaoDebito() {}

    public CartaoDebito(String nomeDono, String codigoSeguranca, String senha, boolean ativo,
                        Conta conta, BigDecimal limiteDiario) {
        super(nomeDono, codigoSeguranca, senha, ativo, conta);
        this.limiteDiario = limiteDiario;
    }

    public CartaoDebito(String nomeDono, String senha, boolean ativo){
        setNomeDono(nomeDono);
        setSenha(senha);
        setAtivo(ativo);
    }

    public BigDecimal getLimiteDiario() {
        return limiteDiario;
    }

    public void setLimiteDiario(BigDecimal limiteDiario) {
        this.limiteDiario = limiteDiario;
    }
}
