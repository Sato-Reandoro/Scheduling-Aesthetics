package com.agendamento.crm.controller;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendamento.crm.infra.security.TokenService;
import com.agendamento.crm.model.Admin;
import com.agendamento.crm.model.Clientes;
import com.agendamento.crm.model.Funcionarios;
import com.agendamento.crm.model.user.AuthenticationDTO;
import com.agendamento.crm.model.user.LoginResponseDTO;
import com.agendamento.crm.model.user.RegisterRequest;
import com.agendamento.crm.model.user.Usuario;
import com.agendamento.crm.model.user.UsuarioRole;
import com.agendamento.crm.repository.AdminRepository;
import com.agendamento.crm.repository.ClientesRepository;
import com.agendamento.crm.repository.FuncionariosRepository;




@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientesRepository clientesRepository;
    @Autowired
    private FuncionariosRepository funcionariosRepository;
    @Autowired


  
    private AdminRepository adminrepository;
 
   
    @Autowired
    private TokenService tokenService;
    
    

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register-client")
    public ResponseEntity<String> registerClient(@RequestBody @Valid RegisterRequest registerRequest) {
        // Lógica de registro de clientes aqui
 
    	// Verifica se já existe um cliente com o mesmo login (email)



    	
    	
        // 1. Crie um objeto Clientes com base nos dados em registerRequest
        Clientes cliente = new Clientes();

     // Configure os campos do cliente com base nos dados em registerRequest
        cliente.setNome(registerRequest.getNome());
        cliente.setCpf(registerRequest.getCpf());
        cliente.setSenha(registerRequest.getSenha());
        cliente.setEmail(registerRequest.getEmail());
        cliente.setEndereco(registerRequest.getEndereco());
        cliente.setBairro(registerRequest.getBairro());
        cliente.setCidade(registerRequest.getCidade());
        cliente.setEstado(registerRequest.getEstado());
        cliente.setTelefone(registerRequest.getTelefone());
        
        // Atribua a role correta com base nos seus requisitos
        cliente.setRole(UsuarioRole.USUARIO_CLI); // Você deve definir essa enum com as roles necessárias

        // Defina os demais campos específicos do cliente (etinia, dataNascimento, sexo, tipoSanguineo, etc.)
        cliente.setEtinia(registerRequest.getEtinia());
        cliente.setDataNascimento(registerRequest.getDataNascimento());
        cliente.setSexo(registerRequest.getSexo());
        cliente.setTipoSanguineo(registerRequest.getTipoSanguineo());
        
     // 3. Valide os dados do cliente (por exemplo, CPF válido, senha forte, etc.)
        if (!validarCpf(cliente.getCpf())) {
            return ResponseEntity.badRequest().body("CPF inválido.");
        }

        if (!validarSenha(registerRequest.getSenha())) {
            return ResponseEntity.badRequest().body("A senha deve ter pelo menos 8 caracteres, um caractere maiúsculo, um caractere minúsculo, um número e um caractere especial.");
        }

     // Gere a senha criptografada
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.getSenha());
        cliente.setSenha(encryptedPassword);
        
     // Crie um objeto User com base nos dados em registerRequest
        Usuario newUser = new Usuario(cliente.getLogin(), cliente.getSenha(), UsuarioRole.USUARIO_CLI);

        
        
        // 4. Gere o token se necessário
        // Aqui você pode usar a lógica de geração de token, se aplicável

        // 5. Salve o cliente no banco de dados
        clientesRepository.save(cliente);
        
        
        return ResponseEntity.ok("Cliente registrado com sucesso.");
    }

    @PostMapping("/register-employee")
    public ResponseEntity<String> registerEmployee(@RequestBody @Valid RegisterRequest registerRequest) {
        // Lógica de registro de funcionários aqui

    	
    	
        // Crie um objeto Funcionarios com base nos dados em registerRequest
        Funcionarios funcionario = new Funcionarios();
        // Configure os campos do funcionário com base nos dados em registerRequest
        funcionario.setNome(registerRequest.getNome());
        funcionario.setCpf(registerRequest.getCpf());
        funcionario.setSenha(registerRequest.getSenha());
        funcionario.setEmail(registerRequest.getEmail());
        funcionario.setEndereco(registerRequest.getEndereco());
        funcionario.setBairro(registerRequest.getBairro());
        funcionario.setCidade(registerRequest.getCidade());
        funcionario.setEstado(registerRequest.getEstado());
        funcionario.setTelefone(registerRequest.getTelefone());
        
    
        // Defina os demais campos específicos de Funcionarios
        funcionario.setRegistroProfissional(registerRequest.getRegistroProfissional());
        funcionario.setCnpj(registerRequest.getCnpj());
        
     // 3. Valide os dados do cliente (por exemplo, CPF válido, senha forte, etc.)
        if (!validarCpf(funcionario.getCpf())) {
            return ResponseEntity.badRequest().body("CPF inválido.");
        }

        if (!validarSenha(registerRequest.getSenha())) {
            return ResponseEntity.badRequest().body("A senha deve ter pelo menos 8 caracteres, um caractere maiúsculo, um caractere minúsculo, um número e um caractere especial.");
        }

     // Gere a senha criptografada
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.getSenha());
        funcionario.setSenha(encryptedPassword);
        
        
        
        // Valide os dados, gere o token, etc.
        
        // Crie um objeto User com base nos dados em registerRequest
        Usuario newUser = new Usuario(funcionario.getLogin(), funcionario.getSenha(), UsuarioRole.USUARIO_FUN);
        
        
        // Salve o funcionário no banco de dados
        funcionariosRepository.save(funcionario);
        
  

        return ResponseEntity.ok("Funcionário registrado com sucesso.");
    }

    
    @PostMapping("/register-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody @Valid RegisterRequest registerRequest) {
        // Lógica de registro de funcionários aqui

        if (AdminRepository.findByLogin(registerRequest.getLogin()) != null) {
            return ResponseEntity.badRequest().body("Login já está em uso.");
        }
    	
    	
        // Crie um objeto Funcionarios com base nos dados em registerRequest
        Admin admin = new Admin();
        // Configure os campos do funcionário com base nos dados em registerRequest
        admin.setNome(registerRequest.getNome());
        admin.setCpf(registerRequest.getCpf());
        admin.setSenha(registerRequest.getSenha());
        admin.setEmail(registerRequest.getEmail());
        admin.setEndereco(registerRequest.getEndereco());
        admin.setBairro(registerRequest.getBairro());
        admin.setCidade(registerRequest.getCidade());
        admin.setEstado(registerRequest.getEstado());
        admin.setTelefone(registerRequest.getTelefone());
        
    
     
        
     // 3. Valide os dados do cliente (por exemplo, CPF válido, senha forte, etc.)
        if (!validarCpf(admin.getCpf())) {
            return ResponseEntity.badRequest().body("CPF inválido.");
        }

        if (!validarSenha(registerRequest.getSenha())) {
            return ResponseEntity.badRequest().body("A senha deve ter pelo menos 8 caracteres, um caractere maiúsculo, um caractere minúsculo, um número e um caractere especial.");
        }

     // Gere a senha criptografada
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.getSenha());
        admin.setSenha(encryptedPassword);
        
        
        
        // Valide os dados, gere o token, etc.
        
        // Crie um objeto User com base nos dados em registerRequest
        Usuario newUser = new Usuario(admin.getLogin(), admin.getSenha(), UsuarioRole.ADMIN);
        
        
        // Salve o funcionário no banco de dados
        adminrepository.save(admin);

        return ResponseEntity.ok("Funcionário registrado com sucesso.");
    }
    
    // Outros métodos e lógica aqui


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