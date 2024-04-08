package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.converter.YearMonthDateAttributeConverter;
import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cartao")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Cartao {

    private static final int NUMERO_LENGTH = 16;
    private static final int CODIGO_SEGURANÇA_LENGTH = 3;

    private static final int VALIDADE_EM_ANOS = 5;

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

    @Column(name = "validade", nullable = false, columnDefinition = "date")
    @Convert(converter = YearMonthDateAttributeConverter.class)
    private YearMonth validade;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @OneToMany(mappedBy = "cartao")
    private List<Pagamento> pagamentos;

    public Cartao(){}

    public Cartao(String nomeDono, String codigoSeguranca, String senha, YearMonth validade,
                  LocalDate dataCriacao, boolean ativo, Conta conta){
        this.setNomeDono(nomeDono);
        this.setCodigoSeguranca(codigoSeguranca);
        this.setSenha(senha);
        this.setDataCriacao(dataCriacao);
        this.setValidade(validade);
        this.setAtivo(ativo);
        this.setConta(conta);
    }

    public abstract boolean realizarPagamento(BigDecimal valorCompra);

    public boolean isValido(){
        if (YearMonth.now(ZoneId.of("Brazil/East")).compareTo(this.validade) > 0)
            return false;

        return true;
    }

    /*
    Retorna uma lista contendo o inicio e o fim do periodo de pagamento do cartão

    EX:
        SE o cartão foi cadastrado em 12-02-2024 então:
            1° mes -> 12-02-2024 até 12-03-2024
            2° mes -> 13-03-2024 até 11-04-2024
    */
    public List<LocalDate> getPeriodoPagamentoAtual(){
        LocalDate dataCriacao = this.dataCriacao;

        LocalDate dataAtual = LocalDate.now(ZoneId.of("Brazil/East"));
        Long quantidadeDias = ChronoUnit.DAYS.between( dataCriacao, dataAtual ) + 1;

        LocalDate dataInicio;
        LocalDate dataFim;

        if(quantidadeDias <= 30){
            dataInicio = dataCriacao;
            dataFim = dataInicio.plusDays(29);
        }else if(quantidadeDias % 30 == 0){
            dataFim = dataAtual;
            dataInicio = dataFim.minusDays(29);
        }else {
            //Diz em qual posição a data atual está em relação ao mês do cartao
            long posicaoNoMes = quantidadeDias % 30;

            dataInicio = dataAtual.minusDays(posicaoNoMes - 1);
            dataFim = dataInicio.plusDays(29);
        }


        List<LocalDate> result = new ArrayList<>();
        result.add(dataInicio);
        result.add(dataFim);
        return  result;
    }

    public abstract TipoCartao getTipo();

    public abstract BigDecimal getLimite();

    public abstract void setLimite(BigDecimal limite);

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

    public YearMonth getValidade() { return validade; }

    public void setValidade(YearMonth validade) { this.validade = validade;}

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

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public static int getNumeroLength(){ return NUMERO_LENGTH; }

    public static int getCodigoSegurancaLength(){ return CODIGO_SEGURANÇA_LENGTH; }

    public static int getValidadeEmAnos(){ return VALIDADE_EM_ANOS; };

}
