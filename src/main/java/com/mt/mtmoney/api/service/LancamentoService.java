package com.mt.mtmoney.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mt.mtmoney.api.model.Lancamento;
import com.mt.mtmoney.api.model.Pessoa;
import com.mt.mtmoney.api.repository.LancamentoRepository;
import com.mt.mtmoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private PessoaService pessoaService;
	
	public List<Lancamento> buscarTodos() {
		return repository.findAll();
	}
	
	public Optional<Lancamento> buscarPeloCodigo(Long codigo) {
		Optional<Lancamento> lancamento = repository.findById(codigo);
		
		return lancamento;
	}
	
	public Lancamento salvar(Lancamento lancamento) {
		
		Pessoa pessoa = pessoaService.buscarPessoaPorCodigo(lancamento.getPessoa().getCodigo());
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return repository.save(lancamento);
	}

	public void remover(Long codigo) {
		repository.deleteById(codigo);		
	}
}
