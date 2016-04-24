package br.ueg.openodonto.dominio.constante;

public enum TipoStatusProcedimento {
	
	NAO_REALIZADO("Não Realizado"),REALIZADO("Realizado"),PLANEJADO("Planejado"),PAGO("Pago");
	
	private String nome;
	
	private TipoStatusProcedimento(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
}
