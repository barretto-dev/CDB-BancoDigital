package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.RendimentoConta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RendimentoContaRepository extends JpaRepository<RendimentoConta, Long> {
}
