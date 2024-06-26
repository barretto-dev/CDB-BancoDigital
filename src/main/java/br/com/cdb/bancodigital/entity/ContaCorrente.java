package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoConta;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CORRENTE")
public class ContaCorrente extends Conta{

    public ContaCorrente(){}
    public ContaCorrente(Long id, String numero, String agencia, BigDecimal saldo, Cliente dono) {
        super(id, numero, agencia, saldo, dono);
    }
    public ContaCorrente(String agencia, BigDecimal saldo, Cliente dono) {
        super(agencia, saldo, dono);
    }

    public ContaCorrente(String agencia, BigDecimal saldo) {
        super(agencia, saldo);
    }

    //aplicação da mensalidade da conta corrente
    public boolean aplicarTaxa(){
        BigDecimal taxaMensal = getDono().getTipo().getMensalidadeConta().getValor();
        BigDecimal saldoFinal = getSaldo().subtract(taxaMensal);
        setSaldo(saldoFinal);
        return true;
    }

    @Override
    public TipoConta getTipo() {
        return TipoConta.CORRENTE;
    }
}
