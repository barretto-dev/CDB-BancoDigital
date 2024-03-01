package br.com.cdb.bancodigital.dto.cartao;

import java.math.BigDecimal;

public class PagamentoDTO {

    private BigDecimal valor;

    public PagamentoDTO(){}
    public PagamentoDTO(BigDecimal valor){
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
