package br.ueg.openodonto.validator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import br.com.vitulus.simple.validator.CnpjValidator;
import br.com.vitulus.simple.validator.CpfValidator;
import br.com.vitulus.simple.validator.DomainValidator;
import br.com.vitulus.simple.validator.EmptyValidator;
import br.com.vitulus.simple.validator.MaskValidator;
import br.com.vitulus.simple.validator.NullValidator;
import br.com.vitulus.simple.validator.NumberSizeValidator;
import br.com.vitulus.simple.validator.NumberValidator;
import br.com.vitulus.simple.validator.StringSizeValidator;
import br.com.vitulus.simple.validator.Validator;

public class ValidatorFactory {

	public static boolean checkInvalidPermiteds(Validator validator,Class<?>... invalidPermiteds){
		return checkInvalidPermiteds(Arrays.asList(invalidPermiteds),validator);
	}
	
	public static boolean checkInvalidPermiteds(List<Class<?>> invalidPermiteds,Validator validator){
		Iterator<Class<?>> iterator  = invalidPermiteds.iterator();
		boolean valid = true;
		while(iterator.hasNext()){
			Class<?> permited = iterator.next();
			valid = valid && !permited.isAssignableFrom(validator.getSource().getClass());
		}
		return valid;
	}
	
	public static Validator newNull(){
		return new NullValidator("");
	}
	
	public static Validator newStrMaxLen(int max, boolean trimmed){
		return new NullValidator(new EmptyValidator(new StringSizeValidator("", max,0),true));
	}
	
	public static Validator newStrMinLen(int min, boolean trimmed){
		return new NullValidator(new EmptyValidator(new StringSizeValidator("", Integer.MAX_VALUE,min),true));
	}
	
	public static Validator newStrRangeLen(int max,int min, boolean trimmed){
		return new NullValidator(new EmptyValidator(new StringSizeValidator("", max,min),true));
	}
	
	public static Validator newStrMask(String mask){
		return newStrMask(mask,Integer.MAX_VALUE);
	}
	
	public static Validator newStrMask(String mask,int max){
		return new NullValidator(new EmptyValidator(new StringSizeValidator(new MaskValidator("", mask), max,0)));
	}
	
	public static Validator newStrEmpty(){
		return new NullValidator(new EmptyValidator(""));
	}
	
	public static Validator newNumRange(Integer max,Integer min){
		return new NullValidator(new NumberValidator(new NumberSizeValidator(0, max,min)));
	}
	
	public static Validator newNumMax(Integer max){
		return new NullValidator(new NumberValidator(new NumberSizeValidator(0, max,0)));
	}
	
	public static Validator newNumMin(Integer min){
		return new NullValidator(new NumberValidator(new NumberSizeValidator(min, Integer.MAX_VALUE,min)));
	}
	
	public static Validator newCpfFormat(){
		return newStrMask("^[0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2}$");//999.999.999-99
	}
	
	public static Validator newCnpjFormat(){
		return newStrMask("^[0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[/]?[0-9]{4}[-]?[0-9]{2}$");//99.999.999/9999-99
	}
	
	public static Validator newCpf(){
		return newCpfFormat().concat(new CpfValidator(null));
	}
	
	public static Validator newCnpj(){
		return newCnpjFormat().concat(new CnpjValidator(null));
	}
	
	public static Validator newEmail(int max){
		return newStrMask("([\\w\\._-])+@([\\w-]+\\.)+[\\w-]+",max);
	}
	
	public static Validator newEmail(){
		return newEmail(Integer.MAX_VALUE);
	}
	
	public static <T> Validator newDomain(List<T> domain){
		return new NullValidator(new DomainValidator<T>(domain, null));
	}

}
