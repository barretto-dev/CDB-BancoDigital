package br.com.cdb.bancodigital.dto.transferenciaPix;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferenciaPixCreateDTO {

    @NotBlank(message = "destinatário é obrigatório")
    private String destinatario;

    @NotNull(message = "valor é obrigatório")
    @Min(value = 1,message = "valor da transferencia não pode ser menor que 1 real")
    private BigDecimal valor;

    @NotNull(message = "id da conta é obrigatório")
    private Long contaId;

    public TransferenciaPixCreateDTO(){}

    public TransferenciaPixCreateDTO(String destinatario, BigDecimal valor, Long contaId) {
        this.destinatario = destinatario;
        this.valor = valor;
        this.contaId = contaId;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }
}
