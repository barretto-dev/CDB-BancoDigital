package br.com.cdb.bancodigital.dto.pagamento;

import br.com.cdb.bancodigital.dto.formatters.LocalDateTimeFormatter;
import br.com.cdb.bancodigital.entity.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagamentoDTO {

    private Long id;
    private String destinatario;
    private BigDecimal valor;
    private Integer parcelaAtual;
    private Integer parcelaTotal;
    private String data;
    private Long cartaoId;

    public PagamentoDTO(Pagamento pagamento){
        this.id = pagamento.getId();
        this.destinatario = pagamento.getDestinatario();
        this.valor = pagamento.getValor();
        this.parcelaAtual = pagamento.getParcelaAtual();
        this.parcelaTotal = pagamento.getParcelaTotal();
        this.data = LocalDateTimeFormatter.formatar(pagamento.getData());
        this.cartaoId = pagamento.getCartao().getId();
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

    public Integer getParcelaAtual() {
        return parcelaAtual;
    }

    public void setParcelaAtual(Integer parcelaAtual) {
        this.parcelaAtual = parcelaAtual;
    }

    public Integer getParcelaTotal() {
        return parcelaTotal;
    }

    public void setParcelaTotal(Integer parcelaTotal) {
        this.parcelaTotal = parcelaTotal;
    }

    public String getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = LocalDateTimeFormatter.formatar(data);
    }

    public Long getCartaoId() {
        return cartaoId;
    }

    public void setCartaoId(Long cartaoId) {
        this.cartaoId = cartaoId;
    }
}
