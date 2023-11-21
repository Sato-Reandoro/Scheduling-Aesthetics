package com.agendamento.crm.model.user;


import java.time.LocalDateTime;



public class AgendamentoDTO {
    private String cpfCliente;
    private LocalDateTime dataProximaSessao;
    private String nomeFuncionario; // Adicione essa linha

    // Construtores, getters e setters

    public AgendamentoDTO() {
    }

    public AgendamentoDTO(String cpfCliente, LocalDateTime dataProximaSessao, String idFuncionario) {
        this.cpfCliente = cpfCliente;
        this.dataProximaSessao = dataProximaSessao;
        this.nomeFuncionario = nomeFuncionario; // Adicione essa linha
    }

    // Getters e Setters
    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public LocalDateTime getDataProximaSessao() {
        return dataProximaSessao;
    }

    public void setDataProximaSessao(LocalDateTime dataProximaSessao) {
        this.dataProximaSessao = dataProximaSessao;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }


}
