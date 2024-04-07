package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoValorSeguro;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Entity
@Table(name = "apolice")
public class Apolice {

    private static final int NUMERO_LENGTH = 12;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cartao_credito_id", referencedColumnName = "cartao_id")
    private CartaoCredito cartaoCredito;

    @ManyToOne
    @JoinColumn(name = "seguro_id", referencedColumnName = "id")
    private Seguro seguro;

    @Column(nullable = false, unique = true, length = NUMERO_LENGTH)
    private String numero;

    @Column(nullable = false)
    private LocalDate data = LocalDate.now(ZoneId.of("Brazil/East"));

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_valor", nullable = false)
    private TipoValorSeguro tipoValor;

    public Apolice() {}

    public Apolice(CartaoCredito cartaoCredito, Seguro seguro, String numero) {
        this.cartaoCredito = cartaoCredito;
        this.seguro = seguro;
        this.numero = numero;

        TipoCliente tipoCliente = cartaoCredito.getConta().getDono().getTipo();

        List<SeguroValorTipoCliente> valoresSeguro = seguro.getValoresSeguro();
        SeguroValorTipoCliente valorSeguro = valoresSeguro.stream()
                .filter( vs -> vs.getTipoCliente().equals(tipoCliente))
                .findFirst()
                .get();

        BigDecimal valor = valorSeguro.getValor();
        TipoValorSeguro tipoValor = valorSeguro.getTipoValor();

        this.valor = valor;
        this.tipoValor = tipoValor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public Seguro getSeguro() {
        return seguro;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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

    public static int getNumeroLength(){ return NUMERO_LENGTH; }


}
