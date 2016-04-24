package br.ueg.openodonto.servico.busca;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.vitulus.simple.validator.Validator;

public class SearchFilterBase implements SearchFilter{

	private SearchField       field;
	private String            name;
	private String            label;
	private MessageDisplayer  display;
	
	public SearchFilterBase() {
	}
	
	public SearchFilterBase(SearchField field,String name, String label,MessageDisplayer display) {
		this.display = display;
		this.field = field;
		this.name = name;
		this.label = label;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public SearchField getField() {
		return field;
	}

	@Override
	public String getLabel() {
		return label;
	}


	public class Field implements SearchField{
		
		private List<InputField<?>> inputFields;
		
		public Field() {
			this.inputFields = new ArrayList<InputField<?>>();
		}		
		
		public Field(List<InputField<?>> inputFields) {
			super();
			this.inputFields = inputFields;
		}

		@Override
		public List<InputField<?>> getInputFields() {
			return inputFields;
		}

		public void setInputFields(List<InputField<?>> inputFields) {
			this.inputFields = inputFields;
		}

		@Override
		public String toString() {
			return "Field [inputFields=" + inputFields + "]";
		}		
	}
	
	public class Input<T> implements InputField<T>{

		private List<T>          domain;
		private InputMask        mask;
		private List<Validator>  validators;
		private T                value;
		
		public Input() {
			this.domain = new ArrayList<T>();
			this.validators = new ArrayList<Validator>();
		}		
		
		public Input(List<T> domain,InputMask mask, List<Validator> validators,T value) {
			this.domain = domain;
			this.mask = mask;
			this.validators = validators;
			this.value = value;
		}

		@Override
		public List<T> getDomain() {
			return domain;
		}

		@Override
		public InputMask getMask() {
			return mask;
		}

		@Override
		public List<Validator> getValidators() {
			return validators;
		}

		@Override
		public T getValue() {
			return value;
		}

		@Override
		public void setValue(T value) {
			this.value = value;
			updateValidators();
		}

		public void setDomain(List<T> domain) {
			this.domain = domain;
		}

		public void setMask(InputMask mask) {
			this.mask = mask;
		}

		public void setValidators(List<Validator> validators) {
			this.validators = validators;
		}

		private void updateValidators(){
			Iterator<Validator> iterator = getValidators().iterator();
			while(iterator.hasNext()){
				iterator.next().setValue(this.value);
			}
		}
		
		@Override
		public String toString() {
			return "Input [domain=" + domain + ", mask=" + mask
					+ ", validators=" + validators + ", value=" + value + "]";
		}		
		
	}
	
	public void setField(SearchField field) {
		this.field = field;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "SearchFilterBase [field=" + field + ", label=" + label
				+ ", name=" + name + "]";
	}

	@Override
	public void displayValidationMessage(String message) {
		display.display(message);
	}
	
}
