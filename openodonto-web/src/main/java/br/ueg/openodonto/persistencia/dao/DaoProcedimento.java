package br.ueg.openodonto.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.jdbc.annotation.Dao;
import br.com.vitulus.simple.jdbc.orm.old.DaoCrud;
import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.ueg.openodonto.dominio.OdontogramaDente;
import br.ueg.openodonto.dominio.OdontogramaDenteProcedimento;
import br.ueg.openodonto.dominio.Procedimento;

@Dao(classe=Procedimento.class)
public class DaoProcedimento extends DaoCrud<Procedimento>{

	private static final long serialVersionUID = 4428579571426531692L;

	public DaoProcedimento() {
		super(Procedimento.class);
	}
	
	@Override
	public Procedimento getNewEntity() {
		return new Procedimento();
	}
	
	public Procedimento findByKey(Long codigo) throws SQLException{
		OrmFormat orm = new OrmFormat(new Procedimento(codigo));
		return super.findByKey(orm);
	}

	public void updateRelationshipOdontogramaDente(OdontogramaDente odontogramaDente) throws Exception{
		Long odontogramaDenteId = odontogramaDente.getCodigo();		
		if(odontogramaDente.getProcedimentosMap() != null){
			List<Procedimento> procedimentos = new ArrayList<Procedimento>(odontogramaDente.getProcedimentosMap().values());
			DaoOdontogramaDenteProcedimento daoODP = new DaoOdontogramaDenteProcedimento();
			List<OdontogramaDenteProcedimento> odps =  daoODP.getODPRelationshipOD(odontogramaDenteId);
			for(OdontogramaDenteProcedimento odp : odps){
				if(!containsODPRelationship(procedimentos,odp)){
					daoODP.remover(odp);
				}
			}			
			Iterator<Map.Entry<OdontogramaDenteProcedimento, Procedimento>> iterator = odontogramaDente.getProcedimentosMap().entrySet().iterator();			
			while(iterator.hasNext()){
				Map.Entry<OdontogramaDenteProcedimento, Procedimento> entry = iterator.next();
				Procedimento procedimento = entry.getValue();
				OdontogramaDenteProcedimento odp = entry.getKey(); 
				if(!containsPRelationship(odps,procedimento)){
					odp.setOdontogramaDenteId(odontogramaDenteId);
					odp.setProcedimentoId(procedimento.getCodigo());
					daoODP.inserir(odp);
				}else{
					daoODP.alterar(odp);
				}
			}
		}
	}
	
	private boolean containsPRelationship(List<OdontogramaDenteProcedimento> odps,Procedimento procedimento){
		for(Iterator<OdontogramaDenteProcedimento> iterator = odps.iterator();iterator.hasNext();){
			if(iterator.next().getProcedimentoId().equals(procedimento.getCodigo())){
				return true;
			}
		}
		return false;
	}
	
	private boolean containsODPRelationship(List<Procedimento> procedimentos,OdontogramaDenteProcedimento odp){
		for(Iterator<Procedimento> iterator = procedimentos.iterator();iterator.hasNext();){
			if(iterator.next().getCodigo().equals(odp.getProcedimentoId())){
				return true;
			}
		}
		return false;
	}
	
	public List<Procedimento> getProcedimentosRelationshipOdontogramaDente(Long odontogramaDenteId)throws SQLException {
		DaoOdontogramaDenteProcedimento dao = new DaoOdontogramaDenteProcedimento();
		return dao.getProcediementos(odontogramaDenteId);
	}	
	
}
