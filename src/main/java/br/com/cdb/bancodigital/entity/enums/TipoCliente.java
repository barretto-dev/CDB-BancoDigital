package br.com.cdb.bancodigital.entity.enums;

public enum TipoCliente {

    COMUM("C", "Comum"),
    SUPER("S", "Super"),
    PREMIUM("P", "Premium");

    private final String codigo;
    private final String descricao;

    TipoCliente(String codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
