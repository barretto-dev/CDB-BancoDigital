package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
}
