package com.mt.mtmoney.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mt.mtmoney.api.model.Categoria;
import com.mt.mtmoney.api.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository repository;
	
	public Categoria salvar(Categoria categoria) {
		return repository.save(categoria);
	}
	
	public List<Categoria> listarTodas() {
		return repository.findAll();
	}
	
	public Optional<Categoria> buscarPeloCodigo(Long codigo) {
		Optional<Categoria> categoria = repository.findById(codigo);
		
		return categoria;
	}
	
	public Categoria atualizar(Long codigo, Categoria categoria) {
		Categoria categoriaCadastrada = this.repository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		BeanUtils.copyProperties(categoria, categoriaCadastrada, "codigo");
		return repository.save(categoriaCadastrada);
	}
	
	public void remover(Long codigo) {
		repository.deleteById(codigo);
	}
	
}
