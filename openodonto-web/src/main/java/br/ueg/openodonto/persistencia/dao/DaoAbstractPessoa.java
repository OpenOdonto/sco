package br.ueg.openodonto.persistencia.dao;

import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.jdbc.EntityManager;
import br.com.vitulus.simple.jdbc.orm.old.DaoCrud;
import br.com.vitulus.simple.jdbc.sql.old.CrudQuery;
import br.ueg.openodonto.dominio.Pessoa;
import br.ueg.openodonto.dominio.Telefone;

public abstract class DaoAbstractPessoa<T extends Pessoa> extends DaoCrud<T>{

	private static final long serialVersionUID = -2530942287674194792L;

	public DaoAbstractPessoa(Class<T> classe) {
		super(classe);
	}
	
	public boolean isLastConstraintWithTelefone(List<String> referencias){
		boolean lastWithThis = referencias.contains(CrudQuery.getTableName(getClasse())) &&
		referencias.contains(CrudQuery.getTableName(Telefone.class))&&
		referencias.size() == 2;
		boolean justLast = referencias.contains(CrudQuery.getTableName(Telefone.class))&&
		referencias.size() == 1;
		return lastWithThis || justLast;
	}	
	
	@Override
	protected void afterUpdate(T o) throws Exception {
		updateRelationship(o);
	}
	
	@Override
	protected void afterInsert(T o) throws Exception {
		updateRelationship(o);
	}

	private void updateRelationship(T o) throws Exception{
		EntityManager<Telefone> entityManagerTelefone = new DaoTelefone();
		DaoTelefone daoTelefone = (DaoTelefone) entityManagerTelefone;
		daoTelefone.updateRelationshipPessoa(o.getTelefone(), o.getCodigo());
	}	
	
	@Override
	public void afterLoad(T o) throws Exception {
		EntityManager<Telefone> entityManagerTelefone = new DaoTelefone();
		DaoTelefone daoTelefone = (DaoTelefone) entityManagerTelefone;
		List<Telefone> telefones = daoTelefone.getTelefonesRelationshipPessoa(o.getCodigo());
		o.setTelefone(telefones);
	}
	
	@Override
	protected boolean beforeRemove(T o, Map<String, Object> params)throws Exception {
		List<String> referencias = referencedConstraint(Pessoa.class, params);
		if (isLastConstraintWithTelefone(referencias)) {
			EntityManager<Telefone> entityManagerTelefone = new DaoTelefone();
			for (Telefone telefone : o.getTelefone()) {
				entityManagerTelefone.remover(telefone);
			}
			return false;
		} else {
			return true;
		}
	}

}
