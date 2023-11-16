package com.agendamento.crm.model;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Disponibilidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @ManyToOne
    private AreasCorpo areasCorpo;
    
    @ManyToOne
    private Funcionarios funcionario; // Referência ao funcionário associado à disponibilidade

    @ManyToOne
    private Procedimentos procedimento; // Referência ao procedimento disponível

    private LocalDateTime dataHora; // Data e hora da disponibilidade

    private String status; // Status da disponibilidade (disponível, agendado, cancelado, etc.)

    private String observacoes; // Observações adicionais

    private int capacidade; // Capacidade de agendamentos para esse horário
    
    @Column(name = "data_fim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;



	@ManyToOne
    private Clientes cliente; // Referência ao cliente agendado, se aplicável

    private String motivoCancelamento; // Motivo de cancelamento, se a disponibilidade for cancelada

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	

	public AreasCorpo getAreasCorpo() {
		return areasCorpo;
	}

	public void setAreasCorpo(AreasCorpo areasCorpo) {
		this.areasCorpo = areasCorpo;
	}

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}

	public Procedimentos getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(Procedimentos procedimento) {
		this.procedimento = procedimento;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	
    public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public Object getProcedimentoId() {
		// TODO Auto-generated method stub
		return null;
	}

    // Getters e setters
    
    
}