package br.ueg.openodonto.servico.busca;

public interface SelectableResult extends ResultFacade{

	boolean getSelected();
	void setSelected(boolean selected);
	
}
