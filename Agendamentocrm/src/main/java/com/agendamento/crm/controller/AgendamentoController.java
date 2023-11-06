package com.agendamento.crm.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendamento.crm.model.Agendamento;
import com.agendamento.crm.model.AgendamentoRequest;
import com.agendamento.crm.model.AreasCorpo;
import com.agendamento.crm.model.Clientes;
import com.agendamento.crm.model.Funcionarios;
import com.agendamento.crm.model.Procedimentos;
import com.agendamento.crm.repository.AgendamentosRepository;
import com.agendamento.crm.repository.AreasCorpoRepository;
import com.agendamento.crm.repository.ClientesRepository;
import com.agendamento.crm.repository.FuncionariosRepository;
import com.agendamento.crm.repository.ProcedimentosRepository;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

	
	//Ligando todos os repositories a fim de validar as informações de cada registro
	@Autowired
	private AgendamentosRepository agendamentosRepository;
	
	@Autowired
	private FuncionariosRepository funcionariosRepository;
	
	@Autowired
	private AreasCorpoRepository areasCorpoRepository;
	
	@Autowired
	private ClientesRepository clientesRepository;
	
	@Autowired
	private ProcedimentosRepository procedimentosRepository;
	
	@GetMapping("/todos")
	public List<Agendamento> listarTodosAgendamentos(){
		return agendamentosRepository.findAll();
	}
	
	@GetMapping("/{funcionario}")
	public List<Agendamento> listarAgendamentoPorProfissional(@PathVariable Funcionarios funcionario){
		return agendamentosRepository.findByFuncionario(funcionario);
	}
	
	@PostMapping
	public ResponseEntity<?> criarAgendamento(@RequestBody AgendamentoRequest agendamentoRequest) {
	    // Verifica se os valores de dataAgendamento e horaAgendamento não são nulos e não vazios
		if (agendamentoRequest.getDataAgendamento() != null && agendamentoRequest.getHoraAgendamento() != null) {
		 
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            // Obtém as datas e horas como strings
            LocalDate dataAgendamentoStr = agendamentoRequest.getDataAgendamento();
            LocalTime horaAgendamentoStr = agendamentoRequest.getHoraAgendamento();

	        // Obtém os nomes das entidades do request
	        String nomeCliente = agendamentoRequest.getNomeCliente();
	        String nomeFuncionario = agendamentoRequest.getNomeFuncionario();
	        String nomeProcedimento = agendamentoRequest.getNomeProcedimento();
	        String nomeAreaCorpo = agendamentoRequest.getNomeAreaCorpo();

	        // Valida os nomes das entidades e busca as entidades correspondentes do banco de dados
	        Clientes cliente = clientesRepository.findByNome(nomeCliente);
	        Funcionarios funcionario = funcionariosRepository.findByNome(nomeFuncionario);
	        Procedimentos procedimento = procedimentosRepository.findByNome(nomeProcedimento);
	        AreasCorpo areaCorpo = areasCorpoRepository.findByNome(nomeAreaCorpo);

	        // Verifica se as entidades foram encontradas
	        if (cliente == null || funcionario == null || procedimento == null || areaCorpo == null) {
	            return ResponseEntity.badRequest().body("Alguma das entidades não foi encontrada.");
	        }

	        // Agora você tem as entidades correspondentes com base nos nomes e pode criar o agendamento.
	        Agendamento agendamento = new Agendamento();
	        agendamento.setClientes(cliente);
	        agendamento.setFuncionarios(funcionario);
	        agendamento.setProcedimentos(procedimento);
	        agendamento.setAreasCorpo(areaCorpo);
	        LocalDate dataAgendamento = null;
			agendamento.setDataAgendamento(dataAgendamento);
	        LocalTime horaAgendamento = null;
			agendamento.setHoraAgendamento(horaAgendamento);

	        return ResponseEntity.ok("Agendamento criado com sucesso.");
		}
	        else {
	        return ResponseEntity.badRequest().body("Data e hora de agendamento não podem ser nulas ou vazias.");
	    }
	}
		}