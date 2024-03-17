package br.com.cdb.bancodigital.service.exception;

public class TransferenciaPixInvalidaException extends  RuntimeException{

    public TransferenciaPixInvalidaException (String msg){
        super(msg);
    }
}
