package br.ueg.openodonto.controle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.validator.EmptyValidator;
import br.com.vitulus.simple.validator.NullValidator;
import br.ueg.openodonto.controle.busca.CommonSearchProdutoHandler;
import br.ueg.openodonto.controle.busca.SearchBase;
import br.ueg.openodonto.controle.busca.SearchableProduto;
import br.ueg.openodonto.controle.busca.ViewDisplayer;
import br.ueg.openodonto.controle.servico.ValidationRequest;
import br.ueg.openodonto.dominio.Produto;
import br.ueg.openodonto.servico.busca.Search;
import br.ueg.openodonto.validator.ValidatorFactory;

public class ManterProduto extends ManageBeanGeral<Produto>{

	private static final long serialVersionUID = -7320449089337162282L;
	
	private static Map<String, String>   params;
	private Search<Produto>              search;
	
	static{
		params = new HashMap<String, String>();
		params.put("managebeanName", "manterProduto");
		params.put("formularioSaida", "formProduto");
		params.put("saidaPadrao", "formProduto:output");
	}
	
	public ManterProduto() {
		super(Produto.class);
	}	

	@Override
	protected void initExtra() {
		makeView(params);
		this.search = new SearchBase<Produto>(
				new SearchableProduto(new ViewDisplayer("searchDefault",getView())),
				"Buscar Produto",
				"painelBusca");
		this.search.addSearchListener(new CommonSearchProdutoHandler());
		this.search.addSearchListener(new SearchSelectedHandler());
	}
	
	@Override
	protected List<String> getCamposFormatados() {
		String[] formatados = {"nome"};
		return Arrays.asList(formatados);
	}
	
	@Override
	protected List<ValidationRequest> getCamposObrigatorios() {
		List<ValidationRequest> obrigatorios = new ArrayList<ValidationRequest>();
		obrigatorios.add(new ValidationRequest("nome", ValidatorFactory.newStrEmpty(), "formProduto:entradaNome"));
		obrigatorios.add(new ValidationRequest("categoria",	ValidatorFactory.newNull(),"formProduto:selectCategoria"));
		return obrigatorios;
	}
	
	@Override
	protected List<ValidationRequest> getCamposValidados(){
		List<ValidationRequest> validados = new ArrayList<ValidationRequest>();
		Class<?>[] allowed = {NullValidator.class,EmptyValidator.class};
		validados.add(new ValidationRequest("nome", ValidatorFactory.newStrRangeLen(150, 4, true), "formProduto:entradaNome"));
		validados.add(new ValidationRequest("descricao", ValidatorFactory.newStrMaxLen(300, true), "formProduto:inTextBoxObs",allowed));
		return validados;
	}	
	
	public Produto getProduto(){
		return getBackBean();
	}
	
	public void setProduto(Produto produto){
		setBackBean(produto);
	}
	
	public Search<Produto> getSearch() {
		return search;
	}

}
