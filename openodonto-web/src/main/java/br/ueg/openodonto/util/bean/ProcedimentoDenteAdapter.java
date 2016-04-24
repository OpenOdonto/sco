package br.ueg.openodonto.util.bean;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import br.com.vitulus.simple.validator.EmptyValidator;
import br.com.vitulus.simple.validator.NullValidator;
import br.com.vitulus.simple.validator.Validator;
import br.ueg.openodonto.dominio.OdontogramaDenteProcedimento;
import br.ueg.openodonto.dominio.Procedimento;
import br.ueg.openodonto.servico.busca.MessageDisplayer;
import br.ueg.openodonto.util.WordFormatter;
import br.ueg.openodonto.validator.ValidatorFactory;

public class ProcedimentoDenteAdapter {

	private static final DateFormat         df = new SimpleDateFormat("dd/MM/yyyy");
	private OdontogramaDenteProcedimento 	odp;
	private String                          abstractObservacao;
	private Procedimento                 	procedimento;
	private String                          valor;
	private DecimalFormat                   dcf;	
	private MessageDisplayer 				displayer;
	private Validator               		validatorObs;

	public ProcedimentoDenteAdapter(OdontogramaDenteProcedimento odp,Procedimento procedimento,MessageDisplayer displayer) {
		this();
		this.displayer = displayer;
		this.odp = odp;		
		this.procedimento = procedimento;
		this.validatorObs = ValidatorFactory.newStrMaxLen(300, false);
		formatObs();
	}

	private boolean isValidObs(){
		boolean valid = true;		
		Class<?>[] allowed = {NullValidator.class,EmptyValidator.class};
		if(!validatorObs.isValid() && ValidatorFactory.checkInvalidPermiteds(validatorObs, allowed)){
			getDisplayer().display("* Observação : " + validatorObs.getErrorMessage());
			valid = false;
		}
		return valid;
	}
	
	public void formatObs(){
		this.abstractObservacao = WordFormatter.abstractStr(getObservacao(), 36);
	}
	
	public ProcedimentoDenteAdapter() {
		super();
		this.dcf = (DecimalFormat) NumberFormat.getCurrencyInstance();
	}	
	
	public OdontogramaDenteProcedimento getOdp() {
		return odp;
	}
	public void setOdp(OdontogramaDenteProcedimento odp) {
		this.odp = odp;
	}
	public Procedimento getProcedimento() {
		return procedimento;
	}
	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = procedimento;
	}
	public String getObservacao(){
		return getOdp().getObservacao();
	}
	public void setObservacao(String observacao){
		validatorObs.setValue(observacao);
		if(isValidObs()){
			getOdp().setObservacao(observacao);
		}
	}
	public String getAbstractObservacao() {
		return abstractObservacao;
	}
	public void setAbstractObservacao(String abstractObservacao) {
	}
	public MessageDisplayer getDisplayer() {
		return displayer;
	}
	private void formatValor(){
		if(valor == null && getOdp().getValor() != null){
			String valor = dcf.format(getOdp().getValor());
			this.valor = valor;
		}
	}
	public String getValor() {
		formatValor();
		return valor;
	}
	public void setValor(String valor) {
		if(valor != null && !valor.isEmpty()){
			try {
				Float nvalor = dcf.parse(valor).floatValue();
				getOdp().setValor(nvalor);
				this.valor = null;
				formatValor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public String getData(){		
		if(getOdp().getData() != null ){
			try{
				return df.format(getOdp().getData());
			}catch (Exception e) {
				return "";
			}
		}else{
			return "";
		}
	}
}
