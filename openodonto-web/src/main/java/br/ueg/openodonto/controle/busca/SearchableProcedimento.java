package br.ueg.openodonto.controle.busca;

import java.lang.reflect.Field;
import java.util.ArrayList;

import br.com.vitulus.simple.jdbc.orm.old.OrmResolver;
import br.com.vitulus.simple.jdbc.orm.old.OrmTranslator;
import br.com.vitulus.simple.validator.EmptyValidator;
import br.com.vitulus.simple.validator.NullValidator;
import br.com.vitulus.simple.validator.Validator;
import br.ueg.openodonto.controle.servico.ExampleRequest;
import br.ueg.openodonto.dominio.Procedimento;
import br.ueg.openodonto.persistencia.dao.sql.SqlWhereOperatorType;
import br.ueg.openodonto.servico.busca.FieldFacade;
import br.ueg.openodonto.servico.busca.MessageDisplayer;
import br.ueg.openodonto.validator.ValidatorFactory;

public class SearchableProcedimento extends AbstractSearchable<Procedimento>{

	public SearchableProcedimento(MessageDisplayer displayer) {
		super(Procedimento.class, displayer);
	}

	private static final long serialVersionUID = 295093090358291518L;

	@Override
	protected void buildFacade() {
		super.buildFacade();
		OrmTranslator translator = new OrmTranslator(OrmResolver.getAllFields(new ArrayList<Field>(), Procedimento.class, true));
		getFacade().add(new FieldFacade("Código",translator.getColumn("codigo")));
		getFacade().add(new FieldFacade("Nome",translator.getColumn("nome")));
		getFacade().add(new FieldFacade("Valor",translator.getColumn("valor")));
		getFacade().add(new FieldFacade("Descrição","shortDescription"));
	}
	
	@Override
	protected void buildFilter() {
		super.buildFilter();
		buildCodigoFilter();
		buildNomeFilter();
		buildDescricaoFilter();
	}
	
	private void buildDescricaoFilter() {
		Validator validator = ValidatorFactory.newStrRangeLen(300,3, true);
		getFiltersMap().put("descricaoFilter", buildBasicFilter("descricaoFilter","Descrição",validator));
	}

	private void buildNomeFilter() {
		Validator validator = ValidatorFactory.newStrRangeLen(150,3, true);
		getFiltersMap().put("nomeFilter", buildBasicFilter("nomeFilter","Nome",validator));
	}

	private void buildCodigoFilter() {
		Validator validator = ValidatorFactory.newNumMax(Integer.MAX_VALUE);
		getFiltersMap().put("idFilter",buildBasicFilter("idFilter","Código",validator));
	}

	@Override
	public Procedimento buildExample() {
		ExampleRequest<Procedimento> request  = new ExampleRequest<Procedimento>(this);		
		request.getFilterRelation().add(request.new TypedFilter("nomeFilter", "nome",SqlWhereOperatorType.LIKE));
		request.getFilterRelation().add(request.new TypedFilter("idFilter","codigo",SqlWhereOperatorType.EQUAL));
		request.getFilterRelation().add(request.new TypedFilter("descricaoFilter", "descricao",SqlWhereOperatorType.LIKE));
		request.getInvalidPermiteds().add(NullValidator.class);
		request.getInvalidPermiteds().add(EmptyValidator.class);
		Procedimento target = getManageExample().processExampleRequest(request);
		return target;
	}

}
