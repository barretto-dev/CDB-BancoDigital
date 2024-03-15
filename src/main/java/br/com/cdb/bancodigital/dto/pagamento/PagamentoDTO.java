package br.com.cdb.bancodigital.dto.pagamento;

import br.com.cdb.bancodigital.entity.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class PagamentoDTO {

    private String remetente;
    private BigDecimal valor;
    private Integer qtdParcelas;
    private Long cartaoId;

    public PagamentoDTO(){}

    public PagamentoDTO(String remetente, BigDecimal valor, Integer qtdParcelas, Long cartaoId) {
        this.remetente = remetente;
        this.valor = valor;
        this.qtdParcelas = qtdParcelas;
        this.cartaoId = cartaoId;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
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
