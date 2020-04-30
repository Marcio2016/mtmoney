package com.mt.mtmoney.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mt.mtmoney.api.model.Lancamento;
import com.mt.mtmoney.api.repository.filter.LancamentoFilter;
import com.mt.mtmoney.api.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable page);
	
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable page);
}
