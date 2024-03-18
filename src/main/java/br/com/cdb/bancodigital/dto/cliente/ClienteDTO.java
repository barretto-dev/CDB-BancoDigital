package br.com.cdb.bancodigital.dto.cliente;

import br.com.cdb.bancodigital.dto.formatters.LocalDateFormatter;
import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.enums.TipoCliente;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.stream.Stream;

public class ClienteDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String endereco;
    private String dataNascimento;
    @NotEmpty
    private TipoCliente tipo;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String nome, String cpf, String endereco, LocalDate dataNascimento, TipoCliente tipo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.dataNascimento = LocalDateFormatter.formatar(dataNascimento);
        this.tipo = tipo;
    }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.endereco = cliente.getEndereco();
        this.dataNascimento = LocalDateFormatter.formatar(cliente.getDataNascimento());
        this.tipo = cliente.getTipo();
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = LocalDateFormatter.formatar(dataNascimento);;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }
}
