package br.ueg.openodonto.dominio;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.Enumerator;
import br.com.vitulus.simple.jdbc.annotation.Id;
import br.com.vitulus.simple.jdbc.annotation.Table;
import br.com.vitulus.simple.jdbc.annotation.type.EnumValueType;
import br.com.vitulus.simple.jdbc.annotation.type.IdIncrementType;
import br.ueg.openodonto.dominio.constante.Dente;
import br.ueg.openodonto.dominio.constante.TipoAscpectoDente;

@Table(name="odontograma_aspectos")
public class OdontogramaDenteAspecto implements Entity{

	private static final long serialVersionUID = 336260993547724226L;
	
	@Column(name="dente")
	@Enumerator(type=EnumValueType.ORDINAL)
	@Id(autoIncrement=IdIncrementType.IDENTITY)	
	private Dente dente;
	
	@Id(autoIncrement=IdIncrementType.IDENTITY)
	@Column(name="fk_odontograma")
	private Long idOdontograma; 
	
	@Column(name="aspecto")
	@Enumerator(type=EnumValueType.ORDINAL)	
	private TipoAscpectoDente aspecto;
	
	public OdontogramaDenteAspecto(Dente dente) {
		this(dente,null);
		this.dente = dente;
	}

	public OdontogramaDenteAspecto(Long idOdontograma) {
		this(null,idOdontograma);
		this.idOdontograma = idOdontograma;
	}

	public OdontogramaDenteAspecto(Dente dente, Long idOdontograma) {
		this();
		this.dente = dente;
		this.idOdontograma = idOdontograma;
	}

	public OdontogramaDenteAspecto() {
		super();
	}

	public Dente getDente() {
		return dente;
	}

	public void setDente(Dente dente) {
		this.dente = dente;
	}

	public TipoAscpectoDente getAspecto() {
		return aspecto;
	}

	public void setAspecto(TipoAscpectoDente aspecto) {
		this.aspecto = aspecto;
	}

	public Long getIdOdontograma() {
		return idOdontograma;
	}

	public void setIdOdontograma(Long idOdontograma) {
		this.idOdontograma = idOdontograma;
	}

	@Override
	public String toString() {
		return "OdontogramaDenteAspecto [aspecto=" + aspecto + ", dente="
				+ dente + ", idOdontograma=" + idOdontograma + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aspecto == null) ? 0 : aspecto.hashCode());
		result = prime * result + ((dente == null) ? 0 : dente.hashCode());
		result = prime * result
				+ ((idOdontograma == null) ? 0 : idOdontograma.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OdontogramaDenteAspecto other = (OdontogramaDenteAspecto) obj;
		if (aspecto == null) {
			if (other.aspecto != null)
				return false;
		} else if (!aspecto.equals(other.aspecto))
			return false;
		if (dente == null) {
			if (other.dente != null)
				return false;
		} else if (!dente.equals(other.dente))
			return false;
		if (idOdontograma == null) {
			if (other.idOdontograma != null)
				return false;
		} else if (!idOdontograma.equals(other.idOdontograma))
			return false;
		return true;
	}
	

}
