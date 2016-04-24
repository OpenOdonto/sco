package br.ueg.openodonto.controle;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.validator.EmptyValidator;
import br.com.vitulus.simple.validator.NullValidator;
import br.ueg.openodonto.controle.busca.CommonSearchAssociateHandler;
import br.ueg.openodonto.controle.busca.CommonSearchQuestaoAnamneseHandler;
import br.ueg.openodonto.controle.busca.CommonSearchQuestionarioAnamneseHandler;
import br.ueg.openodonto.controle.busca.CommonSearchSelectableBeanHandler;
import br.ueg.openodonto.controle.busca.SearchBase;
import br.ueg.openodonto.controle.busca.SearchableQuestaoAnamnese;
import br.ueg.openodonto.controle.busca.SearchableQuestionarioAnamnese;
import br.ueg.openodonto.controle.busca.SelectableSearchBase;
import br.ueg.openodonto.controle.busca.ViewDisplayer;
import br.ueg.openodonto.controle.servico.ManageListagem;
import br.ueg.openodonto.controle.servico.ManageQuestaoQuestionario;
import br.ueg.openodonto.controle.servico.ValidationRequest;
import br.ueg.openodonto.dominio.QuestaoAnamnese;
import br.ueg.openodonto.dominio.QuestionarioAnamnese;
import br.ueg.openodonto.persistencia.dao.DaoQuestaoAnamnese;
import br.ueg.openodonto.servico.busca.ResultFacade;
import br.ueg.openodonto.servico.busca.Search;
import br.ueg.openodonto.servico.busca.SelectableSearch;
import br.ueg.openodonto.validator.ValidatorFactory;

public class ManterQuestionarioAnamnese extends ManageBeanGeral<QuestionarioAnamnese>{

	private static final long serialVersionUID = 5029826299171411486L;
	
	private static Map<String, String>  	 	params;
	private Search<QuestionarioAnamnese>     	search;
	private SelectableSearch<QuestaoAnamnese>   searchQuestao;
	private ManageQuestaoQuestionario           manageQuestao;
	
	static{
		params = new HashMap<String, String>();
		params.put("managebeanName", "manterQuestionarioAnamnese");
		params.put("formularioSaida", "formQuestionarioAnamnese");
		params.put("saidaPadrao", "formQuestionarioAnamnese:output");
	}
	
	public ManterQuestionarioAnamnese() {
		super(QuestionarioAnamnese.class);
	}
	
	@Override
	protected void initExtra() {
		makeView(params);		
		this.search = new SearchBase<QuestionarioAnamnese>(
				new SearchableQuestionarioAnamnese(new ViewDisplayer("searchDefault",getView())),
				"Buscar Questionário",
				"painelBusca");
		this.searchQuestao = new SelectableSearchBase<QuestaoAnamnese>(
				new SearchableQuestaoAnamnese(new ViewDisplayer("searchQuestion",getView())),
				"Associar Questão",
				"painelBuscaQuestao");
		this.search.addSearchListener(new CommonSearchQuestionarioAnamneseHandler());
		this.search.addSearchListener(new SearchSelectedHandler());
		this.searchQuestao.addSearchListener(new SearchQuestaoSelectableHandler());
		this.searchQuestao.addSearchListener(new SearchQuestaoAssociateHandler());
		manageQuestao = new ManageQuestaoQuestionario(getQuestionario().getQuestoes(),getView());
	}
	
	@Override
	public void acaoSalvar() {
		getManageQuestao().syncListOrder();
		super.acaoSalvar();
		ManageListagem.getLista("ALIAS_QUEST").refreshDominio();
	}

	@Override
	protected void handleSalvarException(Exception ex) {
		if(ex instanceof SQLIntegrityConstraintViolationException){
			exibirPopUp("Questão(ões) Referenciada(s).");
			getView().addLocalDynamicMenssage("Registro Referenciado.","saidaPadrao", true);
		}else{
			super.handleSalvarException(ex);
		}
	}
	
	@Override
	protected void carregarExtra() {
		getManageQuestao().setQuestoes(getQuestionario().getQuestoes());
	}
	
	protected List<String> getCamposFormatados() {
		String[] formatados = {"nome"};
		return Arrays.asList(formatados);
	}
	
	@Override
	protected List<ValidationRequest> getCamposObrigatorios() {
		List<ValidationRequest> obrigatorios = new ArrayList<ValidationRequest>();
		obrigatorios.add(new ValidationRequest("nome", ValidatorFactory.newStrEmpty(), "formQuestionarioAnamnese:entradaNome"));
		return obrigatorios;
	}
	
	@Override
	protected List<ValidationRequest> getCamposValidados() {
		List<ValidationRequest> validados = new ArrayList<ValidationRequest>();
		Class<?>[] allowed = {NullValidator.class,EmptyValidator.class};
		validados.add(new ValidationRequest("nome", ValidatorFactory.newStrRangeLen(45, 4, true), "formQuestionarioAnamnese:entradaNome"));
		validados.add(new ValidationRequest("descricao", ValidatorFactory.newStrMaxLen(100, false), "formQuestionarioAnamnese:inTextBoxDesc",allowed));
		return validados;
	}
	
	public Search<QuestionarioAnamnese> getSearch() {
		return search;
	}
	
	public SelectableSearch<QuestaoAnamnese> getSearchQuestao() {
		return searchQuestao;
	}
	
	public QuestionarioAnamnese getQuestionario(){
		return getBackBean();
	}
	
	public void setQuestionario(QuestionarioAnamnese questionario){
		setBackBean(questionario);
	}
	
	public ManageQuestaoQuestionario getManageQuestao() {
		return manageQuestao;
	}

	public void setManageQuestao(ManageQuestaoQuestionario manageQuestao) {
		this.manageQuestao = manageQuestao;
	}

	private class SearchQuestaoSelectableHandler extends CommonSearchSelectableBeanHandler<QuestaoAnamnese>{
		private CommonSearchQuestaoAnamneseHandler searchQuestao;
		public SearchQuestaoSelectableHandler() {
			super(QuestaoAnamnese.class,new DaoQuestaoAnamnese());
			searchQuestao = new CommonSearchQuestaoAnamneseHandler(){
				@Override
				protected void addResults(Search<QuestaoAnamnese> search,List<Map<String, Object>> result) {
					SearchQuestaoSelectableHandler.this.addResults(search, result);
				}
				@Override
				protected ResultFacade buildWrapBean(Map<String, Object> value) {
					return SearchQuestaoSelectableHandler.this.buildWrapBean(value);
				}
			};
		}
		@Override
		public String[] getShowColumns() {
			return searchQuestao.getShowColumns();
		}
		@Override
		protected List<ResultFacade> wrapResult(List<Map<String, Object>> result) {
			return searchQuestao.wrapResult(result);
		}
	}
	
	public class SearchQuestaoAssociateHandler extends CommonSearchAssociateHandler<QuestaoAnamnese>{
		public SearchQuestaoAssociateHandler() {
			super(new DaoQuestaoAnamnese());
		}
		@Override
		public void associateBeans(List<QuestaoAnamnese> associated) {
			getManageQuestao().associarQuestao(associated);
		}
		
	}
	
}
