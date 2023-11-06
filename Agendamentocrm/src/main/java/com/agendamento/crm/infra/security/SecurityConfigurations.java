package com.agendamento.crm.infra.security;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    
    
   
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .antMatchers("/auth/login", "/auth/register").permitAll()
                .antMatchers(HttpMethod.GET, "/areas-corpo", "/procedimentos").permitAll()
                .antMatchers(HttpMethod.GET, "/funcionarios", "/agendamentos").hasAnyRole("ADMIN", "FUNCIONARIO", "USUARIO_CLI")
                .antMatchers(HttpMethod.POST, "/areas-corpo", "/procedimentos").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/agendamentos").hasRole("USUARIO_CLI")
                .antMatchers(HttpMethod.PUT, "/agendamentos").hasAnyRole("ADMIN", "FUNCIONARIO", "USUARIO_CLI")
                .antMatchers(HttpMethod.DELETE, "/agendamentos").hasAnyRole("ADMIN", "FUNCIONARIO", "USUARIO_CLI")
                .antMatchers(HttpMethod.POST, "/funcionarios").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/funcionario/procedimentos").hasRole("FUNCIONARIO")
                .antMatchers(HttpMethod.POST, "/funcionario/disponibilidade").hasRole("FUNCIONARIO")
                .antMatchers(HttpMethod.GET, "/funcionario/disponibilidade").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }



    
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

   
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
