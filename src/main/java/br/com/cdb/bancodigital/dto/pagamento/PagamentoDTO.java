package br.com.cdb.bancodigital.dto.pagamento;

import java.math.BigDecimal;

public class PagamentoDTO {

    private String destinatario;
    private BigDecimal valor;
    private Integer qtdParcelas;
    private Long cartaoId;

    public PagamentoDTO(){}

    public PagamentoDTO(String destinatario, BigDecimal valor, Integer qtdParcelas, Long cartaoId) {
        this.destinatario = destinatario;
        this.valor = valor;
        this.qtdParcelas = qtdParcelas;
        this.cartaoId = cartaoId;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(Integer qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    public Long getCartaoId() {
        return cartaoId;
    }

    public void setCartaoId(Long cartaoId) {
        this.cartaoId = cartaoId;
    }
}
