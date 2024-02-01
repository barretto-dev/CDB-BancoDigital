package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoConta;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@DiscriminatorValue("COR")
public class ContaCorrente extends Conta{

    private final BigDecimal TAXA_MENSAL = new BigDecimal("31.25");
    public boolean pagarTaxaMensal(){
        BigDecimal saldoFinal = getSaldo().subtract(TAXA_MENSAL);
        if(saldoFinal.compareTo(BigDecimal.ZERO) < 0)
            return false;

        setSaldo(saldoFinal.setScale(2, RoundingMode.UP));
        return true;
    }

    public ContaCorrente(BigDecimal saldo) {
        super(saldo);
    }

    @Override
    public TipoConta getTipo() {
        return TipoConta.CORRENTE;
    }
}
