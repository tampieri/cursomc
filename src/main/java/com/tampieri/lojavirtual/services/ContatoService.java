package com.tampieri.lojavirtual.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tampieri.lojavirtual.domain.Contato;
import com.tampieri.lojavirtual.domain.enums.Perfil;
import com.tampieri.lojavirtual.repositories.ContatoRepository;
import com.tampieri.lojavirtual.security.UserSS;
import com.tampieri.lojavirtual.services.exceptions.AuthorizationException;
import com.tampieri.lojavirtual.services.exceptions.DataIntegrityException;
import com.tampieri.lojavirtual.services.exceptions.ObjectNotFoundException;

@Service
public class ContatoService {
	
	@Autowired
	private ContatoRepository repo;
	
	public Contato find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Contato> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Contato.class.getName()));
	}
	
	@Transactional
	public Contato insert(Contato obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}
	
	public Contato update(Contato obj) {
		Contato newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
		}
	}
	
	public List<Contato> findAll() {
		return repo.findAll();
	}
	
	public Contato findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())){
			throw new AuthorizationException("Acesso Negado");
		}
		
		Contato obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Contato.class.getName());
		}
		return obj;
	}
	
	public Page<Contato> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	private void updateData(Contato newObj, Contato obj) {
		newObj.setStatus(obj.isStatus());
	}
}