package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoConta;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Entity
@Table(name = "conta")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 3)
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="agencia", nullable = false)
    private String agencia;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;
    @ManyToOne
    @JoinColumn(name = "taxa_id", nullable = false)
    private TaxaConta taxa;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente dono;

    @OneToMany(mappedBy = "conta")
    private List<Cartao> cartoes;

    public Conta() {}

    public Conta(Long id, String agencia, BigDecimal saldo, Cliente dono) {
        this.id = id;
        this.agencia = agencia;
        this.saldo = saldo.setScale(2,RoundingMode.UP);
        this.dono = dono;
    }

    public Conta(String agencia, BigDecimal saldo, Cliente dono) {
        this.agencia = agencia;
        this.saldo = saldo.setScale(2,RoundingMode.UP);
        this.dono = dono;
    }

    public Conta(String agencia, BigDecimal saldo) {
        this.agencia = agencia;
        this.saldo = saldo.setScale(2,RoundingMode.UP);;
    }

    public boolean transferirPix(Conta destino, BigDecimal valor){
        BigDecimal saldoFinalOrigem = getSaldo().subtract(valor);
        BigDecimal saldoFinalDestino = destino.getSaldo().add(valor);

        if(saldoFinalOrigem.compareTo(BigDecimal.ZERO) < 0)
            return false;

        setSaldo(saldoFinalOrigem);
        destino.setSaldo(saldoFinalDestino);
        return true;
    }

    public abstract TipoConta getTipo();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencia() { return agencia; }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public BigDecimal getSaldo() {
        return saldo.setScale(2, RoundingMode.UP);
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo.setScale(2, RoundingMode.UP);
    }

    public TaxaConta getTaxa() {
        return taxa;
    }

    public void setTaxa(TaxaConta taxa) {
        this.taxa = taxa;
    }

    public Cliente getDono() {
        return dono;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public List<Cartao> getCartoes() { return cartoes; }

    public void setCartoes(List<Cartao> cartoes) { this.cartoes = cartoes; }
}
