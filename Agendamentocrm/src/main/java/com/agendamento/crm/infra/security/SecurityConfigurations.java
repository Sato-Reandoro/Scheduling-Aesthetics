package com.agendamento.crm.infra.security;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;

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

    
    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
            		.requestMatchers(HttpMethod.POST,"/auth/login").permitAll() 
            		.requestMatchers(HttpMethod.GET, "/areas-corpo/listar").permitAll()
            		.requestMatchers(HttpMethod.GET, "/procedimentos/listar").permitAll()
            		.requestMatchers(HttpMethod.GET, "/areas-corpo/listar/{id}").permitAll()
            		.requestMatchers(HttpMethod.GET, "/procedimentos/listar/{id}").permitAll()
            		.requestMatchers(HttpMethod.PUT, "/Admin/atualizar/{id}").hasRole("ADMIN")
            		.requestMatchers(HttpMethod.GET, "/funcionario/listar").hasRole("ADMIN")
            		.requestMatchers(HttpMethod.GET, "/funcionario/listar/{id}").hasRole("ADMIN")
            		.requestMatchers(HttpMethod.GET, "/clientes/listar").hasRole("ADMIN")
            		.requestMatchers(HttpMethod.GET, "/clientes/listar/{id}").hasRole("ADMIN")
            		.requestMatchers(HttpMethod.DELETE, "/funcionarios/apagar/{id}").hasRole("ADMIN")
            		.requestMatchers(HttpMethod.DELETE, "/Admin/apagar/{id}").hasRole("ADMIN")
            		.requestMatchers(HttpMethod.DELETE, "/clientes/apagar-conta").hasRole("USUARIO_CLI")
            		.requestMatchers(HttpMethod.POST, "/auth/register-client").permitAll()
            		.requestMatchers(HttpMethod.POST, "/auth/register-employee").hasRole("ADMIN")
            		.requestMatchers(HttpMethod.POST, "/auth/register-admin").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/agendamento/listar").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/agendamento/listar/funcionario").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/clientes/apagar/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/clientes/atualizar/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/disponibilidade//gerenciar-disponibilidade").hasRole("USUARIO_FUN")
                    .requestMatchers(HttpMethod.GET, "/disponibilidade/listar").hasAnyRole("ADMIN, USUARIO_CLI, USUARIO_FUN")
                    .requestMatchers(HttpMethod.POST, "/funcionarios/agendar-sessao").hasRole("USUARIO_FUN")
                    .requestMatchers(HttpMethod.POST, "/agendamentos/criar-agendamento").hasRole("USUARIO_CLI")
                    .requestMatchers(HttpMethod.PUT, "/areas-corpo/atualizar/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/areas-corpo/apagar/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/procedimentos/atualizar/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/procedimentos/apagar/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/areas-corpo/criar-areas-corpo").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/procedimentos/criar-procedimentos").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/Admin/listar").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/Admin/listar/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/funcionarios/atualizar/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/agendamento/cancelar-agendamento/{id}").hasAnyRole("ADMIN, USUARIO_FUN, USUARIO_CLI")
                    .requestMatchers(HttpMethod.PUT, "/agendamento/atualizar-agendamento/{id}").hasAnyRole("ADMIN, USUARIO_FUN, USUARIO_CLI")
                    .requestMatchers(HttpMethod.DELETE, "/disponibilidade/excluir-disponibilidade/{id}").hasRole("USUARIO_FUN")
                    .requestMatchers(HttpMethod.PUT, "/disponibilidade/atualizar-disponibilidade/{id}").hasRole("USUARIO_FUN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }



    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

   @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
