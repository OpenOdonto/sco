package br.ueg.openodonto.validator;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.EntityManager;
import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.com.vitulus.simple.jdbc.orm.old.OrmResolver;
import br.com.vitulus.simple.jdbc.orm.old.OrmTranslator;
import br.com.vitulus.simple.jdbc.sql.old.CrudQuery;
import br.com.vitulus.simple.jdbc.sql.old.IQuery;
import br.com.vitulus.simple.validator.AbstractValidator;
import br.com.vitulus.simple.validator.CpfValidator;
import br.com.vitulus.simple.validator.tipo.ObjectValidatorType;
import br.ueg.openodonto.controle.ManageBeanGeral;
import br.ueg.openodonto.dominio.constante.PessoaFisica;
import br.ueg.openodonto.util.WordFormatter;

public class UniqueCpfValidator<T extends Entity & PessoaFisica<T>> extends AbstractValidator implements ObjectValidatorType{	
	
	public UniqueCpfValidator(T value) {
		this(null,value);
	}
	
	public UniqueCpfValidator(CpfValidator next,T value) {
		super(next, value);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) super.getValue();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected boolean validate() {
		Class<T> type = (Class<T>) getValue().getClass();
		String cpf = WordFormatter.clear(getValue().getCpf());
		EntityManager<T> dao = (EntityManager<T>) ManageBeanGeral.getDao(type);	
		Map<String,Object> params = new HashMap<String, Object>(1);
		params.put("cpf", cpf);
		List<Field> keyFields = OrmResolver.getKeyFields(new LinkedList<Field>(), type, true);
		String[] fields = new String[keyFields.size()];
		for(int i = 0;i < keyFields.size();i++){
			fields[i] = keyFields.get(i).getName();
		}
		IQuery query = CrudQuery.getSelectQuery(getValue().getClass(), params, fields);
		try {
			List<Map<String,Object>> result = dao.getSqlExecutor().executarUntypedQuery(query);
			if(result.size() == 1){
				OrmFormat format = new OrmFormat(getValue());
				Map<String,Object> already = result.get(0);
				Map<String,Object> local = format.format(fields);
				boolean isSamePf = true;
				OrmTranslator translator = new OrmTranslator(keyFields);				
				for(String field : fields){
					isSamePf = isSamePf && already.get(translator.getColumn(field)).equals(local.get(field));
				}
				if(!isSamePf){
					setErrorMsg("CPF já está sendo usado.");
				}
				return isSamePf;
			}else if(result.size() > 1){
				throw new IllegalStateException("Falha de integridade.Permitiso apenas uma pessoa por CPF.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}	

}
