package br.ueg.openodonto.servico.busca;

import java.util.List;

public interface SelectableSearch<T> extends Search<T>{

	void                       associateSelecteds();
	List<SelectableResult>     getSelectableResults();
}
