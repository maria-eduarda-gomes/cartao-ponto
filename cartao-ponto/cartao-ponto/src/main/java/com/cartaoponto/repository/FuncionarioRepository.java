package com.cartaoponto.repository;

import com.cartaoponto.entity.Departamento;
import com.cartaoponto.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    // Query method: busca funcionário pela matrícula
    Optional<Funcionario> findByMatricula(String matricula);

    // Query method: busca funcionários pelo nome (contém, ignora case)
    List<Funcionario> findByNomeCompletoContainingIgnoreCase(String nome);

    // Query method: busca funcionários por departamento
    List<Funcionario> findByDepartamento(Departamento departamento);

    // Query method: busca funcionários por ID do departamento
    List<Funcionario> findByDepartamentoId(Long departamentoId);

    // JPQL: busca funcionário com departamento carregado (evita N+1)
    @Query("SELECT f FROM Funcionario f JOIN FETCH f.departamento WHERE f.matricula = :matricula")
    Optional<Funcionario> findByMatriculaWithDepartamento(@Param("matricula") String matricula);

    // JPQL: busca funcionário com departamento carregado por ID
    @Query("SELECT f FROM Funcionario f JOIN FETCH f.departamento WHERE f.id = :id")
    Optional<Funcionario> findByIdWithDepartamento(@Param("id") Long id);
}
