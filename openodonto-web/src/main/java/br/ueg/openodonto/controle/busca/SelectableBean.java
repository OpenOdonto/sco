package br.ueg.openodonto.controle.busca;

import java.util.Map;

import br.ueg.openodonto.servico.busca.SelectableResult;

public class SelectableBean extends ResultFacadeBean implements SelectableResult{

	private boolean selected;
	
	public <T>SelectableBean(T bean) {
		super(bean);
	}	
	
	public SelectableBean(Map<String, Object> beanValue) {
		super(beanValue);
	}
	
	@Override
	public boolean getSelected() {
		return selected;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
