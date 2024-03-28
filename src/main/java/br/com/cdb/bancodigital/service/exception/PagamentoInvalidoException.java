package br.com.cdb.bancodigital.service.exception;

import br.com.cdb.bancodigital.dto.formatters.LocalDateFormatter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagamentoInvalidoException extends RuntimeException{

    private BigDecimal valorRestante;

    private String inicioMes;

    private String finalMes;

    public PagamentoInvalidoException(String msg){
        super(msg);
    }

    public PagamentoInvalidoException(String msg, BigDecimal valorRestante){
        super(msg);
        this.valorRestante = valorRestante;
    }

    public PagamentoInvalidoException(String msg, BigDecimal valorRestante, LocalDate inicioMes, LocalDate finalMes){
        super(msg);
        this.valorRestante = valorRestante;
        this.inicioMes = LocalDateFormatter.formatar(inicioMes);
        this.finalMes = LocalDateFormatter.formatar(finalMes);
    }

    public BigDecimal getValorRestante() {
        return valorRestante;
    }

    public String getInicioMes() {
        return inicioMes;
    }

    public String getFinalMes() {
        return finalMes;
    }
}
