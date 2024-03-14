package br.com.cdb.bancodigital.service.exception;

public class PagamentoInvalidoException extends RuntimeException{

    public PagamentoInvalidoException(String msg){
        super(msg);
    }
}
