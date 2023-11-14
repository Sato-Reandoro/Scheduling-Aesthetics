package com.agendamento.crm.model.user;

import java.time.LocalDate;



public class AgendamentoDTO {
    private String cpfCliente;
    private LocalDate dataProximaSessao;
    private String idFuncionario; // Adicione essa linha

    // Construtores, getters e setters

    public AgendamentoDTO() {
    }

    public AgendamentoDTO(String cpfCliente, LocalDate dataProximaSessao, String idFuncionario) {
        this.cpfCliente = cpfCliente;
        this.dataProximaSessao = dataProximaSessao;
        this.idFuncionario = idFuncionario; // Adicione essa linha
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

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
}
