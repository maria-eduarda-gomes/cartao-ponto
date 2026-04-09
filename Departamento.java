package com.cartaoponto.runner;

import com.cartaoponto.entity.Departamento;
import com.cartaoponto.entity.Funcionario;
import com.cartaoponto.entity.RegistroPonto;
import com.cartaoponto.repository.DepartamentoRepository;
import com.cartaoponto.repository.FuncionarioRepository;
import com.cartaoponto.repository.RegistroPontoRepository;
import com.cartaoponto.service.CartaoPontoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final DepartamentoRepository departamentoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final RegistroPontoRepository registroPontoRepository;
    private final CartaoPontoService cartaoPontoService;

    public DataLoader(DepartamentoRepository departamentoRepository,
                      FuncionarioRepository funcionarioRepository,
                      RegistroPontoRepository registroPontoRepository,
                      CartaoPontoService cartaoPontoService) {
        this.departamentoRepository = departamentoRepository;
        this.funcionarioRepository  = funcionarioRepository;
        this.registroPontoRepository = registroPontoRepository;
        this.cartaoPontoService      = cartaoPontoService;
    }

    @Override
    public void run(String... args) {
        // ---------------------------------------------------------------
        // 1. DEPARTAMENTOS
        // ---------------------------------------------------------------
        Departamento gestao = departamentoRepository.save(new Departamento("Gestão de Pessoas"));
        Departamento ti     = departamentoRepository.save(new Departamento("Tecnologia da Informação"));
        Departamento financeiro = departamentoRepository.save(new Departamento("Financeiro"));

        // ---------------------------------------------------------------
        // 2. FUNCIONÁRIOS
        // ---------------------------------------------------------------
        Funcionario james = funcionarioRepository.save(
                new Funcionario("F001", "James Gosling", gestao));

        Funcionario linus = funcionarioRepository.save(
                new Funcionario("F002", "Linus Torvalds", ti));

        Funcionario ada = funcionarioRepository.save(
                new Funcionario("F003", "Ada Lovelace", financeiro));

        // ---------------------------------------------------------------
        // 3. REGISTROS DE PONTO
        // ---------------------------------------------------------------

        // James – 10/03/2026 (dois períodos)
        LocalDate dataJames = LocalDate.of(2026, 3, 10);
        registroPontoRepository.save(new RegistroPonto(james, LocalDateTime.of(2026, 3, 10, 8,  2)));
        registroPontoRepository.save(new RegistroPonto(james, LocalDateTime.of(2026, 3, 10, 12, 1)));
        registroPontoRepository.save(new RegistroPonto(james, LocalDateTime.of(2026, 3, 10, 13, 5)));
        registroPontoRepository.save(new RegistroPonto(james, LocalDateTime.of(2026, 3, 10, 17, 58)));

        // Linus – 10/03/2026 (dois períodos + hora extra)
        LocalDate dataLinus = LocalDate.of(2026, 3, 10);
        registroPontoRepository.save(new RegistroPonto(linus, LocalDateTime.of(2026, 3, 10, 9,  0)));
        registroPontoRepository.save(new RegistroPonto(linus, LocalDateTime.of(2026, 3, 10, 12, 30)));
        registroPontoRepository.save(new RegistroPonto(linus, LocalDateTime.of(2026, 3, 10, 13, 30)));
        registroPontoRepository.save(new RegistroPonto(linus, LocalDateTime.of(2026, 3, 10, 19, 15)));

        // Ada – 11/03/2026 (apenas um período – saída sem par para demonstrar)
        LocalDate dataAda = LocalDate.of(2026, 3, 11);
        registroPontoRepository.save(new RegistroPonto(ada, LocalDateTime.of(2026, 3, 11, 8,  45)));
        registroPontoRepository.save(new RegistroPonto(ada, LocalDateTime.of(2026, 3, 11, 12, 0)));
        registroPontoRepository.save(new RegistroPonto(ada, LocalDateTime.of(2026, 3, 11, 13, 0)));
        // Saída final não registrada (número ímpar de batidas)

        // ---------------------------------------------------------------
        // 4. GERAÇÃO DOS RELATÓRIOS
        // ---------------------------------------------------------------
        System.out.println("==============================================");
        System.out.println("  SISTEMA DE CARTÃO PONTO - Spring Data JPA  ");
        System.out.println("==============================================");

        cartaoPontoService.gerarRelatorio(james.getId(), dataJames);
        cartaoPontoService.gerarRelatorio(linus.getId(), dataLinus);
        cartaoPontoService.gerarRelatorio(ada.getId(), dataAda);
    }
}
