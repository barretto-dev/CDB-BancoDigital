package br.com.cdb.bancodigital.dto.pagamento;

import br.com.cdb.bancodigital.dto.formatters.LocalDateFormatter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public class PaginaPagamentoDTO {

    private String dataInicio;
    private  String dataFinal;
    private Page<PagamentoDTO> pagina;

    public PaginaPagamentoDTO(LocalDate dataInicio, LocalDate dataFinal, Page<PagamentoDTO> pagina) {
        this.dataInicio = LocalDateFormatter.formatar(dataInicio);
        this.dataFinal =  LocalDateFormatter.formatar(dataFinal);
        this.pagina = pagina;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio =  LocalDateFormatter.formatar(dataInicio);
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = LocalDateFormatter.formatar(dataFinal);
    }

    public Page<PagamentoDTO> getPagina() {
        return pagina;
    }

    public void setPagina(Page<PagamentoDTO> pagina) {
        this.pagina = pagina;
    }
}
