package br.ueg.openodonto.controle.busca;

import java.io.Serializable;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.EntityManager;
import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.ueg.openodonto.servico.busca.event.AbstractSearchListener;
import br.ueg.openodonto.servico.busca.event.SearchSelectedEvent;

public abstract class CommonSearchSelectedHandler<T extends Entity> extends AbstractSearchListener implements Serializable{
	private static final long serialVersionUID = -8455981783765205027L;
	
	private EntityManager<T> dao;
	
	public CommonSearchSelectedHandler(EntityManager<T> dao){
		this.dao = dao;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void resultRequested(SearchSelectedEvent event) {
		try{
			T entity = (T)getBean().getClass().newInstance();
			setBean(entity);
			OrmFormat format = new OrmFormat(entity);
			format.parse(event.getSelected().getValue());
			load();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void load(){
		try {
			dao.load(getBean());
			extraLoad();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void extraLoad(){		
	}	
	public abstract T getBean();
	public abstract void setBean(T bean);
	
}
