package br.ueg.openodonto.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import br.com.vitulus.simple.jdbc.annotation.Dao;
import br.com.vitulus.simple.jdbc.orm.old.DaoCrud;
import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.ueg.openodonto.dominio.QuestaoAnamnese;
import br.ueg.openodonto.dominio.QuestaoQuestionarioAnamnese;
import br.ueg.openodonto.dominio.QuestionarioAnamnese;

@Dao(classe=QuestaoAnamnese.class)
public class DaoQuestaoAnamnese extends DaoCrud<QuestaoAnamnese> { //DaoProduto

	private static final long serialVersionUID = 3460742959166812919L;
	
	public DaoQuestaoAnamnese() {
		super(QuestaoAnamnese.class);
	}

	@Override
	public QuestaoAnamnese getNewEntity() {
		return new QuestaoAnamnese();
	}
	
	public QuestaoAnamnese findByKey(Long codigo) throws SQLException{
		OrmFormat orm = new OrmFormat(new QuestaoAnamnese(codigo));
		return super.findByKey(orm);
	}

	public void updateRelationshipQuestionarioAnamnese(QuestionarioAnamnese questionarioAnamnese) throws Exception {
		Long questionarioAnamneseId = questionarioAnamnese.getCodigo();
		if(questionarioAnamnese.getQuestoes() != null){
			List<QuestaoAnamnese> questoes = new ArrayList<QuestaoAnamnese>(questionarioAnamnese.getQuestoes().values());
			DaoQuestaoQuestionarioAnamnese daoQQA = new DaoQuestaoQuestionarioAnamnese();
			List<QuestaoQuestionarioAnamnese> qqas =   daoQQA.getQQARelationshipQA(questionarioAnamneseId);
			for(QuestaoQuestionarioAnamnese qqa : qqas){
				if(!containsQQARelationship(questoes,qqa)){
					daoQQA.remover(qqa);
				}
			}			
			Iterator<Entry<QuestaoQuestionarioAnamnese, QuestaoAnamnese>> iterator = questionarioAnamnese.getQuestoes().entrySet().iterator();
			while(iterator.hasNext()){
				Entry<QuestaoQuestionarioAnamnese, QuestaoAnamnese> entry = iterator.next();
				QuestaoAnamnese questao = entry.getValue();
				QuestaoQuestionarioAnamnese qqa = entry.getKey();
				if(!containsQARelationship(qqas,questao)){
					qqa.setQuestionarioAnamneseId(questionarioAnamneseId);
					qqa.setQuestaoAnamneseId(questao.getCodigo());
					daoQQA.inserir(qqa);
				}else{
					daoQQA.alterar(qqa);
				}
			}
		}
	}

	private boolean containsQARelationship(	List<QuestaoQuestionarioAnamnese> qqas, QuestaoAnamnese questao) {
		for(Iterator<QuestaoQuestionarioAnamnese> iterator = qqas.iterator();iterator.hasNext();){
			if(iterator.next().getQuestaoAnamneseId().equals(questao.getCodigo())){
				return true;
			}
		}
		return false;
	}

	private boolean containsQQARelationship(List<QuestaoAnamnese> questoes,	QuestaoQuestionarioAnamnese qqa) {
		for(Iterator<QuestaoAnamnese> iterator = questoes.iterator();iterator.hasNext();){
			if(iterator.next().getCodigo().equals(qqa.getQuestaoAnamneseId())){
				return true;
			}
		}
		return false;
	}
}
