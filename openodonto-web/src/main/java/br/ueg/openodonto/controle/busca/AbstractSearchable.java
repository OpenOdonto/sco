package br.ueg.openodonto.controle.busca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.validator.Validator;
import br.ueg.openodonto.controle.servico.ManageExample;
import br.ueg.openodonto.servico.busca.FieldFacade;
import br.ueg.openodonto.servico.busca.InputMask;
import br.ueg.openodonto.servico.busca.MessageDisplayer;
import br.ueg.openodonto.servico.busca.SearchFilter;
import br.ueg.openodonto.servico.busca.SearchFilterBase;
import br.ueg.openodonto.servico.busca.Searchable;

public abstract class AbstractSearchable<T> implements Searchable<T>,Serializable {

	private static final long serialVersionUID = 8694703534298665402L;
	
	private List<FieldFacade>          facade;
	private Map<String,SearchFilter>   filtersMap;
	private Map<String,InputMask>      masksMap;
	private MessageDisplayer           displayer;
	private ManageExample<T>           manageExample;
	
	private List<SearchFilter>         filtersList;
	private List<InputMask>            masksList;
	
	
	public AbstractSearchable(Class<T> classe,MessageDisplayer displayer) {
		this.displayer = displayer;
		buildMask();
		buildFilter();
		buildFacade();
		this.manageExample = new ManageExample<T>(classe);
		filtersList = new ArrayList<SearchFilter>(filtersMap.values());
		masksList = new ArrayList<InputMask>(masksMap.values());
	}

	protected <E>SearchFilterBase buildBasicFilter(String name,String label,InputMask mask,List<E> domain,Validator... validator){
		SearchFilterBase filter = new SearchFilterBase(null,name,label,displayer);
		SearchFilterBase.Field field = filter.new Field();
		filter.setField(field);
		SearchFilterBase.Input<E> input = filter.new Input<E>();
		input.getValidators().addAll(Arrays.asList(validator));
		input.setMask(mask);
		input.setDomain(domain);
		field.getInputFields().add(input);
		return filter;
	}
	
	protected SearchFilterBase buildBasicFilter(String name,String label,InputMask mask,Validator... validator){
		return buildBasicFilter(name,label,mask,null,validator);
	}
	
	protected <E>SearchFilterBase buildBasicFilter(String name,String label,List<E> domain,Validator... validator){
		return buildBasicFilter(name,label,null,domain,validator);
	}
	
	protected SearchFilterBase buildBasicFilter(String name,String label,Validator... validator){
		return buildBasicFilter(name,label,null,null,validator);
	}
	
	public abstract T buildExample();
	
	protected void buildFacade(){
		facade = new ArrayList<FieldFacade>();
	}
	
	protected void buildFilter(){
		filtersMap = new HashMap<String,SearchFilter>();
	}
	
	protected void buildMask(){
		masksMap = new HashMap<String,InputMask>();
	}
	
	@Override
	public List<FieldFacade> getFacade() {
		return facade;
	}

	@Override
	public List<SearchFilter> getFilters() {
		return filtersList;
	}

	@Override
	public List<InputMask> getMasks() {
		return masksList;
	}	
	
	public ManageExample<T> getManageExample() {
		return manageExample;
	}

	public void setManageExample(ManageExample<T> manageExample) {
		this.manageExample = manageExample;
	}

	public Map<String, SearchFilter> getFiltersMap() {
		return filtersMap;
	}

	public void setFiltersMap(Map<String, SearchFilter> filtersMap) {
		this.filtersMap = filtersMap;
	}

	public Map<String, InputMask> getMasksMap() {
		return masksMap;
	}

	public void setMasksMap(Map<String, InputMask> masksMap) {
		this.masksMap = masksMap;
	}

	public List<SearchFilter> getFiltersList() {
		return filtersList;
	}

	public void setFiltersList(List<SearchFilter> filtersList) {
		this.filtersList = filtersList;
	}

	public List<InputMask> getMasksList() {
		return masksList;
	}

	public void setMasksList(List<InputMask> masksList) {
		this.masksList = masksList;
	}

	public MessageDisplayer getDisplayer() {
		return displayer;
	}

	public void setDisplayer(MessageDisplayer displayer) {
		this.displayer = displayer;
	}	

}
