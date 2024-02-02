package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ContaRepository extends JpaRepository<Conta,Long>{

    @Modifying
    @Query(nativeQuery = true, value="UPDATE Conta SET tipo = :tipo, saldo = :saldo WHERE id = :id")
    public Conta updateAlter(Long id, String tipo, BigDecimal saldo);
}
