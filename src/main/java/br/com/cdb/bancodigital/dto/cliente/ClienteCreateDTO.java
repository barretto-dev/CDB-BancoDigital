package br.com.cdb.bancodigital.dto.cliente;

import br.com.cdb.bancodigital.dto.cep.CepResultDTO;
import br.com.cdb.bancodigital.dto.endereco.EnderecoCreateDTO;
import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.enums.TipoCliente;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ClienteCreateDTO {

    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @NotBlank(message = "cpf é obrigatório")
    @Pattern(regexp="(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)", message = "cpf deve está no formato xxx.xxx.xxx-xx")
    private String cpf;

    @Valid
    private EnderecoCreateDTO endereco;

    @NotNull(message = "data de nascimento é obrigatória")
    @Past(message = "data de nascimento não pode ter data futura")
    private LocalDate dataNascimento;
    @NotNull(message = "tipo é obrigatório")
    private TipoCliente tipo;

    public ClienteCreateDTO(){}

    public ClienteCreateDTO(String nome, String cpf, EnderecoCreateDTO endereco, LocalDate dataNascimento, TipoCliente tipo) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.tipo = tipo;
    }

    public Cliente toCliente(CepResultDTO dto) {
        return new Cliente(nome, cpf, endereco.toEndereco(dto), dataNascimento, tipo);
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

    public EnderecoCreateDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoCreateDTO endereco) {
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
