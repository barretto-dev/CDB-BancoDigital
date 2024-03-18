package br.com.cdb.bancodigital.dto.cartao;

public class CartaoUpdateSenhaDTO {

    private String senhaAtual;

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
