package br.ueg.openodonto.dominio.constante;

public enum TiposTelefone {

    CELULAR("Celular"), RESIDENCIAL("Residencial"), COMERCIAL("Comercial");

    private String descricao;

    private TiposTelefone(String descricao) {
	this.descricao = descricao;
    }

    public String getDescricao() {
	return descricao;
    }

    public long getCodigo() {
	return ordinal();
    }

    public String toString() {
	return this.descricao;
    }
}
