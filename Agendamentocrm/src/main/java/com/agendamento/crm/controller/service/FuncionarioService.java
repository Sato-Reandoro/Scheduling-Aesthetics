package com.agendamento.crm.controller.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamento.crm.model.Funcionarios;
import com.agendamento.crm.repository.FuncionariosRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionariosRepository funcionariosRepository;

    // Método para verificar disponibilidade do funcionário
    public boolean verificarDisponibilidade(Long idFuncionario, LocalDate dataProximaSessao) {
        // Lógica para verificar se o funcionário está disponível na data fornecida
        // Isso pode envolver a consulta do banco de dados para verificar se há conflitos com outras sessões agendadas.
        // Retorna true se estiver disponível, false caso contrário.
        // Implemente conforme necessário.
        return true; // Exemplo: sempre disponível
    }

    // Método para buscar funcionário por ID
    public Funcionarios findById(Long id) {
        return funcionariosRepository.findById(id).orElse(null);
    }
}
