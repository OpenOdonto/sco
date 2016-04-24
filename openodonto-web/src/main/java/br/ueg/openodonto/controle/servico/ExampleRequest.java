package br.ueg.openodonto.controle.servico;

import java.util.ArrayList;
import java.util.List;

import br.ueg.openodonto.persistencia.dao.sql.SqlWhereOperatorType;
import br.ueg.openodonto.servico.busca.Searchable;

public class ExampleRequest<T> {

	private Searchable<T>             searchable;
	private List<Class<?>>            invalidPermiteds;
	private List<TypedFilter>         filterRelation;
	
	public ExampleRequest(Searchable<T> searchable) {
		this.searchable = searchable;
		this.invalidPermiteds = new ArrayList<Class<?>>();
		this.filterRelation = new ArrayList<TypedFilter>();
	}
	
	public Searchable<T> getSearchable() {
		return searchable;
	}
	public void setSearchable(Searchable<T> searchable) {
		this.searchable = searchable;
	}
	public List<Class<?>> getInvalidPermiteds() {
		return invalidPermiteds;
	}
	public void setInvalidPermiteds(List<Class<?>> invalidPermiteds) {
		this.invalidPermiteds = invalidPermiteds;
	}	
	
	public List<TypedFilter> getFilterRelation() {
		return filterRelation;
	}

	public void setFilterRelation(List<TypedFilter> filterRelation) {
		this.filterRelation = filterRelation;
	}

	public class TypedFilter{
		private String                filterName;
		private String                beanPath;
		private SqlWhereOperatorType  type;
		
		public TypedFilter(String filterName, String beanPath,SqlWhereOperatorType  type) {
			this.filterName = filterName;
			this.beanPath = beanPath;
			this.type = type;
		}
		
		public TypedFilter() {
		}
		
		public String getFilterName() {
			return filterName;
		}
		public void setFilterName(String filterName) {
			this.filterName = filterName;
		}
		public String getBeanPath() {
			return beanPath;
		}
		public void setBeanPath(String beanPath) {
			this.beanPath = beanPath;
		}
		public SqlWhereOperatorType getType() {
			return type;
		}
		public void setType(SqlWhereOperatorType type) {
			this.type = type;
		}		
	}
	
}
