package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoTaxa;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "taxa")
public class Taxa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoTaxa tipo;
    @Column(name = "valor")
    private BigDecimal valor;

    @OneToMany(mappedBy = "taxa")
    private List<Conta> contas;

    public Taxa() {}
    public Taxa(TipoTaxa tipo, BigDecimal valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTaxa getTipoTaxa() {
        return tipo;
    }

    public void setTipoTaxa(TipoTaxa tipoTaxa) {
        this.tipo = tipoTaxa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
