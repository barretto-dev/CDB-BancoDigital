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

    private static final int AGENCIA_LENGHT = 4;

    private static final int NUMERO_LENGHT = 9;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="numero", unique = true, nullable = false, length = NUMERO_LENGHT)
    private String numero;

    @Column(name="agencia", nullable = false, length = AGENCIA_LENGHT)
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

    @OneToMany(mappedBy = "conta")
    private List<TransferenciaPix> transferenciasPix;

    public Conta() {}

    public Conta(Long id, String numero, String agencia, BigDecimal saldo, Cliente dono) {
        this.id = id;
        this.numero = numero;
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

    public boolean transferirPix(BigDecimal valor){
        BigDecimal saldoFinalOrigem = getSaldo().subtract(valor);

        if(saldoFinalOrigem.compareTo(BigDecimal.ZERO) < 0)
            return false;

        setSaldo(saldoFinalOrigem);
        return true;
    }

    public abstract TipoConta getTipo();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() { return numero; }

    public void setNumero(String numero) { this.numero = numero; }

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

    public List<TransferenciaPix> getTransferenciasPix() {
        return transferenciasPix;
    }

    public void setTransferenciasPix(List<TransferenciaPix> transferenciasPix) {
        this.transferenciasPix = transferenciasPix;
    }

    public static int getAgenciaLenght(){  return AGENCIA_LENGHT;};

    public static int getNumeroLenght(){ return NUMERO_LENGHT; };
}
