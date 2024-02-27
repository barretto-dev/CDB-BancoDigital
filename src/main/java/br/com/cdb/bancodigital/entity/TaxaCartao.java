package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoTaxaCartao;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

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

    @OneToMany(mappedBy = "taxa")
    private List<Cartao> cartoes;

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

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }
}
