package br.ueg.openodonto.persistencia.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.vitulus.simple.jdbc.annotation.Dao;
import br.com.vitulus.simple.jdbc.orm.old.DaoCrud;
import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.com.vitulus.simple.jdbc.sql.old.CrudQuery;
import br.com.vitulus.simple.jdbc.sql.old.IQuery;
import br.ueg.openodonto.dominio.OdontogramaDenteAspecto;

@Dao(classe=OdontogramaDenteAspecto.class)
public class DaoOdontogramaDenteAspecto extends DaoCrud<OdontogramaDenteAspecto>{

	private static final long serialVersionUID = 1175197509339635710L;

	public DaoOdontogramaDenteAspecto() {
		super(OdontogramaDenteAspecto.class);
	}

	@Override
	public OdontogramaDenteAspecto getNewEntity() {
		return new OdontogramaDenteAspecto();
	}
	
	public void updateRelationshipOdontograma(List<OdontogramaDenteAspecto> aspectos,Long idOdontograma) throws Exception {
		if(aspectos != null){
			List<OdontogramaDenteAspecto> todos = getAscpectosRelationshipOdontograma(idOdontograma);
			for (OdontogramaDenteAspecto aspecto : todos) {
				if (!aspectos.contains(aspecto)) {
					remover(aspecto);
				}
			}
			for (OdontogramaDenteAspecto aspecto : aspectos) {
				aspecto.setIdOdontograma(idOdontograma);
				alterar(aspecto);
			}
		}
	}	
	
	public List<OdontogramaDenteAspecto> getAscpectosRelationshipOdontograma(Long idOdontograma)throws SQLException {
		OrmFormat orm = new OrmFormat(new OdontogramaDenteAspecto(idOdontograma));
		IQuery query = CrudQuery.getSelectQuery(OdontogramaDenteAspecto.class, orm.formatNotNull(), "*");
		return getSqlExecutor().executarQuery(query);
	}	

}
