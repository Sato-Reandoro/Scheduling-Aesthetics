package com.agendamento.crm.model.user;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "users")
@Entity(name = "users")

public class Usuario implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private UsuarioRole role;

    public Usuario(String login, String senha, UsuarioRole role) {
        this.role = role;
    }

    
  



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UsuarioRole getRole() {
		return role;
	}


	public void setRole(UsuarioRole role) {
		this.role = role;
	}




	  @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        if (this.role == UsuarioRole.ADMIN) {
	            // Defina as permissões/roles apropriadas para administradores
	            return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
	        } else if (this.role == UsuarioRole.USUARIO_CLI) {
	            // Defina as permissões/roles apropriadas para clientes
	            return Collections.singleton(new SimpleGrantedAuthority("ROLE_CLIENTE"));
	        } else if (this.role == UsuarioRole.USUARIO_FUN) {
	            // Defina as permissões/roles apropriadas para funcionários
	            return Collections.singleton(new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
	        } else {
	            // Defina outras permissões/roles, se necessário
	            return Collections.singleton(new SimpleGrantedAuthority("ROLE_USUARIO"));
	        }
	    }

	    @Override
	    public String getUsername() {
	        // Retorne o nome de usuário apropriado com base na sua lógica
	        // Por exemplo, retorne o ID do usuário ou outro identificador exclusivo
	        return id;
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