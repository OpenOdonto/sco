package br.ueg.openodonto.persistencia.dao;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import br.com.vitulus.simple.jdbc.EntityManager;
import br.com.vitulus.simple.jdbc.annotation.Dao;
import br.com.vitulus.simple.jdbc.orm.old.DaoCrud;
import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.com.vitulus.simple.jdbc.sql.old.CrudQuery;
import br.com.vitulus.simple.jdbc.sql.old.IQuery;
import br.ueg.openodonto.dominio.Odontograma;
import br.ueg.openodonto.dominio.OdontogramaDente;
import br.ueg.openodonto.dominio.OdontogramaDenteAspecto;

@Dao(classe=Odontograma.class)
public class DaoOdontograma extends DaoCrud<Odontograma>{

	private static final long serialVersionUID = -4979683578095554032L;

	public DaoOdontograma() {
		super(Odontograma.class);
	}

	@Override
	public Odontograma getNewEntity() {
		return new Odontograma();
	}

	@Override
	protected void afterUpdate(Odontograma o) throws Exception {
		updateRelationship(o);
	}
	
	@Override
	protected void afterInsert(Odontograma o) throws Exception {
		updateRelationship(o);
	}
	
	private void updateRelationship(Odontograma o) throws Exception{
		DaoOdontogramaDente daoOD = new DaoOdontogramaDente();
		daoOD.updateRelationshipOdontograma(o.getOdontogramaDentes(),o.getId());
		DaoOdontogramaDenteAspecto daoODA = new DaoOdontogramaDenteAspecto();
		daoODA.updateRelationshipOdontograma(o.getAspectos(), o.getId());
	}
	
	@Override
	protected void afterLoad(Odontograma o) throws Exception {
		DaoOdontogramaDente daoOD = new DaoOdontogramaDente();
		TreeSet<OdontogramaDente> ods = daoOD.getRelacionamentoOdontograma(o.getId());
		o.setOdontogramaDentes(ods);
		DaoOdontogramaDenteAspecto daoODA = new DaoOdontogramaDenteAspecto();
		List<OdontogramaDenteAspecto> aspectos = daoODA.getAscpectosRelationshipOdontograma(o.getId());
		o.setAspectos(aspectos);
	}
	
	@Override
	protected boolean beforeRemove(Odontograma o, Map<String, Object> params) throws Exception {
		EntityManager<OdontogramaDente> daoOD = new DaoOdontogramaDente();
		for (OdontogramaDente od : o.getOdontogramaDentes()) {
			daoOD.remover(od);			
		}
		EntityManager<OdontogramaDenteAspecto> daoODA = new DaoOdontogramaDenteAspecto();
		for(OdontogramaDenteAspecto oda : o.getAspectos()){
		    daoODA.remover(oda);
		}
		return true;
	}
	
	@Override
	public List<Odontograma> listar(boolean lazy, String... fields) {
		EntityManager<OdontogramaDente> emOD = new DaoOdontogramaDente();
		DaoOdontogramaDente daoOD = (DaoOdontogramaDente)emOD;
		List<Odontograma> lista = super.listar(lazy, fields);
		if (lista != null && !lazy) {
			for (Odontograma odontograma : lista) {
				try {
					odontograma.setOdontogramaDentes(daoOD.getRelacionamentoOdontograma(odontograma.getId()));
				} catch (Exception ex) {
				}
			}
		}
		return lista;
	}
	
	public List<Odontograma> getOdontogramasRelationshipPaciente(Long idPaciente) throws Exception{
		OrmFormat orm = new OrmFormat(new Odontograma(idPaciente));
		IQuery query = CrudQuery.getSelectQuery(Odontograma.class, orm.formatNotNull(), "*");
		List<Odontograma> loads = getSqlExecutor().executarQuery(query);
		for(Odontograma o : loads){
			afterLoad(o);
		}
		return loads;
	}
	
	public void updateRelationshipPaciente(List<Odontograma> odontogramas,Long idPaciente) throws Exception {
		if(odontogramas != null){
			List<Odontograma> todos = getOdontogramasRelationshipPaciente(idPaciente);
			for (Odontograma odontograma : todos) {
				if (!odontogramas.contains(odontograma)) {
					remover(odontograma);
				}
			}
			for (Odontograma odontograma : odontogramas) {
				odontograma.setIdPessoa(idPaciente);
				alterar(odontograma);
			}
		}
	}

}
