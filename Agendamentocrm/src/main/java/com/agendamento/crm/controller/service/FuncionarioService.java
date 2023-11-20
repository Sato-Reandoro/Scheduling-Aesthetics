package com.agendamento.crm.controller.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamento.crm.model.Agendamento;
import com.agendamento.crm.model.Disponibilidade;
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
    private DisponibilidadeService disponibilidadeService;
    
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    // Método para verificar disponibilidade do funcionário por CPF
    public boolean verificarDisponibilidade(String cpfFuncionario, LocalDate dataProximaSessao) {
        // Busca o funcionário pelo CPF
        Funcionarios funcionario = findByCpf(cpfFuncionario);

        
        if (funcionario == null) {
            throw new RuntimeException("Funcionário não encontrado.");
        }

        // Converta LocalDate para LocalDateTime
        LocalDateTime dataHoraAgendamento = dataProximaSessao.atStartOfDay();
        
        // Consulta as disponibilidades do funcionário na data fornecida
        List<Disponibilidade> disponibilidades = disponibilidadeRepository
                .findAllByFuncionarioAndDataHoraBetween(
                        funcionario,
                        dataHoraAgendamento,
                        dataProximaSessao.plusDays(1).atStartOfDay()
                    );

        // Consulta os agendamentos do funcionário na data fornecida
        List<Agendamento> agendamentos = agendamentosRepository.findByFuncionarioAndDataAgendamento(funcionario, dataProximaSessao);

        // Verifica se existe alguma disponibilidade para o horário desejado
        boolean disponibilidadeDisponivel = disponibilidades.stream().anyMatch(disponibilidade ->
            // Verifica se a disponibilidade está disponível e não está agendada
            disponibilidade.getStatus().equals("Disponível") && !verificarAgendamentoConflitante(agendamentos, disponibilidade.getDataHora().toLocalTime())
        );

        return disponibilidadeDisponivel;
    }

    // Método para buscar funcionário por CPF
    public Funcionarios findByCpf(String cpf) {
        return funcionariosRepository.findByCpf(cpf).orElse(null);
    }

    private boolean verificarAgendamentoConflitante(List<Agendamento> agendamentos, LocalTime horario) {
        // Implemente a lógica para verificar se há agendamentos conflitantes para o horário específico
        // Retorna true se houver um conflito, caso contrário, retorna false
        return agendamentos.stream().anyMatch(agendamento ->
            agendamento.getHoraAgendamento().equals(horario)
        );
    }
}

