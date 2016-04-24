package br.ueg.openodonto.util.bean;

import br.ueg.openodonto.dominio.constante.TipoStatusProcedimento;

public class StatusOdontogramaFilter implements Comparable<StatusOdontogramaFilter>{

	private TipoStatusProcedimento tipo;
	private Boolean                filter;
	
	public StatusOdontogramaFilter(TipoStatusProcedimento tipo) {
		this(tipo,true);
	}
	
	public StatusOdontogramaFilter(TipoStatusProcedimento tipo,Boolean filter) {
		this.tipo = tipo;
		this.filter = filter;
	}

	public TipoStatusProcedimento getTipo() {
		return tipo;
	}

	public void setTipo(TipoStatusProcedimento tipo) {
		this.tipo = tipo;
	}

	public Boolean getFilter() {
		return filter;
	}

	public void setFilter(Boolean filter) {
		this.filter = filter;
	}

	@Override
	public int compareTo(StatusOdontogramaFilter o) {
		if(o == null){
			return 1;
		}
		if(this.getFilter() == null && o.getFilter() == null){
			return 0;
		}
		return this.getTipo().compareTo(o.getTipo());
	}
	
	
	
}
