package com.mt.mtmoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mt.mtmoney.api.model.Pessoa;
import com.mt.mtmoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	PessoaRepository repository;
	
	@GetMapping
	public List<Pessoa> listar() {
		return repository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodico(@PathVariable Long codigo) {
		
		Optional<Pessoa> pessoa = repository.findById(codigo);
		
		return pessoa.isPresent() ? 
				ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
		Pessoa pessoaSalva = repository.save(pessoa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(pessoaSalva.getCodigo()).toUri();	
		
		return ResponseEntity.created(uri).body(pessoaSalva);
		
	}
	
}
