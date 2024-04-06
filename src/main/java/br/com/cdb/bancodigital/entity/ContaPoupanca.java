package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoConta;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@DiscriminatorValue("POUPANCA")
public class ContaPoupanca extends Conta{
    public ContaPoupanca(){}
    public ContaPoupanca(Long id, String numero, String agencia, BigDecimal saldo, Cliente dono) {
        super(id, numero, agencia, saldo, dono);
    }
    public ContaPoupanca(String agencia, BigDecimal saldo, Cliente dono) {
        super(agencia, saldo, dono);
    }

    public ContaPoupanca(String agencia, BigDecimal saldo) {
        super(agencia, saldo);
    }

    //aplicar taxa de rendimento da conta corrente
    public boolean aplicarTaxa(){
        BigDecimal taxa = getDono().getTipo().getRendimentoConta().getValor();
        BigDecimal rendimento = BigDecimal.ONE.add(
                taxa.divide(new BigDecimal("100.00"), 5, RoundingMode.UP)
        );
        BigDecimal novoSaldo = getSaldo().multiply(rendimento);
        setSaldo(novoSaldo);
        return true;
    }

    @Override
    public TipoConta getTipo() {
        return TipoConta.POUPANCA;
    }
}
