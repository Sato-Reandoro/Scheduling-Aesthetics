
package com.agendamento.crm.model.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "users")
public class Usuario implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;
    private UsuarioRole role;

    public Usuario() {
        // Construtor padrão necessário para JPA
    }

    public Usuario(String email, String senha, UsuarioRole role) {
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

   

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Lógica para determinar as permissões/roles com base na role
        if (this.role == UsuarioRole.ADMIN) {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (this.role == UsuarioRole.USUARIO_CLI) {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        } else if (this.role == UsuarioRole.USUARIO_FUN) {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
        } else {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_USUARIO"));
        }
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

   
}
