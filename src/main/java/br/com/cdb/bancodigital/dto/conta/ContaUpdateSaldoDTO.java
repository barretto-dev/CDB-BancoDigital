package br.com.cdb.bancodigital.dto.conta;

import java.math.BigDecimal;

public class ContaUpdateSaldoDTO {

    private BigDecimal saldo;

    public ContaUpdateSaldoDTO() {}

    public ContaUpdateSaldoDTO(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
