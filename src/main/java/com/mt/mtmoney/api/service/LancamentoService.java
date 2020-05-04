package com.mt.mtmoney.api.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mt.mtmoney.api.model.Lancamento;
import com.mt.mtmoney.api.model.Pessoa;
import com.mt.mtmoney.api.repository.LancamentoRepository;
import com.mt.mtmoney.api.repository.PessoaRepository;
import com.mt.mtmoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	PessoaRepository pessoaRepository;
	
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

	public Lancamento atualizar(Long codigo, @Valid Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}

		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

		return repository.save(lancamentoSalvo);
	}
	
	private void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;
		if (lancamento.getPessoa().getCodigo() != null) {
			pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo())
					.orElseThrow(() -> new EmptyResultDataAccessException(1));
		}

		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}
	
	public Lancamento buscarLancamentoExistente(Long codigo) {
		Lancamento lancamentoSalvo = this.repository.findById(codigo)
				.orElseThrow(() -> new IllegalArgumentException());
				return lancamentoSalvo;		
	}
}
