# Sistema de Cartão Ponto — Spring Data JPA

Atividade prática da disciplina **Programação Orientada a Objetos II**  
Universidade da Região de Joinville — Bacharelado em Engenharia de Software

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.3 |
| Spring Data JPA | (incluso no Boot) |
| Hibernate | (incluso no Boot) |
| Banco de dados | H2 (in-memory) |
| Build | Maven |

---

## Estrutura do Projeto

```
cartao-ponto/
├── pom.xml
└── src/main/java/com/cartaoponto/
    ├── CartaoPontoApplication.java      # Ponto de entrada Spring Boot
    ├── entity/
    │   ├── Departamento.java            # Entidade JPA – Departamento
    │   ├── Funcionario.java             # Entidade JPA – Funcionário
    │   └── RegistroPonto.java           # Entidade JPA – Registro de Ponto
    ├── repository/
    │   ├── DepartamentoRepository.java  # Repository + query methods
    │   ├── FuncionarioRepository.java   # Repository + query methods + JPQL
    │   └── RegistroPontoRepository.java # Repository + query methods + JPQL
    ├── service/
    │   └── CartaoPontoService.java      # Lógica de cálculo e relatório
    └── runner/
        └── DataLoader.java              # Inserção de dados + execução dos relatórios
```

---

## Modelo de Dados (MER)

```
┌──────────────────┐         ┌──────────────────────┐         ┌────────────────────┐
│   DEPARTAMENTO   │         │      FUNCIONARIO      │         │   REGISTRO_PONTO   │
├──────────────────┤         ├──────────────────────┤         ├────────────────────┤
│ id  (PK)         │ 1     N │ id           (PK)    │ 1     N │ id        (PK)     │
│ nome             │◄────────│ matricula    (UNIQUE) │◄────────│ funcionario_id(FK) │
└──────────────────┘         │ nome_completo         │         │ data_hora          │
                             │ departamento_id (FK)  │         └────────────────────┘
                             └──────────────────────┘
```

---

## Diagrama de Classes (resumido)

```
Departamento                Funcionario                  RegistroPonto
─────────────               ─────────────────            ──────────────────
- id: Long                  - id: Long                   - id: Long
- nome: String              - matricula: String          - funcionario: Funcionario
- funcionarios: List<>      - nomeCompleto: String       - dataHora: LocalDateTime
                            - departamento: Departamento

DepartamentoRepository      FuncionarioRepository        RegistroPontoRepository
──────────────────────      ─────────────────────        ───────────────────────
findByNome()                findByMatricula()             findByFuncionarioOrderBy…()
existsByNome()              findByNomeCompleto…()         findByFuncionarioIdAndData()
                            findByDepartamento()          countByFuncionarioAnd…()
                            findByIdWithDepartamento()
```

---

## Query Methods utilizados

### `DepartamentoRepository`
| Método | Descrição |
|---|---|
| `findByNome(String)` | Busca departamento pelo nome |
| `existsByNome(String)` | Verifica existência pelo nome |

### `FuncionarioRepository`
| Método | Descrição |
|---|---|
| `findByMatricula(String)` | Busca pelo número de matrícula |
| `findByNomeCompletoContainingIgnoreCase(String)` | Busca por parte do nome |
| `findByDepartamento(Departamento)` | Lista funcionários de um departamento |
| `findByDepartamentoId(Long)` | Lista por ID do departamento |
| `findByMatriculaWithDepartamento(String)` | JPQL com JOIN FETCH |
| `findByIdWithDepartamento(Long)` | JPQL com JOIN FETCH por ID |

### `RegistroPontoRepository`
| Método | Descrição |
|---|---|
| `findByFuncionarioOrderByDataHoraAsc(Funcionario)` | Registros ordenados por hora |
| `findByFuncionarioIdOrderByDataHoraAsc(Long)` | Idem, por ID |
| `findByFuncionarioAndDataHoraBetweenOrderByDataHoraAsc(...)` | Registros em intervalo |
| `findByFuncionarioIdAndData(Long, LocalDateTime, LocalDateTime)` | JPQL: registros de uma data específica |
| `countByFuncionarioAndDataHoraBetween(...)` | Conta registros em intervalo |

---

## Como Executar

```bash
# Clonar o repositório
git clone <URL_DO_REPOSITORIO>
cd cartao-ponto

# Compilar e executar
./mvnw spring-boot:run
```

A aplicação irá:
1. Criar as tabelas automaticamente no H2 (in-memory)
2. Inserir os dados de exemplo via `DataLoader`
3. Gerar e imprimir os relatórios no console

### Exemplo de saída esperada

```
==============================================
  SISTEMA DE CARTÃO PONTO - Spring Data JPA  
==============================================

RELATÓRIO DE PONTO
Funcionário : James Gosling
Departamento: Gestão de Pessoas
Data        : 10/03/2026

Entrada  Saída    Horas   
----------------------------
08:02    12:01    03:59   
13:05    17:58    04:53   
----------------------------
Total trabalhado: 08:52
```

---

## H2 Console (opcional)

Com a aplicação rodando, acesse:  
**http://localhost:8080/h2-console**

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:cartaoponto` |
| User Name | `sa` |
| Password | *(vazio)* |

---

## Usando MySQL (opcional)

Substitua a dependência H2 no `pom.xml`:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

E ajuste o `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cartaoponto?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```
