package com.mt.mtmoney.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mt.mtmoney.api.model.Pessoa;
import com.mt.mtmoney.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	PessoaRepository repository;
	
	public Pessoa atualizar(Long codigo,Pessoa pessoa) {
		
		Pessoa pessoaCadastrada = buscarPessoaPorCodigo(codigo);
		
		BeanUtils.copyProperties(pessoa, pessoaCadastrada, "codigo");
		return repository.save(pessoaCadastrada);		
	}	
	
	public List<Pessoa> listarTodos() {
		return repository.findAll();
	}
	
	public Optional<Pessoa> buscarPeloCodigo(Long codigo) {
		Optional<Pessoa> pessoa = repository.findById(codigo);				
		 
		 return pessoa;
	}
	
	public Pessoa salvar(Pessoa pessoa) {
		return repository.save(pessoa);
	}
	
	public void remover(Long codigo) {
		repository.deleteById(codigo);
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaCadastrada = buscarPessoaPorCodigo(codigo);
		pessoaCadastrada.setAtivo(ativo);
		
		repository.save(pessoaCadastrada);
	}
	
	public Pessoa buscarPessoaPorCodigo(Long codigo) {
		Pessoa pessoaCadastrada = this.repository.findById(codigo)
		.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return pessoaCadastrada;
	}
}
