package br.com.cdb.bancodigital.dto.cartao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartaoUpdateLimiteDTO {

    @NotNull(message = "limite do cartão é obrigatório")
    @Min(1)
    private BigDecimal limite;

    @NotBlank(message = "senha é obrigatória")
    @Pattern(regexp="[\\d]{6}", message = "senha deve ter 6 digitos")
    private String senha;

    public CartaoUpdateLimiteDTO(){}

    public CartaoUpdateLimiteDTO(BigDecimal limite, String senha){
        this.limite = limite.setScale( 2, RoundingMode.DOWN);
        this.senha = senha;
    }

    public BigDecimal getLimite() { return limite; }

    public void setLimite(BigDecimal limite) { this.limite = limite.setScale( 2, RoundingMode.DOWN); }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
