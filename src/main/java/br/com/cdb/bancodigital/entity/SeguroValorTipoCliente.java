package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.compositeKey.SeguroValorTipoClienteID;
import br.com.cdb.bancodigital.entity.enums.TipoValorSeguro;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "seguro_valor_tipo_cliente")
@IdClass(SeguroValorTipoClienteID.class)
public class SeguroValorTipoCliente {

    @Id
    @ManyToOne
    @JoinColumn(name = "seguro_id", referencedColumnName = "id")
    private Seguro seguro;

    @Id
    @ManyToOne
    @JoinColumn(name = "tipo_cliente_id", referencedColumnName = "id")
    private TipoCliente tipoCliente;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_valor", nullable = false)
    private TipoValorSeguro tipoValor;


    public SeguroValorTipoCliente(){}

    public SeguroValorTipoCliente(Seguro seguro, TipoCliente tipoCliente, BigDecimal valor, TipoValorSeguro tipoValor) {
        this.seguro = seguro;
        this.tipoCliente = tipoCliente;
        this.valor = valor;
        this.tipoValor = tipoValor;
    }

    public Seguro getSeguro() {
        return seguro;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
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
