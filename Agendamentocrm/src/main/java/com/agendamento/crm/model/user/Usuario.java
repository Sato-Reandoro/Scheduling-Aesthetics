package com.agendamento.crm.model.user;
import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "users")

public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String login;
    private String senha;
    private UsuarioRole role;

    public Usuario(String login, String senha, UsuarioRole role){
        this.login = login;
        this.senha = senha;
        this.role = role;
    }

    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}



	public String getSenha() {
		return senha;
	}



	public void setSenha(String senha) {
		this.senha = senha;
	}


	public UsuarioRole getRole() {
		return role;
	}


	public void setRole(UsuarioRole role) {
		this.role = role;
	}





	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UsuarioRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USUARIO"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
    }

    @Override
    public String getUsername() {
        return login;
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


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
}