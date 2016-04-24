package br.ueg.openodonto.dominio.constante;

public enum TipoPessoa {

	PESSOA_FISICA("Física"),PESSOA_JURIDICA("Jurídica");
		
	private String descricao;
	
	private TipoPessoa(String descricao) {
		this.descricao = descricao;
	}
	
	public long getCodigo(){
		return ordinal();
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public boolean isPf(){
		return this.equals(PESSOA_FISICA);
	}
	
	public boolean isPj(){
		return this.equals(PESSOA_JURIDICA);
	}
	
	@Override
	public String toString() {
		return descricao;
	}
	
}
