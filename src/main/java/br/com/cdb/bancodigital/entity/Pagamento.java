package br.com.cdb.bancodigital.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "destinatario", nullable = false)
    private String destinatario;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "parcela_atual")
    private Integer parcelaAtual;

    @Column(name = "parcela_total")
    private Integer parcelaTotal;

    @Column(name = "data_pag", nullable = false)
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "cartao_id", nullable = false)
    private Cartao cartao;

    public Pagamento() {}

    public Pagamento(String destinatario, BigDecimal valor, Integer parcelaAtual,
                     Integer parcelaTotal, LocalDateTime data, Cartao cartao) {
        this.destinatario = destinatario;
        this.valor = valor;
        this.parcelaAtual = parcelaAtual;
        this.parcelaTotal = parcelaTotal;
        this.data = data;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getParcelaAtual() {
        return parcelaAtual;
    }

    public void setParcelaAtual(Integer parcelaAtual) {
        this.parcelaAtual = parcelaAtual;
    }

    public Integer getParcelaTotal() {
        return parcelaTotal;
    }

    public void setParcelaTotal(Integer parcelaTotal) {
        this.parcelaTotal = parcelaTotal;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
