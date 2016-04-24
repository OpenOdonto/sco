package br.ueg.openodonto.persistencia.dao;

import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.jdbc.EntityManager;
import br.com.vitulus.simple.jdbc.annotation.Dao;
import br.ueg.openodonto.dominio.Odontograma;
import br.ueg.openodonto.dominio.Paciente;
import br.ueg.openodonto.dominio.PacienteQuestionarioAnamnese;
import br.ueg.openodonto.dominio.QuestionarioAnamnese;

@Dao(classe=Paciente.class)
public class DaoPaciente extends DaoAbstractPessoa<Paciente>{

    private static final long serialVersionUID = -4278752127118870714L;

	public DaoPaciente() {
		super(Paciente.class);
	}

	@Override
	public Paciente getNewEntity() {
		return new Paciente();
	}
	
	@Override
	protected void afterUpdate(Paciente o) throws Exception {
		super.afterUpdate(o);
		updateRelationshipOdontograma(o);
		updateRelationshipAnaminese(o);
		updateRelationshipPlanejamento(o);
	}
	
	private void updateRelationshipOdontograma(Paciente o) throws Exception{
		DaoOdontograma daoOdontograma = new DaoOdontograma();
		daoOdontograma.updateRelationshipPaciente(o.getOdontogramas(), o.getCodigo());
	}
	
	private void updateRelationshipAnaminese(Paciente o) throws Exception{
		DaoQuestionarioAnamnese daoQA = new DaoQuestionarioAnamnese();
		daoQA.updateRelationshipPaciente(o);
	}
	
	private void updateRelationshipPlanejamento(Paciente o){
		
	}
	
	@Override
	protected void afterInsert(Paciente o) throws Exception {
		super.afterInsert(o);
		updateRelationshipOdontograma(o);
		updateRelationshipAnaminese(o);
		updateRelationshipPlanejamento(o);
	}
	
	@Override
	public void afterLoad(Paciente o) throws Exception {
		super.afterLoad(o);
		loadOdontograma(o);
		loadAnaminese(o);
		loadPlanejamento(o);
	}

	private void loadOdontograma(Paciente o) throws Exception{
		DaoOdontograma daoOdontograma = new DaoOdontograma();
		o.setOdontogramas(daoOdontograma.getOdontogramasRelationshipPaciente(o.getCodigo()));
	}
	
	private void loadAnaminese(Paciente o) throws Exception{
		DaoQuestionarioAnamnese daoQA = new DaoQuestionarioAnamnese();
		DaoPacienteQuestionarioAnamnese daoPQA = new DaoPacienteQuestionarioAnamnese();
		List<PacienteQuestionarioAnamnese> pqas = daoPQA.getPQARelationshipPaciente(o.getCodigo());
		for(PacienteQuestionarioAnamnese pqa : pqas){
			QuestionarioAnamnese qa = daoQA.findByKey(pqa.getQuestionarioAnamneseId());
			daoQA.afterLoad(qa);
			daoPQA.afterLoad(pqa);
			o.getAnamneses().put(pqa, qa);
		}
	}
	
	private void loadPlanejamento(Paciente o){		
	}
	
	@Override
	protected boolean beforeRemove(Paciente o, Map<String, Object> params)throws Exception {
		boolean referenced = super.beforeRemove(o, params);
		removeOdontograma(o);
		removeAnamise(o);
		removePlanejamento(o);
		return referenced;
	}
	
	private void removeOdontograma(Paciente o) throws Exception{
		EntityManager<Odontograma> entityManagerOdontograma = new DaoOdontograma();
		for (Odontograma odontograma : o.getOdontogramas()) {
			entityManagerOdontograma.remover(odontograma);			
		}
	}
	
	private void removeAnamise(Paciente o) throws Exception{		
		EntityManager<PacienteQuestionarioAnamnese> entityManagerPQA = new DaoPacienteQuestionarioAnamnese();
		for(PacienteQuestionarioAnamnese pqa : o.getAnamneses().keySet()){
		    entityManagerPQA.remover(pqa);
		}		
	}
	
	private void removePlanejamento(Paciente o){		
	}
	
}
