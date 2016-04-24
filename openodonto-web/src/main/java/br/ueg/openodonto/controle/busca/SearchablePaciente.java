package br.ueg.openodonto.controle.busca;

import java.lang.reflect.Field;
import java.util.ArrayList;

import br.com.vitulus.simple.jdbc.orm.old.OrmResolver;
import br.com.vitulus.simple.jdbc.orm.old.OrmTranslator;
import br.com.vitulus.simple.validator.EmptyValidator;
import br.com.vitulus.simple.validator.NullValidator;
import br.com.vitulus.simple.validator.Validator;
import br.ueg.openodonto.controle.servico.ExampleRequest;
import br.ueg.openodonto.dominio.Paciente;
import br.ueg.openodonto.persistencia.dao.sql.SqlWhereOperatorType;
import br.ueg.openodonto.servico.busca.FieldFacade;
import br.ueg.openodonto.servico.busca.InputMask;
import br.ueg.openodonto.servico.busca.MessageDisplayer;
import br.ueg.openodonto.validator.ValidatorFactory;

public class SearchablePaciente extends AbstractSearchable<Paciente>{

	private static final long serialVersionUID = -946348173920879375L;

	public SearchablePaciente(MessageDisplayer displayer) {
		super(Paciente.class,displayer);
	}
	
	public void buildFacade(){
		super.buildFacade();
		OrmTranslator translator = new OrmTranslator(OrmResolver.getAllFields(new ArrayList<Field>(), Paciente.class, true));
		getFacade().add(new FieldFacade("Código",translator.getColumn("codigo")));
		getFacade().add(new FieldFacade("Nome",translator.getColumn("nome")));
		getFacade().add(new FieldFacade("CPF",translator.getColumn("cpf")));
		getFacade().add(new FieldFacade("E-mail",translator.getColumn("email")));
	}
	
	public void buildMask(){
		super.buildMask();
		getMasksMap().put("cpf",new JsMask("mask('999.999.999-99',{placeholder:'_'})","maskCpf","maskCpf"));
	}
	
	public void buildFilter(){
		super.buildFilter();
		buildNameFilter();
		buildEmailFilter();
		buildCpfFilter();
		buildCodigoFilter();
	}
	
	private void buildNameFilter(){
		Validator validator = ValidatorFactory.newStrRangeLen(100,3, true);
		getFiltersMap().put("nomeFilter", buildBasicFilter("nomeFilter","Nome",validator));
	}
	
	private void buildCpfFilter(){
		Validator validator = ValidatorFactory.newCpf();
		InputMask mask = getMasksMap().get("cpf");
		getFiltersMap().put("cpfFilter",buildBasicFilter("cpfFilter","CPF",mask,validator));
	}
	
	private void buildEmailFilter(){
		Validator validator = ValidatorFactory.newEmail();
		getFiltersMap().put("emailFilter", buildBasicFilter("emailFilter","E-mail",validator));
	}
	
	private void buildCodigoFilter(){
		Validator validator = ValidatorFactory.newNumMax(Integer.MAX_VALUE);
		getFiltersMap().put("idFilter",buildBasicFilter("idFilter","Código",validator));
	}

	public Paciente buildExample(){
		ExampleRequest<Paciente> request  = new ExampleRequest<Paciente>(this);		
		request.getFilterRelation().add(request.new TypedFilter("nomeFilter", "nome",SqlWhereOperatorType.LIKE));
		request.getFilterRelation().add(request.new TypedFilter("idFilter","codigo",SqlWhereOperatorType.EQUAL));
		request.getFilterRelation().add(request.new TypedFilter("emailFilter", "email",SqlWhereOperatorType.EQUAL));
		request.getFilterRelation().add(request.new TypedFilter("cpfFilter", "cpf",SqlWhereOperatorType.EQUAL));
		request.getInvalidPermiteds().add(NullValidator.class);
		request.getInvalidPermiteds().add(EmptyValidator.class);
		Paciente target = getManageExample().processExampleRequest(request);
		return target;
	}
	
}
