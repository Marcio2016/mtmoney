package com.mt.mtmoney.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mt.mtmoney.api.model.Lancamento;
import com.mt.mtmoney.api.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repository;
	
	public List<Lancamento> buscarTodos() {
		return repository.findAll();
	}
	
	public Optional<Lancamento> buscarPeloCodigo(Long codigo) {
		Optional<Lancamento> lancamento = repository.findById(codigo);
		
		return lancamento;
	}
}
