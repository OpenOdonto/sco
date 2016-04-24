package br.ueg.openodonto.controle.busca;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.jdbc.dao.DaoFactory;
import br.ueg.openodonto.dominio.Colaborador;
import br.ueg.openodonto.dominio.ColaboradorProduto;
import br.ueg.openodonto.dominio.Produto;
import br.ueg.openodonto.dominio.constante.CategoriaProduto;
import br.ueg.openodonto.persistencia.dao.DaoColaboradorProduto;
import br.ueg.openodonto.persistencia.dao.DaoProduto;
import br.ueg.openodonto.servico.busca.ResultFacade;
import br.ueg.openodonto.servico.busca.Search;
import br.ueg.openodonto.util.WordFormatter;

public class CommonSearchProdutoHandler extends CommonSearchBeanHandler<Produto>{
	
	private String[] showColumns = {"codigo", "nome", "categoria", "descricao"};
	
	public CommonSearchProdutoHandler(){
		super(Produto.class , new DaoProduto());
	}
	
	@Override
	public List<ResultFacade> wrapResult(List<Map<String, Object>> result) {
		List<ResultFacade> resultWrap = new ArrayList<ResultFacade>(result.size());
		Iterator<Map<String, Object>> iterator = result.iterator();
		while(iterator.hasNext()){
			Map<String,Object> value = iterator.next();
			value.put("shortDescription", WordFormatter.abstractStr(value.get("descricao").toString(), 60));
			value.put("categoriaDesc", CategoriaProduto.parseCategoria(value.get("categoria")));
			resultWrap.add(buildWrapBean(value));
		}
		return resultWrap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> evaluateResult(Search<Produto> search) throws SQLException{
		SearchableProduto searchable = (SearchableProduto)search.getSearchable();
		Produto produto = searchable.buildExample();
		Colaborador colaborador = searchable.buildExampleColaborador();
		DaoColaboradorProduto dao = new DaoColaboradorProduto();
		List<Map<String,Object>> result;
		if(colaborador == null){
			result = getDao().getSqlExecutor().executarUntypedQuery(getQuery(produto));
		}else{
			result = dao.getUntypeProdutos(colaborador, produto);
		}
		return result;			
	}
	
	@Override
	public String[] getShowColumns() {
		return showColumns;
	}
}
