package br.com.cdb.bancodigital.dto.cliente;

import br.com.cdb.bancodigital.dto.endereco.EnderecoDTO;
import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.Endereco;
import br.com.cdb.bancodigital.entity.enums.TipoClienteEnum;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public class ClienteDTO {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    @NotEmpty
    private TipoClienteEnum tipo;
    private EnderecoDTO endereco;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String nome, String cpf, Endereco endereco, LocalDate dataNascimento, TipoClienteEnum tipo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = new EnderecoDTO(endereco);
        this.dataNascimento = dataNascimento;
        this.tipo = tipo;
    }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.endereco = new EnderecoDTO(cliente.getEndereco());
        this.dataNascimento = cliente.getDataNascimento();
        this.tipo = cliente.getTipo().getNome();
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento =dataNascimento;
    }

    public TipoClienteEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoClienteEnum tipo) {
        this.tipo = tipo;
    }
}
