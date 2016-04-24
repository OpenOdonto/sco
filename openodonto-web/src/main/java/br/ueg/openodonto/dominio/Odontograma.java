package br.ueg.openodonto.dominio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.Id;
import br.com.vitulus.simple.jdbc.annotation.Relationship;
import br.com.vitulus.simple.jdbc.annotation.Table;
import br.com.vitulus.simple.jdbc.annotation.type.Cardinality;
import br.com.vitulus.simple.jdbc.annotation.type.IdIncrementType;
import br.ueg.openodonto.util.OdontogramaDenteComparator;
import br.ueg.openodonto.util.WordFormatter;

@Table(name = "odontogramas")
public class Odontograma implements Entity {

	private static final long serialVersionUID = 6394723722499152433L;	

	@Column(name = "id")
	@Id(autoIncrement = IdIncrementType.IDENTITY)
	private Long id;

	@Column(name = "id_pessoa")
	private Long idPessoa;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "data")
	private Timestamp data;

	@Relationship(cardinality = Cardinality.OneToMany)
	private TreeSet<OdontogramaDente> odontogramaDentes;
	
	@Relationship(cardinality=Cardinality.OneToMany)
	private List<OdontogramaDenteAspecto> aspectos;	
	
	public Odontograma(Long idPessoa,Long id) {
		this(idPessoa);
		this.id = id;
	}
	
	public Odontograma(Long idPessoa) {
		this();
		this.idPessoa = idPessoa;
	}

	public Odontograma() {
		super();
		aspectos = new ArrayList<OdontogramaDenteAspecto>();
		odontogramaDentes = new TreeSet<OdontogramaDente>(new OdontogramaDenteComparator());
	}	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}	
	
	public TreeSet<OdontogramaDente> getOdontogramaDentes() {
		return odontogramaDentes;
	}

	public void setOdontogramaDentes(TreeSet<OdontogramaDente> odontogramaDentes) {
		this.odontogramaDentes = odontogramaDentes;
	}	
	
	public String getDescricaoResumida(){
		return WordFormatter.abstractStr(getDescricao(), 100);
	}	
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<OdontogramaDenteAspecto> getAspectos() {
		return aspectos;
	}

	public void setAspectos(List<OdontogramaDenteAspecto> aspectos) {
		this.aspectos = aspectos;
	}
	
	@Override
	public String toString() {
		return "Odontograma [aspectos=" + aspectos + ", data=" + data
				+ ", descricao=" + descricao + ", id=" + id + ", idPessoa="
				+ idPessoa + ", nome=" + nome + ", odontogramaDentes="
				+ odontogramaDentes + "]";
	}	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((aspectos == null) ? 0 : aspectos.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((idPessoa == null) ? 0 : idPessoa.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime
				* result
				+ ((odontogramaDentes == null) ? 0 : odontogramaDentes
						.hashCode());
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
		Odontograma other = (Odontograma) obj;
		if (aspectos == null) {
			if (other.aspectos != null)
				return false;
		} else if (!aspectos.equals(other.aspectos))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idPessoa == null) {
			if (other.idPessoa != null)
				return false;
		} else if (!idPessoa.equals(other.idPessoa))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (odontogramaDentes == null) {
			if (other.odontogramaDentes != null)
				return false;
		} else if (!odontogramaDentes.equals(other.odontogramaDentes))
			return false;
		return true;
	}	

}
