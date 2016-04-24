package br.ueg.openodonto.controle.busca;

import br.ueg.openodonto.dominio.constante.CategoriaProduto;
import br.ueg.openodonto.servico.busca.MessageDisplayer;

public class SearchableVendedor extends SearchableColaborador{

	private static final long serialVersionUID = -3152223853330373472L;

	public SearchableVendedor(MessageDisplayer displayer) {
		super(displayer,CategoriaProduto.PRODUTO);
	}

}
