package br.ueg.openodonto.dominio;

import java.util.HashMap;
import java.util.Map;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.Enumerator;
import br.com.vitulus.simple.jdbc.annotation.Id;
import br.com.vitulus.simple.jdbc.annotation.Relationship;
import br.com.vitulus.simple.jdbc.annotation.Table;
import br.com.vitulus.simple.jdbc.annotation.type.Cardinality;
import br.com.vitulus.simple.jdbc.annotation.type.EnumValueType;
import br.com.vitulus.simple.jdbc.annotation.type.IdIncrementType;
import br.ueg.openodonto.dominio.constante.Dente;
import br.ueg.openodonto.dominio.constante.FaceDente;

@Table(name = "odontograma_dentes")
public class OdontogramaDente implements Entity{ //Colaboradores

	private static final long serialVersionUID = 7164270562064929889L;

	@Column(name = "id_odontograma_dente")
	@Id(autoIncrement = IdIncrementType.IDENTITY)
	private Long codigo;	
	
	@Column(name = "dente")
	@Enumerator(type = EnumValueType.ORDINAL)
	private Dente dente;
	
	@Column(name = "face")
	@Enumerator(type = EnumValueType.ORDINAL)
	private FaceDente face;
	
	@Column(name="fk_odontograma")
	private Long idOdontograma;

	@Relationship(cardinality=Cardinality.OneToMany)
	private Map<OdontogramaDenteProcedimento,Procedimento> procedimentosMap;	
	
	public OdontogramaDente(Long idOdontograma,Dente dente, FaceDente face) {
		this(dente,face);
		this.idOdontograma = idOdontograma;
	}
	
	public OdontogramaDente(Dente dente, FaceDente face) {
		this();
		this.dente = dente;
		this.face = face;
	}

	
	public OdontogramaDente(Long codigo) {
		this();
		this.codigo = codigo;
	}
	

	public OdontogramaDente() {
		super();
		procedimentosMap = new HashMap<OdontogramaDenteProcedimento, Procedimento>();
	}
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Dente getDente() {
		return dente;
	}

	public void setDente(Dente dente) {
		this.dente = dente;
	}	
	
	public Long getIdOdontograma() {
		return idOdontograma;
	}

	public void setIdOdontograma(Long idOdontograma) {
		this.idOdontograma = idOdontograma;
	}

	public FaceDente getFace() {
		return face;
	}

	public void setFace(FaceDente face) {
		this.face = face;
	}
	
	public Map<OdontogramaDenteProcedimento, Procedimento> getProcedimentosMap() {
		return procedimentosMap;
	}

	public void setProcedimentosMap(
			Map<OdontogramaDenteProcedimento, Procedimento> procedimentosMap) {
		this.procedimentosMap = procedimentosMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((dente == null) ? 0 : dente.hashCode());
		result = prime * result + ((face == null) ? 0 : face.hashCode());
		result = prime * result
				+ ((idOdontograma == null) ? 0 : idOdontograma.hashCode());
		result = prime
				* result
				+ ((procedimentosMap == null) ? 0 : procedimentosMap.hashCode());
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
		OdontogramaDente other = (OdontogramaDente) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (dente == null) {
			if (other.dente != null)
				return false;
		} else if (!dente.equals(other.dente))
			return false;
		if (face == null) {
			if (other.face != null)
				return false;
		} else if (!face.equals(other.face))
			return false;
		if (idOdontograma == null) {
			if (other.idOdontograma != null)
				return false;
		} else if (!idOdontograma.equals(other.idOdontograma))
			return false;
		if (procedimentosMap == null) {
			if (other.procedimentosMap != null)
				return false;
		} else if (!procedimentosMap.equals(other.procedimentosMap))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OdontogramaDente [codigo=" + codigo + ", dente=" + dente
				+ ", face=" + face + ", idOdontograma=" + idOdontograma
				+ ", procedimentosMap=" + procedimentosMap + "]";
	}	
}
