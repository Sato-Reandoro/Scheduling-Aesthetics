package com.agendamento.crm.model.user;


public record RegisterDTO(String login, String senha, UsuarioRole role) {
}