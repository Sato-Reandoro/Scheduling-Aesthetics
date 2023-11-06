package com.agendamento.crm.model.user;

public enum UsuarioRole {

    ADMIN("admin"),
    FUNCIONARIO("fun"),
    USUARIO("usuario"),
    USUARIO_CLI("usuario_cli"),  // Nova role para clientes
    USUARIO_FUN("usuario_fun");  // Nova role para funcionários

    private String role;

    UsuarioRole(String role) {
        this.setRole(role);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
