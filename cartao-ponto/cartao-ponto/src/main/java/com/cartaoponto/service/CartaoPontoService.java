package com.cartaoponto.service;

import com.cartaoponto.entity.Funcionario;
import com.cartaoponto.entity.RegistroPonto;
import com.cartaoponto.repository.FuncionarioRepository;
import com.cartaoponto.repository.RegistroPontoRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CartaoPontoService {

    private static final DateTimeFormatter FMT_DATA  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_HORA  = DateTimeFormatter.ofPattern("HH:mm");

    private final FuncionarioRepository funcionarioRepository;
    private final RegistroPontoRepository registroPontoRepository;

    public CartaoPontoService(FuncionarioRepository funcionarioRepository,
                              RegistroPontoRepository registroPontoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.registroPontoRepository = registroPontoRepository;
    }

    /**
     * Gera e imprime o relatório de ponto de um funcionário em uma data específica.
     *
     * @param funcionarioId ID do funcionário
     * @param data          Data a ser consultada
     */
    public void gerarRelatorio(Long funcionarioId, LocalDate data) {
        Funcionario funcionario = funcionarioRepository
                .findByIdWithDepartamento(funcionarioId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Funcionário não encontrado com ID: " + funcionarioId));

        LocalDateTime inicioDia = data.atStartOfDay();
        LocalDateTime fimDia    = data.atTime(LocalTime.MAX);

        List<RegistroPonto> registros = registroPontoRepository
                .findByFuncionarioIdAndData(funcionarioId, inicioDia, fimDia);

        imprimirRelatorio(funcionario, data, registros);
    }

    /**
     * Calcula e imprime o relatório no formato especificado pelo enunciado.
     */
    private void imprimirRelatorio(Funcionario funcionario, LocalDate data,
                                   List<RegistroPonto> registros) {
        System.out.println();
        System.out.println("RELATÓRIO DE PONTO");
        System.out.println("Funcionário : " + funcionario.getNomeCompleto());
        System.out.println("Departamento: " + funcionario.getDepartamento().getNome());
        System.out.println("Data        : " + data.format(FMT_DATA));
        System.out.println();
        System.out.printf("%-8s %-8s %-8s%n", "Entrada", "Saída", "Horas");
        System.out.println("----------------------------");

        if (registros.isEmpty()) {
            System.out.println("Nenhum registro encontrado para esta data.");
            System.out.println("----------------------------");
            System.out.println("Total trabalhado: 00:00");
            System.out.println();
            return;
        }

        Duration totalTrabalhado = Duration.ZERO;

        // Processa pares entrada/saída
        for (int i = 0; i + 1 < registros.size(); i += 2) {
            LocalDateTime entrada = registros.get(i).getDataHora();
            LocalDateTime saida   = registros.get(i + 1).getDataHora();

            Duration periodo = Duration.between(entrada, saida);
            totalTrabalhado  = totalTrabalhado.plus(periodo);

            System.out.printf("%-8s %-8s %-8s%n",
                    entrada.format(FMT_HORA),
                    saida.format(FMT_HORA),
                    formatarDuracao(periodo));
        }

        // Caso ímpar: último registro sem saída correspondente
        if (registros.size() % 2 != 0) {
            LocalDateTime entradaSemPar = registros.get(registros.size() - 1).getDataHora();
            System.out.printf("%-8s %-8s %-8s%n",
                    entradaSemPar.format(FMT_HORA), "--:--", "--:--");
        }

        System.out.println("----------------------------");
        System.out.println("Total trabalhado: " + formatarDuracao(totalTrabalhado));
        System.out.println();
    }

    /**
     * Formata uma Duration no padrão HH:mm.
     */
    private String formatarDuracao(Duration duracao) {
        long horas   = duracao.toHours();
        long minutos = duracao.toMinutesPart();
        return String.format("%02d:%02d", horas, minutos);
    }
}
