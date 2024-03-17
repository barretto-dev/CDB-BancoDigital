package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;

@Entity
@Table(name = "cartao_debito")
@PrimaryKeyJoinColumn(name = "cartao_id")
public class CartaoDebito extends Cartao {

    @Column(name = "limite_diario", nullable = false)
    private BigDecimal limiteDiario = BigDecimal.valueOf(600.00);

    public CartaoDebito() {}

    public CartaoDebito(String nomeDono, String codigoSeguranca, YearMonth validade, String senha,
                        LocalDate dataCriacao, boolean ativo, Conta conta, BigDecimal limiteDiario) {
        super(nomeDono, codigoSeguranca, senha, validade, dataCriacao, ativo, conta);
        this.limiteDiario = limiteDiario;
    }

    public CartaoDebito(String nomeDono, String senha, boolean ativo){
        setNomeDono(nomeDono);
        setSenha(senha);
        setAtivo(ativo);
    }

    public TipoCartao getTipo(){
        return TipoCartao.DEBITO;
    }

    public BigDecimal getLimite() {
        return limiteDiario;
    }

    public void setLimite(BigDecimal limite){
        this.limiteDiario = limite.setScale(2, RoundingMode.DOWN);
    }

    public BigDecimal getLimiteDiario() {
        return limiteDiario;
    }

    public void setLimiteDiario(BigDecimal limiteDiario) {
        this.limiteDiario = limiteDiario;
    }
}
