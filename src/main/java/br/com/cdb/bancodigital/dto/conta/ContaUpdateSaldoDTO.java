package br.com.cdb.bancodigital.dto.conta;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ContaUpdateSaldoDTO {

    @NotNull(message = "Saldo de acrescimo é obrigatorio")
    @Min(value = 1,message = "Deve adicionar ao menos 1 real")
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
