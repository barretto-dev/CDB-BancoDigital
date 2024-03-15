package br.com.cdb.bancodigital.service.exception;

public class OperacaoProibidaException extends RuntimeException{

    public OperacaoProibidaException (String  msg){
        super(msg);
    }
}
