package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.TransferenciaPix;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransferenciaPixRepository extends JpaRepository<TransferenciaPix, Long> {

    @Query("SELECT tp FROM TransferenciaPix tp WHERE tp.conta.id = :contaId")
    Page<TransferenciaPix> findAllByCartaoId(Long contaId, Pageable pageable);
}
