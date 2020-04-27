package com.mt.mtmoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mt.mtmoney.api.evento.RecursoCriadoEvent;
import com.mt.mtmoney.api.model.Lancamento;
import com.mt.mtmoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService service;
	
	@Autowired
	private ApplicationEventPublisher publisher; 
	
	@GetMapping
	public List<Lancamento> buscarTodos() {
		return service.buscarTodos();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
		
		Optional<Lancamento> lancamento = service.buscarPeloCodigo(codigo);
		
		return lancamento.isPresent() ?
				ResponseEntity.ok(lancamento.get()) :  ResponseEntity.notFound().build();		
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> salvar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Lancamento lancamentoSalvo = service.salvar(lancamento);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);		
	}
	
}
