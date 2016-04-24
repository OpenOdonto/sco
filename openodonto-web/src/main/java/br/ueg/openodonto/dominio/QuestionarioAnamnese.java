package br.ueg.openodonto.dominio;

import java.util.Map;
import java.util.TreeMap;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.Id;
import br.com.vitulus.simple.jdbc.annotation.Relationship;
import br.com.vitulus.simple.jdbc.annotation.Table;
import br.com.vitulus.simple.jdbc.annotation.type.Cardinality;
import br.com.vitulus.simple.jdbc.annotation.type.IdIncrementType;
import br.ueg.openodonto.util.WordFormatter;

@Table(name="questionario_anamnese")
public class QuestionarioAnamnese  implements Entity{

	private static final long serialVersionUID = 4353856046511265104L;

	@Column(name = "id_questionario_anamnese")
	@Id(autoIncrement = IdIncrementType.IDENTITY)
	private Long 		codigo;
	
	@Column(name="nome")
	private String 		nome;
	
	@Column(name="descricao")
	private String 		descricao;	
	
	@Relationship(cardinality = Cardinality.OneToMany)
	private Map<QuestaoQuestionarioAnamnese,QuestaoAnamnese> questoes;
	
	public QuestionarioAnamnese(Long codigo) {
		this();
		this.codigo = codigo;
	}

	public QuestionarioAnamnese() {
		this.questoes = new TreeMap<QuestaoQuestionarioAnamnese, QuestaoAnamnese>();
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public String getDescricaoResumida(){
		return WordFormatter.abstractStr(getDescricao(), 60);
	}	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Map<QuestaoQuestionarioAnamnese, QuestaoAnamnese> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(
			Map<QuestaoQuestionarioAnamnese, QuestaoAnamnese> questoes) {
		this.questoes = questoes;
	}	
	@Override
	public String toString() {
		return "QuestionarioAnamnese [codigo=" + codigo + ", descricao="
				+ descricao + ", nome=" + nome + ", questoes=" + questoes + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((questoes == null) ? 0 : questoes.hashCode());
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
		QuestionarioAnamnese other = (QuestionarioAnamnese) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (questoes == null) {
			if (other.questoes != null)
				return false;
		} else if (!questoes.equals(other.questoes))
			return false;
		return true;
	}
}
