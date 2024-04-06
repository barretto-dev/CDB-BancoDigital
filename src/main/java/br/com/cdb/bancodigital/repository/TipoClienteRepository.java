package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoClienteRepository extends JpaRepository<TipoCliente, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM tipo_cliente AS tc WHERE tc.nome = :nome ")
    Optional<TipoCliente> findByNome(String nome);

}
