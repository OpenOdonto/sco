/**
 * 
 */
package br.ueg.openodonto.dominio;

import java.io.Serializable;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.Enumerator;
import br.com.vitulus.simple.jdbc.annotation.Id;
import br.com.vitulus.simple.jdbc.annotation.Table;
import br.com.vitulus.simple.jdbc.annotation.type.EnumValueType;
import br.com.vitulus.simple.jdbc.annotation.type.IdIncrementType;
import br.ueg.openodonto.dominio.constante.TiposTelefone;

@Table(name = "telefones")
public class Telefone implements Serializable, Entity {

	private static final long serialVersionUID = 77367905036522189L;

	@Column(name = "id")
	@Id(autoIncrement = IdIncrementType.IDENTITY)
	private Long codigo;

	@Column(name = "ddd")
	private String ddd;

	@Column(name = "numero")
	private String numero;

	@Column(name = "tipo")
	@Enumerator(type = EnumValueType.ORDINAL)
	private TiposTelefone tipoTelefone;

	@Column(name = "id_pessoa")
	private Long idPessoa;

	public Telefone() {
	}

	public Telefone(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDdd() {
		return this.ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TiposTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TiposTelefone tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((idPessoa == null) ? 0 : idPessoa.hashCode());
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
		Telefone other = (Telefone) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (idPessoa == null) {
			if (other.idPessoa != null)
				return false;
		} else if (!idPessoa.equals(other.idPessoa))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Telefone [codigo=" + codigo + ", ddd=" + ddd + ", id_pessoa="
				+ idPessoa + ", numero=" + numero + ", tipoTelefone="
				+ tipoTelefone + "]";
	}

}
