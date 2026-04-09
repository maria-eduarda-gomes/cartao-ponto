package com.cartaoponto.repository;

import com.cartaoponto.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    // Query method: busca departamento pelo nome
    Optional<Departamento> findByNome(String nome);

    // Query method: verifica existência pelo nome
    boolean existsByNome(String nome);
}
