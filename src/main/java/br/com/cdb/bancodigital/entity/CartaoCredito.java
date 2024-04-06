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

    @OneToMany(mappedBy = "cartaoCredito")
    private List<Apolice> apolices;

    public CartaoCredito(){}

    public CartaoCredito(String nomeDono, String codigoSeguranca, String senha, YearMonth validade,
                         LocalDate dataCriacao, boolean ativo, Conta conta) {
        super(nomeDono, codigoSeguranca, senha, validade, dataCriacao, ativo, conta);
    }

    public void pagarApolice(Apolice apolice){
        BigDecimal valorApolice = apolice.getValor();
        Conta conta = this.getConta();
        BigDecimal novoSaldo = conta.getSaldo().min(valorApolice).setScale(2,RoundingMode.DOWN);
        conta.setSaldo(novoSaldo);
    }

    public CartaoCredito(String nomeDono, String senha, boolean ativo){
        setNomeDono(nomeDono);
        setSenha(senha);
        setAtivo(ativo);
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
