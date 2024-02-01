package br.com.cdb.bancodigital.entity.enums;

public enum TipoConta {

    CORRENTE("COR", "Corrente"),
    POUPANCA("POU", "Poupança");

    private final String codigo;
    private final String descricao;

    TipoConta(String codigo, String descricao){
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
