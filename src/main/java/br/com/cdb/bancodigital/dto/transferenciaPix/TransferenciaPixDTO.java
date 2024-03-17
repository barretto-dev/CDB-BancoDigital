package br.com.cdb.bancodigital.dto.transferenciaPix;

import br.com.cdb.bancodigital.dto.formatters.LocalDateTimeFormatter;
import br.com.cdb.bancodigital.entity.TransferenciaPix;

import java.math.BigDecimal;

public class TransferenciaPixDTO {

    private Long id;
    private String destinatario;

    private BigDecimal valor;

    private String data;

    private Long contaId;

    public TransferenciaPixDTO(){}

    public TransferenciaPixDTO(TransferenciaPix transferenciaPix){
        this.id = transferenciaPix.getId();
        this.destinatario = transferenciaPix.getDestinatario();
        this.valor = transferenciaPix.getValor();
        this.data = LocalDateTimeFormatter.formatar(transferenciaPix.getData());
        this.contaId = transferenciaPix.getConta().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }
}
