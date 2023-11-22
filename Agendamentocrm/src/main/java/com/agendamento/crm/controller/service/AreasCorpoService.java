package com.agendamento.crm.controller.service;


import org.springframework.stereotype.Service;


import com.agendamento.crm.model.AreasCorpo;

import com.agendamento.crm.repository.AreasCorpoRepository;

@Service
public class AreasCorpoService {
    private final AreasCorpoRepository areasCorpoRepository;

    
    public AreasCorpoService(AreasCorpoRepository areasCorpoRepository) {
        this.areasCorpoRepository = areasCorpoRepository;
    }

    public AreasCorpo buscarAreaCorpoPorNome(String nome) {
        return areasCorpoRepository.findByNome(nome);
    }

	public static AreasCorpo findByNome(String areasCorpo) {
		// TODO Auto-generated method stub
		return null;
	}

    // Outros métodos, se necessário
}