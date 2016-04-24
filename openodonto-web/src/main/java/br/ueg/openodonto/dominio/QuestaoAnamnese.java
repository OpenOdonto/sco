package br.ueg.openodonto.dominio;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.Id;
import br.com.vitulus.simple.jdbc.annotation.Table;
import br.com.vitulus.simple.jdbc.annotation.type.IdIncrementType;

@Table(name="questao_anamnese")
public class QuestaoAnamnese implements Entity{

	private static final long serialVersionUID = 7023689631649841556L;
	
	@Column(name = "id_questao_anamnese")
	@Id(autoIncrement = IdIncrementType.IDENTITY)
	private Long 		codigo;
	
	@Column(name = "pergunta")
	private String 		pergunta;	
	
	public QuestaoAnamnese(Long codigo) {
		this();
		this.codigo = codigo;
	}

	public QuestaoAnamnese() {
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getPergunta() {
		return pergunta;
	}
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	@Override
	public String toString() {
		return "QuestaoAnamnese [codigo=" + codigo + ", pergunta=" + pergunta + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((pergunta == null) ? 0 : pergunta.hashCode());
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
		QuestaoAnamnese other = (QuestaoAnamnese) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (pergunta == null) {
			if (other.pergunta != null)
				return false;
		} else if (!pergunta.equals(other.pergunta))
			return false;
		return true;
	}

}
