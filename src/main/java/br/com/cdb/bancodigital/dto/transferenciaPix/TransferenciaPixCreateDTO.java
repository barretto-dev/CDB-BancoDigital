package br.com.cdb.bancodigital.dto.transferenciaPix;


import java.math.BigDecimal;

public class TransferenciaPixCreateDTO {

    private String destinatario;

    private BigDecimal valor;

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
