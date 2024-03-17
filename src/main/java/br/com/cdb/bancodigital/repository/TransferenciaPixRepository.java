package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.TransferenciaPix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenciaPixRepository extends JpaRepository<TransferenciaPix, Long> {
}
