package br.ueg.openodonto.controle.busca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.ueg.openodonto.servico.busca.ResultFacade;
import br.ueg.openodonto.servico.busca.Searchable;
import br.ueg.openodonto.servico.busca.SelectableResult;
import br.ueg.openodonto.servico.busca.SelectableSearch;
import br.ueg.openodonto.servico.busca.event.SearchAssociateEvent;
import br.ueg.openodonto.servico.busca.event.SearchListener;

public class SelectableSearchBase<T> extends SearchBase<T> implements SelectableSearch<T> {

	private static final long serialVersionUID = -1781119263710365722L;
	
	private List<SelectableResult>        results;

	public SelectableSearchBase(Searchable<T> searchable, String title,String name) {
		super(searchable,title,name);
		this.results = new ArrayList<SelectableResult>();
		
	}

	private SearchAssociateEvent buildAssociateEvent(){
		return new SearchAssociateEvent() {			
			@Override
			public Object getSource() {
				return SelectableSearchBase.this;
			}
			
			@Override
			public Collection<ResultFacade> getSelecteds() {
				return SelectableSearchBase.this.getResults();
			}
			
			@Override
			public Collection<SelectableResult> getAll() {
				return SelectableSearchBase.this.getSelectableResults();
			}
		};
	}
	
	private void fireAssociatePerformed(){
		final SearchAssociateEvent event = buildAssociateEvent();
		new SearchEventTrigger() {
			@Override
			public void doActionEvent(SearchListener listener) {
				listener.associatePerformed(event);
			}
		}.doAction();
	}
	
	@Override
	public void associateSelecteds() {
		getResults().clear();
		for(Iterator<SelectableResult> iterator = results.iterator();iterator.hasNext();){
			SelectableResult selectable = iterator.next();
			if(selectable.getSelected()){
				getResults().add(selectable);
			}
		}
		fireAssociatePerformed();
	}
	
	@Override
	public List<SelectableResult> getSelectableResults() {
		return results;
	}
	
	@Override
	public void clean() {
		super.clean();
		getSelectableResults().clear();
	}

}
