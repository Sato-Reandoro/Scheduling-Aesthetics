package com.agendamento.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agendamento.crm.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmailAndSenha(String email, String senha);
   Admin findById(long id);

static Object findByLogin(Object login) {
	// TODO Auto-generated method stub
	return null;
	
}
}
