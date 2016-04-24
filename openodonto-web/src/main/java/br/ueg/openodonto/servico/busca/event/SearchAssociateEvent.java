package br.ueg.openodonto.servico.busca.event;

import java.util.Collection;

import br.ueg.openodonto.servico.busca.ResultFacade;
import br.ueg.openodonto.servico.busca.SelectableResult;

public interface SearchAssociateEvent extends SearchEvent{

	Collection<ResultFacade> getSelecteds();
	Collection<SelectableResult> getAll();
	
}
