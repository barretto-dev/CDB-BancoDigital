package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.Apolice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApoliceRepository extends JpaRepository<Apolice, Long> {

    @Query("SELECT a FROM Apolice a WHERE a.numero = :numero")
    public Apolice findApoliceByNumero(String numero);

    @Query("SELECT a FROM Apolice a ORDER BY a.numero DESC LIMIT 1")
    public Apolice findLastApolice();

    @Query("SELECT a FROM Apolice a WHERE a.cartaoCredito.id = :cartaoId")
    public Page<Apolice> findApolicesByCartao(Long cartaoId, Pageable pageable);
}
