package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    @Query("SELECT c FROM Cartao c ORDER BY c.id DESC LIMIT 1")
    public Cartao findLastCartao();
}
