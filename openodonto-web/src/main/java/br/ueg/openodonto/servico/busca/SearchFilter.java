package br.ueg.openodonto.servico.busca;


public interface SearchFilter {
	String              getName();
	String              getLabel();
	SearchField         getField();
	void                displayValidationMessage(String message);

}
