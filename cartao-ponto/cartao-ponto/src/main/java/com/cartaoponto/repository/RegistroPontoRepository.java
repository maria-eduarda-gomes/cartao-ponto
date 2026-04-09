package com.cartaoponto.repository;

import com.cartaoponto.entity.Funcionario;
import com.cartaoponto.entity.RegistroPonto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, Long> {

    // Query method: busca registros de um funcionário ordenados por data/hora
    List<RegistroPonto> findByFuncionarioOrderByDataHoraAsc(Funcionario funcionario);

    // Query method: busca registros de um funcionário por ID ordenados
    List<RegistroPonto> findByFuncionarioIdOrderByDataHoraAsc(Long funcionarioId);

    // Query method: busca registros entre duas datas/horas para um funcionário
    List<RegistroPonto> findByFuncionarioAndDataHoraBetweenOrderByDataHoraAsc(
            Funcionario funcionario,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    // JPQL: busca registros de um funcionário em uma data específica (por dia)
    @Query("""
            SELECT r FROM RegistroPonto r
            WHERE r.funcionario.id = :funcionarioId
              AND r.dataHora >= :inicio
              AND r.dataHora < :fim
            ORDER BY r.dataHora ASC
            """)
    List<RegistroPonto> findByFuncionarioIdAndData(
            @Param("funcionarioId") Long funcionarioId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    // Query method: conta registros de um funcionário em um intervalo
    long countByFuncionarioAndDataHoraBetween(
            Funcionario funcionario,
            LocalDateTime inicio,
            LocalDateTime fim
    );
}
