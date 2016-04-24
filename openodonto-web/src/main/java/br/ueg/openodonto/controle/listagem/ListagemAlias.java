package br.ueg.openodonto.controle.listagem;

import java.util.List;

import br.ueg.openodonto.servico.listagens.core.AbstractLista;

public class ListagemAlias<T> extends AbstractLista<T> {

	private AbstractLista<T> facade;
	
	public ListagemAlias(AbstractLista<T> facade) {
		super(facade.getClasse());
		this.facade = facade;
	}

	@Override
	public List<T> getRefreshDominio() {
		return facade.getRefreshDominio();
	}

	@Override
	public boolean isChangeable() {
		return facade.isChangeable();
	}

}
