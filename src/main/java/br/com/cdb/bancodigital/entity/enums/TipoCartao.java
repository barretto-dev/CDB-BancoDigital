package br.com.cdb.bancodigital.entity.enums;

public enum TipoCartao {

    DEBITO("D"),
    CREDITO("C");

    private final String codigo;

    TipoCartao(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
