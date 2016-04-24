package br.ueg.openodonto.dominio;

import java.util.HashMap;
import java.util.Map;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.ForwardKey;
import br.com.vitulus.simple.jdbc.annotation.Id;
import br.com.vitulus.simple.jdbc.annotation.Relationship;
import br.com.vitulus.simple.jdbc.annotation.Table;
import br.com.vitulus.simple.jdbc.annotation.type.Cardinality;

@Table(name="paciente_anamnese")
public class PacienteQuestionarioAnamnese implements Entity,Comparable<PacienteQuestionarioAnamnese>{
	
	private static final long serialVersionUID = -1345966759980880798L;
	
	@Id
	@Column(name="fk_pacientes")
	@Relationship(cardinality=Cardinality.OneToOne,joinFields={@ForwardKey(tableField="id_pessoa",foreginField="fk_pacientes")})
	private Long pacienteId;
	
	@Id
	@Column(name="fk_questionario_anamnese")
	@Relationship(cardinality=Cardinality.OneToOne,joinFields={@ForwardKey(tableField="id_questionario_anamnese",foreginField="fk_questionario_anamnese")})
	private Long questionarioAnamneseId;
	
	@Relationship(cardinality=Cardinality.OneToMany)
	private Map<QuestaoAnamnese,PacienteAnamneseResposta> 	respotas;
	
	public PacienteQuestionarioAnamnese(Long pacienteId,Long questionarioAnamneseId) {
		this();
		this.pacienteId = pacienteId;
		this.questionarioAnamneseId = questionarioAnamneseId;
	}

	public PacienteQuestionarioAnamnese() {
		this.respotas = new HashMap<QuestaoAnamnese, PacienteAnamneseResposta>();
	}
	
	public Long getPacienteId() {
		return pacienteId;
	}

	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}

	public Long getQuestionarioAnamneseId() {
		return questionarioAnamneseId;
	}

	public void setQuestionarioAnamneseId(Long questionarioAnamneseId) {
		this.questionarioAnamneseId = questionarioAnamneseId;
	}

	public Map<QuestaoAnamnese, PacienteAnamneseResposta> getRespotas() {
		return respotas;
	}

	public void setRespotas(Map<QuestaoAnamnese, PacienteAnamneseResposta> respotas) {
		this.respotas = respotas;
	}

	@Override
	public int compareTo(PacienteQuestionarioAnamnese o) {
		if(o == null){
			return 1;
		}
		return this.getQuestionarioAnamneseId().compareTo(o.getQuestionarioAnamneseId());
	}
	
	@Override
	public String toString() {
		return "PacienteQuestionarioAnamnese [pacienteId=" + pacienteId
				+ ", questionarioAnamneseId=" + questionarioAnamneseId
				+ ", respotas=" + respotas + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pacienteId == null) ? 0 : pacienteId.hashCode());
		result = prime
				* result
				+ ((questionarioAnamneseId == null) ? 0
						: questionarioAnamneseId.hashCode());
		result = prime * result
				+ ((respotas == null) ? 0 : respotas.hashCode());
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
		PacienteQuestionarioAnamnese other = (PacienteQuestionarioAnamnese) obj;
		if (pacienteId == null) {
			if (other.pacienteId != null)
				return false;
		} else if (!pacienteId.equals(other.pacienteId))
			return false;
		if (questionarioAnamneseId == null) {
			if (other.questionarioAnamneseId != null)
				return false;
		} else if (!questionarioAnamneseId.equals(other.questionarioAnamneseId))
			return false;
		if (respotas == null) {
			if (other.respotas != null)
				return false;
		} else if (!respotas.equals(other.respotas))
			return false;
		return true;
	}
	
}
