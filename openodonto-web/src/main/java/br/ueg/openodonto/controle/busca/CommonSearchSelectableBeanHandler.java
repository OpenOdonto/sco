package br.ueg.openodonto.controle.busca;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.jdbc.EntityManager;
import br.ueg.openodonto.servico.busca.Search;
import br.ueg.openodonto.servico.busca.SelectableResult;
import br.ueg.openodonto.servico.busca.SelectableSearch;

public abstract class CommonSearchSelectableBeanHandler<E> extends CommonSearchBeanHandler<E>{

	@SuppressWarnings("rawtypes")
	public CommonSearchSelectableBeanHandler(Class<E> classe, EntityManager dao) {
		super(classe, dao);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void addResults(Search<E> search,	List<Map<String, Object>> result) {
		SelectableSearch<E> selectableSearch = (SelectableSearch<E>)search;
		selectableSearch.getSelectableResults().clear();
		selectableSearch.getSelectableResults().addAll((Collection<SelectableResult>)wrapResult(result));
	}
	@Override
	protected SelectableResult buildWrapBean(Map<String, Object> value) {
		return new SelectableBean(value);
	}
}
