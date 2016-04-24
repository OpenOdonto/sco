package br.ueg.openodonto.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.jdbc.annotation.Dao;
import br.com.vitulus.simple.jdbc.orm.old.DaoCrud;
import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.com.vitulus.simple.jdbc.sql.old.CrudQuery;
import br.com.vitulus.simple.jdbc.sql.old.IQuery;
import br.ueg.openodonto.dominio.PacienteAnamneseResposta;
import br.ueg.openodonto.dominio.PacienteQuestionarioAnamnese;
import br.ueg.openodonto.dominio.QuestaoAnamnese;


@Dao(classe=PacienteAnamneseResposta.class)
public class DaoPacienteAnamneseRespostas extends DaoCrud<PacienteAnamneseResposta>{

	private static final long serialVersionUID = 3114100911322875992L;
	
	public DaoPacienteAnamneseRespostas() {
		super(PacienteAnamneseResposta.class);
	}	

	@Override
	public PacienteAnamneseResposta getNewEntity() {
		return new PacienteAnamneseResposta();
	}
	
	public void updateRelationshipPQA(PacienteQuestionarioAnamnese pqa) throws Exception{
		Long pacienteId = pqa.getPacienteId();
		Long questionarioAnamneseId = pqa.getQuestionarioAnamneseId();
		if(pqa.getRespotas() != null){
			List<PacienteAnamneseResposta> respostas = new ArrayList<PacienteAnamneseResposta>(pqa.getRespotas().values());	
			List<PacienteAnamneseResposta> todos = getRelationshipPQA(pacienteId, questionarioAnamneseId); 
			for(PacienteAnamneseResposta resposta : todos){
				if(!respostas.contains(resposta)){
					remover(resposta);
				}
			}
			for(Iterator<PacienteAnamneseResposta> iterator = respostas.iterator();iterator.hasNext();){				
				PacienteAnamneseResposta respota = iterator.next();
				if(!todos.contains(respota)){
					respota.setPacienteId(pacienteId);
					respota.setQuestionarioAnamneseId(questionarioAnamneseId);
					inserir(respota);
				}else{
					alterar(respota);
				}
			}
		}


	}

	public List<PacienteAnamneseResposta> getRelationshipPQA(Long pacienteId,Long questionarioAnamneseId) throws SQLException{		
		OrmFormat orm = new OrmFormat(new PacienteAnamneseResposta(null, questionarioAnamneseId, pacienteId));
		IQuery query = CrudQuery.getSelectQuery(PacienteAnamneseResposta.class, orm.formatNotNull(), "*");
		List<PacienteAnamneseResposta> respostas = getSqlExecutor().executarQuery(query);
		return respostas;
	}
	
	public Map<QuestaoAnamnese,PacienteAnamneseResposta> getRespostasRelationshipPQA(Long pacienteId,Long questionarioAnamneseId) throws SQLException{
		List<PacienteAnamneseResposta> respostas = getRelationshipPQA(pacienteId,questionarioAnamneseId);
		Map<QuestaoAnamnese,PacienteAnamneseResposta> questaoRespostasMap = new HashMap<QuestaoAnamnese, PacienteAnamneseResposta>();
		DaoQuestaoAnamnese daoQA = new DaoQuestaoAnamnese();
		for(PacienteAnamneseResposta resposta : respostas){
			QuestaoAnamnese questao = daoQA.findByKey(resposta.getQuestaoAnamneseId());
			if(questao != null){
				questaoRespostasMap.put(questao, resposta);
			}
		}	
	    return questaoRespostasMap;
	}
	
}
