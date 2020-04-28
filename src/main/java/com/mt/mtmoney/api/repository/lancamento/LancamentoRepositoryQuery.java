package com.mt.mtmoney.api.repository.lancamento;

import java.util.List;

import com.mt.mtmoney.api.model.Lancamento;
import com.mt.mtmoney.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter filter);
}
