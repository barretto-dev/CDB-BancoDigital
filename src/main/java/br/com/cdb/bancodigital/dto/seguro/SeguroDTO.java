package br.com.cdb.bancodigital.dto.seguro;

import br.com.cdb.bancodigital.entity.Seguro;

import java.util.List;

public class SeguroDTO {

    private Long id;

    private String nome;

    private String descricao;

    private List<ValoresSeguroDTO> valores;

    public SeguroDTO(){}

    public SeguroDTO(Seguro seguro){
        this.id = seguro.getId();
        this.nome = seguro.getNome();
        this.descricao = seguro.getDescricao();

        this.valores = seguro.getValoresSeguro()
                .stream().map(
                        vs -> new ValoresSeguroDTO(vs.getTipoCliente().getNome(), vs.getValor(), vs.getTipoValor())
                ).toList();
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

    public List<ValoresSeguroDTO> getValores() {
        return valores;
    }

    public void setValores(List<ValoresSeguroDTO> valores) {
        this.valores = valores;
    }
}
