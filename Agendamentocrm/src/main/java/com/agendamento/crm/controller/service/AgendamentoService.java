package com.agendamento.crm.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamento.crm.model.Agendamento;
import com.agendamento.crm.repository.AgendamentosRepository;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentosRepository agendamentosRepository;

    // Método para salvar um agendamento no banco de dados
    public Agendamento save(Agendamento agendamento) {
        return agendamentosRepository.save(agendamento);
    }

    
    // Outros métodos necessários...

}
