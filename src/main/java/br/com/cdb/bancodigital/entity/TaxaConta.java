package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoTaxaConta;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "taxa_conta")
public class TaxaConta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoTaxaConta tipo;
    @Column(name = "valor")
    private BigDecimal valor;

    @OneToMany(mappedBy = "taxa")
    private List<Conta> contas;

    public TaxaConta() {}
    public TaxaConta(TipoTaxaConta tipo, BigDecimal valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTaxaConta getTipoTaxa() {
        return tipo;
    }

    public void setTipoTaxa(TipoTaxaConta tipoTaxaConta) {
        this.tipo = tipoTaxaConta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
