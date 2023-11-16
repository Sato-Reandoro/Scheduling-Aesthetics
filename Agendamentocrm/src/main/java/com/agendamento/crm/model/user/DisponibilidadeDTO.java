package com.agendamento.crm.model.user;

import java.sql.Date;
import java.time.LocalDateTime;

import com.agendamento.crm.model.Disponibilidade;

public class DisponibilidadeDTO {
    private Long id;
    private Long funcionarioId;
    private Long procedimentoId;
    private LocalDateTime dataHora;
    private Date dataFim;
    private String status;
    private String observacoes;
    private int capacidade;
    private Long clienteId;
    private String motivoCancelamento;
    private Long areasCorpoId;

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Long getProcedimentoId() {
        return procedimentoId;
    }

    public void setProcedimentoId(Long procedimentoId) {
        this.procedimentoId = procedimentoId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    
    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
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

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }
    
    
    public Long getAreasCorpoId() {
		return areasCorpoId;
	}

	public void setAreasCorpoId(Long areasCorpoId) {
		this.areasCorpoId = areasCorpoId;
	}

	public static DisponibilidadeDTO toDTO(Disponibilidade disponibilidade) {
    	DisponibilidadeDTO dto = new DisponibilidadeDTO();
        dto.setId(disponibilidade.getId());
        dto.setDataHora(disponibilidade.getDataHora());
        dto.setDataFim(disponibilidade.getDataFim());
        dto.setFuncionarioId(disponibilidade.getFuncionario().getId());
        dto.setProcedimentoId(disponibilidade.getProcedimento().getId());
        dto.setAreasCorpoId(disponibilidade.getAreasCorpo().getID());

        // Defina outros atributos do DTO aqui com base nos atributos da entidade.

        return dto;
    }
    
}
