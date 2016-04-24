package br.ueg.openodonto.controle.servico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.vitulus.simple.validator.AbstractValidator;
import br.com.vitulus.simple.validator.EmptyValidator;
import br.com.vitulus.simple.validator.NullValidator;
import br.com.vitulus.simple.validator.Validator;
import br.com.vitulus.simple.validator.tipo.ObjectValidatorType;
import br.ueg.openodonto.dominio.PacienteQuestionarioAnamnese;
import br.ueg.openodonto.dominio.QuestionarioAnamnese;
import br.ueg.openodonto.dominio.constante.TiposRespostaAnamnese;
import br.ueg.openodonto.persistencia.dao.DaoQuestionarioAnamnese;
import br.ueg.openodonto.util.bean.QuestionarioAnamneseAdapter;
import br.ueg.openodonto.util.bean.QuestionarioQuestaoAdapter;
import br.ueg.openodonto.validator.ValidatorFactory;
import br.ueg.openodonto.visao.ApplicationView;

public class ManageQuestionarioAnamnese {

	private static final String PROPERTY_FAIL_SINGLE 	= "A questão : ";
	private static final String PROPERTY_FAIL_PLURAL 	= "As questões : ";
	private static final String MSG_FAIL_SINGLE 		= "msg_single";
	private static final String MSG_FAIL_PLURAL 		= "msg_plural";
	private static final Integer MAX_OBS_LENGTH         = 300;
	
	private Map<PacienteQuestionarioAnamnese,QuestionarioAnamnese>		questionariosAnamnese;
	private List<QuestionarioAnamneseAdapter> 							questionariosAdapter;
	private QuestionarioAnamneseAdapter									anamnese;
	private Long                           								add;
	private ApplicationView 											view;
	private static Class<?>[] 											allowed = {NullValidator.class,EmptyValidator.class};
	private static Map<String,String>                                   msgBundleValidationObs;
	private static Map<String,String>                                   msgBundleValidationAns;

	static{
		msgBundleValidationObs = new HashMap<String, String>();
		msgBundleValidationObs.put(MSG_FAIL_SINGLE, " possui observação com valor muito longo. Máximo permitido = "+MAX_OBS_LENGTH);
		msgBundleValidationObs.put(MSG_FAIL_PLURAL, " possuem observação com valor muito longo. Máximo permitido = "+MAX_OBS_LENGTH);
		msgBundleValidationAns = new HashMap<String, String>();
		msgBundleValidationAns.put(MSG_FAIL_SINGLE, " é obrigatória.");
		msgBundleValidationAns.put(MSG_FAIL_PLURAL, " são obrigatórias.");
	}
	
	public ManageQuestionarioAnamnese(Map<PacienteQuestionarioAnamnese,QuestionarioAnamnese> anamneses, ApplicationView view) {
		this.questionariosAnamnese = anamneses;
		this.questionariosAdapter = new ArrayList<QuestionarioAnamneseAdapter>();
		this.view = view;
		makeAdpter();		
	}
	
	private void loadFirstAnamnese(){
		if(questionariosAnamnese != null && questionariosAnamnese.size() != 0){
			anamnese = questionariosAdapter.get(0);
		}else{
			anamnese = null;
		}
	}	
	
	public void makeAdpter(){
		if(questionariosAnamnese != null){
			questionariosAdapter.clear();
			for(Entry<PacienteQuestionarioAnamnese,QuestionarioAnamnese> entry : questionariosAnamnese.entrySet()){
				addAdapter(entry.getKey(),entry.getValue());
			}
			loadFirstAnamnese();
		}
	}
	
	private QuestionarioAnamneseAdapter addAdapter(PacienteQuestionarioAnamnese pqa,QuestionarioAnamnese qa){
	    QuestionarioAnamneseAdapter adapter;
		questionariosAdapter.add(adapter = new QuestionarioAnamneseAdapter(pqa,qa,qa.getQuestoes()));
		return adapter;
	}
	
	public void acaoRemoveAnamnese(QuestionarioAnamneseAdapter adapter){
		if(questionariosAdapter.remove(adapter)){
			questionariosAnamnese.remove(adapter.getPqa());
			if(anamnese == adapter){
				loadFirstAnamnese();
			}
		}
	}
	
	public void acaoAddAnamnese(Long codigo){
		if(alreadyAddAnamnese(codigo)){
			return;
		}
		PacienteQuestionarioAnamnese pqa = new PacienteQuestionarioAnamnese(null,codigo);
		DaoQuestionarioAnamnese daoQA = new DaoQuestionarioAnamnese();
		QuestionarioAnamnese novo = new QuestionarioAnamnese(codigo);
		try {
            daoQA.load(novo);
        } catch (Exception e) {
            e.printStackTrace();
        }
		this.questionariosAnamnese.put(pqa , novo);
		this.anamnese = addAdapter(pqa,novo);
	}
	
	private boolean alreadyAddAnamnese(Long codigo){
		boolean already = false;
		for(QuestionarioAnamneseAdapter qqa : questionariosAdapter){
			if(qqa.getQa().getCodigo().equals(codigo)){
				already = true;
				break;
			}
		}
		if(already){
			String saida = this.view.getProperties().get("formularioSaida") + ":" + "comboQuestionariosAnamnese";
			getView().addResourceDynamicMenssage("* O questionário selecionado ja foi adicionado a lista. ",	saida);
		}
		return already;
	}
	
	public ValidationRequest[] getValidationsObrigatorio(){
		return getValidations(VALIDATION_TYPE.OBRIGATORIOS);
	}
	
	public ValidationRequest[] getValidationsValidados(){
		return getValidations(VALIDATION_TYPE.VALIDADOS);
	}
	
	protected ValidationRequest[] getValidations(VALIDATION_TYPE vType){
		ValidationRequest[] validations = new ValidationRequest[questionariosAdapter.size()];
		String form = this.view.getProperties().get("formularioSaida");
		for(int i = 0;i < questionariosAdapter.size();i++){
			Validator validator = null;
			switch(vType){
				case OBRIGATORIOS:
					validator = getValidatorAnswer(questionariosAdapter.get(i)); 
					break;
				case VALIDADOS:
					validator = getValidatorObservacao(questionariosAdapter.get(i));
					break;
			} 
			validations[i] = new ValidationRequest(null, validator, form+":anamneseValidationTable:"+i+":qaValidation");
		}
		return validations;
	}
	
	public Validator getValidatorObservacao(QuestionarioAnamneseAdapter adapter){
		List<AnamneseValidationRequest> validations = new ArrayList<AnamneseValidationRequest>();
		for(int i = 0 ; i < adapter.getQuestoes().size();i++){
			QuestionarioQuestaoAdapter questaoAdapter = adapter.getQuestoes().get(i);
			validations.add(new ObsValidationRequest(getObsValidator(), questaoAdapter));
		}		
		return new AnamneseValidator(validations,msgBundleValidationObs,adapter);
	}
	
	public Validator getValidatorAnswer(QuestionarioAnamneseAdapter adapter){		
		List<AnamneseValidationRequest> validations = new ArrayList<AnamneseValidationRequest>();
		for(int i = 0 ; i < adapter.getQuestoes().size();i++){
			QuestionarioQuestaoAdapter questaoAdapter = adapter.getQuestoes().get(i);
			if(questaoAdapter.getQqa().getObrigatoria()){
				validations.add(new AnswerValidationRequest(getAnswerValidator(), questaoAdapter));
			}
		}		
		return new AnamneseValidator(validations,msgBundleValidationAns,adapter);
	}

	
	private Validator getAnswerValidator(){
		List<TiposRespostaAnamnese> domain = Arrays.asList(TiposRespostaAnamnese.values()); 
		Validator ansValidator = ValidatorFactory.newDomain(domain);
		return ansValidator;
	}
	
	private Validator getObsValidator(){
		Validator obsValidator = ValidatorFactory.newStrMaxLen(MAX_OBS_LENGTH, false);
		return obsValidator;
	}
	
	public ApplicationView getView() {
		return view;
	}

	public Map<PacienteQuestionarioAnamnese, QuestionarioAnamnese> getQuestionariosAnamnese() {
		return questionariosAnamnese;
	}

	public void setQuestionariosAnamnese(Map<PacienteQuestionarioAnamnese, QuestionarioAnamnese> questionariosAnamnese) {
		this.questionariosAnamnese = questionariosAnamnese;
		makeAdpter();
	}

	public List<QuestionarioAnamneseAdapter> getQuestionariosAdapter() {
		return questionariosAdapter;
	}
	
	public Long getAdd() {
        return add;
    }

    public void setAdd(Long add) {
        this.add = add;
    }

    public QuestionarioAnamneseAdapter getAnamnese() {
		return anamnese;
	}
	
	public void setAnamnese(QuestionarioAnamneseAdapter anamnese) {
		this.anamnese = anamnese;
	}

	protected enum VALIDATION_TYPE{
		OBRIGATORIOS,VALIDADOS;
	}
	
	protected abstract class AnamneseValidationRequest{
		private Validator 					validator;
		private QuestionarioQuestaoAdapter 	adapter;		
		public AnamneseValidationRequest(Validator validator, QuestionarioQuestaoAdapter adapter) {
			this.validator = validator;
			this.adapter = adapter;
		}

		public abstract boolean isValid();

		public Validator getValidator() {
			return validator;
		}

		public void setValidator(Validator validator) {
			this.validator = validator;
		}

		public QuestionarioQuestaoAdapter getAdapter() {
			return adapter;
		}

		public void setAdapter(QuestionarioQuestaoAdapter adapter) {
			this.adapter = adapter;
		}
	}
	
	protected class ObsValidationRequest extends AnamneseValidationRequest{
		public ObsValidationRequest(Validator validator,QuestionarioQuestaoAdapter qqa) {
			super(validator,qqa);
			validator.setValue(qqa.getResposta().getObservacao());
		}
		@Override
		public boolean isValid() {
			return getValidator().isValid() || !ValidatorFactory.checkInvalidPermiteds(getValidator(),allowed);
		}
	}
	
	protected class AnswerValidationRequest extends AnamneseValidationRequest{
		public AnswerValidationRequest(Validator validator,QuestionarioQuestaoAdapter qqa) {
			super(validator,qqa);
			validator.setValue(qqa.getResposta().getResposta());
		}
		@Override
		public boolean isValid() {
			return getValidator().isValid();
		}		
	}
	
	protected class AnamneseValidator extends AbstractValidator implements ObjectValidatorType{

		private List<AnamneseValidationRequest> validations;
		private Map<String,String> 				msgBunlde;
		
		public AnamneseValidator(List<AnamneseValidationRequest> validations,Map<String,String> msgBunlde,QuestionarioAnamneseAdapter value) {
			this(null,validations,msgBunlde,value);
		}		
		
		public AnamneseValidator(Validator next,List<AnamneseValidationRequest> validations,Map<String,String> msgBunlde,QuestionarioAnamneseAdapter value) {
			super(next, value);
			this.validations = validations;
			this.msgBunlde = msgBunlde;
		}

		@Override
		protected boolean validate() {
			boolean valid = true;
			QuestionarioAnamneseAdapter value = (QuestionarioAnamneseAdapter) getValue();
			StringBuilder stb = new StringBuilder().append(value.getQa().getNome()).append(" : ");
			List<Integer> fail = new ArrayList<Integer>();
			for(AnamneseValidationRequest validator : validations){
				if(!validator.isValid()){
					fail.add(validator.getAdapter().getQqa().getIndex());
					valid = false;
				}
			}			
			if(!valid){
				logInvalid(stb, fail);
			}
			return valid;
		}

		private void logInvalid(StringBuilder stb, List<Integer> fail) {
			boolean many = fail.size() > 1;
			stb.append(many ? PROPERTY_FAIL_PLURAL : PROPERTY_FAIL_SINGLE);
			printIndexes(fail,stb);
			stb.append(many ? msgBunlde.get(MSG_FAIL_PLURAL) : msgBunlde.get(MSG_FAIL_SINGLE));
			setErrorMsg(stb.toString());
		}
		
		private void printIndexes(List<Integer> indices , StringBuilder stb){
			boolean first = true;
			for(Iterator<Integer> iterator = indices.iterator();iterator.hasNext();){
				boolean has = iterator.hasNext();
				Integer index = iterator.next();
				if(!first){
					if(has && iterator.hasNext()){
						stb.append(", ");
					}else if(has && !iterator.hasNext()){
						stb.append(" e ");
					}
				}
				stb.append(index+1);
				first = false;
			}
		}
		

	}
	
}
