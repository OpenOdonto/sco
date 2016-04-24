package br.ueg.openodonto.controle.busca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.ueg.openodonto.servico.busca.InputField;
import br.ueg.openodonto.servico.busca.MessageDisplayer;
import br.ueg.openodonto.servico.busca.ResultFacade;
import br.ueg.openodonto.servico.busca.Search;
import br.ueg.openodonto.servico.busca.SearchFilter;
import br.ueg.openodonto.servico.busca.Searchable;
import br.ueg.openodonto.servico.busca.event.SearchEvent;
import br.ueg.openodonto.servico.busca.event.SearchListener;
import br.ueg.openodonto.servico.busca.event.SearchSelectedEvent;

public class SearchBase<T> implements Search<T>,Serializable {

	private static final long serialVersionUID = 7711768887942893883L;
	private String                    title;
	private String                    name;
	private List<ResultFacade>        results;
	private Searchable<T>             searchable;
	private List<SearchListener>      listeners;
	private MessageDisplayer          displayer;

	
	public SearchBase(Searchable<T> searchable,String title,String name) {
		if(searchable instanceof AbstractSearchable<?>){
			this.displayer = ((AbstractSearchable<T>)searchable).getDisplayer();
		}
		this.name = name;
		this.title = title;
		this.results = new ArrayList<ResultFacade>();
		this.searchable = searchable;
		this.listeners = new ArrayList<SearchListener>();
	}
	
	@Override
	public MessageDisplayer getDisplayer() {
		return displayer;
	}	
	
	@Override
	public List<ResultFacade> getResults() {
		return results;
	}

	@Override
	public Searchable<T> getSearchable() {
		return searchable;
	}

	public void setResults(List<ResultFacade> results) {
		this.results = results;
	}

	public void setSearchable(Searchable<T> searchable) {
		this.searchable = searchable;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void addSearchListener(SearchListener listener) {
		if(listener == null){
			return;
		}
		listeners.add(listener);
	}

	@Override
	public void showTimeQuery(int size , long time){
		String buscarMessage = "formSearch"+getName()+":buscar"+getName(),
		timeMessage = "formSearch"+getName()+":queryTime"+getName();
		StringBuilder fetchedMsg = new StringBuilder();
		fetchedMsg.append(String.format("Foram encontrados %d resultados.",size));
		getDisplayer().getView().addResourceDynamicMenssage(fetchedMsg.toString(),buscarMessage);
		if(time != -1){
			StringBuilder FetchedTimeMsg = new StringBuilder();
			double fTime = time / 1000.0;
			FetchedTimeMsg.append(String.format("( %.3f segundos )",fTime));
			getDisplayer().getView().addResourceDynamicMenssage(FetchedTimeMsg.toString(), timeMessage);
		}
	}
	
	private SearchEvent buildEvent(){
		return new SearchEvent() {
			@Override
			public Object getSource() {
				return SearchBase.this;
			}
		};
	}
	
	private SearchSelectedEvent buildSelectEvent(final ResultFacade bean){
		return new SearchSelectedEvent() {
			@Override
			public Object getSource() {
				return SearchBase.this;
			}			
			@Override
			public ResultFacade getSelected() {
				return bean;
			}
		};
	}
	
	private void fireSearchPerfomed(){
		final SearchEvent event = buildEvent();
		new SearchEventTrigger() {
			@Override
			public void doActionEvent(SearchListener listener) {
				listener.searchPerformed(event);
			}
		}.doAction();
	}
	
	private void fireCleanPerformed(){
		final SearchEvent event = buildEvent();
		new SearchEventTrigger() {
			@Override
			public void doActionEvent(SearchListener listener) {
				listener.cleanPerformed(event);
			}
		};
	}
	
	private void fireSelectPerfomed(ResultFacade bean){
		final SearchSelectedEvent event = buildSelectEvent(bean);
		new SearchEventTrigger() {
			@Override
			public void doActionEvent(SearchListener listener) {
				listener.resultRequested(event);
			}
		}.doAction();
	}
	
	public abstract class SearchEventTrigger{
		public void doAction(){
			Iterator<SearchListener> iterator = listeners.iterator();
			while(iterator.hasNext()){
				SearchListener listener = iterator.next();
				doActionEvent(listener);
			}
		}
		public abstract void doActionEvent(SearchListener listener);
	}
	
	@Override
	public void search() {
		fireSearchPerfomed();
	}

	@Override
	public void setSelected(ResultFacade bean) {
		fireSelectPerfomed(bean);
	}

	@Override
	public void clean() {
		for(SearchFilter filter : getSearchable().getFilters()){
			for(InputField<?> input: filter.getField().getInputFields()){
				input.setValue(null);
			}
		}
		getResults().clear();
		fireCleanPerformed();
	}
}
