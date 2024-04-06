package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.MensalidadeConta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensalidadeContaRepository extends JpaRepository<MensalidadeConta, Long> {
}
