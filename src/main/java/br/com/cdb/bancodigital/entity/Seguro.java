package br.com.cdb.bancodigital.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "seguro")
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_produto", nullable = false)
    private String nomeProduto;

    @Column(name = "descricao", nullable = false, length = 6000)
    private String descricao;

    @OneToMany(mappedBy = "seguro")
    private List<Apolice> apolices;

    public Seguro(){}

    public Seguro( String nomeProduto, String descricao){
        this.nomeProduto = nomeProduto;
        this.descricao = descricao;
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

    public List<Apolice> getApolices() {
        return apolices;
    }
}
