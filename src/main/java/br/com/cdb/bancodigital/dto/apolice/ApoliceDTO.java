package br.com.cdb.bancodigital.dto.apolice;

import br.com.cdb.bancodigital.dto.cartao.CartaoMinDTO;
import br.com.cdb.bancodigital.dto.formatters.LocalDateFormatter;
import br.com.cdb.bancodigital.dto.formatters.NumeroApoliceFormatter;
import br.com.cdb.bancodigital.dto.seguro.SeguroDTO;
import br.com.cdb.bancodigital.entity.Apolice;
import br.com.cdb.bancodigital.entity.enums.TipoValorSeguro;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ApoliceDTO {

    private Long id;

    private String numero;

    private String data;

    private BigDecimal valor;

    private TipoValorSeguro tipoValor;

    private CartaoMinDTO dadosCartao;

    private SeguroDTO dadosSeguro;

    public ApoliceDTO(){}

    public ApoliceDTO(Apolice apolice){
        this.id = apolice.getId();
        this.numero = NumeroApoliceFormatter.formatar(apolice);
        this.data = LocalDateFormatter.formatar(apolice.getData());
        this.valor = apolice.getValor();
        this.tipoValor = apolice.getTipoValor();
        this.dadosCartao = new CartaoMinDTO(apolice.getCartaoCredito());
        this.dadosSeguro = new SeguroDTO(apolice.getSeguro());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getData() {
        return data;
    }

    public void setData(LocalDate data) {this.data = LocalDateFormatter.formatar(data);}

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoValorSeguro getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(TipoValorSeguro tipoValor) {
        this.tipoValor = tipoValor;
    }

    public CartaoMinDTO getDadosCartao() {
        return dadosCartao;
    }

    public void setDadosCartao(CartaoMinDTO dadosCartao) {
        this.dadosCartao = dadosCartao;
    }

    public SeguroDTO getDadosSeguro() {
        return dadosSeguro;
    }

    public void setDadosSeguro(SeguroDTO dadosSeguro) {
        this.dadosSeguro = dadosSeguro;
    }
}
