package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.TaxaCartao;
import br.com.cdb.bancodigital.entity.enums.TipoTaxaCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaxaCartaoRepository extends JpaRepository<TaxaCartao, Long> {

    @Query("SELECT t FROM TaxaCartao t WHERE t.tipo = :tipo")
    public TaxaCartao findByTipo(TipoTaxaCartao t);
}
