package br.com.cdb.bancodigital.dto.cartao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CartaoUpdateAtivoDTO {

    @NotNull(message = "atividade do cartão é obrigatório")
    private boolean ativo;

    @NotBlank(message = "senha é obrigatória")
    @Pattern(regexp="[\\d]{6}", message = "senha deve ter 6 digitos")
    private String senha;

    public CartaoUpdateAtivoDTO(){}

    public CartaoUpdateAtivoDTO(boolean ativo, String senha) {
        this.ativo = ativo;
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
