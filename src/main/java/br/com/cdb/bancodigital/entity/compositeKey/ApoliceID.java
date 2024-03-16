package br.com.cdb.bancodigital.entity.compositeKey;

import java.io.Serializable;
import java.util.Objects;

public class ApoliceID implements Serializable {

    private Long cartaoCredito;

    private Long seguro;

    public ApoliceID() {}
    public ApoliceID(Long cartaoCredito, Long seguro) {
        this.cartaoCredito = cartaoCredito;
        this.seguro = seguro;
    }

    public Long getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCreditoId(Long cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public Long getSeguro() {
        return seguro;
    }

    public void setSeguro(Long seguro) {
        this.seguro = seguro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApoliceID apoliceID = (ApoliceID) o;
        return Objects.equals(cartaoCredito, apoliceID.cartaoCredito) && Objects.equals(seguro, apoliceID.seguro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartaoCredito, seguro);
    }
}
