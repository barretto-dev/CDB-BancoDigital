package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoConta;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@DiscriminatorValue("POU")
public class ContaPoupanca extends Conta{

    private final BigDecimal TAXA_RENDIMENTO = new BigDecimal("11.25");;
    public void aplicarRendimento(BigDecimal taxaRendimento){
        BigDecimal taxa = BigDecimal.ONE.add(
                TAXA_RENDIMENTO.divide(new BigDecimal("100.00"), 5, RoundingMode.UP)
        );
        BigDecimal novoSaldo = getSaldo().multiply(taxa);
        setSaldo(novoSaldo.setScale(2, RoundingMode.UP));
    }

    public ContaPoupanca(BigDecimal saldo) {
        super(saldo);
    }

    @Override
    public TipoConta getTipo() {
        return TipoConta.POUPANCA;
    }
}
