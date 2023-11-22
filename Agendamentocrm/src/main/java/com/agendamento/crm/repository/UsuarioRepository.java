package com.agendamento.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.agendamento.crm.model.user.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    UserDetails findByEmail(String email);
}
