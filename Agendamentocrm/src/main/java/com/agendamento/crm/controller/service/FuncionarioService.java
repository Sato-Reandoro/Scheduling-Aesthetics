package com.agendamento.crm.controller.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamento.crm.model.Disponibilidade;
import com.agendamento.crm.model.Agendamento;

import com.agendamento.crm.model.Funcionarios;
import com.agendamento.crm.repository.AgendamentosRepository;
import com.agendamento.crm.repository.DisponibilidadeRepository;
import com.agendamento.crm.repository.FuncionariosRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionariosRepository funcionariosRepository;

    @Autowired
    private AgendamentosRepository agendamentosRepository;

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    // Método para verificar disponibilidade do funcionário por CPF
    public boolean verificarDisponibilidade(String cpfFuncionario, LocalDate dataProximaSessao) {
        // Busca o funcionário pelo CPF
        Funcionarios funcionario = findByCpf(cpfFuncionario);

        if (funcionario == null) {
            throw new RuntimeException("Funcionário não encontrado.");
        }

        // Consulta as disponibilidades do funcionário na data fornecida
        List<Disponibilidade> disponibilidades = disponibilidadeRepository
            .findAllByFuncionarioAndDataHoraBetween(
                funcionario,
                dataProximaSessao.atStartOfDay(),
                dataProximaSessao.plusDays(1).atStartOfDay()
            );

        // Se a lista de disponibilidades for nula, inicializa como uma lista vazia
        disponibilidades = disponibilidades != null ? disponibilidades : new ArrayList<>();

        // Consulta os agendamentos do funcionário na data fornecida
        List<Agendamento> agendamentos = agendamentosRepository.findByFuncionarioAndDataAgendamento(funcionario, dataProximaSessao);

        // Verifica se existe alguma disponibilidade para o horário desejado
        boolean disponibilidadeDisponivel = disponibilidades.stream().anyMatch(disponibilidade -> {
            // Verifica se a disponibilidade está disponível e não está agendada
            return disponibilidade.getStatus().equals("disponível") && agendamentos.stream().noneMatch(agendamento ->
                    agendamento.getDataAgendamento().equals(dataProximaSessao) &&
                            agendamento.getHoraAgendamento().equals(disponibilidade.getDataHora().toLocalTime()));
        });

        return disponibilidadeDisponivel;
    }

    // Método para buscar funcionário por CPF
    public Funcionarios findByCpf(String cpf) {
        return funcionariosRepository.findByCpf(cpf).orElse(null);
    }
}

