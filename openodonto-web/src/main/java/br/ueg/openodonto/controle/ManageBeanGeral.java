package br.ueg.openodonto.controle;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.jdbc.BeanProperty;
import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.EntityManager;
import br.com.vitulus.simple.jdbc.dao.DaoCrud;
import br.com.vitulus.simple.validator.Validator;
import br.ueg.openodonto.controle.busca.CommonSearchPessoaSelectedHandler;
import br.ueg.openodonto.controle.busca.CommonSearchSelectedHandler;
import br.ueg.openodonto.controle.context.ApplicationContext;
import br.ueg.openodonto.controle.servico.ValidationRequest;
import br.ueg.openodonto.dominio.Pessoa;
import br.ueg.openodonto.dominio.Usuario;
import br.ueg.openodonto.util.WordFormatter;
import br.ueg.openodonto.validator.ValidatorFactory;
import br.ueg.openodonto.visao.ApplicationView;
import br.ueg.openodonto.visao.ApplicationViewFactory;
import br.ueg.openodonto.visao.ApplicationViewFactory.ViewHandler;
import br.ueg.openodonto.web.WebContext;

/**
 * @author vinicius.rodrigues
 * 
 * @param <T>
 */
public abstract class ManageBeanGeral<T extends Entity> implements Serializable{

	private static final long serialVersionUID = -6270953407778330292L;

	private T                   backBean;
	private Class<T>            classe;
	protected EntityManager<T>  dao;
	private ApplicationContext  context;
	private ApplicationView     view;
	private String              msgBundle;
	private boolean             canDelete;
	private DecimalFormat       decimalFormat;

	public ManageBeanGeral(Class<T> classe) {
		this.classe = classe;
		init();
	}
	
	protected void initExtra(){}

	protected void init() {
		this.canDelete = false;
		this.backBean = fabricarNovoBean();
		this.dao = getDao(classe);		
		this.context = new WebContext();
		decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(getContext().getClientLocale());
		initExtra();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static <E extends Entity> EntityManager<E> getDao(Class<E> clazz){
		String name = "Dao" + clazz.getSimpleName();
		String pakage = "br.ueg.openodonto.persistencia.dao";
		try {
			Class<EntityManager<E>> c = (Class<EntityManager<E>>) Class.forName(pakage + "." + name);
			return c.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected T fabricarNovoBean() {
		try {
			return classe.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void acaoShowed() {
		getView().showAction();
	}

	public void exibirSaida() {
		getView().showOut();
	}

	public void exibirPopUp(String message) {
		getView().showPopUp(message);
	}

	@SuppressWarnings("unchecked")
	protected List<ValidationRequest> getCamposObrigatorios(){
		return Collections.EMPTY_LIST;
	}
	
	@SuppressWarnings("unchecked")
	protected List<ValidationRequest> getCamposValidados(){
		return Collections.EMPTY_LIST;
	}

	@SuppressWarnings("unchecked")
	protected List<String> getCamposFormatados(){
		return Collections.EMPTY_LIST;
	}

	protected void formatarCamposExtras(){};

	protected void acaoFormatarCampos() throws Exception {
		formatarCamposExtras();
		List<String> camposFormatados = getCamposFormatados();
		for (String path : camposFormatados) {
			Object o = BeanProperty.instance().getNestedProperty(getBackBean(), path);
			String campoParaFormatar = null;
			if (o != null){
				campoParaFormatar = String.valueOf(o);
			}else{
				continue;
			}
			if (!campoParaFormatar.isEmpty()) {
				String atributoFormatado = WordFormatter.clear(WordFormatter.remover(campoParaFormatar)).toUpperCase();
				BeanProperty.instance().setNestedProperty(getBackBean(), path, atributoFormatado);
			}
		}
	}

	protected boolean evaluateValidators(List<ValidationRequest> validations) throws Exception{
		boolean returned = true;
		for (ValidationRequest validation : validations) {
			Validator validador = validation.getValidator();
			if(validation.getPath() != null && !validation.getPath().isEmpty()){
				validador.setValue(BeanProperty.instance().getNestedProperty(getBackBean(), validation.getPath()));
			}
			if (!validador.isValid() && ValidatorFactory.checkInvalidPermiteds(validation.getInvalidPermiteds(),validador)) {
				for(String out : validation.getOut()){
					getView().addLocalDynamicMenssage("* " + validador.getErrorMessage(), out, false);
				}				
				returned = false;
			}
		}
		return returned;
	}
	
	protected boolean checarCamposObrigatoriosExtras() {
		return true;
	}
	
	protected boolean checarCamposObrigatorios() throws Exception {
		List<ValidationRequest> camposObrigatorios = getCamposObrigatorios();
		return checarCamposObrigatoriosExtras() && evaluateValidators(camposObrigatorios);
	}

	protected boolean checarCamposValidadosExtras() {
		return true;
	}
	
	protected boolean checarCamposValidados()throws Exception{
		List<ValidationRequest> camposValidados = getCamposValidados();
		return checarCamposValidadosExtras() && evaluateValidators(camposValidados);
	}
	
	public void acaoSalvar() {
		boolean alredy = false;
		try {
			acaoFormatarCampos();
			if(!checarCamposObrigatorios()) {
				exibirPopUp(getView().getMessageFromResource("camposObrigatorios"));
				getView().addLocalMessage("camposObrigatorios", "saidaPadrao", true);
				return;
			}
			if(!checarCamposValidados()){
				exibirPopUp(getView().getMessageFromResource("camposInvalidos"));
				getView().addLocalMessage("camposInvalidos", "saidaPadrao", true);
				return;
			}
			if (dao.exists(getBackBean())){
				alredy = true;
			}
			acaoSalvarExtra();
			this.dao.alterar(this.backBean);
		} catch (Exception ex) {
			handleSalvarException(ex);
			return;
		} finally {
			dao.closeConnection();
		}
		init();
		exibirPopUp(getView().getMessageFromResource(alredy ? "Atualizado" : "Cadastro"));
		getView().addLocalMessage(alredy ? "Atualizado" : "Cadastro", "saidaPadrao", true);
	}

	protected void handleSalvarException(Exception ex){
		exibirPopUp(getView().getMessageFromResource("ErroSistema"));
		getView().addLocalMessage("ErroSistema", "saidaPadrao", true);
		ex.printStackTrace();
	}
	
	public void acaoSalvarExtra() {}

	public void acaoRemoverSim() {
		try {
			if(canDelete){
				this.dao.remover(this.backBean);
			}else{
				exibirPopUp(getView().getMessageFromResource("naoPodeRemover"));
				getView().addLocalMessage("naoPodeRemover","saidaPadrao", true);
				return;
			}
		}catch(SQLIntegrityConstraintViolationException fke){
			exibirPopUp("Registro Referenciado.");
			getView().addLocalDynamicMenssage("Registro Referenciado.","saidaPadrao", true);
			return;
		} catch (Exception e) {
			exibirPopUp("Não foi possivel remover o registro.");
			getView().addLocalDynamicMenssage("Nao foi possivel remover o registro.","saidaPadrao", true);
			return;
		} finally {
			dao.closeConnection();
		}
		init();
		exibirPopUp(getView().getMessageFromResource("removido"));
		getView().addLocalMessage("removido", "saidaPadrao", true);
	}

	public void acaoAtualizar() {
		getView().refresh();
	}

	protected Usuario getUsuarioSessao() {
		return getContext().getUsuarioSessao();
	}


	protected void carregarExtra(){};

	public T getBackBean() {
		return this.backBean;
	}

	public void setBackBean(T backBean) {
		this.backBean = backBean;
	}
	
	public String getMsgBundle() {
		return this.msgBundle;
	}

	public void setMsgBundle(String msgBundle) {
		this.msgBundle = msgBundle;
	}
	
	public ApplicationContext getContext() {
		return context;
	}

	public Currency getCurrency(){
		return Currency.getInstance(getContext().getClientLocale());
	}
	
	public DecimalFormatSymbols getDecimalSymbols(){
		return this.decimalFormat.getDecimalFormatSymbols();
	}
	
	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public ApplicationView getView() {
		return view;
	}

	public EntityManager<T> getDao() {
		return dao;
	}

	public void setView(ApplicationView view) {
		this.view = view;
	}

	public boolean isCanDelete() {
		return canDelete;
	}
	
	public void makeView(Map<String, String> params) {
		this.view = ApplicationViewFactory.getViewInstance(ViewHandler.JSF,	params);
	}
	
	protected class SearchPessoaSelectedHandler extends CommonSearchPessoaSelectedHandler<T>{
		private static final long serialVersionUID = 7996822907210618133L;
		@Override
		public EntityManager<T> getSuperDao() {
			return getDao();
		}
		@Override
		public void extraLoad() {
			carregarExtra();
		}
		@Override
		public Pessoa getBean() {
			return (Pessoa)getBackBean();
		}
		@SuppressWarnings("unchecked")
		@Override
		public void setBean(Pessoa bean) {
			canDelete = true;
			setBackBean((T)bean);
		}	
	}	

	public class SearchSelectedHandler extends CommonSearchSelectedHandler<T>{
		private static final long serialVersionUID = 9152759135402687202L;
		public SearchSelectedHandler() {
			super(getDao());
		}
		@Override
		public void extraLoad() {
			canDelete = true;
			carregarExtra();
		}
		@Override
		public T getBean() {
			return getBackBean();
		}
		@Override
		public void setBean(T bean) {
			setBackBean(bean);
		}
	}

}
