package br.ueg.openodonto.controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ueg.openodonto.controle.busca.CommonSearchQuestaoAnamneseHandler;
import br.ueg.openodonto.controle.busca.SearchBase;
import br.ueg.openodonto.controle.busca.SearchableQuestaoAnamnese;
import br.ueg.openodonto.controle.busca.ViewDisplayer;
import br.ueg.openodonto.controle.servico.ValidationRequest;
import br.ueg.openodonto.dominio.QuestaoAnamnese;
import br.ueg.openodonto.servico.busca.Search;
import br.ueg.openodonto.validator.ValidatorFactory;

public class ManterQuestaoAnamnese extends ManageBeanGeral<QuestaoAnamnese>{

	private static final long serialVersionUID = 2478819295034765768L;

	private static Map<String, String>   			params;
	private Search<QuestaoAnamnese>      			search;
	
	static{
		params = new HashMap<String, String>();
		params.put("managebeanName", "manterQuestaoAnamnese");
		params.put("formularioSaida", "formQuestaoAnamnese");
		params.put("saidaPadrao", "formQuestaoAnamnese:output");
	}
	
	public ManterQuestaoAnamnese() {
		super(QuestaoAnamnese.class);
	}
	
	protected void initExtra() {
		makeView(params);
		this.search = new SearchBase<QuestaoAnamnese>(
				new SearchableQuestaoAnamnese(new ViewDisplayer("searchDefault",getView())),
				"Buscar Questão",
				"painelBusca");
		this.search.addSearchListener(new CommonSearchQuestaoAnamneseHandler());
		this.search.addSearchListener(new SearchSelectedHandler());
	}

	@Override
	protected List<ValidationRequest> getCamposObrigatorios() {
		List<ValidationRequest> obrigatorios = new ArrayList<ValidationRequest>();
		obrigatorios.add(new ValidationRequest("pergunta", ValidatorFactory.newStrEmpty(), "formQuestaoAnamnese:entradaQuestao"));
		return obrigatorios;
	}
	
	@Override
	protected List<ValidationRequest> getCamposValidados(){
		List<ValidationRequest> validados = new ArrayList<ValidationRequest>();
		validados.add(new ValidationRequest("pergunta", ValidatorFactory.newStrRangeLen(300, 5, false), "formQuestaoAnamnese:entradaQuestao"));
		return validados;
	}
	
	public QuestaoAnamnese getQuestao(){
		return getBackBean();	
	}
	
	public void setQuestaoAnamnese(QuestaoAnamnese questao){
		setBackBean(questao);
	}
	
	public Search<QuestaoAnamnese> getSearch() {
		return search;
	}
	
}
