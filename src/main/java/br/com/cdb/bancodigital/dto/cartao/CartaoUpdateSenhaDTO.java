package br.com.cdb.bancodigital.dto.cartao;

import jakarta.validation.constraints.NotBlank;

public class CartaoUpdateSenhaDTO {

    @NotBlank(message = "senha atual é obrigatória")
    private String senhaAtual;

    @NotBlank(message = "senha nova é obrigatória")
    private String senhaNova;

    public CartaoUpdateSenhaDTO(){}

    public CartaoUpdateSenhaDTO(String senhaAtual, String senhaNova) {
        this.senhaAtual = senhaAtual;
        this.senhaNova = senhaNova;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }
}
