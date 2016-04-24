package br.ueg.openodonto.dominio;

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
import br.ueg.openodonto.dominio.constante.TiposRespostaAnamnese;

@Table(name="paciente_anamnese_respostas")
public class PacienteAnamneseResposta implements Entity{

	private static final long serialVersionUID = -6522681709270082326L;
	
	@Column(name="id")
	@Id(autoIncrement=IdIncrementType.IDENTITY)
	private Long codigo;
	
	@Column(name="resposta")
	@Enumerator(type=EnumValueType.ORDINAL)
	private TiposRespostaAnamnese resposta;
	
	@Column(name="observacao")
	private String observacao;
	
	@Column(name="fk_questao_anamnese")
	@Relationship(cardinality=Cardinality.OneToOne,joinFields={@ForwardKey(tableField="id",foreginField="fk_questao_anamnese")})
	private Long questaoAnamneseId;
	
	@Column(name="fk_questionario_anamnese")
	@Relationship(cardinality=Cardinality.OneToOne,joinFields={@ForwardKey(tableField="id_questionario_anamnese",foreginField="fk_questionario_anamnese")})
	private Long questionarioAnamneseId;
	
	@Column(name="fk_pacientes")
	@Relationship(cardinality=Cardinality.OneToOne,joinFields={@ForwardKey(tableField="id_pessoa",foreginField="fk_pacientes")})
	private Long pacienteId;	
	
	public PacienteAnamneseResposta(Long codigo) {
		this();
		this.codigo = codigo;
	}

	public PacienteAnamneseResposta(Long questaoAnamneseId, Long questionarioAnamneseId, Long pacienteId) {
		this();
		this.questaoAnamneseId = questaoAnamneseId;
		this.questionarioAnamneseId = questionarioAnamneseId;
		this.pacienteId = pacienteId;
	}

	public PacienteAnamneseResposta() {
	}
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public TiposRespostaAnamnese getResposta() {
		return resposta;
	}

	public void setResposta(TiposRespostaAnamnese resposta) {
		this.resposta = resposta;
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

	public Long getPacienteId() {
		return pacienteId;
	}

	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}
	
	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public String toString() {
		return "PacienteAnamneseResposta [codigo=" + codigo + ", observacao="
				+ observacao + ", pacienteId=" + pacienteId
				+ ", questaoAnamneseId=" + questaoAnamneseId
				+ ", questionarioAnamneseId=" + questionarioAnamneseId
				+ ", resposta=" + resposta + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		PacienteAnamneseResposta other = (PacienteAnamneseResposta) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	

}
