package br.com.cdb.bancodigital.repository;

import br.com.cdb.bancodigital.entity.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Modifying
    @Query(nativeQuery = true, value="SELECT * FROM Pagamento WHERE cartao_id = :cartaoId AND CAST(data_pagamento AS DATE) = CURRENT_DATE ")
    public List<Pagamento> getPagamentosCartaoHoje(Long cartaoId);

    @Modifying
    @Query(nativeQuery = true, value="SELECT * FROM Pagamento WHERE cartao_id = :cartaoId AND CAST(data_pagamento AS DATE) BETWEEN :inicioMes AND :finalMes")
    public List<Pagamento> getPagamentosCartaoMensal(Long cartaoId, LocalDate inicioMes, LocalDate finalMes);

    @Query(nativeQuery = true, value="SELECT * FROM Pagamento WHERE cartao_id = :cartaoId AND CAST(data_pagamento AS DATE) BETWEEN :inicioMes AND :finalMes")
    public Page<Pagamento> getPagamentosCartaoMensal(Long cartaoId, LocalDate inicioMes, LocalDate finalMes, Pageable pageable);
}
