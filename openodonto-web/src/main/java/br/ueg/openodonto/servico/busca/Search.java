package br.ueg.openodonto.servico.busca;

import java.util.List;

import br.ueg.openodonto.servico.busca.event.SearchListener;

public interface Search<T>{	
	
	String                         getTitle();
	String                         getName();
	List<ResultFacade>             getResults();
	Searchable<T>                  getSearchable();
	MessageDisplayer               getDisplayer();
	void                           addSearchListener(SearchListener listener);
	void                           search();
	void                           setSelected(ResultFacade bean);
	void                           clean();
	void                           showTimeQuery(int size , long time);
	
}
