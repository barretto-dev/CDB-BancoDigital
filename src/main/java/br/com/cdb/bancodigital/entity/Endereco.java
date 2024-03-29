package br.com.cdb.bancodigital.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cep", nullable = false, length = 8)
    private String cep;

    @Column(name = "uf", nullable = false, length = 2)
    private String unidadeFederativa;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @OneToOne(mappedBy = "endereco")
    private Cliente cliente;

    public Endereco(){}

    public Endereco(Long id, String cep, String unidadeFederativa, String estado, String bairro,
                    String logradouro, String numero, String complemento, Cliente cliente) {
        this.id = id;
        this.cep = cep;
        this.unidadeFederativa = unidadeFederativa;
        this.estado = estado;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cliente = cliente;
    }

    public Endereco(String cep, String unidadeFederativa, String estado,
                    String bairro, String logradouro, String numero, String complemento) {
        this.cep = cep;
        this.unidadeFederativa = unidadeFederativa;
        this.estado = estado;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUnidadeFederativa() {
        return unidadeFederativa;
    }

    public void setUnidadeFederativa(String unidadeFederativa) {
        this.unidadeFederativa = unidadeFederativa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
