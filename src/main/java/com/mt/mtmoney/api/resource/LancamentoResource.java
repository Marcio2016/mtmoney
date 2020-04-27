package com.mt.mtmoney.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mt.mtmoney.api.model.Lancamento;
import com.mt.mtmoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService service;
	
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
	
}
