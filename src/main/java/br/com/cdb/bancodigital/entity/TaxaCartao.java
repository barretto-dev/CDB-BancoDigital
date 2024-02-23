package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoTaxaCartao;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "taxa_cartao")
public class TaxaCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoTaxaCartao tipo;

    @Column(name = "valor")
    private BigDecimal valor;

    public TaxaCartao(){}
    public TaxaCartao(TipoTaxaCartao tipo, BigDecimal valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTaxaCartao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTaxaCartao tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
