package br.ueg.openodonto.servico.listagens.core;

import java.util.ArrayList;
import java.util.List;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.EntityManager;
import br.ueg.openodonto.controle.ManageBeanGeral;

/**
 * @author vinicius.rodrigues
 * 
 * @param <T>
 */
public class ListaDominio<T extends Entity> extends AbstractLista<T> {

	public ListaDominio(Class<T> classe) {
		super(classe);
	}

	public List<T> getRefreshDominio() {
		EntityManager<T> daoDominio = ManageBeanGeral.getDao(getClasse());
		List<T> lista = new ArrayList<T>();
		try {
			lista = daoDominio.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public boolean isChangeable() {
		return true;
	}
}
