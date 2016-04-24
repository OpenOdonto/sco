package br.ueg.openodonto.controle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.validator.EmptyValidator;
import br.com.vitulus.simple.validator.NullValidator;
import br.ueg.openodonto.controle.busca.CommonSearchProcedimentoHandler;
import br.ueg.openodonto.controle.busca.SearchBase;
import br.ueg.openodonto.controle.busca.SearchableProcedimento;
import br.ueg.openodonto.controle.busca.ViewDisplayer;
import br.ueg.openodonto.controle.servico.ManageListagem;
import br.ueg.openodonto.controle.servico.ValidationRequest;
import br.ueg.openodonto.dominio.Procedimento;
import br.ueg.openodonto.servico.busca.Search;
import br.ueg.openodonto.validator.ValidatorFactory;

public class ManterProcedimento extends ManageBeanGeral<Procedimento>{
	
	private static final long serialVersionUID = 6529628317751409701L;
	
	private static Map<String, String>   params;
	private Search<Procedimento>         search;
	
	static{
		params = new HashMap<String, String>();
		params.put("managebeanName", "manterProcedimento");
		params.put("formularioSaida", "formProcedimento");
		params.put("saidaPadrao", "formProcedimento:output");
	}
	
	public ManterProcedimento() {
		super(Procedimento.class);
	}

	protected void initExtra() {
		makeView(params);
		this.search = new SearchBase<Procedimento>(
				new SearchableProcedimento(new ViewDisplayer("searchDefault",getView())),
				"Buscar Procedimento",
				"painelBusca");
		this.search.addSearchListener(new CommonSearchProcedimentoHandler());
		this.search.addSearchListener(new SearchSelectedHandler());
	}
	
	@Override
	protected List<String> getCamposFormatados() {
		String[] formatados = {"nome"};
		return Arrays.asList(formatados);
	}
	
	@Override
	public void acaoSalvar() {
		super.acaoSalvar();
		ManageListagem.getLista("ALIAS_PROC").refreshDominio();
	}
	
	@Override
	protected List<ValidationRequest> getCamposObrigatorios() {
		List<ValidationRequest> obrigatorios = new ArrayList<ValidationRequest>();
		obrigatorios.add(new ValidationRequest("nome", ValidatorFactory.newStrEmpty(), "formProcedimento:entradaNome"));
		obrigatorios.add(new ValidationRequest("valor", ValidatorFactory.newNumMin(0), "formProcedimento:entradaValor"));
		return obrigatorios;
	}
	
	protected List<ValidationRequest> getCamposValidados(){
		List<ValidationRequest> validados = new ArrayList<ValidationRequest>();
		Class<?>[] allowed = {NullValidator.class,EmptyValidator.class};
		validados.add(new ValidationRequest("nome", ValidatorFactory.newStrRangeLen(150, 4, true), "formProcedimento:entradaNome"));
		validados.add(new ValidationRequest("descricao", ValidatorFactory.newStrMaxLen(300, true), "formProcedimento:inTextBoxObs",allowed));
		return validados;
	}	
	
	public Procedimento getProcedimento(){
		return getBackBean();
	}
	
	public void setProcedimento(Procedimento procedimento){
		setProcedimento(procedimento);
	}

	public Search<Procedimento> getSearch() {
		return search;
	}
	
}
