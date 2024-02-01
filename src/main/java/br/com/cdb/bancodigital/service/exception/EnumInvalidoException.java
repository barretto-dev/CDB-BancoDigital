package br.com.cdb.bancodigital.service.exception;

import java.util.List;

public class EnumInvalidoException extends RuntimeException {

    public EnumInvalidoException(String msg){
        super(msg);
    }
}
