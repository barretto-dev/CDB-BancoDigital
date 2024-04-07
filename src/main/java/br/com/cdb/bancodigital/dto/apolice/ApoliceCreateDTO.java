package br.com.cdb.bancodigital.dto.apolice;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ApoliceCreateDTO {

    @NotNull(message = "id do cartão é obrigatório")
    private Long cartaoId;

    @NotNull(message = "id do seguro é obrigatório")
    private Long seguroId;

    private ApoliceCreateDTO(){}

    public ApoliceCreateDTO(Long cartaoId, Long seguroId) {
        this.cartaoId = cartaoId;
        this.seguroId = seguroId;
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
}
