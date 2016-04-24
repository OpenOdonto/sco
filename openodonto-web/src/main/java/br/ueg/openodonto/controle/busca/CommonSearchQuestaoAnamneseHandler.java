package br.ueg.openodonto.controle.busca;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.ueg.openodonto.dominio.QuestaoAnamnese;
import br.ueg.openodonto.persistencia.dao.DaoQuestaoAnamnese;
import br.ueg.openodonto.servico.busca.ResultFacade;
import br.ueg.openodonto.util.WordFormatter;

public class CommonSearchQuestaoAnamneseHandler extends CommonSearchBeanHandler<QuestaoAnamnese>{

	private String[] showColumns = {"codigo", "pergunta"};
	
	public CommonSearchQuestaoAnamneseHandler() {
		super(QuestaoAnamnese.class, new DaoQuestaoAnamnese());
	}

	public List<ResultFacade> wrapResult(List<Map<String, Object>> result) {
		List<ResultFacade> resultWrap = new ArrayList<ResultFacade>(result.size());
		Iterator<Map<String, Object>> iterator = result.iterator();
		while(iterator.hasNext()){
			Map<String,Object> value = iterator.next();
			value.put("shortQuestion", WordFormatter.abstractStr(value.get("pergunta").toString(), 90));
			resultWrap.add(buildWrapBean(value));
		}
		return resultWrap;
	}
	
	@Override
	public String[] getShowColumns() {
		return showColumns;
	}

}
