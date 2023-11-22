package com.agendamento.crm.model.user;


import java.time.LocalDateTime;



public class AgendamentoDTO {
    private String cpfCliente;
    private LocalDateTime dataProximaSessao;
    private String nomeFuncionario; 
    private String procedimento;
    private String areasCorpo;// Adicione essa linha
	private String setProcedimento;

    // Construtores, getters e setters

    public AgendamentoDTO() {
    }

    public AgendamentoDTO(String cpfCliente, LocalDateTime dataProximaSessao, String nomeFuncionario, 
    		String procedimento, String areasCorpo) {
        this.cpfCliente = cpfCliente;
        this.dataProximaSessao = dataProximaSessao;
        this.nomeFuncionario = nomeFuncionario; 
        this.setSetProcedimento((procedimento));
        this.setAreasCorpo((areasCorpo));// Adicione essa linha
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

	public String getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(String procedimento) {
		this.procedimento = procedimento;
	}

	public String getSetProcedimento() {
		return setProcedimento;
	}

	public void setSetProcedimento(String setProcedimento) {
		this.setProcedimento = setProcedimento;
	}

	public String getAreasCorpo() {
		return areasCorpo;
	}

	public void setAreasCorpo(String areasCorpo) {
		this.areasCorpo = areasCorpo;
	}

	


    
}
