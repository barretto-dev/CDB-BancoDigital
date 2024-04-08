package br.com.cdb.bancodigital.dto.seguro;

import br.com.cdb.bancodigital.entity.TipoCliente;
import br.com.cdb.bancodigital.entity.enums.TipoClienteEnum;
import br.com.cdb.bancodigital.entity.enums.TipoValorSeguro;

import java.math.BigDecimal;

public class ValoresSeguroDTO {

    private TipoClienteEnum tipoCliente;

    private BigDecimal valor;

    private TipoValorSeguro tipoValor;


    public ValoresSeguroDTO(){};

    public ValoresSeguroDTO(TipoClienteEnum tipoCliente, BigDecimal valor, TipoValorSeguro tipoValor) {
        this.tipoCliente = tipoCliente;
        this.valor = valor;
        this.tipoValor = tipoValor;
    }

    public TipoClienteEnum getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoClienteEnum tipoCliente) {
        this.tipoCliente = tipoCliente;
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
}
