package com.agendamento.crm.repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendamento.crm.model.Disponibilidade;
import com.agendamento.crm.model.Funcionarios;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {

	List<Disponibilidade> findAll();
    // Você pode definir consultas personalizadas, se necessário.

	List<Disponibilidade> findAllByFuncionarioAndDataHoraBetween(Funcionarios funcionario, LocalDateTime atStartOfDay,
			LocalDateTime atStartOfDay2);

	List<Disponibilidade> findAllByFuncionarioAndDataHoraBetween(Funcionarios funcionario, LocalDateTime dataHora,
			Date dataFim);
	
}
