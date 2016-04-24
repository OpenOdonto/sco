package br.ueg.openodonto.util.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.ueg.openodonto.dominio.PacienteAnamneseResposta;
import br.ueg.openodonto.dominio.PacienteQuestionarioAnamnese;
import br.ueg.openodonto.dominio.QuestaoAnamnese;
import br.ueg.openodonto.dominio.QuestaoQuestionarioAnamnese;
import br.ueg.openodonto.dominio.QuestionarioAnamnese;

public class QuestionarioAnamneseAdapter {

	private PacienteQuestionarioAnamnese 					pqa;
	private QuestionarioAnamnese 							qa;
	private List<QuestionarioQuestaoAdapter> 				questoes;

	public QuestionarioAnamneseAdapter(PacienteQuestionarioAnamnese pqa,QuestionarioAnamnese qa,Map<QuestaoQuestionarioAnamnese, QuestaoAnamnese> questoes) {
		this.pqa = pqa;
		this.qa = qa;
		this.questoes = new ArrayList<QuestionarioQuestaoAdapter>();
		for(Entry<QuestaoQuestionarioAnamnese, QuestaoAnamnese> entry : questoes.entrySet()){			
			PacienteAnamneseResposta resposta;
			if((resposta = pqa.getRespotas().get(entry.getValue())) == null){
				resposta = new PacienteAnamneseResposta(
						entry.getKey().getQuestaoAnamneseId(),
						entry.getKey().getQuestionarioAnamneseId(),
						null);
				pqa.getRespotas().put(entry.getValue(), resposta);
			}
			this.questoes.add(new QuestionarioQuestaoAdapter(entry.getKey(), entry.getValue(),resposta));
		}
	}

	public PacienteQuestionarioAnamnese getPqa() {
		return pqa;
	}
	public void setPqa(PacienteQuestionarioAnamnese pqa) {
		this.pqa = pqa;
	}
	public QuestionarioAnamnese getQa() {
		return qa;
	}
	public void setQa(QuestionarioAnamnese qa) {
		this.qa = qa;
	}

	public List<QuestionarioQuestaoAdapter> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(List<QuestionarioQuestaoAdapter> questoes) {
		this.questoes = questoes;
	}

	@Override
	public String toString() {
		return "QuestionarioAnamneseAdapter [pqa=" + pqa + ", qa=" + qa
				+ ", questoes=" + questoes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pqa == null) ? 0 : pqa.hashCode());
		result = prime * result + ((qa == null) ? 0 : qa.hashCode());
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
		QuestionarioAnamneseAdapter other = (QuestionarioAnamneseAdapter) obj;
		if (pqa == null) {
			if (other.pqa != null)
				return false;
		} else if (!pqa.equals(other.pqa))
			return false;
		if (qa == null) {
			if (other.qa != null)
				return false;
		} else if (!qa.equals(other.qa))
			return false;
		if (questoes == null) {
			if (other.questoes != null)
				return false;
		} else if (!questoes.equals(other.questoes))
			return false;
		return true;
	}
	

}
