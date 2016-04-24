package br.ueg.openodonto.persistencia.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.jdbc.annotation.Dao;
import br.com.vitulus.simple.jdbc.dao.DaoFactory;
import br.com.vitulus.simple.jdbc.orm.old.DaoCrud;
import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.com.vitulus.simple.jdbc.orm.old.OrmTranslator;
import br.com.vitulus.simple.jdbc.sql.old.CrudQuery;
import br.com.vitulus.simple.jdbc.sql.old.IQuery;
import br.ueg.openodonto.dominio.Paciente;
import br.ueg.openodonto.dominio.PacienteAnamneseResposta;
import br.ueg.openodonto.dominio.PacienteQuestionarioAnamnese;
import br.ueg.openodonto.dominio.QuestaoAnamnese;
import br.ueg.openodonto.dominio.QuestionarioAnamnese;

@Dao(classe=PacienteQuestionarioAnamnese.class)
public class DaoPacienteQuestionarioAnamnese extends DaoCrud<PacienteQuestionarioAnamnese> {

	public DaoPacienteQuestionarioAnamnese() {
		super(PacienteQuestionarioAnamnese.class);
	}

	private static final long serialVersionUID = 2819939273529100056L;

	@Override
	public PacienteQuestionarioAnamnese getNewEntity() {
		return new PacienteQuestionarioAnamnese();
	}
	
	@Override
	protected void afterUpdate(PacienteQuestionarioAnamnese o) throws Exception {
	    updateRelationship(o);
	}
	
	@Override
	protected void afterInsert(PacienteQuestionarioAnamnese o) throws Exception {
	    updateRelationship(o);
	}

	private void updateRelationship(PacienteQuestionarioAnamnese o) throws Exception{
	    DaoPacienteAnamneseRespostas daoPAR = new DaoPacienteAnamneseRespostas();
	    daoPAR.updateRelationshipPQA(o);
	}
	
	@Override
	protected void afterLoad(PacienteQuestionarioAnamnese o) throws Exception {	    
	    DaoPacienteAnamneseRespostas daoPAR = new DaoPacienteAnamneseRespostas();
	    Map<QuestaoAnamnese,PacienteAnamneseResposta> questaoRespostasMap = daoPAR.getRespostasRelationshipPQA(o.getPacienteId(),o.getQuestionarioAnamneseId());
	    o.setRespotas(questaoRespostasMap);
	    
	}
	
	@Override
	protected boolean beforeRemove(PacienteQuestionarioAnamnese o,Map<String, Object> params) throws Exception {
	    DaoPacienteAnamneseRespostas daoPAR = new DaoPacienteAnamneseRespostas();
	    for(PacienteAnamneseResposta par : o.getRespotas().values()){
	        daoPAR.remover(par);
	    }
	    return true;
	}
	
	public List<Paciente> getPacientes(QuestionarioAnamnese questionario,Paciente paciente) throws SQLException{
		return getRelacionamento(questionario,paciente,"questionarioAnamneseId","pacienteId");
	}
	
	public List<QuestionarioAnamnese> getQuestionarios(Paciente paciente,QuestionarioAnamnese questionario) throws SQLException{
		return getRelacionamento(paciente,questionario,"pacienteId","questionarioAnamneseId");
	}

	public List<Paciente> getPacientes(Long questionarioAnamneseId) throws SQLException{
		Map<String,Object> whereParams = new HashMap<String, Object>();
		OrmTranslator translator = new OrmTranslator(fields);
		whereParams.put(translator.getColumn("questionarioAnamneseId"), questionarioAnamneseId);
		return getRelacionamento(Paciente.class,"pacienteId",whereParams);
	}
	
	public List<QuestionarioAnamnese> getQuestionarios(Long pacienteId) throws SQLException{
		Map<String,Object> whereParams = new HashMap<String, Object>();
		OrmTranslator translator = new OrmTranslator(fields);
		whereParams.put(translator.getColumn("pacienteId"), pacienteId);
		return getRelacionamento(QuestionarioAnamnese.class,"questionarioAnamneseId",whereParams);
	}
	
	public List<PacienteQuestionarioAnamnese> getPQARelationshipPaciente(Long pacienteId) throws Exception {
		OrmFormat orm = new OrmFormat(new PacienteQuestionarioAnamnese(pacienteId, null));
		IQuery query = CrudQuery.getSelectQuery(PacienteQuestionarioAnamnese.class, orm.formatNotNull(), "*");
		List<PacienteQuestionarioAnamnese> loads = getSqlExecutor().executarQuery(query);
		for(PacienteQuestionarioAnamnese pqa : loads){
			afterLoad(pqa);
		}
		return loads;
	}
	
	public List<PacienteQuestionarioAnamnese> getPQARelationshipQA(Long questionarioAnamneseId) throws SQLException {
		OrmFormat orm = new OrmFormat(new PacienteQuestionarioAnamnese(null, questionarioAnamneseId));
		IQuery query = CrudQuery.getSelectQuery(PacienteQuestionarioAnamnese.class, orm.formatNotNull(), "*");
		return getSqlExecutor().executarQuery(query);
	}

	public List<PacienteQuestionarioAnamnese> getPQARelationshipPAR(Long pacienteId, Long questionarioAnamneseId) throws SQLException {
		OrmFormat orm = new OrmFormat(new PacienteQuestionarioAnamnese(pacienteId, questionarioAnamneseId));
		IQuery query = CrudQuery.getSelectQuery(PacienteQuestionarioAnamnese.class, orm.formatNotNull(), "*");
		return getSqlExecutor().executarQuery(query);
	}

}
