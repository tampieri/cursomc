package com.tampieri.lojavirtual.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tampieri.lojavirtual.domain.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {
	
	@Transactional(readOnly=true)
	Contato findByEmail(String email);
	
	@Transactional(readOnly=true)
	List<Contato> findAll();

	
}
