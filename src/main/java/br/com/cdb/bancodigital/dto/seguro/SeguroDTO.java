package br.com.cdb.bancodigital.dto.seguro;

import br.com.cdb.bancodigital.entity.Seguro;

public class SeguroDTO {

    private Long id;

    private String nomeProduto;

    private String descricao;

    public SeguroDTO(){}

    public SeguroDTO(Seguro seguro){
        this.id = seguro.getId();
        this.nomeProduto = seguro.getNomeProduto();
        this.descricao = seguro.getDescricao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
