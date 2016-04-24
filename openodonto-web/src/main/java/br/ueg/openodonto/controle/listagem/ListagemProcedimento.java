package br.ueg.openodonto.controle.listagem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.vitulus.simple.jdbc.EntityManager;
import br.ueg.openodonto.dominio.Procedimento;
import br.ueg.openodonto.persistencia.dao.DaoProcedimento;
import br.ueg.openodonto.servico.listagens.core.ListaDominio;

public class ListagemProcedimento extends ListaDominio<Procedimento> {

	public ListagemProcedimento() {
		super(Procedimento.class);
	}
	
	@Override
	public List<Procedimento> getRefreshDominio() {
		EntityManager<Procedimento> daoDominio = new DaoProcedimento();
		List<Procedimento> lista = new ArrayList<Procedimento>();		
		try {
			lista = daoDominio.listar(true, "codigo","nome","valor");
			Collections.sort(lista, new ProcedimentoNomeComparator());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}	
	
	@Override
	public boolean isChangeable() {
		return false;
	}

	private class ProcedimentoNomeComparator implements Comparator<Procedimento>{
		@Override
		public int compare(Procedimento o1, Procedimento o2) {
			if(o1 == o2){
				return 0;
			}else if(o1 == null){
				return -1;
			}else if(o2 == null){
				return 1;
			}else if(o1.getNome() == o2.getNome()){
				return 0;
			}else if(o1.getNome() == null || o1.getNome().isEmpty()){
				return -1;
			}else if(o2.getNome() == null || o2.getNome().isEmpty()){
				return 1;
			}else{
				return o1.getNome().compareTo(o2.getNome());
			}
		}
		
	}
	
}
