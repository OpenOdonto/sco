package br.ueg.openodonto.controle.busca;

import br.ueg.openodonto.servico.busca.InputMask;

public class JsMask implements InputMask {

	private String query;
	private String seletor;
	private String styleClass;

	public JsMask() {
	}

	public JsMask(String query, String seletor, String styleClass) {
		this.query = query;
		this.seletor = seletor;
		this.styleClass = styleClass;
	}

	@Override
	public String getQuery() {
		return query;
	}

	@Override
	public String getSeletor() {
		return seletor;
	}

	@Override
	public String getStyleClass() {
		return styleClass;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setSeletor(String seletor) {
		this.seletor = seletor;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	@Override
	public String toString() {
		return "Mask [mask=" + query + ", seletor=" + seletor + ", styleClass="
				+ styleClass + "]";
	}
}