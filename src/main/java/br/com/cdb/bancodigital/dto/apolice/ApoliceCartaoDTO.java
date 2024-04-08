package br.com.cdb.bancodigital.dto.apolice;

import br.com.cdb.bancodigital.dto.cartao.CartaoMinDTO;
import br.com.cdb.bancodigital.dto.formatters.LocalDateFormatter;
import br.com.cdb.bancodigital.dto.seguro.SeguroMinDTO;
import br.com.cdb.bancodigital.entity.Apolice;
import br.com.cdb.bancodigital.entity.enums.TipoValorSeguro;

import java.math.BigDecimal;

public class ApoliceCartaoDTO {

    private Long id;

    private String numero;

    private String data;

    private BigDecimal valor;

    private TipoValorSeguro tipoValor;

    private SeguroMinDTO dadosSeguro;

    public ApoliceCartaoDTO(Long id, String numero, String data, BigDecimal valor,
                            TipoValorSeguro tipoValor, SeguroMinDTO dadosSeguro) {
        this.id = id;
        this.numero = numero;
        this.data = data;
        this.valor = valor;
        this.tipoValor = tipoValor;
        this.dadosSeguro = dadosSeguro;
    }

    public ApoliceCartaoDTO(Apolice apolice){
        this.id = apolice.getId();
        this.numero = apolice.getNumero();
        this.data = LocalDateFormatter.formatar(apolice.getData());
        this.valor = apolice.getValor();
        this.tipoValor = apolice.getTipoValor();
        this.dadosSeguro = new SeguroMinDTO(apolice.getSeguro());
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

    public void setData(String data) {
        this.data = data;
    }

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

    public SeguroMinDTO getDadosSeguro() {
        return dadosSeguro;
    }

    public void setDadosSeguro(SeguroMinDTO dadosSeguro) {
        this.dadosSeguro = dadosSeguro;
    }
}
