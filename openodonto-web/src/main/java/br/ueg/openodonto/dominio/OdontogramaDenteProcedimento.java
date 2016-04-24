package br.ueg.openodonto.dominio;

import java.sql.Date;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.Enumerator;
import br.com.vitulus.simple.jdbc.annotation.ForwardKey;
import br.com.vitulus.simple.jdbc.annotation.Id;
import br.com.vitulus.simple.jdbc.annotation.Relationship;
import br.com.vitulus.simple.jdbc.annotation.Table;
import br.com.vitulus.simple.jdbc.annotation.type.Cardinality;
import br.com.vitulus.simple.jdbc.annotation.type.EnumValueType;
import br.com.vitulus.simple.jdbc.annotation.type.IdIncrementType;
import br.ueg.openodonto.dominio.constante.TipoStatusProcedimento;

@Table(name = "procedimentos_dentes")
public class OdontogramaDenteProcedimento implements Entity{

	private static final long serialVersionUID = -2263101580965612324L;

	@Column(name="id")
	@Id(autoIncrement = IdIncrementType.IDENTITY)
	private Long codigo;
	
	@Column(name="fk_procedimento")
	@Relationship(cardinality=Cardinality.OneToOne,joinFields={@ForwardKey(tableField="id_procedimento",foreginField="fk_procedimento")})
	private Long procedimentoId;
	
	@Column(name="fk_odontograma_dente")
	@Relationship(cardinality=Cardinality.OneToMany,joinFields={@ForwardKey(tableField="id_odontograma_dente",foreginField="fk_odontograma_dente")})
	private Long odontogramaDenteId;
	
	private Float valor;

	@Column(name = "status")
	@Enumerator(type = EnumValueType.ORDINAL)
	private TipoStatusProcedimento status;
	
	@Column(name="data_procedimento")
	private Date data;
	
	@Column(name="obs")
	private String observacao;
		
	public OdontogramaDenteProcedimento(Long procedimentoId,Long odontogramaDenteId) {
		this();
		this.procedimentoId = procedimentoId;
		this.odontogramaDenteId = odontogramaDenteId;
	}
	
	public OdontogramaDenteProcedimento(Long codigo) {
		this();
		this.codigo = codigo;
	}
	
	public OdontogramaDenteProcedimento() {
	}	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getProcedimentoId() {
		return procedimentoId;
	}

	public void setProcedimentoId(Long procedimentoId) {
		this.procedimentoId = procedimentoId;
	}
	
	public Long getOdontogramaDenteId() {
		return odontogramaDenteId;
	}

	public void setOdontogramaDenteId(Long odontogramaDenteId) {
		this.odontogramaDenteId = odontogramaDenteId;
	}	

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public TipoStatusProcedimento getStatus() {
		return status;
	}

	public void setStatus(TipoStatusProcedimento status) {
		this.status = status;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public String toString() {
		return "OdontogramaDenteProcedimento [codigo=" + codigo + ", data="
				+ data + ", observacao=" + observacao + ", odontogramaDenteId="
				+ odontogramaDenteId + ", procedimentoId=" + procedimentoId
				+ ", status=" + status + ", valor=" + valor + "]";
	}
	
}
