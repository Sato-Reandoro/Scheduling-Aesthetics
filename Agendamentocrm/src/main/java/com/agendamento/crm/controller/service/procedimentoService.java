package com.agendamento.crm.controller.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.agendamento.crm.model.Procedimentos;
import com.agendamento.crm.repository.ProcedimentosRepository;

@Service
public class procedimentoService {
	private final ProcedimentosRepository procedimentosRepository;

   
    public procedimentoService(ProcedimentosRepository procedimentosRepository) {
        this.procedimentosRepository = procedimentosRepository;
    }

    // Métodos de serviço, como buscar procedimentos
    public List<Procedimentos> listarProcedimentos() {
        return procedimentosRepository.findNome();
    }

	public static Procedimentos findByNome(String procedimento) {
		// TODO Auto-generated method stub
		return null;
	}
}