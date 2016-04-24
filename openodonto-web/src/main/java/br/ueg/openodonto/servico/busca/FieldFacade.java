package br.ueg.openodonto.servico.busca;

public class FieldFacade {

	private String displayName;
	private String fieldName;
	
	public FieldFacade(String displayName, String fieldName) {
		this.displayName = displayName;
		this.fieldName = fieldName;
	}

	public FieldFacade() {
	}

	public String getColunmName() {
		return displayName;
	}

	public void setColunmName(String displayName) {
		this.displayName = displayName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
