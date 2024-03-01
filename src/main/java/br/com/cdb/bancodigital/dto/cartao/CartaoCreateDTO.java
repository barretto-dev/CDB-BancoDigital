package br.com.cdb.bancodigital.dto.cartao;

import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.CartaoDebito;
import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import jakarta.validation.constraints.NotEmpty;

public class CartaoCreateDTO {

    private String nomeDono;

    private String senha;

    private Long contaId;

    @NotEmpty
    private TipoCartao tipoCartao;

    public CartaoCreateDTO(){}

    public CartaoCreateDTO(String nomeDono, String senha, Long contaId, TipoCartao tipoCartao) {
        this.nomeDono = nomeDono;
        this.senha = senha;
        this.contaId = contaId;
        this.tipoCartao = tipoCartao;
    }

    public Cartao toCartao(){
        if(tipoCartao.equals(TipoCartao.DEBITO))
            return new CartaoDebito(nomeDono,senha,true);
        else
            return new CartaoCredito(nomeDono,senha,true);
    }

    public String getNomeDono() {
        return nomeDono;
    }

    public void setNomeDono(String nomeDono) {
        this.nomeDono = nomeDono;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }

    public TipoCartao getTipoCartao() {
        return tipoCartao;
    }

    public void setTipoCartao(TipoCartao tipoCartao) {
        this.tipoCartao = tipoCartao;
    }
}
