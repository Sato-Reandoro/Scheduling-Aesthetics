package com.agendamento.crm.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonFormat;

public class AgendamentoRequest {
    private String cpfCliente;
    private String nomeFuncionario;
    private String nomeProcedimento;
    private String nomeAreaCorpo;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataAgendamento;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaAgendamento;

    // Getters

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public String getNomeProcedimento() {
        return nomeProcedimento;
    }

    public String getNomeAreaCorpo() {
        return nomeAreaCorpo;
    }

    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }

    public LocalTime getHoraAgendamento() {
        return horaAgendamento;
    }

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}


}
