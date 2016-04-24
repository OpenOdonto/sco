package br.ueg.openodonto.dominio;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.ForwardKey;
import br.com.vitulus.simple.jdbc.annotation.Id;
import br.com.vitulus.simple.jdbc.annotation.Relationship;
import br.com.vitulus.simple.jdbc.annotation.Table;
import br.com.vitulus.simple.jdbc.annotation.type.Cardinality;

@Table(name="questao_questionario_anamnese")
public class QuestaoQuestionarioAnamnese  implements Entity,Comparable<QuestaoQuestionarioAnamnese>{

	private static final long serialVersionUID = 4958385716835920311L;
	
	@Id
	@Column(name="fk_questao_anamnese")
	@Relationship(cardinality=Cardinality.OneToOne,joinFields={@ForwardKey(tableField="id",foreginField="fk_questao_anamnese")})
	private Long questaoAnamneseId;
	
	@Id
	@Column(name="fk_questionario_anamnese")
	@Relationship(cardinality=Cardinality.OneToOne,joinFields={@ForwardKey(tableField="id_questionario_anamnese",foreginField="fk_questionario_anamnese")})
	private Long questionarioAnamneseId;

	@Column(name="index")
	private Integer index;
	
	@Column(name="obrigatoria")
	private Boolean obrigatoria;	
	
	public QuestaoQuestionarioAnamnese(Long questaoAnamneseId, Long questionarioAnamneseId) {
		this();
		this.questaoAnamneseId = questaoAnamneseId;
		this.questionarioAnamneseId = questionarioAnamneseId;
	}

	public QuestaoQuestionarioAnamnese() {
	}
	
	public Long getQuestaoAnamneseId() {
		return questaoAnamneseId;
	}

	public void setQuestaoAnamneseId(Long questaoAnamneseId) {
		this.questaoAnamneseId = questaoAnamneseId;
	}

	public Long getQuestionarioAnamneseId() {
		return questionarioAnamneseId;
	}

	public void setQuestionarioAnamneseId(Long questionarioAnamneseId) {
		this.questionarioAnamneseId = questionarioAnamneseId;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Boolean getObrigatoria() {
		return obrigatoria;
	}

	public void setObrigatoria(Boolean obrigatoria) {
		this.obrigatoria = obrigatoria;
	}

	@Override
	public String toString() {
		return "QuestaoQuestionarioAnamnese [index=" + index + ", obrigatoria="
				+ obrigatoria + ", questaoAnamneseId=" + questaoAnamneseId
				+ ", questionarioAnamneseId=" + questionarioAnamneseId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result
				+ ((obrigatoria == null) ? 0 : obrigatoria.hashCode());
		result = prime
				* result
				+ ((questaoAnamneseId == null) ? 0 : questaoAnamneseId
						.hashCode());
		result = prime
				* result
				+ ((questionarioAnamneseId == null) ? 0
						: questionarioAnamneseId.hashCode());
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
		QuestaoQuestionarioAnamnese other = (QuestaoQuestionarioAnamnese) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (obrigatoria == null) {
			if (other.obrigatoria != null)
				return false;
		} else if (!obrigatoria.equals(other.obrigatoria))
			return false;
		if (questaoAnamneseId == null) {
			if (other.questaoAnamneseId != null)
				return false;
		} else if (!questaoAnamneseId.equals(other.questaoAnamneseId))
			return false;
		if (questionarioAnamneseId == null) {
			if (other.questionarioAnamneseId != null)
				return false;
		} else if (!questionarioAnamneseId.equals(other.questionarioAnamneseId))
			return false;
		return true;
	}

	@Override
	public int compareTo(QuestaoQuestionarioAnamnese o) {
		if(getIndex() == null && o.getIndex() == null){
			return 0;
		}else if(getIndex() == null){
			return -1;
		}else if(o.getIndex() == null){
			return 1;
		}
		return getIndex().compareTo(o.getIndex());
	}
	

}
