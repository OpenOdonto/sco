package br.ueg.openodonto.controle.servico;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.com.vitulus.simple.jdbc.orm.old.OrmResolver;
import br.com.vitulus.simple.jdbc.orm.old.OrmTranslator;
import br.com.vitulus.simple.validator.Validator;
import br.ueg.openodonto.persistencia.dao.sql.SqlWhereOperatorType;
import br.ueg.openodonto.servico.busca.InputField;
import br.ueg.openodonto.servico.busca.SearchFilter;
import br.ueg.openodonto.util.WordFormatter;
import br.ueg.openodonto.validator.ValidatorFactory;

public class ManageExample<T> implements Serializable{
	private static final long serialVersionUID = -9067163321882594609L;
	private Class<T> classe;
	
	public ManageExample(Class<T> classe) {
		this.classe = classe;
	}
	
	private T factoryExample(){
		try {
			return classe.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private SearchFilter findSearchFilter(String name,List<SearchFilter> filters){
		for(Iterator<SearchFilter> iterator = filters.iterator();iterator.hasNext();){
			SearchFilter filter;
			if((filter = iterator.next()).getName().equals(name)){
				return filter;
			}
		}
		return null;
	}
	
	private InputField<?> getCommonInput(SearchFilter filter){
		return filter.getField().getInputFields().get(0);
	}
	
	private boolean checkValidators(List<Validator> validators){
		boolean valid = true;
		for(Iterator<Validator> iterator = validators.iterator();iterator.hasNext();valid = valid && iterator.next().isValid());
		return valid;
	}
	
	private void notifyValidation(SearchFilter filter,InputField<?> inputField,List<Class<?>> invalidPermiteds){
		for(Iterator<Validator> iterator = inputField.getValidators().iterator();iterator.hasNext();){
			Validator validator = iterator.next();
			if(!validator.isValid() && ValidatorFactory.checkInvalidPermiteds(invalidPermiteds,validator)){
				filter.displayValidationMessage(
						WordFormatter.formatErrorMessage(
								filter.getLabel(),
								inputField.getValue().toString(),
								validator.getErrorMessage()));
			}
		}
	}
	

	
	private Object getValue(InputField<?> inputField ,ExampleRequest<T>.TypedFilter typedFilter){
		Object value = inputField.getValue();
		if(typedFilter.getType() == SqlWhereOperatorType.LIKE && (value instanceof String)){
			return "%" + value + "%";
		}
		return value;
	}
	
  	public T processExampleRequest(ExampleRequest<T> req){
		Map<String, Object> values = new HashMap<String, Object>();
		OrmTranslator translator = new OrmTranslator(OrmResolver.getAllFields(new ArrayList<Field>(), classe, true));
		Iterator<ExampleRequest<T>.TypedFilter> iterator = req.getFilterRelation().iterator();
		while(iterator.hasNext()){
			ExampleRequest<T>.TypedFilter typedFilter  = iterator.next();
			SearchFilter filter = findSearchFilter(typedFilter.getFilterName(),req.getSearchable().getFilters());
			InputField<?> inputField = getCommonInput(filter);
			boolean valid = checkValidators(inputField.getValidators());
			if(valid){
				String field = typedFilter.getBeanPath();
				String column = translator.getColumn(field);
				values.put(column, getValue(inputField,typedFilter));
			}else{
				notifyValidation(filter,inputField,req.getInvalidPermiteds());
			}
		}
		T bean = factoryExample();
		OrmFormat format = new OrmFormat(bean);
		format.parse(values);
		return bean;
	}
	
}
