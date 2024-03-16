package br.com.cdb.bancodigital.dto.apolice;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ApoliceCreateDTO {

    private Long cartaoId;

    private Long seguroId;

    private BigDecimal valor;

    private ApoliceCreateDTO(){}

    public ApoliceCreateDTO(Long cartaoId, Long seguroId, BigDecimal valor) {
        this.cartaoId = cartaoId;
        this.seguroId = seguroId;
        this.valor = valor.setScale(2, RoundingMode.DOWN);
    }

    public Long getCartaoId() {
        return cartaoId;
    }

    public void setCartaoId(Long cartaoId) {
        this.cartaoId = cartaoId;
    }

    public Long getSeguroId() {
        return seguroId;
    }

    public void setSeguroId(Long seguroId) {
        this.seguroId = seguroId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
