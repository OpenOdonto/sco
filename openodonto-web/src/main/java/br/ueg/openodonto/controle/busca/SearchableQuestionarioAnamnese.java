package br.ueg.openodonto.controle.busca;

import java.lang.reflect.Field;
import java.util.ArrayList;

import br.com.vitulus.simple.jdbc.orm.old.OrmResolver;
import br.com.vitulus.simple.jdbc.orm.old.OrmTranslator;
import br.com.vitulus.simple.validator.EmptyValidator;
import br.com.vitulus.simple.validator.NullValidator;
import br.com.vitulus.simple.validator.Validator;
import br.ueg.openodonto.controle.servico.ExampleRequest;
import br.ueg.openodonto.dominio.QuestionarioAnamnese;
import br.ueg.openodonto.persistencia.dao.sql.SqlWhereOperatorType;
import br.ueg.openodonto.servico.busca.FieldFacade;
import br.ueg.openodonto.servico.busca.MessageDisplayer;
import br.ueg.openodonto.validator.ValidatorFactory;

public class SearchableQuestionarioAnamnese extends AbstractSearchable<QuestionarioAnamnese>{

	private static final long serialVersionUID = 7390396145255209318L;

	public SearchableQuestionarioAnamnese(MessageDisplayer displayer) {
		super(QuestionarioAnamnese.class, displayer);
	}

	@Override
	protected void buildFacade() {
		super.buildFacade();
		OrmTranslator translator = new OrmTranslator(OrmResolver.getAllFields(new ArrayList<Field>(), QuestionarioAnamnese.class, true));
		getFacade().add(new FieldFacade("Código",translator.getColumn("codigo")));
		getFacade().add(new FieldFacade("Nome",translator.getColumn("nome")));
		getFacade().add(new FieldFacade("Descrição","shortDescription"));
	}

	@Override
	protected void buildFilter() {
		super.buildFilter();
		buildCodigoFilter();
		buildNomeFilter();
		buildDescricaoFilter();
	}
	
	private void buildCodigoFilter() {
		Validator validator = ValidatorFactory.newNumMax(Integer.MAX_VALUE);
		getFiltersMap().put("idFilter",buildBasicFilter("idFilter","Código",validator));
	}

	private void buildNomeFilter() {
		Validator validator = ValidatorFactory.newStrRangeLen(45,3, false);
		getFiltersMap().put("descriptionFilter", buildBasicFilter("descriptionFilter","Descrição",validator));
	}

	private void buildDescricaoFilter() {
		Validator validator = ValidatorFactory.newStrRangeLen(100,3, false);
		getFiltersMap().put("nomeFilter", buildBasicFilter("nomeFilter","Nome",validator));
	}

	@Override
	public QuestionarioAnamnese buildExample() {
		ExampleRequest<QuestionarioAnamnese> request  = new ExampleRequest<QuestionarioAnamnese>(this);		
		request.getFilterRelation().add(request.new TypedFilter("descriptionFilter", "descricao",SqlWhereOperatorType.LIKE));
		request.getFilterRelation().add(request.new TypedFilter("nomeFilter", "nome",SqlWhereOperatorType.LIKE));
		request.getFilterRelation().add(request.new TypedFilter("idFilter","codigo",SqlWhereOperatorType.EQUAL));
		request.getInvalidPermiteds().add(NullValidator.class);
		request.getInvalidPermiteds().add(EmptyValidator.class);
		QuestionarioAnamnese target = getManageExample().processExampleRequest(request);
		return target;
	}

}
