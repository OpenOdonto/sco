package br.ueg.openodonto.controle.servico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.vitulus.simple.validator.Validator;

public class ValidationRequest {

	private String            path;
	private Validator         validator;
	private String[]	      out;
	private List<Class<?>>    invalidPermiteds;
	
	public ValidationRequest(String path, Validator validator, String[] out) {
		this.path = path;
		this.validator = validator;
		this.out = out;
		this.invalidPermiteds = new ArrayList<Class<?>>();
	}
	
	public ValidationRequest(String path, Validator validator, String out) {
		this(path,validator,new String[]{out});
	}
	
	public ValidationRequest(String path, Validator validator, String out,Class<?>... invalidPermiteds) {
		this(path,validator,out);
		this.invalidPermiteds = Arrays.asList(invalidPermiteds);
	}
	
	public ValidationRequest(String path, Validator validator, String[] out,Class<?>... invalidPermiteds) {
		this(path,validator,out);
		this.invalidPermiteds = Arrays.asList(invalidPermiteds);
	}
	
	public String getPath() {
		return path;
	}	
	public void setPath(String path) {
		this.path = path;
	}
	public Validator getValidator() {
		return validator;
	}
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	public List<Class<?>> getInvalidPermiteds() {
		return invalidPermiteds;
	}
	public void setInvalidPermiteds(List<Class<?>> invalidPermiteds) {
		this.invalidPermiteds = invalidPermiteds;
	}
	public String[] getOut() {
		return out;
	}

	public void setOut(String[] out) {
		this.out = out;
	}	
}
