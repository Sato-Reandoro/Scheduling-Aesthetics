package com.agendamento.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.agendamento.crm.model.Funcionarios;

@Repository
public interface FuncionariosRepository extends JpaRepository<Funcionarios, Long>{

	Optional<Funcionarios> findByCpf(String cpf);
	Funcionarios findById(long id);
    Funcionarios findByNome(String nomeFuncionario);
	Object findByLogin(Object login);
	Optional<Funcionarios> findByEmail(String emailFuncionario);

	
	

}
