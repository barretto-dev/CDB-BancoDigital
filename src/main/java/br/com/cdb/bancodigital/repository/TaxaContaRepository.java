package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.TaxaConta;
import br.com.cdb.bancodigital.entity.enums.TipoTaxaConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaxaContaRepository extends JpaRepository<TaxaConta,Long> {


    @Query("SELECT t FROM TaxaConta t WHERE t.tipo = :tipo")
    public TaxaConta findByTipo(TipoTaxaConta tipo);
}
