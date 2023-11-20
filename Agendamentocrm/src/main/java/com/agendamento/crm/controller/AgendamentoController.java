package com.agendamento.crm.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.agendamento.crm.repository.DisponibilidadeRepository;
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
	
	@Autowired
	private DisponibilidadeRepository disponibilidadeRepository;
	
	@GetMapping("/listar")
	public List<Agendamento> listarTodosAgendamentos(){
		return agendamentosRepository.findAll();
	}
	
	@GetMapping("/listar/funcionario")
	public List<Agendamento> listarAgendamentoPorProfissional(@PathVariable Funcionarios funcionario){
		return agendamentosRepository.findByFuncionario(funcionario);
	}
	
	@PostMapping("/criar-agendamento")
	public ResponseEntity<?> criarAgendamento(@RequestBody AgendamentoRequest agendamentoRequest) {
	    if (agendamentoRequest.getDataAgendamento() != null && agendamentoRequest.getHoraAgendamento() != null) {
 	        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	        LocalDate dataAgendamentoStr = agendamentoRequest.getDataAgendamento();
	        LocalTime horaAgendamentoStr = agendamentoRequest.getHoraAgendamento();

	        String nomeCliente = agendamentoRequest.getNomeCliente();
	        String nomeFuncionario = agendamentoRequest.getNomeFuncionario();
	        String nomeProcedimento = agendamentoRequest.getNomeProcedimento();
	        String nomeAreaCorpo = agendamentoRequest.getNomeAreaCorpo();

	        Clientes cliente = clientesRepository.findByNome(nomeCliente);
	        Funcionarios funcionario = funcionariosRepository.findByNome(nomeFuncionario);
	        Procedimentos procedimento = procedimentosRepository.findByNome(nomeProcedimento);
	        AreasCorpo areaCorpo = areasCorpoRepository.findByNome(nomeAreaCorpo);

	        if (cliente == null || funcionario == null || procedimento == null || areaCorpo == null) {
	            return ResponseEntity.badRequest().body("Alguma das entidades não foi encontrada.");
	        }

	     // Verificar se existe uma disponibilidade para o funcionário no horário desejado
	        if (disponibilidadeRepository.existsByFuncionarioAndDataHoraAndStatus(funcionario, LocalDateTime.of(dataAgendamentoStr, horaAgendamentoStr), "Disponível")) {
	            Agendamento agendamento = new Agendamento();
	            agendamento.setClientes(cliente);
	            agendamento.setFuncionario(funcionario);
	            agendamento.setProcedimentos(Set.of(procedimento)); // Usando Set.of para criar um conjunto com um único procedimento
	            agendamento.setAreaCorpo(areaCorpo);
	            agendamento.setDataAgendamento(dataAgendamentoStr);
	            agendamento.setHoraAgendamento(horaAgendamentoStr);
	            agendamento.setStatus("Agendado"); // Defina o status conforme necessário

	            agendamentosRepository.save(agendamento);

	            return ResponseEntity.ok("Agendamento criado com sucesso.");
	        } else {
	            return ResponseEntity.badRequest().body("Não há disponibilidade para o funcionário no horário desejado.");
	        }
	    } else {
	        return ResponseEntity.badRequest().body("Data e hora de agendamento não podem ser nulas ou vazias.");
	    }
	}

	@PutMapping("/atualizar-agendamento/{id}")
	public ResponseEntity<?> atualizarAgendamento(@PathVariable Long id, @RequestBody AgendamentoRequest agendamentoRequest) {
	    Optional<Agendamento> agendamentoExistente = agendamentosRepository.findById(id);
	    if (agendamentoExistente.isPresent()) {
	        Agendamento agendamentoAtualizado = agendamentoExistente.get();

	        // Lógica para validar e atualizar o agendamento
	        if (agendamentoRequest.getDataAgendamento() != null && agendamentoRequest.getHoraAgendamento() != null) {
	            // Verificar se existe uma disponibilidade para o funcionário no horário desejado
	            if (disponibilidadeRepository.existsByFuncionarioAndDataHoraAndStatus(agendamentoAtualizado.getFuncionario(), LocalDateTime.of(agendamentoRequest.getDataAgendamento(), agendamentoRequest.getHoraAgendamento()), "Disponível")) {
	                // Atualize os campos conforme necessário
	                agendamentoAtualizado.setDataAgendamento(agendamentoRequest.getDataAgendamento());
	                agendamentoAtualizado.setHoraAgendamento(agendamentoRequest.getHoraAgendamento());
	                agendamentoAtualizado.setStatus("NovoStatus");  // Substitua "NovoStatus" pelo status desejado
	                // Adicione lógica de atualização para outros campos, se necessário
	                String nomeAreaCorpo = agendamentoRequest.getNomeAreaCorpo();
	                String nomeFuncionario = agendamentoRequest.getNomeFuncionario();
	                String nomeProcedimento = agendamentoRequest.getNomeProcedimento();

	                if (nomeAreaCorpo != null) {
	                    AreasCorpo areaCorpo = areasCorpoRepository.findByNome(nomeAreaCorpo);
	                    agendamentoAtualizado.setAreaCorpo(areaCorpo);
	                }

	                if (nomeFuncionario != null) {
	                    Funcionarios funcionario = funcionariosRepository.findByNome(nomeFuncionario);
	                    agendamentoAtualizado.setFuncionario(funcionario);
	                }

	                if (nomeProcedimento != null) {
	                    Procedimentos procedimento = procedimentosRepository.findByNome(nomeProcedimento);
	                    agendamentoAtualizado.setProcedimentos(Set.of(procedimento));
	                }

	                agendamentosRepository.save(agendamentoAtualizado);
	                return ResponseEntity.ok("Agendamento atualizado com sucesso.");
	            } else {
	                return ResponseEntity.badRequest().body("Não há disponibilidade para o funcionário no horário desejado.");
	            }
	        } else {
	            return ResponseEntity.badRequest().body("Data e hora de agendamento não podem ser nulas ou vazias.");
	        }
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	    
	
	@DeleteMapping("/cancelar-agendamento/{id}")
	public ResponseEntity<?> cancelarAgendamento(@PathVariable Long id) {
	    Optional<Agendamento> agendamentoExistente = agendamentosRepository.findById(id);
	    if (agendamentoExistente.isPresent()) {
	        Agendamento agendamento = agendamentoExistente.get();
	        // Verifica se o agendamento já foi concluído
	        if (agendamento.getDataHoraConclusao() != null) {
	            return ResponseEntity.badRequest().body("O agendamento já foi concluído e não pode ser cancelado.");
	        }
	        // Cancela o agendamento
	        try {
	            agendamentosRepository.deleteById(id);
	            return ResponseEntity.ok("Agendamento cancelado com sucesso.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cancelar o agendamento: " + e.getMessage());
	        }
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
}