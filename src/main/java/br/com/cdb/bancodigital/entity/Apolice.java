package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.compositeKey.ApoliceID;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Table(name = "apolice")
@IdClass(ApoliceID.class)
public class Apolice {

    private static final int NUMERO_LENGTH = 12;

    @Id
    @ManyToOne
    @JoinColumn(name = "cartao_credito_id", referencedColumnName = "cartao_id")
    private CartaoCredito cartaoCredito;

    @Id
    @ManyToOne
    @JoinColumn(name = "seguro_id", referencedColumnName = "id")
    private Seguro seguro;

    @Column(nullable = false, unique = true, length = NUMERO_LENGTH)
    private String numero;

    @Column(nullable = false)
    private LocalDate data = LocalDate.now(ZoneId.of("Brazil/East"));

    @Column(nullable = false)
    private BigDecimal valor;

    public Apolice() {}
    public Apolice(CartaoCredito cartaoCredito, Seguro seguro, String numero, BigDecimal valor) {
        this.cartaoCredito = cartaoCredito;
        this.seguro = seguro;
        this.numero = numero;
        this.valor = valor;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public Seguro getSeguro() {
        return seguro;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
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

    public static int getNumeroLength(){ return NUMERO_LENGTH; }
}
