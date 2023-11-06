package com.agendamento.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendamento.crm.model.Agendamento;
import com.agendamento.crm.model.Funcionarios;

public interface AgendamentosRepository extends JpaRepository<Agendamento, Long>{

	List<Agendamento> findByFuncionario(Funcionarios funcionario);

	
}
