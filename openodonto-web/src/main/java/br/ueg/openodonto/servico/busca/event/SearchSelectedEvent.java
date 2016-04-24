package br.ueg.openodonto.servico.busca.event;

import br.ueg.openodonto.servico.busca.ResultFacade;

public interface SearchSelectedEvent extends SearchEvent {

	ResultFacade getSelected();
	
}
