package br.ueg.openodonto.controle.busca;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.ueg.openodonto.dominio.Colaborador;
import br.ueg.openodonto.dominio.Produto;
import br.ueg.openodonto.dominio.constante.CategoriaProduto;
import br.ueg.openodonto.persistencia.dao.DaoColaborador;
import br.ueg.openodonto.persistencia.dao.DaoColaboradorProduto;
import br.ueg.openodonto.servico.busca.ResultFacade;
import br.ueg.openodonto.servico.busca.Search;

public abstract class CommonSearchColaboradorHandler extends CommonSearchBeanHandler<Colaborador>{

	private String[] showColumns = {"codigo","nome","email","cpf","cnpj"};
	
	public CommonSearchColaboradorHandler() {
		super(Colaborador.class, new DaoColaborador());
	}
	
	@Override
	public List<ResultFacade> wrapResult(List<Map<String, Object>> result) {
		List<ResultFacade> resultWrap = new ArrayList<ResultFacade>(result.size());
		Iterator<Map<String, Object>> iterator = result.iterator();
		while(iterator.hasNext()){
			Map<String,Object> value = iterator.next();
			Object cpf = value.get("cpf");
			Object cnpj = value.get("cnpj");
			String documento = (cpf == null ? (cnpj != null ? cnpj : "") : cpf).toString();
			value.put("documento", documento);
			resultWrap.add(buildWrapBean(value));
		}
		return resultWrap;
	}
	
	@Override
	@SuppressWarnings("unchecked")	
	public List<Map<String,Object>> evaluateResult(Search<Colaborador> search) throws SQLException{
		SearchableColaborador searchable = (SearchableColaborador)search.getSearchable();
		searchable.setCategoria(getCategoria());
		Colaborador colaborador = searchable.buildExample();
		Produto produto = searchable.buildExampleProduto();
		DaoColaboradorProduto dao = new DaoColaboradorProduto();
		List<Map<String,Object>> result;
		if(produto == null){
			result = getDao().getSqlExecutor().executarUntypedQuery(getQuery(colaborador));
		}else{
			result = dao.getUntypeColaboradores(produto,colaborador);
		}
		return result;			
	}
	public abstract CategoriaProduto getCategoria();
	public String[] getShowColumns() {
		return showColumns;
	}
}
