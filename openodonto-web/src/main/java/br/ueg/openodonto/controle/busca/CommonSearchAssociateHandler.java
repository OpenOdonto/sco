package br.ueg.openodonto.controle.busca;

import java.util.ArrayList;
import java.util.List;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.EntityManager;
import br.com.vitulus.simple.jdbc.orm.old.DaoCrud;
import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.ueg.openodonto.servico.busca.ResultFacade;
import br.ueg.openodonto.servico.busca.event.AbstractSearchListener;
import br.ueg.openodonto.servico.busca.event.SearchAssociateEvent;

public abstract class CommonSearchAssociateHandler<T extends Entity> extends AbstractSearchListener{

	private EntityManager<T> dao;
	
	public CommonSearchAssociateHandler(EntityManager<T> dao) {
		this.dao = dao;
	}	
	
	@Override
	public void associatePerformed(SearchAssociateEvent event) {
		List<T> associated = new ArrayList<T>();
		for(ResultFacade result : event.getSelecteds()){
			try{
				T entity = ((DaoCrud<T>)dao).getNewEntity();			
				OrmFormat format = new OrmFormat(entity);
				format.parse(result.getValue());
				load(entity);
				associated.add(entity);
			}catch (Exception e) {
				e.printStackTrace();
			}		
		}
		associateBeans(associated);
	}
	
	public abstract void associateBeans(List<T> associated);
	
	public void load(T bean){
		try {
			dao.load(bean);
			extraLoad();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void extraLoad(){		
	}
	
	
}
