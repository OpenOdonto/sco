package br.ueg.openodonto.servico.busca;

import br.ueg.openodonto.visao.ApplicationView;

public interface MessageDisplayer{
	String              getOutput();
	ApplicationView     getView();
	void display(String message);
}
