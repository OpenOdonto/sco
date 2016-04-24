package br.ueg.openodonto.servico.busca.event;

import java.util.EventListener;

public interface SearchListener extends EventListener{
	
	void searchPerformed(SearchEvent event);
	void resultRequested(SearchEvent event);
	void associatePerformed(SearchEvent event);
	void cleanPerformed(SearchEvent event);

}
