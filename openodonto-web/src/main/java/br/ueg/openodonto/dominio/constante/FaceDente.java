package br.ueg.openodonto.dominio.constante;

public enum FaceDente {
	
	LINGUAL("Lingual ou Palatina"),VESTIBULAR("Vestibular"),MESIAL("Mesial"),DISTAL("Distal"),OCLUSAL("Oclusal ou Incisal"),ROOT("Raiz");
	
	private String nome;
	
	private FaceDente(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
}
