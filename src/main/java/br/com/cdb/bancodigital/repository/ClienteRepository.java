package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
