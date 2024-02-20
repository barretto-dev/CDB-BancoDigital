package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.Taxa;
import br.com.cdb.bancodigital.entity.enums.TipoTaxa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface TaxaRepository extends JpaRepository<Taxa,Long> {


    @Query("SELECT t FROM Taxa t WHERE t.tipo = :tipo")
    public Taxa findByTipo(TipoTaxa tipo);
}
