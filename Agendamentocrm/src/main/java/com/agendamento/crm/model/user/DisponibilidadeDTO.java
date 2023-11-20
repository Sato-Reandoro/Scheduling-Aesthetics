package com.agendamento.crm.model.user;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.agendamento.crm.model.Disponibilidade;

public class DisponibilidadeDTO {
    private Long id;
    private Long funcionarioId;
    private List<Long> procedimentoIds;
    private LocalDateTime dataHora;
    private Date dataFim;
    private String status;
    private String observacoes;
    private int capacidade;
    private Long clienteId;
    private String motivoCancelamento;
    

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

    public List<Long> getProcedimentoId() {
        return procedimentoIds;
    }

    public void setProcedimentoId(List<Long> procedimentoId) {
        this.procedimentoIds = procedimentoId;
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
    
    

	public static DisponibilidadeDTO toDTO(Disponibilidade disponibilidade) {
    	DisponibilidadeDTO dto = new DisponibilidadeDTO();
        dto.setId(disponibilidade.getId());
        dto.setDataHora(disponibilidade.getDataHora());
        dto.setDataFim(disponibilidade.getDataFim());
        dto.setFuncionarioId(disponibilidade.getFuncionario().getId());
        dto.setProcedimentoId(Collections.singletonList(disponibilidade.getProcedimento().getId()));
      

        // Defina outros atributos do DTO aqui com base nos atributos da entidade.

        return dto;
    }
    
}
