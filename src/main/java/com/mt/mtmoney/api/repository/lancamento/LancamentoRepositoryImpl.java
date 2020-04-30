package com.mt.mtmoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.mt.mtmoney.api.model.Lancamento;
import com.mt.mtmoney.api.repository.filter.LancamentoFilter;
import com.mt.mtmoney.api.repository.projection.ResumoLancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter filter,Pageable page) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicate = criarRestricoes(filter,builder,root);
		criteria.where(predicate);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adicionarRestricoesdePaginacao(query,page);
		
		return new PageImpl<>(query.getResultList(), page, total(filter)) ;
	}
	
	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable page) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class
				, root.get("codigo"),root.get("descricao")
				, root.get("dataVencimento"),root.get("dataPagamento")
				, root.get("valor"),root.get("tipo")
				, root.get("categoria").get("nome")
				, root.get("pessoa").get("nome")));
		
		Predicate[] predicate = criarRestricoes(filter,builder,root);
		criteria.where(predicate);
		
		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesdePaginacao(query,page);
		
		return new PageImpl<>(query.getResultList(), page, total(filter)) ;
	}

	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {
		
		List<Predicate> predicate = new ArrayList<>();		
		
		if(!StringUtils.isEmpty(filter.getDescricao())) {
			predicate.add(builder.like(
					builder.lower(root.get("descricao")), "%" + filter.getDescricao().toLowerCase() + "%"));
		}
		
		if(filter.getDataVencimentoDe() != null) {
			predicate.add(
					builder.greaterThanOrEqualTo(root.get("dataVencimento"),filter.getDataVencimentoDe()));
		}
		
		if(filter.getDataVencimentoAte() != null) {
			predicate.add(builder.lessThanOrEqualTo(root.get("dataVencimento"), filter.getDataVencimentoAte()));
		}		
		
		return predicate.toArray(new Predicate[predicate.size()]);
	}
	
	private void adicionarRestricoesdePaginacao(TypedQuery<?> query, Pageable page) {
		int paginaAtual = page.getPageNumber();
		int totalRegistrosPorPagina = page.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(LancamentoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
}
