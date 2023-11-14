package com.agendamento.crm.model.user;

import java.time.LocalDate;

public class AgendamentoDTO {
    private String cpfCliente;
    private LocalDate dataProximaSessao;

    // Construtores, getters e setters

    public AgendamentoDTO() {
    }

    public AgendamentoDTO(String cpfCliente, LocalDate dataProximaSessao) {
        this.cpfCliente = cpfCliente;
        this.dataProximaSessao = dataProximaSessao;
    }

    // Getters e Setters
    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public LocalDate getDataProximaSessao() {
        return dataProximaSessao;
    }

    public void setDataProximaSessao(LocalDate dataProximaSessao) {
        this.dataProximaSessao = dataProximaSessao;
    }

	public Object getIdFuncionario() {
		// TODO Auto-generated method stub
		return null;
	}
}
