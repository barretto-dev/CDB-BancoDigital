package br.com.cdb.bancodigital.dto.endereco;


import br.com.cdb.bancodigital.entity.Endereco;

public class EnderecoDTO {

    private Long id;

    private String cep;

    private String unidadeFederativa;

    private String estado;

    private String bairro;

    private String logradouro;

    private String numero;

    private String complemento;

    public EnderecoDTO(Long id, String cep, String unidadeFederativa, String estado, String bairro,
                       String logradouro, String numero, String complemento) {
        this.id = id;
        this.cep = cep;
        this.unidadeFederativa = unidadeFederativa;
        this.estado = estado;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public EnderecoDTO(Endereco endereco){
        this.id = endereco.getId();
        this.cep = endereco.getCep();
        this.unidadeFederativa = endereco.getUnidadeFederativa();
        this.estado = endereco.getEstado();
        this.bairro = endereco.getBairro();
        this.logradouro = endereco.getLogradouro();
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
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
}
