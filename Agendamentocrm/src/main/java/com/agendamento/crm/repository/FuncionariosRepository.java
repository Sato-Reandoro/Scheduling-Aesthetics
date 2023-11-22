package com.agendamento.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agendamento.crm.model.Funcionarios;
import com.agendamento.crm.model.user.Usuario;

@Repository
public interface FuncionariosRepository extends JpaRepository<Funcionarios, Long> {
    Optional<Funcionarios> findByEmailAndSenha(String email, String senha);
    Optional<Funcionarios> findByCpf(String cpf);
    Optional<Funcionarios> findById(long id);
    Optional<Funcionarios> findByNome(String nomeFuncionario);
	void save(Usuario newUser);

}
