package br.com.cdb.bancodigital.entity.compositeKey;

import java.util.Objects;

public class SeguroValorTipoClienteID {

    private Long seguro;

    private Long tipoCliente;

    public SeguroValorTipoClienteID(){}

    public SeguroValorTipoClienteID(Long seguro, Long tipoCliente) {
        this.seguro = seguro;
        this.tipoCliente = tipoCliente;
    }

    public Long getSeguro() {
        return seguro;
    }

    public void setSeguro(Long seguro) {
        this.seguro = seguro;
    }

    public Long getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(Long tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SeguroValorTipoClienteID that = (SeguroValorTipoClienteID) object;
        return Objects.equals(seguro, that.seguro) && Objects.equals(tipoCliente, that.tipoCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seguro, tipoCliente);
    }
}
