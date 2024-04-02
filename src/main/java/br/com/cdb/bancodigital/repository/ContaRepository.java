package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.Conta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ContaRepository extends JpaRepository<Conta,Long>{

    @Modifying
    @Query(nativeQuery = true, value="UPDATE Conta SET tipo = :tipo, saldo = :saldo WHERE id = :id")
    public Conta updateAlter(Long id, String tipo, BigDecimal saldo);

    @Query("SELECT c FROM Conta c ORDER BY c.id DESC LIMIT 1")
    public Conta findLastConta();

    @Query(nativeQuery = true, value="SELECT * FROM Conta")
    public Page<Conta> findAllPaged(Pageable pageable);
}
