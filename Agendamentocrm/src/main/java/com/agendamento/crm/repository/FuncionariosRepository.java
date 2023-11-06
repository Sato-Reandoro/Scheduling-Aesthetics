package com.agendamento.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agendamento.crm.model.Funcionarios;

@Repository
public interface FuncionariosRepository extends JpaRepository<Funcionarios, Long>{

	Funcionarios findById(long id);
    Funcionarios findByCpf(String cpf);
    Funcionarios findByNome(String nomeFuncionario);

}
