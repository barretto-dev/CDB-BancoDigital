package br.com.cdb.bancodigital.dto.apolice;

import br.com.cdb.bancodigital.dto.cartao.CartaoMinDTO;
import br.com.cdb.bancodigital.dto.formatters.NumeroApoliceFormatter;
import br.com.cdb.bancodigital.dto.seguro.SeguroDTO;
import br.com.cdb.bancodigital.entity.Apolice;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ApoliceDTO {

    private String numero;

    private LocalDate data;

    private BigDecimal valor;

    private CartaoMinDTO dadosCartao;

    private SeguroDTO dadosSeguro;

    public ApoliceDTO(){}

    public ApoliceDTO(Apolice apolice){
        this.numero = NumeroApoliceFormatter.formatar(apolice);
        this.data = apolice.getData();
        this.valor = apolice.getValor();
        this.dadosCartao = new CartaoMinDTO(apolice.getCartaoCredito());
        this.dadosSeguro = new SeguroDTO(apolice.getSeguro());
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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
