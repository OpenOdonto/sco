package br.ueg.openodonto.controle.busca;

import java.util.Map;

import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.ueg.openodonto.servico.busca.ResultFacade;

public class ResultFacadeBean implements ResultFacade {

	private Map<String , Object> beanValue;
	
	public <T>ResultFacadeBean(T bean) {
		OrmFormat orm = new OrmFormat(bean);
		this.beanValue = orm.format();
	}	
	
	public ResultFacadeBean(Map<String, Object> beanValue) {
		this.beanValue = beanValue;
	}

	@Override
	public Map<String, Object> getValue() {
		return getBeanValue();
	}

	public Map<String, Object> getBeanValue() {
		return beanValue;
	}

	public void setBeanValue(Map<String, Object> beanValue) {
		this.beanValue = beanValue;
	}

	@Override
	public String toString() {
		return "ResultFacadeBean [beanValue=" + beanValue + "]";
	}	
	
}
