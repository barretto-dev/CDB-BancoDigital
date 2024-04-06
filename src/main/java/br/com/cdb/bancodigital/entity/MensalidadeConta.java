package br.com.cdb.bancodigital.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "mensalidade_conta")
public class MensalidadeConta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="nome", unique = true, nullable = false)
    private String nome;

    @Column(name="valor", unique = true, nullable = false)
    private BigDecimal valor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_cliente_id", referencedColumnName = "id", unique = true)
    TipoCliente tipoCliente;

    public MensalidadeConta(){}

    public MensalidadeConta(String nome, BigDecimal valor, TipoCliente tipoCliente) {
        this.nome = nome;
        this.valor = valor;
        this.tipoCliente = tipoCliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}
