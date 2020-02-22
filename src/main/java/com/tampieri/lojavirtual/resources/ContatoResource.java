package com.tampieri.lojavirtual.resources;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tampieri.lojavirtual.domain.Contato;
import com.tampieri.lojavirtual.dto.ContatoDTO;
import com.tampieri.lojavirtual.services.ContatoService;

@RestController
@RequestMapping(value="/contatos")
public class ContatoResource {
	
	@Autowired
	private ContatoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Contato> find(@PathVariable Integer id) {
		Contato obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Contato> find(@RequestParam(value="value") String email) {
		Contato obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ContatoDTO objDto) {
		Contato obj = new Contato();
		obj.setNome(objDto.getNome());
		obj.setEmail(objDto.getEmail());
		obj.setMensagem(objDto.getMensagem());
		obj.setDataInclusao(new Date());
		obj.setStatus(true);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/*
	 * @RequestMapping(value="/{id}", method=RequestMethod.PUT) public
	 * ResponseEntity<Void> update(@Valid @RequestBody ContatoDTO
	 * objDto, @PathVariable Integer id) { Contato obj =
	 * service.findByEmail(objDto.getEmail()); obj.setId(id); obj =
	 * service.update(obj); return ResponseEntity.noContent().build(); }
	 */
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ContatoDTO>> findAll() {
		List<Contato> list = service.findAll();
		List<ContatoDTO> listDto = list.stream().map(obj -> new ContatoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ContatoDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Contato> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ContatoDTO> listDto = list.map(obj -> new ContatoDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}
}