package br.com.cdb.bancodigital.entity;


import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Entity
@Table(name = "cartao_credito")
@PrimaryKeyJoinColumn(name = "cartao_id")
public class CartaoCredito extends Cartao{

    private static final BigDecimal taxaDeUsoPercentual = BigDecimal.valueOf(5.00);

    @OneToMany(mappedBy = "cartaoCredito")
    private List<Apolice> apolices;

    public CartaoCredito(){}

    public CartaoCredito(String nomeDono, String codigoSeguranca, String senha, YearMonth validade,
                         LocalDate dataCriacao, boolean ativo, Conta conta) {
        super(nomeDono, codigoSeguranca, senha, validade, dataCriacao, ativo, conta);
    }

    public CartaoCredito(String nomeDono, String senha, boolean ativo){
        setNomeDono(nomeDono);
        setSenha(senha);
        setAtivo(ativo);
    }

    public void pagarApolice(Apolice apolice){
        BigDecimal valorApolice = apolice.getValor();
        Conta conta = this.getConta();
        BigDecimal novoSaldo = conta.getSaldo().min(valorApolice).setScale(2,RoundingMode.DOWN);
        conta.setSaldo(novoSaldo);
    }

    public boolean realizarPagamento(BigDecimal valorCompra){
        BigDecimal saldoFinal = getConta().getSaldo().subtract(valorCompra);
        getConta().setSaldo(saldoFinal);
        return true;
    };

    public void aplicarTaxaDeUso(BigDecimal valorPagamentosMes){

        BigDecimal limiteMensal = getConta().getDono().getTipo().getLimiteMensalCartao().getValor();

        //Se a soma de todos os pagamentos do mês for maior que 80% do limiteMensal
        //Será aplicada uma taxa de 5% sobre o valor da soma
        if( valorPagamentosMes.divide(limiteMensal, 2,RoundingMode.UP).compareTo(BigDecimal.valueOf(0.80)) == 1) {

            BigDecimal taxaAbsoluta = taxaDeUsoPercentual.divide(new BigDecimal("100.00"), 5, RoundingMode.UP);
            BigDecimal pagamentoTaxa = valorPagamentosMes.multiply(taxaAbsoluta).setScale(2, RoundingMode.UP);
            BigDecimal saldoFinal = getConta().getSaldo().subtract(pagamentoTaxa);

            getConta().setSaldo(saldoFinal);
        }
    }

    public TipoCartao getTipo(){
        return TipoCartao.CREDITO;
    }

    public BigDecimal getLimite() {
        BigDecimal limiteMensal = getConta().getDono().getTipo().getLimiteMensalCartao().getValor();
        return limiteMensal;
    }

    @Override
    public void setLimite(BigDecimal limite) {
        //limite do cartao de credito não pode ser editado
        //pois ele depende do tipo de cliente do seu dono
        //(COMUM, SUPER, PREMIUM)
    }

    public BigDecimal getLimiteMensal() {
        return getLimite();
    }

    public List<Apolice> getApolices() {
        return apolices;
    }
}
