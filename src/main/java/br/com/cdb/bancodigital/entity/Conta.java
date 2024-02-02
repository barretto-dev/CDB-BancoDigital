package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoConta;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "conta")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "saldo")
    private BigDecimal saldo;

    /*@ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente dono;*/

    public Conta() {}

    public Conta(Long id, BigDecimal saldo) {
        this.id = id;
        this.saldo = saldo.setScale(2,RoundingMode.UP);;
    }
    public Conta(BigDecimal saldo) {
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

    public BigDecimal getSaldo() {
        return saldo.setScale(2, RoundingMode.UP);
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo.setScale(2, RoundingMode.UP);
    }
}
