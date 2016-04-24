package br.ueg.openodonto.dominio.constante;

import br.com.vitulus.simple.jdbc.Entity;

public enum CategoriaProduto implements Entity{

	SERVICO("Serviço","Prestador de Serviço"), PRODUTO("Produto","Fornecedor");

	private String descricao;
	private String colaborador;

	private CategoriaProduto(String descricao,String colabordor) {
		this.descricao = descricao;
		this.colaborador = colabordor;
	}

	public String getDescricao() {
		return descricao;
	}	
	
	public String getColaborador() {
		return colaborador;
	}

	public long getCodigo() {
		return ordinal();
	}

	public static CategoriaProduto parseCategoria(Object id){
		int index = Integer.parseInt(id.toString());
		return values()[index];
	}
	
	public String toString() {
		return this.descricao;
	}

}
