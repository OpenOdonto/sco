package br.ueg.openodonto.visao.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.richfaces.component.UIOrderingList;

import br.ueg.openodonto.controle.ManterQuestionarioAnamnese;
import br.ueg.openodonto.util.bean.QuestionarioQuestaoAdapter;

public class QuestaoQuestionarioConverter implements Converter{

	
	private Map<String,Integer> 	indexMap;
	private boolean 				isIndexed;
	
	private void doIndexMap(FacesContext context, UIComponent component){
		indexMap = new HashMap<String, Integer>();
		UIOrderingList orderingList = (UIOrderingList) component;		
		String clientId = orderingList.getBaseClientId(context);
        ExternalContext externalContext = context.getExternalContext();
		Map<String, String[]> requestParameterValuesMap = externalContext.getRequestParameterValuesMap();        
		String[] strings = (String[]) requestParameterValuesMap.get(clientId);        
		if (strings != null && strings.length != 0) {
			for(String string : strings){				
				boolean fwl = Character.isLetter(string.charAt(0));
				boolean swl = Character.isLetter(string.charAt(1));
				int indexDivisor = string.indexOf(":");
				int indexReal =  fwl ? swl ? 2 : 1 : 0;
				int indexVirtual = indexDivisor+1;
				Integer real = Integer.valueOf(string.substring(indexReal,indexDivisor));
				String virtual = string.substring(indexVirtual);
				indexMap.put(virtual, real);
			}
		}
		isIndexed = true;
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(!isIndexed){
			doIndexMap(context,component);
		}
		Integer id = indexMap.get(value);		
		ManterQuestionarioAnamnese mBean = getMbean(context);		
		List<QuestionarioQuestaoAdapter> questoes =  mBean.getManageQuestao().getQuestoesAdapter();
		QuestionarioQuestaoAdapter questao = questoes.get(id);
		return questao;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		QuestionarioQuestaoAdapter adapter = (QuestionarioQuestaoAdapter) value;
		return adapter.getQqa().getIndex().toString();
	}
	
	private ManterQuestionarioAnamnese getMbean(FacesContext context){
		ELContext elctx = context.getELContext();
		ManterQuestionarioAnamnese mBean = (ManterQuestionarioAnamnese)elctx.getELResolver().getValue(elctx, null, "manterQuestionarioAnamnese");
		return mBean;
	}

}
