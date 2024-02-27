package br.com.cdb.bancodigital.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "cartao")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_dono", nullable = false)
    private String nomeDono;

    @Column(name = "codigo_seg", nullable = false)
    private int codigoSeguranca;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "taxa_id", nullable = false)
    private TaxaCartao taxa;

    public Cartao(String nomeDono, int codigoSeguranca, String senha, boolean ativo, Conta conta){
        this.setNomeDono(nomeDono);
        this.setCodigoSeguranca(codigoSeguranca);
        this.setSenha(senha);
        this.setAtivo(ativo);
        this.setConta(conta);
    }

    private boolean aplicarTaxaDeUso(BigDecimal valorCompra){
        BigDecimal taxaAbsoluta = taxa.getValor().divide(new BigDecimal("100.00"), 5, RoundingMode.UP);
        BigDecimal pagamentoTaxa  = valorCompra.multiply(taxaAbsoluta).setScale(2, RoundingMode.UP);
        BigDecimal saldoFinal = conta.getSaldo().subtract(pagamentoTaxa);

        if(saldoFinal.compareTo(BigDecimal.ZERO) < 0)
            return false;

        conta.setSaldo(saldoFinal);
        return true;


    }

    public boolean realizarPagamento(BigDecimal valorCompra){
        boolean isTaxaPaga = aplicarTaxaDeUso(valorCompra);
        if(!isTaxaPaga)
            return false;

        BigDecimal saldoFinal = conta.getSaldo().subtract(valorCompra);
        if(saldoFinal.compareTo(BigDecimal.ZERO) < 0)
            return false;

        conta.setSaldo(saldoFinal);
        return true;
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDono() {
        return nomeDono;
    }

    public void setNomeDono(String nomeDono) {
        this.nomeDono = nomeDono;
    }

    public int getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public void setCodigoSeguranca(int codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public TaxaCartao getTaxa() {
        return taxa;
    }

    public void setTaxa(TaxaCartao taxa) {
        this.taxa = taxa;
    }
}
