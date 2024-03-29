package br.com.cdb.bancodigital.dto.endereco;

import br.com.cdb.bancodigital.dto.cep.CepResultDTO;
import br.com.cdb.bancodigital.entity.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EnderecoCreateDTO {

    @NotBlank(message = "cep é obrigatorio")
    @Pattern(regexp="[\\d]{8}", message = "cep deve ter 8 digitos")
    private String cep;

    @NotBlank(message = "número do endereço é obrigatorio")
    private String numero;

    private String complemento;

    public EnderecoCreateDTO(){}

    public Endereco toEndereco(CepResultDTO dto){
        return new Endereco(cep, dto.getUf(), dto.getLocalidade(), dto.getBairro(),
                            dto.getLogradouro(), numero, complemento);
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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
