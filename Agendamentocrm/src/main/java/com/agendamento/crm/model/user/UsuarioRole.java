package com.agendamento.crm.model.user;

public enum UsuarioRole {

	ADMIN("admin"),
	FUNCIONARIO("fun"),
	USUARIO("usuario");
	
	private String role;
	
	UsuarioRole(String role){
		this.setRole(role);
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
