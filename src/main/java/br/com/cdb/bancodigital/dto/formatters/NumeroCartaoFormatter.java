package br.com.cdb.bancodigital.dto.formatters;

import br.com.cdb.bancodigital.entity.Cartao;

public class NumeroCartaoFormatter {

    //Recebe o numero do cartao e aplica um espaçamento
    //a cada 4 digitos;
    public static String formatar(Cartao cartao){
        int numeroDigitos = Cartao.getNumeroLength();
        String numeroSemFormatacao = cartao.getNumero();
        StringBuilder numeroComFormatacao = new StringBuilder();
        for(int i = 0; i < numeroDigitos; i++){

            if( i != 0 && i % 4 == 0)
                numeroComFormatacao.append(" ").append(numeroSemFormatacao.charAt(i));
            else
                numeroComFormatacao.append(numeroSemFormatacao.charAt(i));
        }

        return numeroComFormatacao.toString();

    }
}
