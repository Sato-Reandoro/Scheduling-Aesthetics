package com.agendamento.crm.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.agendamento.crm.model.Disponibilidade;
import com.agendamento.crm.model.Funcionarios;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {

    List<Disponibilidade> findAll();
    
    List<Disponibilidade> findAllByFuncionarioAndDataHoraBetween(Funcionarios funcionario, LocalDateTime atStartOfDay,
            LocalDateTime atStartOfDay2);

    List<Disponibilidade> findAllByFuncionarioAndDataHoraBetween(Funcionarios funcionario, LocalDateTime dataHora,
            Date dataFim);

    @Query("SELECT COUNT(d) > 0 FROM Disponibilidade d " +
           "WHERE d.funcionario = :funcionario " +
           "AND d.dataHora = :dataHora " +
           "AND d.status = :status")
    boolean existsByFuncionarioAndDataHoraAndStatus(
        @Param("funcionario") Funcionarios funcionario,
        @Param("dataHora") LocalDateTime dataHora,
        @Param("status") String status
    );
}
