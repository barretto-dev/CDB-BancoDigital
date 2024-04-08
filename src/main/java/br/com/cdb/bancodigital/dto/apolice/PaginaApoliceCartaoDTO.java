package br.com.cdb.bancodigital.dto.apolice;

import br.com.cdb.bancodigital.dto.cartao.CartaoMinDTO;
import br.com.cdb.bancodigital.entity.Apolice;
import br.com.cdb.bancodigital.entity.Cartao;
import org.springframework.data.domain.Page;

public class PaginaApoliceCartaoDTO {

    private CartaoMinDTO dadosCartao;

    private Page<ApoliceCartaoDTO> pagina;

    public PaginaApoliceCartaoDTO(){};

    public PaginaApoliceCartaoDTO(Cartao cartao, Page<Apolice> paginaApolice ) {
        this.dadosCartao = new CartaoMinDTO(cartao);
        this.pagina = paginaApolice.map(apolice -> new ApoliceCartaoDTO(apolice));
    }

    public CartaoMinDTO getDadosCartao() {
        return dadosCartao;
    }

    public void setDadosCartao(CartaoMinDTO dadosCartao) {
        this.dadosCartao = dadosCartao;
    }

    public Page<ApoliceCartaoDTO> getPagina() {
        return pagina;
    }

    public void setPagina(Page<ApoliceCartaoDTO> pagina) {
        this.pagina = pagina;
    }
}
