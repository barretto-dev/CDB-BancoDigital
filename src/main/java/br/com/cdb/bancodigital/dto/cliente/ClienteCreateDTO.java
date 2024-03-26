package br.com.cdb.bancodigital.dto.cliente;

import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.enums.TipoCliente;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ClienteCreateDTO {

    @NotEmpty(message = "nome é obrigatório")
    private String nome;

    @NotEmpty(message = "cpf é obrigatório")
    @Pattern(regexp="[\\d]{11}", message = "cpf deve ter 11 digitos")
    private String cpf;

    @NotEmpty(message = "endereco é obrigatório")
    private String endereco;

    @NotNull(message = "data de nascimento é obrigatória")
    @Past(message = "data de nascimento não pode ter data futura")
    private LocalDate dataNascimento;
    @NotNull(message = "tipo é obrigatório")
    private TipoCliente tipo;

    public ClienteCreateDTO(){}

    public ClienteCreateDTO(String nome, String cpf, String endereco, LocalDate dataNascimento, TipoCliente tipo) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.tipo = tipo;
    }

    public Cliente toCliente() {
        return new Cliente(nome, cpf, endereco, dataNascimento, tipo);
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }
}
