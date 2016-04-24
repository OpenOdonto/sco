package br.ueg.openodonto.controle.servico;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import br.com.vitulus.simple.validator.EmptyValidator;
import br.com.vitulus.simple.validator.NullValidator;
import br.com.vitulus.simple.validator.Validator;
import br.ueg.openodonto.controle.busca.ViewDisplayer;
import br.ueg.openodonto.dominio.Odontograma;
import br.ueg.openodonto.dominio.OdontogramaDente;
import br.ueg.openodonto.dominio.OdontogramaDenteAspecto;
import br.ueg.openodonto.dominio.OdontogramaDenteProcedimento;
import br.ueg.openodonto.dominio.Procedimento;
import br.ueg.openodonto.dominio.constante.Dente;
import br.ueg.openodonto.dominio.constante.FaceDente;
import br.ueg.openodonto.dominio.constante.TipoAscpectoDente;
import br.ueg.openodonto.dominio.constante.TipoStatusProcedimento;
import br.ueg.openodonto.servico.busca.MessageDisplayer;
import br.ueg.openodonto.util.bean.DenteMetaAdapter;
import br.ueg.openodonto.util.bean.OdontogramaAdapter;
import br.ueg.openodonto.util.bean.ProcedimentoDenteAdapter;
import br.ueg.openodonto.util.bean.StatusOdontogramaFilter;
import br.ueg.openodonto.validator.ValidatorFactory;
import br.ueg.openodonto.visao.ApplicationView;

public class ManageOdontograma {
	
	private Odontograma 					odontograma;
	private OdontogramaDente    			od;
	private List<ProcedimentoDenteAdapter>  procedimentos;	
	private List<StatusOdontogramaFilter>   odontogramaStatusFilters;
	private ProcedimentoDenteAdapter   	    procedimento;
	private String 							nome;	
	private String               			aspecto;
	private Integer 						numero;
	private String 							face;
	private MessageDisplayer 				displayer;
	private List<OdontogramaAdapter>    	odontogramas;
	private List<Odontograma>    	        odontogramasTarget;
	private OdontogramaAdapter              odontogramaDelete;
	private Map<String,DenteMetaAdapter>    viewMetaAdapter;
	
	private Procedimento                    procedimentoAdd;
	private Float                           valorAdd;
	private String                          observacaoAdd;
	private TipoStatusProcedimento          statusAdd;
	private Date                            dataAdd;
	
	private Validator                       validatorProcedimento;
	private Validator                       validatorValor;
	private Validator                       validatorObs;
	
	private String                          descricaoOAdd;
	private Date                            dataOadd;
	private String                          nomeOAdd;
	
	private Validator                       validatorONome;
	private Validator                       validatorOData;
	private Validator                       validatorODesc;
	
	private String                          filterStatusAdd;
	private String                          filterProcedimentoAdd;
	private String                          filterDataAdd;
	private String                          filterObservacaoAdd;
	private String                          filterValorAdd;
	
	private String                          filterNomeOAdd;
	private String                          filterDataOAdd;
	private String                          filterDescricaoOAdd;
	
	public ManageOdontograma(List<Odontograma> odontogramas,ApplicationView view) {
		this.displayer = new ViewDisplayer("manageProcOdontograma" , view);
		this.viewMetaAdapter = new HashMap<String, DenteMetaAdapter>();		
		this.odontogramas = wrapOdontogramas(odontogramas);
		this.odontogramaStatusFilters = new ArrayList<StatusOdontogramaFilter>();
		loadLastOdontograma();
		initValidator();
	}	

	public List<OdontogramaAdapter> wrapOdontogramas(List<Odontograma> odontogramas){
		List<OdontogramaAdapter> wraper = new ArrayList<OdontogramaAdapter>();
		this.odontogramasTarget = odontogramas;
		for(Odontograma odontograma : odontogramas){
			wraper.add(new OdontogramaAdapter(odontograma, getDisplayer()));
		}
		return wraper;
	}
	
	private void makeDefaultOdontograma(){
		this.odontograma = new Odontograma();
		this.odontograma.setData(new Timestamp(System.currentTimeMillis()));
		this.odontograma.setNome("Odontograma Padrão");
		this.odontograma.setDescricao("Primeira configuração do odontograma do paciente.");
		initAddOdp();
		initOdontogramaStatusFilter();
	}

	public void acaoMudarAspecto(){
		Dente dente = evaluateDente();
		TipoAscpectoDente aspecto = evaluateAspecto();
	    if(dente != null && aspecto != null && odontograma != null && odontograma.getAspectos() != null){
	    	OdontogramaDenteAspectoComparator comparator = new OdontogramaDenteAspectoComparator();	    	
	    	setDenteAspecto(dente, aspecto, comparator);	    	
	    	updateMeta();
	    }	    
	}

	private void setDenteAspecto(Dente dente, TipoAscpectoDente aspecto,OdontogramaDenteAspectoComparator comparator) {
		OdontogramaDenteAspecto key = new OdontogramaDenteAspecto(dente);
		Collections.sort(odontograma.getAspectos(), comparator);
		int index = Collections.binarySearch(odontograma.getAspectos(),key,comparator);	    	
		if(index >= 0){
			OdontogramaDenteAspecto oda = odontograma.getAspectos().get(index);
			oda.setAspecto(aspecto);	    		
		}else{
			OdontogramaDenteAspecto oda = new OdontogramaDenteAspecto(dente,odontograma.getId());
			oda.setAspecto(aspecto);
			odontograma.getAspectos().add(oda);
		}
	}
	
	public void acaoRemoverOdp(){
		if(this.procedimento != null){
			procedimentos.remove(procedimento);
	    	Iterator<Map.Entry<OdontogramaDenteProcedimento,Procedimento>> iterator = getOd().getProcedimentosMap().entrySet().iterator();
	    	OdontogramaDenteProcedimento remove = null;
	    	while(iterator.hasNext()){
	    		Map.Entry<OdontogramaDenteProcedimento,Procedimento> entry = iterator.next();
	    		if(this.procedimento.getOdp() == entry.getKey()){
	    			remove = entry.getKey();
	    			break;
	    		}
	    	}
	    	if(remove != null){
	    		getOd().getProcedimentosMap().remove(remove);
	    		updateMeta();
	    	}else{
	    		throw new RuntimeException("Falha ao excluir.");
	    	}
			setProcedimento(null);
		}
	}
	
	public void acaoRemoverOdontograma(){
		if(this.odontogramaDelete != null){
			odontogramas.remove(odontogramaDelete);
			odontogramasTarget.remove(odontogramaDelete.getOdontograma());
			if(odontogramaDelete.getOdontograma() == odontograma){
				loadLastOdontograma();
			}    		
    	}else{
    		throw new RuntimeException("Falha ao excluir.");
    	}
		setOdontogramaDelete(null);
	}
	
	public void acaoAddOdontograma(){
		if(!isValidOdontogramaAdd()){
			return;
		}
		Odontograma odontograma = new Odontograma();
		odontograma.setData(new Timestamp(getDataOadd().getTime()));
		odontograma.setDescricao(getDescricaoOAdd());
		odontograma.setNome(getNomeOAdd());
		if(odontogramas != null){
			odontogramas.add(new OdontogramaAdapter(odontograma,getDisplayer()));
			odontogramasTarget.add(odontograma);
		}
		initAddOdontograma();
	}
	
	private boolean isValidOdontogramaAdd(){
		boolean valid = true;
		validatorOData.setValue(getDataOadd());
		validatorODesc.setValue(getDescricaoOAdd());
		validatorONome.setValue(getNomeOAdd());
		String formName = getDisplayer().getView().getProperties().get("formularioSaida");
		
		if(!validatorONome.isValid()){
			getDisplayer().getView().addResourceDynamicMenssage("* Nome : " + validatorONome.getErrorMessage(), formName+":subViewAddOdontograma:entradaONome");
		}
		
		if (!validatorOData.isValid()) {
			getDisplayer().getView().addResourceDynamicMenssage("* Data : " + validatorOData.getErrorMessage(), formName+":subViewAddOdontograma:entradaOData");
			valid = valid && false;
		}
		
		Class<?>[] allowed = {NullValidator.class,EmptyValidator.class};
		if(!validatorODesc.isValid() && ValidatorFactory.checkInvalidPermiteds(validatorODesc, allowed)){
			getDisplayer().getView().addResourceDynamicMenssage("* Descrição : " + validatorODesc.getErrorMessage(),formName+":entradaODescricao");
			valid = valid && false;
		}
		return valid;
	}
	
	public void acaoAddOdp(){		
		if(!isValidOdpAdd()){
			return;
		}		
		OdontogramaDenteProcedimento odp = new OdontogramaDenteProcedimento();
		odp.setData(dataAdd);
		odp.setObservacao(observacaoAdd);
		odp.setProcedimentoId(procedimentoAdd.getCodigo());
		odp.setStatus(statusAdd);
		odp.setValor(valorAdd);		
		if(procedimentos != null && getOd() != null){
			getOd().getProcedimentosMap().put(odp, procedimentoAdd);
			procedimentos.add(new ProcedimentoDenteAdapter(odp, procedimentoAdd,getDisplayer()));
		}
		updateMeta();
		initAddOdp();		
	}
	
	private boolean isValidOdpAdd(){
		boolean valid = true;
		validatorProcedimento.setValue(procedimentoAdd);
		validatorValor.setValue(valorAdd);
		validatorObs.setValue(observacaoAdd);
		if (!validatorProcedimento.isValid()) {
			getDisplayer().display("* Procedimento : " + validatorProcedimento.getErrorMessage());
			valid = valid && false;
		}
		if (!validatorValor.isValid()) {
			getDisplayer().display("* Valor : " + validatorValor.getErrorMessage());
			valid = valid && false;
		}
		Class<?>[] allowed = {NullValidator.class,EmptyValidator.class};
		if(!validatorObs.isValid() && ValidatorFactory.checkInvalidPermiteds(validatorObs, allowed)){
			getDisplayer().display("* Observação : " + validatorObs.getErrorMessage());
			valid = valid && false;
		}
		return valid;
	}
	
	public void acaoCopiaValor(){
		if(procedimentoAdd != null && procedimentoAdd.getValor() != null){
			setValorAdd(procedimentoAdd.getValor());
		}
	}
	
	private void initAddOdp(){
		this.procedimentoAdd = null;
		this.valorAdd = 0.0f;
		this.statusAdd = TipoStatusProcedimento.NAO_REALIZADO;
		this.dataAdd = new Date(System.currentTimeMillis());
		this.observacaoAdd = "";		
	}
	
	private void initAddOdontograma(){
		nomeOAdd = null;
		descricaoOAdd = null;
		dataOadd = null;
	}
	
	private void initValidator(){
		validatorProcedimento = ValidatorFactory.newNull();
		validatorValor = ValidatorFactory.newNumMin(0);
		validatorObs = ValidatorFactory.newStrMaxLen(300, false);
		validatorOData = ValidatorFactory.newNull();
		validatorODesc = ValidatorFactory.newStrMaxLen(500, false);
		validatorONome = ValidatorFactory.newStrRangeLen(150, 4, false);
	}
	
	public void acaoManageProcedimento(){
	    OdontogramaDente selected = evaluateSelected();
	    if(selected != null && odontograma != null && odontograma.getOdontogramaDentes() != null){
	    	boolean finded = false;
	    	for(Iterator<OdontogramaDente> iterator = odontograma.getOdontogramaDentes().iterator();iterator.hasNext() && !finded;){
	    		OdontogramaDente findOd = iterator.next();
	    		if(selected.getDente() == findOd.getDente() && selected.getFace() == findOd.getFace()){
	    			setOd(findOd);
	    			finded = true;
	    		}
	    	}
	    	if(!finded){
	    		odontograma.getOdontogramaDentes().add(selected);
	    		setOd(selected);
	    	}
	    }
	    procedimentos = new ArrayList<ProcedimentoDenteAdapter>();
	    if(getOd() != null && getOd().getProcedimentosMap() != null){
	    	Iterator<Map.Entry<OdontogramaDenteProcedimento,Procedimento>> iterator = getOd().getProcedimentosMap().entrySet().iterator();
	    	while(iterator.hasNext()){
	    		Map.Entry<OdontogramaDenteProcedimento,Procedimento> entry = iterator.next();	    		
	    		procedimentos.add(new ProcedimentoDenteAdapter(entry.getKey(), entry.getValue(),getDisplayer()));
	    	}
	    }
	}
	
	private OdontogramaDente evaluateSelected(){
	    try {
	    	Dente dente = evaluateDente();
	    	FaceDente face = evaluateFace();
		    return new OdontogramaDente(dente, face);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	private TipoAscpectoDente evaluateAspecto(){
		try {
			TipoAscpectoDente aspecto = TipoAscpectoDente.valueOf(getAspecto().toUpperCase());
			return aspecto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Dente evaluateDente(){
		try {
			Dente dente = Dente.getDente(getNumero());
			return dente;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private FaceDente evaluateFace(){
		try {
			FaceDente face = FaceDente.valueOf(getFace().toUpperCase());
			return face;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void acaoRemoverTodosSuperior(){
		batChangeAspect(getAllByHem(STATUS_HEM.UP),TipoAscpectoDente.EXTRAIDO);
	}
	
	public void acaoRemoverTodosInferior(){
		batChangeAspect(getAllByHem(STATUS_HEM.DOWN),TipoAscpectoDente.EXTRAIDO);
	}
	
	public void acaoImplantarTodosSuperior(){
		batChangeAspect(getAllByHem(STATUS_HEM.UP),TipoAscpectoDente.IMPLANTE);		
	}
	
	public void acaoImplantarTodosInferior(){
		batChangeAspect(getAllByHem(STATUS_HEM.DOWN),TipoAscpectoDente.IMPLANTE);
	}
	
	public void acaoNormalizarTodosSuperior(){
		batChangeAspect(getAllByHem(STATUS_HEM.UP),TipoAscpectoDente.NORMAL);
	}
	
	public void acaoNormalizarTodosInferior(){
		batChangeAspect(getAllByHem(STATUS_HEM.DOWN),TipoAscpectoDente.NORMAL);
	}
	
	public void batChangeAspect(List<Dente> dentes,TipoAscpectoDente aspecto){
		boolean has = false;
		OdontogramaDenteAspectoComparator comparator = new OdontogramaDenteAspectoComparator();		
		for(Dente dente : dentes){
		    if(dente != null && aspecto != null && odontograma != null && odontograma.getAspectos() != null){
		    	has = true;
		    	setDenteAspecto(dente, aspecto, comparator);    		    	
		    }			
		}
	    if(has){
	    	updateMeta();
	    }    
	}
	
	private List<Dente> getAllByHem(STATUS_HEM hem){
		Predicate predicate = new HemPredicate(hem);
		List<Dente> dentes = new ArrayList<Dente>(Arrays.asList(Dente.values()));
		CollectionUtils.filter(dentes, predicate);
		return dentes;
	}
	
	
	public void updateMeta(){
		if(this.odontograma != null &&
				this.odontograma.getAspectos() != null &&
				this.odontograma.getOdontogramaDentes() != null &&
				this.viewMetaAdapter != null){			
			initializeViewMeta();
			Iterator<OdontogramaDente> iteratorOD = this.odontograma.getOdontogramaDentes().iterator();
			while(iteratorOD.hasNext()){
				OdontogramaDente od = iteratorOD.next();
				if(od.getDente() != null && od.getFace() != null && od.getProcedimentosMap() != null){
					DenteMetaAdapter meta = this.viewMetaAdapter.get(od.getDente().getNumero().toString());
					if(meta != null){
						if(odontogramaStatusFilters != null){
							meta.getProcedimentos().put(od.getFace().toString().toLowerCase(),countProcByActiveStatus(od.getProcedimentosMap()));
						}						
					}
				}
			}			
			Iterator<OdontogramaDenteAspecto> iteratorODA = this.odontograma.getAspectos().iterator();
			while(iteratorODA.hasNext()){
				OdontogramaDenteAspecto oda = iteratorODA.next();
				DenteMetaAdapter meta = this.viewMetaAdapter.get(oda.getDente().getNumero().toString());
				if(oda.getAspecto() != null && meta != null){
					meta.setAscpecto(oda.getAspecto());
				}				
			}
		}
	}
	
	private void initOdontogramaStatusFilter() {
		odontogramaStatusFilters.clear();
		for(TipoStatusProcedimento tipo : TipoStatusProcedimento.values()){
			odontogramaStatusFilters.add(new StatusOdontogramaFilter(tipo));
		}		
		Collections.sort(odontogramaStatusFilters);
	}
	
	private void initializeViewMeta(){
		this.viewMetaAdapter.clear();
		for(Dente dente : Dente.values()){
			DenteMetaAdapter meta = maketDenteViewMeta(dente.getNumero());
			viewMetaAdapter.put(dente.getNumero().toString(), meta);
		}
	}
	
	private DenteMetaAdapter maketDenteViewMeta(Integer numero){
		DenteMetaAdapter meta = new DenteMetaAdapter();		
		meta.setDente(Dente.getDente(numero));
		for(FaceDente face : FaceDente.values()){
			meta.getProcedimentos().put(face.toString().toLowerCase(),0);
		}
		return meta;
	}
	
	private int countProcByActiveStatus(Map<OdontogramaDenteProcedimento, Procedimento> map){
		int count = 0; 
		for(OdontogramaDenteProcedimento odp : map.keySet()){			
			TipoStatusProcedimento odpStatus = odp.getStatus();
			StatusOdontogramaFilter key = new StatusOdontogramaFilter(odpStatus);
			int index = Collections.binarySearch(odontogramaStatusFilters, key);
			if(odpStatus == null || index < 0){
				count++;
			}else if(odontogramaStatusFilters.get(index).getFilter()){
				count++;
			}
		}
		return count;
	}
	
	public void loadLastOdontograma(){
		if(getOdontogramas().size() == 0){
			makeDefaultOdontograma();
			getOdontogramas().add(new OdontogramaAdapter(this.odontograma,getDisplayer()));
			this.odontogramasTarget.add(this.odontograma);
		}else{
			Collections.sort(getOdontogramas(),new OdontogramaDateComparator());
			setOdontograma(getOdontogramas().get(0).getOdontograma());
		}
		updateMeta();
	}
	
	public Odontograma getOdontograma() {
		return odontograma;
	}	
	public void setOdontograma(Odontograma odontograma) {
		this.odontograma = odontograma;
	}
	public void setOdontogramaAdapter(OdontogramaAdapter odontogramaAdapter){
		if(odontogramaAdapter != null){
			setOdontograma(odontogramaAdapter.getOdontograma());
		}
	}	
    public Integer getNumero() {
        return numero;
    }    
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public String getFace() {
        return face;
    }
    public void setFace(String face) {
        this.face = face;
    }
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<OdontogramaAdapter> getOdontogramas() {
		return odontogramas;
	}
	
	public void setOdontogramas(List<OdontogramaAdapter> odontogramas) {
		this.odontogramas = odontogramas;
	}
	
	public OdontogramaDente getOd() {
		return od;
	}
	
	public void setOd(OdontogramaDente od) {
		this.od = od;
	}
	
	public List<ProcedimentoDenteAdapter> getProcedimentos() {
		return procedimentos;
	}
	
	public void setProcedimentos(List<ProcedimentoDenteAdapter> procedimentos) {
		this.procedimentos = procedimentos;
	}
	
	public ProcedimentoDenteAdapter getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(ProcedimentoDenteAdapter procedimento) {
		this.procedimento = procedimento;
	}

	public String getAspecto() {
		return aspecto;
	}

	public void setAspecto(String aspecto) {
		this.aspecto = aspecto;
	}
	
	public Map<String, DenteMetaAdapter> getViewMetaAdapter() {
		return viewMetaAdapter;
	}

	public void setViewMetaAdapter(Map<String, DenteMetaAdapter> viewMetaAdapter) {
		this.viewMetaAdapter = viewMetaAdapter;
	}
	public Procedimento getProcedimentoAdd() {
		return procedimentoAdd;
	}

	public void setProcedimentoAdd(Procedimento procedimentoAdd) {
		this.procedimentoAdd = procedimentoAdd;
	}

	public Float getValorAdd() {
		return valorAdd;
	}

	public void setValorAdd(Float valorAdd) {
		this.valorAdd = valorAdd;
	}

	public String getObservacaoAdd() {
		return observacaoAdd;
	}

	public void setObservacaoAdd(String observacaoAdd) {
		this.observacaoAdd = observacaoAdd;
	}
	
	public TipoStatusProcedimento getStatusAdd() {
		return statusAdd;
	}

	public void setStatusAdd(TipoStatusProcedimento statusAdd) {
		this.statusAdd = statusAdd;
	}

	public Date getDataAdd() {
		return dataAdd;
	}

	public void setDataAdd(Date dataAdd) {
		this.dataAdd = dataAdd;
	}
	
	public MessageDisplayer getDisplayer() {
		return displayer;
	}

	public void setDisplayer(MessageDisplayer displayer) {
		this.displayer = displayer;
	}
	
	public String getFilterStatusAdd() {
		return filterStatusAdd;
	}

	public void setFilterStatusAdd(String filterStatusAdd) {
		this.filterStatusAdd = filterStatusAdd;
	}
	
	public String getFilterProcedimentoAdd() {
		return filterProcedimentoAdd;
	}

	public void setFilterProcedimentoAdd(String filterProcedimentoAdd) {
		this.filterProcedimentoAdd = filterProcedimentoAdd;
	}

	public String getFilterObservacaoAdd() {
		return filterObservacaoAdd;
	}

	public void setFilterObservacaoAdd(String filterObservacaoAdd) {
		this.filterObservacaoAdd = filterObservacaoAdd;
	}

	public String getFilterValorAdd() {
		return filterValorAdd;
	}

	public void setFilterValorAdd(String filterValorAdd) {
		this.filterValorAdd = filterValorAdd;
	}
	
	public String getFilterDataAdd() {
		return filterDataAdd;
	}

	public void setFilterDataAdd(String filterDataAdd) {
		this.filterDataAdd = filterDataAdd;
	}
	public List<StatusOdontogramaFilter> getOdontogramaStatusFilters() {
		return odontogramaStatusFilters;
	}
	public void setDescricaoOAdd(String descricaoOAdd) {
		this.descricaoOAdd = descricaoOAdd;
	}

	public Date getDataOadd() {
		return dataOadd;
	}

	public void setDataOadd(Date dataOadd) {
		this.dataOadd = dataOadd;
	}

	public String getNomeOAdd() {
		return nomeOAdd;
	}

	public void setNomeOAdd(String nomeOAdd) {
		this.nomeOAdd = nomeOAdd;
	}
	public void setOdontogramaStatusFilters(List<StatusOdontogramaFilter> odontogramaStatusFilters) {
		this.odontogramaStatusFilters = odontogramaStatusFilters;
	}	
	public String getFilterNomeOAdd() {
		return filterNomeOAdd;
	}

	public void setFilterNomeOAdd(String filterNomeOAdd) {
		this.filterNomeOAdd = filterNomeOAdd;
	}

	public String getFilterDescricaoOAdd() {
		return filterDescricaoOAdd;
	}

	public void setFilterDescricaoOAdd(String filterDescricaoOAdd) {
		this.filterDescricaoOAdd = filterDescricaoOAdd;
	}

	public String getFilterDataOAdd() {
		return filterDataOAdd;
	}
	
	public void setFilterDataOAdd(String filterDataOAdd) {
		this.filterDataOAdd = filterDataOAdd;
	}

	public OdontogramaAdapter getOdontogramaDelete() {
		return odontogramaDelete;
	}

	public void setOdontogramaDelete(OdontogramaAdapter odontogramaDelete) {
		this.odontogramaDelete = odontogramaDelete;
	}	
	
	private class OdontogramaDenteAspectoComparator implements Comparator<OdontogramaDenteAspecto>{
		@Override
		public int compare(OdontogramaDenteAspecto o1,	OdontogramaDenteAspecto o2) {
			return o1.getDente().compareTo(o2.getDente());
		}
		
	}
	private class OdontogramaDateComparator implements Comparator<OdontogramaAdapter>{
		@Override
		public int compare(OdontogramaAdapter o1, OdontogramaAdapter o2) {
			if(o1 == null || o1.getOdontograma() == null || o1.getOdontograma().getData() == null){
				return 1;
			}else if (o2 == null || o2.getOdontograma() == null || o2.getOdontograma().getData() == null){
				return -1;
			}
			return o2.getOdontograma().getData().compareTo(o1.getOdontograma().getData());
		}		
	}
	public class HemPredicate implements Predicate{
		private STATUS_HEM hem;
		public HemPredicate(STATUS_HEM hem) {
			if(hem == null){
				throw new IllegalArgumentException("Não pode ser nulo");
			}
			this.hem = hem;
		}
		@Override
		public boolean evaluate(Object o) {
			if(o == null){
				return false;
			}			
			Dente dente = (Dente)o;
			if(hem == STATUS_HEM.UP){
				return dente.isSuperior();
			}else if(hem == STATUS_HEM.DOWN){
				return dente.isInferior();
			}else{
				return false;
			}
		}
	}
	private enum STATUS_HEM{
		UP,DOWN;
	}
	public String getDescricaoOAdd() {
		return descricaoOAdd;
	}	
	
}
