package br.com.cdb.bancodigital.dto.cartao;

public class CartaoUpdateAtivoDTO {

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
