package com.cartaoponto.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegistroPonto> registrosPonto = new ArrayList<>();

    public Funcionario() {}

    public Funcionario(String matricula, String nomeCompleto, Departamento departamento) {
        this.matricula = matricula;
        this.nomeCompleto = nomeCompleto;
        this.departamento = departamento;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    public List<RegistroPonto> getRegistrosPonto() { return registrosPonto; }
    public void setRegistrosPonto(List<RegistroPonto> registrosPonto) { this.registrosPonto = registrosPonto; }

    @Override
    public String toString() {
        return "Funcionario{id=" + id + ", matricula='" + matricula + "', nomeCompleto='" + nomeCompleto + "'}";
    }
}
