package com.agendamento.crm.model;

import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Procedimentos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToMany(mappedBy = "procedimentos")
    private Set<Agendamento> agendamentos;
    
    @ManyToMany(mappedBy = "procedimentos")
    private Set<Funcionarios> funcionarios = new HashSet<>();
    
	@Column(nullable = false)
    private String nome;
	
    private String descricao;
    
    @Column(nullable = false)
    private double preco;

    //Getters e Setters para os campos.	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

}