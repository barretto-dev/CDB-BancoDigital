package br.com.cdb.bancodigital.dto.pagamento;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PagamentoCreateDTO {

    @NotBlank(message = "destinatário é obrigatório")
    private String destinatario;

    @NotNull(message = "valor é obrigatório")
    private BigDecimal valor;

    @NotNull(message = "quantidade de parcelas é obrigatório")
    @Min(value = 1, message = "quantidade de parcelas deve ser maior ou igual à 1")
    private Integer qtdParcelas;

    @NotNull(message = "id do cartão é obrigatório")
    private Long cartaoId;

    public PagamentoCreateDTO(){}

    public PagamentoCreateDTO(String destinatario, BigDecimal valor, Integer qtdParcelas, Long cartaoId) {
        this.destinatario = destinatario;
        this.valor = valor;
        this.qtdParcelas = qtdParcelas;
        this.cartaoId = cartaoId;
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

    public Integer getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(Integer qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    public Long getCartaoId() {
        return cartaoId;
    }

    public void setCartaoId(Long cartaoId) {
        this.cartaoId = cartaoId;
    }
}
