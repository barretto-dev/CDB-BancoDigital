package br.com.cdb.bancodigital.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cartao")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Cartao {

    private static final int NUMERO_LENGTH = 16;
    private static final int CODIGO_SEGURANÇA_LENGTH = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero", nullable = false, unique = true, length = NUMERO_LENGTH)
    private String numero;

    @Column(name = "nome_dono", nullable = false)
    private String nomeDono;

    @Column(name = "codigo_seg", nullable = false, length = 3)
    private String codigoSeguranca;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "taxa_id", nullable = false)
    private TaxaCartao taxa;

    @OneToMany(mappedBy = "cartao")
    private List<Pagamento> pagamentos;

    public Cartao(){}

    public Cartao(String nomeDono, String codigoSeguranca, String senha, LocalDate dataCriacao, boolean ativo, Conta conta){
        this.setNomeDono(nomeDono);
        this.setCodigoSeguranca(codigoSeguranca);
        this.setSenha(senha);
        this.setDataCriacao(dataCriacao);
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomeDono() {
        return nomeDono;
    }

    public void setNomeDono(String nomeDono) {
        this.nomeDono = nomeDono;
    }

    public String getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public void setCodigoSeguranca(String codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
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

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public static int getNumeroLength(){ return NUMERO_LENGTH; }

    public static int getCodigoSegurancaLength(){ return CODIGO_SEGURANÇA_LENGTH; }
}
