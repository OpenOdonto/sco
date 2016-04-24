package br.ueg.openodonto.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import br.com.vitulus.simple.jdbc.EntityManager;
import br.com.vitulus.simple.jdbc.annotation.Dao;
import br.com.vitulus.simple.jdbc.dao.DaoFactory;
import br.com.vitulus.simple.jdbc.orm.old.DaoCrud;
import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.com.vitulus.simple.jdbc.sql.old.CrudQuery;
import br.com.vitulus.simple.jdbc.sql.old.IQuery;
import br.ueg.openodonto.dominio.Odontograma;
import br.ueg.openodonto.dominio.Paciente;
import br.ueg.openodonto.dominio.PacienteQuestionarioAnamnese;
import br.ueg.openodonto.dominio.QuestaoAnamnese;
import br.ueg.openodonto.dominio.QuestaoQuestionarioAnamnese;
import br.ueg.openodonto.dominio.QuestionarioAnamnese;

@Dao(classe=QuestionarioAnamnese.class)
public class DaoQuestionarioAnamnese extends DaoCrud<QuestionarioAnamnese> {

	private static final long serialVersionUID = -2997010647680704668L;
	
	public DaoQuestionarioAnamnese() {
		super(QuestionarioAnamnese.class);
	}	

	@Override
	public QuestionarioAnamnese getNewEntity() {
		return new QuestionarioAnamnese();
	}
	
	public QuestionarioAnamnese findByKey(Long codigo) throws SQLException {
		OrmFormat format = new OrmFormat(new QuestionarioAnamnese(codigo));
		return super.findByKey(format);
	}
	
	@Override
	protected void afterUpdate(QuestionarioAnamnese o) throws Exception {
		updateRelationship(o);
	}

	@Override
	protected void afterInsert(QuestionarioAnamnese o) throws Exception {
		updateRelationship(o);
	}
	
	private void updateRelationship(QuestionarioAnamnese o) throws Exception {
		DaoQuestaoAnamnese daoQA = new DaoQuestaoAnamnese();
		daoQA.updateRelationshipQuestionarioAnamnese(o);
	}
	
	@Override
	protected void afterLoad(QuestionarioAnamnese o) throws Exception {
		DaoQuestaoQuestionarioAnamnese daoQQA = new DaoQuestaoQuestionarioAnamnese();		
		List<QuestaoQuestionarioAnamnese> questoes = daoQQA.getQQARelationshipQA(o.getCodigo());
		Map<QuestaoQuestionarioAnamnese,QuestaoAnamnese> questoesMap = new TreeMap<QuestaoQuestionarioAnamnese, QuestaoAnamnese>();
		DaoQuestaoAnamnese daoQA = new DaoQuestaoAnamnese();
		for(QuestaoQuestionarioAnamnese qqa : questoes){
			questoesMap.put(qqa, daoQA.findByKey(qqa.getQuestaoAnamneseId()));
		}
		o.setQuestoes(questoesMap);
	}
	
	@Override
	protected boolean beforeRemove(QuestionarioAnamnese o,	Map<String, Object> params) throws Exception {
		EntityManager<QuestaoQuestionarioAnamnese> daoQQA = new DaoQuestaoQuestionarioAnamnese();
		for(QuestaoQuestionarioAnamnese qqa : o.getQuestoes().keySet()){
			daoQQA.remover(qqa);
		}
		return true;
	}

	public void updateRelationshipPaciente(Paciente paciente) throws Exception {
		Long pacienteId = paciente.getCodigo();	
		List<QuestionarioAnamnese> questionarios = new ArrayList<QuestionarioAnamnese>(paciente.getAnamneses().values());	
		if(questionarios != null){		    
			DaoPacienteQuestionarioAnamnese daoPQA = new DaoPacienteQuestionarioAnamnese();			
			List<PacienteQuestionarioAnamnese> pqas =  daoPQA.getPQARelationshipPaciente(pacienteId);			
			for(PacienteQuestionarioAnamnese pqa : pqas){
				if(!containsPQARelationship(questionarios,pqa)){
					daoPQA.remover(pqa);
				}
			}
			Iterator<Entry<PacienteQuestionarioAnamnese, QuestionarioAnamnese>> iterator = paciente.getAnamneses().entrySet().iterator();
			while(iterator.hasNext()){
			    Entry<PacienteQuestionarioAnamnese, QuestionarioAnamnese> entry = iterator.next();
			    PacienteQuestionarioAnamnese pqa = entry.getKey();
			    QuestionarioAnamnese questionario = entry.getValue();
			    if(!containsQARelationship(pqas, questionario)){
			        pqa.setPacienteId(pacienteId);
			        pqa.setQuestionarioAnamneseId(questionario.getCodigo());
			        daoPQA.inserir(pqa);
			    }else{
			        daoPQA.alterar(pqa);
			    }
			}			
		}
	}

	private boolean containsQARelationship(	List<PacienteQuestionarioAnamnese> pqas,QuestionarioAnamnese questionario) {
		for(Iterator<PacienteQuestionarioAnamnese> iterator = pqas.iterator();iterator.hasNext();){
			if(iterator.next().getQuestionarioAnamneseId().equals(questionario.getCodigo())){
				return true;
			}
		}
		return false;
	}

	private boolean containsPQARelationship(List<QuestionarioAnamnese> questionarios,PacienteQuestionarioAnamnese pqa) {
		for(Iterator<QuestionarioAnamnese> iterator = questionarios.iterator();iterator.hasNext();){
			if(iterator.next().getCodigo().equals(pqa.getQuestionarioAnamneseId())){
				return true;
			}
		}
		return false;
	}

	public List<QuestionarioAnamnese> getQARelationshipPaciente(Long idPaciente) throws Exception {
		OrmFormat orm = new OrmFormat(new Odontograma(idPaciente));
		IQuery query = CrudQuery.getSelectQuery(QuestionarioAnamnese.class, orm.formatNotNull(), "*");
		List<QuestionarioAnamnese> loads = getSqlExecutor().executarQuery(query);
		for(QuestionarioAnamnese o : loads){
			afterLoad(o);
		}
		return loads;
	}

}
