package com.agendamento.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendamento.crm.model.Disponibilidade;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {

	List<Disponibilidade> findAll();
    // Você pode definir consultas personalizadas, se necessário.
}
