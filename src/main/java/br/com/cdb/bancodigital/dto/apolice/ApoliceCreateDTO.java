package br.com.cdb.bancodigital.dto.apolice;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ApoliceCreateDTO {

    @NotNull(message = "id do cartão é obrigatório")
    private Long cartaoId;

    @NotNull(message = "id do seguro é obrigatório")
    private Long seguroId;

    @NotNull(message = "valor da apolice é obrigatório")
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
