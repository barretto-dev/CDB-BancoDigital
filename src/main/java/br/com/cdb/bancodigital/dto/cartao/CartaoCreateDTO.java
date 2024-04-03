package br.com.cdb.bancodigital.dto.cartao;

import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.CartaoDebito;
import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CartaoCreateDTO {

    @NotBlank(message = "nome do dono do cartão é obrigatório")
    private String nomeDono;

    @NotBlank(message = "senha do cartao é obrigatória")
    @Pattern(regexp="[\\d]{6}", message = "senha deve ter 6 digitos")
    private String senha;

    @NotNull(message = "id da conta do cartão é obrigatório")
    private Long contaId;

    @NotNull(message = "tipo do cartao é obrigatorio")
    private TipoCartao tipo;

    public CartaoCreateDTO(){}

    public CartaoCreateDTO(String nomeDono, String senha, Long contaId, TipoCartao tipo) {
        this.nomeDono = nomeDono;
        this.senha = senha;
        this.contaId = contaId;
        this.tipo = tipo;
    }

    public Cartao toCartao(){
        if(tipo.equals(TipoCartao.DEBITO))
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

    public TipoCartao getTipo() {
        return tipo;
    }

    public void setTipo(TipoCartao tipo) {
        this.tipo = tipo;
    }
}
