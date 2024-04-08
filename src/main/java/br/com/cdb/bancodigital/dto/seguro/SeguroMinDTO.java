package br.com.cdb.bancodigital.dto.seguro;

import br.com.cdb.bancodigital.entity.Seguro;

public class SeguroMinDTO {

    private Long id;

    private String nome;

    private String descricao;

    public SeguroMinDTO(){}

    public SeguroMinDTO(Seguro seguro){
        this.id = seguro.getId();
        this.nome = seguro.getNome();
        this.descricao = seguro.getDescricao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
