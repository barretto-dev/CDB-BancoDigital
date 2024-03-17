package br.com.cdb.bancodigital.dto.formatters;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class YearMonthFormatter {

    //Receber um YearMonth e retorna uma string no formato mes/ano
    public static String formatar(YearMonth yearMonth){
        return yearMonth.format(DateTimeFormatter.ofPattern("MM/yyyy"));
    }
}
