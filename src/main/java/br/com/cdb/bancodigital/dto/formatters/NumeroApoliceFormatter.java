package br.com.cdb.bancodigital.dto.formatters;

import br.com.cdb.bancodigital.entity.Apolice;
import br.com.cdb.bancodigital.entity.Cartao;

public class NumeroApoliceFormatter{

    //Recebe o numero da apolice e aplica um espaçamento
    //a cada 4 digitos;
    public static String formatar(Apolice apolice){
        int numeroDigitos = Apolice.getNumeroLength();
        String numeroSemFormatacao = apolice.getNumero();
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
