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

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 6000)
    private String descricao;

    @OneToMany(mappedBy = "seguro")
    private List<Apolice> apolices;

    @OneToMany(mappedBy = "seguro")
    private List<SeguroValorTipoCliente> valoresSeguro;

    public Seguro(){}

    public Seguro(String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;
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

    public List<Apolice> getApolices() {
        return apolices;
    }

    public List<SeguroValorTipoCliente> getValoresSeguro() {
        return valoresSeguro;
    }
}
