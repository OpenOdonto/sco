package br.ueg.openodonto.controle.busca;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.jdbc.EntityManager;
import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.com.vitulus.simple.jdbc.sql.old.CrudQuery;
import br.com.vitulus.simple.jdbc.sql.old.IQuery;
import br.ueg.openodonto.dominio.Pessoa;
import br.ueg.openodonto.persistencia.dao.DaoPessoa;
import br.ueg.openodonto.servico.busca.Search;

public class CommonSearchPessoaHandler extends CommonSearchBeanHandler<Pessoa>{
	
	private String[] showColumns = {"codigo", "nome", "email"};
	
	public CommonSearchPessoaHandler() {
		super(Pessoa.class, new DaoPessoa());
	}	
	@Override
	public String[] getShowColumns() {
		return showColumns;
	}		
	@Override
	public IQuery getQuery(Pessoa example){
		OrmFormat format = new OrmFormat(example);
		return CrudQuery.getSelectQuery(Pessoa.class, format.formatNotNull(),  getShowColumns());
	}
	@Override
	public List<Map<String,Object>> evaluateResult(Search<Pessoa> search) throws SQLException{
		Pessoa target = buildExample(search.getSearchable());
		IQuery query = getQuery(target);	
		EntityManager<Pessoa> dao = new DaoPessoa();
		List<Map<String,Object>> result = dao.getSqlExecutor().executarUntypedQuery(query);
		return result;
	}		
}
