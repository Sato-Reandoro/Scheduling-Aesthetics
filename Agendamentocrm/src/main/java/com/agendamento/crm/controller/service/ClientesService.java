package com.agendamento.crm.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamento.crm.model.Clientes;
import com.agendamento.crm.repository.ClientesRepository;

@Service
public class ClientesService {

    @Autowired
    private ClientesRepository clientesRepository;

    // Método para buscar cliente por CPF
    public Clientes findByCpf(String cpf) {
        return clientesRepository.findByCpf(cpf).orElse(null);
    }
}
