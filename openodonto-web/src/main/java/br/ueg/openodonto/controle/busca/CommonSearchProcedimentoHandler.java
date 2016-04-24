package br.ueg.openodonto.controle.busca;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.ueg.openodonto.dominio.Procedimento;
import br.ueg.openodonto.persistencia.dao.DaoProcedimento;
import br.ueg.openodonto.servico.busca.ResultFacade;
import br.ueg.openodonto.util.WordFormatter;

public class CommonSearchProcedimentoHandler extends CommonSearchBeanHandler<Procedimento>{
	
	private String[] showColumns = {"codigo", "nome", "valor","descricao"};
	
	public CommonSearchProcedimentoHandler(){
		super(Procedimento.class , new DaoProcedimento());
	}
	
	public List<ResultFacade> wrapResult(List<Map<String, Object>> result) {
		List<ResultFacade> resultWrap = new ArrayList<ResultFacade>(result.size());
		Iterator<Map<String, Object>> iterator = result.iterator();
		while(iterator.hasNext()){
			Map<String,Object> value = iterator.next();
			value.put("shortDescription", WordFormatter.abstractStr(value.get("descricao").toString(), 60));
			resultWrap.add(buildWrapBean(value));
		}
		return resultWrap;
	}

	@Override
	public String[] getShowColumns() {
		return showColumns;
	}
	
}
