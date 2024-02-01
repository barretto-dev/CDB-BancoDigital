package br.com.cdb.bancodigital.service.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String msg){
        super(msg);
    }
}
