package br.ueg.openodonto.controle.servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import br.ueg.openodonto.dominio.QuestaoAnamnese;
import br.ueg.openodonto.dominio.QuestaoQuestionarioAnamnese;
import br.ueg.openodonto.util.bean.QuestionarioQuestaoAdapter;
import br.ueg.openodonto.visao.ApplicationView;

public class ManageQuestaoQuestionario {

	private Map<QuestaoQuestionarioAnamnese,QuestaoAnamnese> 	questoes;
	private List<QuestionarioQuestaoAdapter>					questoesAdapter;
	private ApplicationView									 	view;	
	
	public ManageQuestaoQuestionario(Map<QuestaoQuestionarioAnamnese,QuestaoAnamnese> 	questoes,	ApplicationView view) {
		this.questoes = questoes;
		this.questoesAdapter = new ArrayList<QuestionarioQuestaoAdapter>();
		this.view = view;
		buildAdapter();
	}
	
	public void syncListOrder(){
		for(int i = 0;i < questoesAdapter.size();i++){
			questoesAdapter.get(i).getQqa().setIndex(i);
		}
	}
	
	private void buildAdapter(){
		this.questoesAdapter.clear();
		for(Map.Entry<QuestaoQuestionarioAnamnese, QuestaoAnamnese> entry : questoes.entrySet()){
			QuestionarioQuestaoAdapter qqa = new QuestionarioQuestaoAdapter(entry.getKey(), entry.getValue());
			this.questoesAdapter.add(qqa);
		}
	}	
	
	public void acaoRemoverQuestao(QuestionarioQuestaoAdapter adapter){
		if(adapter != null){
			questoesAdapter.remove(adapter);
			questoes.remove(adapter.getQqa());
		}
	}
	
	public void associarQuestao(List<QuestaoAnamnese> associated) {
		QuestaoComparator comparator = new QuestaoComparator();
		List<QuestaoAnamnese> questoes = new ArrayList<QuestaoAnamnese>(this.questoes.values());
		for(QuestaoAnamnese questao : associated){
			Collections.sort(questoes,comparator);
			if(Collections.binarySearch(questoes, questao,comparator) < 0){
				QuestaoQuestionarioAnamnese qqa = new QuestaoQuestionarioAnamnese(questao.getCodigo(), null);
				qqa.setIndex(this.questoes.size());
				qqa.setObrigatoria(false);
				this.questoes.put(qqa, questao);
			}
		}
		buildAdapter();
	}

	public Map<QuestaoQuestionarioAnamnese, QuestaoAnamnese> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(Map<QuestaoQuestionarioAnamnese, QuestaoAnamnese> questoes) {		
		this.questoes = questoes;
		buildAdapter();
	}

	public ApplicationView getView() {
		return view;
	}

	public void setView(ApplicationView view) {
		this.view = view;
	}
	
	public List<QuestionarioQuestaoAdapter> getQuestoesAdapter() {
		return questoesAdapter;
	}
	
	public void setQuestoesAdapter(List<QuestionarioQuestaoAdapter> questoesAdapter) {
		this.questoesAdapter = questoesAdapter;
	}	
	
	private class QuestaoComparator implements Comparator<QuestaoAnamnese>{
		@Override
		public int compare(QuestaoAnamnese o1, QuestaoAnamnese o2) {
			return o1.getCodigo().compareTo(o2.getCodigo());
		}		
	}

}
