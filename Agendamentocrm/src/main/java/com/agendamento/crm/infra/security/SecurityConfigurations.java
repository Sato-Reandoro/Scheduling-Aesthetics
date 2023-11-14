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
        return httpSecurity
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/login", "/auth/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/areas-corpo", "/procedimentos").permitAll()
                .requestMatchers(HttpMethod.GET, "/funcionarios", "/agendamentos").hasAnyRole("ADMIN", "USUARIO_FUN")
                .requestMatchers(HttpMethod.GET, "/funcionarios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/clientes").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/areas-corpo", "/procedimentos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/agendamentos").hasAnyRole("USUARIO_CLI, USUARIO_FUN")
                .requestMatchers(HttpMethod.PUT, "/agendamentos").hasAnyRole("ADMIN", "USUARIO_FUN", "USUARIO_CLI")
                .requestMatchers(HttpMethod.DELETE, "/agendamentos").hasAnyRole("ADMIN", "USUARIO_FUN", "USUARIO_CLI")
                .requestMatchers(HttpMethod.DELETE, "/funcionario") .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/clientes").hasAnyRole("USUARIO_CLI, ADMIN")
                .requestMatchers(HttpMethod.PUT, "/funcionarios").hasRole("ADMIN")
                
                .requestMatchers(HttpMethod.POST, "/funcionario/procedimentos").hasRole("USUARIO_FUN")
                .requestMatchers(HttpMethod.POST, "/funcionario/disponibilidade").hasRole("USUARIO_FUN")
                .requestMatchers(HttpMethod.GET, "/funcionario/disponibilidade").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/register-client").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/register-employee").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/auth/register-admin").hasRole("ADMIN")
       
                .anyRequest().authenticated()
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
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
