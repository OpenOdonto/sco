package br.ueg.openodonto.util.bean;

import java.util.HashMap;
import java.util.Map;

import br.ueg.openodonto.dominio.constante.Dente;
import br.ueg.openodonto.dominio.constante.TipoAscpectoDente;

public class DenteMetaAdapter {
	
	private Dente                   dente;
	private TipoAscpectoDente       ascpecto;
	private Map<String,Integer>     procedimentos;

	public DenteMetaAdapter() {		
		this.ascpecto = TipoAscpectoDente.NORMAL;
		this.procedimentos = new HashMap<String, Integer>(5);		
	}
	
	public Dente getDente() {
		return dente;
	}
	
	public void setDente(Dente dente) {
		this.dente = dente;
	}

	public boolean isImplante() {
		return getAscpecto() == TipoAscpectoDente.IMPLANTE;
	}

	public boolean isExtraido() {
		return getAscpecto() == TipoAscpectoDente.EXTRAIDO;
	}

	public boolean isNormal() {
		return getAscpecto() == TipoAscpectoDente.NORMAL;
	}
	
	public Map<String, Integer> getProcedimentos() {
		return procedimentos;
	}

	public Integer getDistal(){
	    return procedimentos.get("distal");
	}
	
	public Integer getOclusal(){
	    return procedimentos.get("oclusal");
	}
	
	public Integer getMesial(){
	    return procedimentos.get("mesial");
	}
	
	public Integer getVestibular(){
	    return procedimentos.get("vestibular");
	}
	
	public Integer getLingual(){
	    return procedimentos.get("lingual");
	}
	
	public Integer getRoot(){
	    return procedimentos.get("root");
	}
	
	public void setProcedimentos(Map<String, Integer> procedimentos) {
		this.procedimentos = procedimentos;
	}

	public TipoAscpectoDente getAscpecto() {
		return ascpecto;
	}

	public void setAscpecto(TipoAscpectoDente ascpecto) {
		this.ascpecto = ascpecto;
	}
}

