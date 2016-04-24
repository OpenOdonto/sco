package br.ueg.openodonto.controle.busca;

import br.ueg.openodonto.dominio.constante.CategoriaProduto;
import br.ueg.openodonto.servico.busca.MessageDisplayer;

public class SearchablePrestadorServico extends SearchableColaborador{

	private static final long serialVersionUID = -3139360409951798677L;
	
	public SearchablePrestadorServico(MessageDisplayer displayer) {
		super(displayer,CategoriaProduto.SERVICO);
	}

}
