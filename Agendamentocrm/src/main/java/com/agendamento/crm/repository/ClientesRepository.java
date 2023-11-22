package com.agendamento.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agendamento.crm.model.Clientes;
import com.agendamento.crm.model.user.Usuario;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {
    Optional<Clientes> findByEmailAndSenha(String email, String senha);
    Clientes findByCpf(String cpf);
    Clientes findByNome(String nomeCliente);
    Optional<Clientes> findById(Long id);
	void save(Usuario newUser);
}
