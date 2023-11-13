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
                .requestMatchers(HttpMethod.GET, "/funcionarios", "/agendamentos").hasAnyRole("ADMIN", "FUNCIONARIO", "USUARIO_CLI")
                .requestMatchers(HttpMethod.POST, "/areas-corpo", "/procedimentos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/agendamentos").hasRole("USUARIO_CLI")
                .requestMatchers(HttpMethod.PUT, "/agendamentos").hasAnyRole("ADMIN", "FUNCIONARIO", "USUARIO_CLI")
                .requestMatchers(HttpMethod.DELETE, "/agendamentos").hasAnyRole("ADMIN", "FUNCIONARIO", "USUARIO_CLI")
                .requestMatchers(HttpMethod.POST, "/funcionarios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/funcionario/procedimentos").hasRole("FUNCIONARIO")
                .requestMatchers(HttpMethod.POST, "/funcionario/disponibilidade").hasRole("FUNCIONARIO")
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
