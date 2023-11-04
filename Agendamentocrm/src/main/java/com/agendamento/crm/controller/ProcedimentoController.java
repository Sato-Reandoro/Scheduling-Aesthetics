package com.agendamento.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.agendamento.crm.model.Procedimentos;
import com.agendamento.crm.repository.ProcedimentosRepository;

@RestController
@RequestMapping("/api")
public class ProcedimentoController {

	@Autowired
	private ProcedimentosRepository procedimentosRepository;
	
	@GetMapping("/procedimentos")
	public List<Procedimentos> listarProcedimentos() {
		return procedimentosRepository.findAll();
	}

	@GetMapping("/procedimento/{id}")
	public Procedimentos listarProcedimentoUnico(@PathVariable(value="id") long id) {
		return procedimentosRepository.findById(id);
	}

	@PostMapping("/procedimentos")
	@ResponseStatus(HttpStatus.CREATED)
	public Procedimentos adicionarProcedimento (@RequestBody Procedimentos procedimentos) {
		return procedimentosRepository.save(procedimentos);
	}
	
	@DeleteMapping("/procedimentos")
	public void deletarProcedimento (@RequestBody Procedimentos procedimentos) {
		procedimentosRepository.delete(procedimentos);
	}
	
	@PutMapping("/procedimentos")
	public Procedimentos atualizarProcedimento (@RequestBody Procedimentos procedimentos) {
		return procedimentosRepository.save(procedimentos);
	}
}