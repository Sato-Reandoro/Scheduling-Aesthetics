package com.agendamento.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendamento.crm.controller.service.AgendamentoService;
import com.agendamento.crm.controller.service.ClientesService;
import com.agendamento.crm.controller.service.FuncionarioService;
import com.agendamento.crm.model.Agendamento;
import com.agendamento.crm.model.Clientes;
import com.agendamento.crm.model.Funcionarios;
import com.agendamento.crm.model.user.AgendamentoDTO;
import com.agendamento.crm.repository.FuncionariosRepository;


import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionariosRepository funcionariosRepository;
	@Autowired
    private FuncionarioService funcionarioService;
    @Autowired
	private ClientesService clientesService;
    @Autowired
	private AgendamentoService agendamentoService;
	
	@GetMapping("/listar")
	public List<Funcionarios> listarFuncionarios(){
		return funcionariosRepository.findAll();
	}
	
	@GetMapping("/listar/{id}")
	public Funcionarios listarFuncionarioUnico(@PathVariable(value="id") long id) {
		return funcionariosRepository.findById(id);
	}
	
   
    
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionarios funcionarios) {
        Optional<Funcionarios> funcionarioExistente = funcionariosRepository.findById(id);
        if (funcionarioExistente.isPresent()) {
            // Verifica se o CPF é válido
            if (!validarCpf(funcionarios.getCpf())) {
                return ResponseEntity.badRequest().body("CPF inválido.");
            }
            // Valida a nova senha, se informada
            if (funcionarios.getSenha() != null && !funcionarios.getSenha().isEmpty() && !validarSenha(funcionarios.getSenha())) {
                return ResponseEntity.badRequest().body("A nova senha deve ter pelo menos 8 caracteres, um caractere maiúsculo, um caractere minúsculo, um número e um caractere especial.");
            }
            // Atualiza os dados do funcionário
            Funcionarios funcionarioAtualizado = funcionarioExistente.get();
            funcionarioAtualizado.setNome(funcionarios.getNome());
            funcionarioAtualizado.setRegistroProfissional(funcionarios.getRegistroProfissional());
            funcionarioAtualizado.setCpf(funcionarios.getCpf());
            funcionarioAtualizado.setCnpj(funcionarios.getCnpj());
            funcionarioAtualizado.setEndereco(funcionarios.getEndereco());
            funcionarioAtualizado.setBairro(funcionarios.getBairro());
            funcionarioAtualizado.setCidade(funcionarios.getCidade());
            funcionarioAtualizado.setEstado(funcionarios.getEstado());
            if (funcionarios.getSenha() != null && !funcionarios.getSenha().isEmpty()) {
                funcionarioAtualizado.setSenha(funcionarios.getSenha());
            }
            funcionarioAtualizado.setEmail(funcionarios.getEmail());
            funcionarioAtualizado.setTelefone(funcionarios.getTelefone());
            funcionariosRepository.save(funcionarioAtualizado);
            return ResponseEntity.ok(funcionarioAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    @PostMapping("/agendar-sessao")
    public ResponseEntity<String> agendarSessao(@RequestBody @Valid AgendamentoDTO agendamentoDTO) {
        // 1. Verificação de Disponibilidade
        boolean funcionarioDisponivel = funcionarioService.verificarDisponibilidade(
                agendamentoDTO.getIdFuncionario(),
                agendamentoDTO.getDataProximaSessao()
        );

        if (!funcionarioDisponivel) {
            return ResponseEntity.badRequest().body("Funcionário não está disponível na data solicitada.");
        }

     // 2. Verificação de Cliente Existente
        Clientes cliente = clientesService.findByCpf(agendamentoDTO.getCpfCliente());

        if (cliente == null) {
            return ResponseEntity.badRequest().body("Cliente não encontrado.");
        }

        
     // 3. Criação do Agendamento
        Agendamento agendamento = new Agendamento();
        agendamento.setDataSessao(agendamentoDTO.getDataProximaSessao());
        agendamento.setFuncionario(funcionarioService.findByCpf(agendamentoDTO.getIdFuncionario()));
        agendamento.setCliente(cliente);

        // Salve o agendamento no banco de dados
        agendamentoService.save(agendamento);

        // Use agendamentoDTO.getCpfCliente() e agendamentoDTO.getDataProximaSessao() para obter os dados

  

        // Faça o que for necessário para agendar a sessão

        return ResponseEntity.ok("Sessão agendada com sucesso!");
    }

    
    
    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<?> removerFuncionario(@PathVariable Long id) {
        Optional<Funcionarios> funcionarios = funcionariosRepository.findById(id);
        if (funcionarios.isPresent()) {
            funcionariosRepository.delete(funcionarios.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        
		public static boolean validarCpf(String cpf) {
		    cpf = cpf.replaceAll("[^0-9]", ""); // Remove caracteres não numéricos
		    if (cpf.length() != 11) {
		        return false;
		    }
		    int[] digitos = new int[11];
		    for (int i = 0; i < 11; i++) {
		        digitos[i] = Integer.parseInt(cpf.substring(i, i + 1));
		    }
		    // Verifica se todos os dígitos são iguais
		    if (digitos[0] == digitos[1] && digitos[1] == digitos[2] && digitos[2] == digitos[3] &&
		        digitos[3] == digitos[4] && digitos[4] == digitos[5] && digitos[5] == digitos[6] &&
		        digitos[6] == digitos[7] && digitos[7] == digitos[8] && digitos[8] == digitos[9] &&
		        digitos[9] == digitos[10]) {
		        return false;
		    }
		    // Verifica o primeiro dígito verificador
		    int soma = 0;
		    for (int i = 0; i < 9; i++) {
		        soma += digitos[i] * (10 - i);
		    }
		    int resto = soma % 11;
		    int digitoVerificador1 = resto < 2 ? 0 : 11 - resto;
		    if (digitos[9] != digitoVerificador1) {
		        return false;
		    }
		    // Verifica o segundo dígito verificador
		    soma = 0;
		    for (int i = 0; i < 10; i++) {
		        soma += digitos[i] * (11 - i);
		    }
		    resto = soma % 11;
		    int digitoVerificador2 = resto < 2 ? 0 : 11 - resto;
		    if (digitos[10] != digitoVerificador2) {
		        return false;
		    }
		    return true;
    }
		
	    public static boolean validarSenha(String senha) {
	        // Verifica se a senha tem pelo menos 8 caracteres
	        if (senha.length() < 8) {
	            return false;
	        }
	        // Verifica se a senha contém pelo menos um caractere maiúsculo, um caractere minúsculo, um número e um caractere especial
	        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(senha);
	        return matcher.matches();
	    }
}
