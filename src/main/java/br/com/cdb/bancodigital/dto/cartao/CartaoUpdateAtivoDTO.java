package br.com.cdb.bancodigital.dto.cartao;

import jakarta.validation.constraints.NotNull;

public class CartaoUpdateAtivoDTO {

    @NotNull(message = "atividade do cartão é obrigatório")
    private boolean ativo;

    public CartaoUpdateAtivoDTO(){}

    public CartaoUpdateAtivoDTO(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
