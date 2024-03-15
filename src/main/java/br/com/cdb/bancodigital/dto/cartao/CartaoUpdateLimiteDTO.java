package br.com.cdb.bancodigital.dto.cartao;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartaoUpdateLimiteDTO {

    private BigDecimal limite;

    public CartaoUpdateLimiteDTO(){}

    public CartaoUpdateLimiteDTO(BigDecimal limite){
        this.limite = limite.setScale( 2, RoundingMode.DOWN);
    }

    public BigDecimal getLimite() { return limite; }

    public void setLimite(BigDecimal limite) { this.limite = limite.setScale( 2, RoundingMode.DOWN); }
}
