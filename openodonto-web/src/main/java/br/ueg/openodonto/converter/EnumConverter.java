package br.ueg.openodonto.converter;

public class EnumConverter<T extends Enum<T>> implements BeanConverter<T> {

	private Class<T> classe;
	
	public EnumConverter(Class<T> classe){
		this.classe = classe;
	}
	
	@Override
	public T getAsObject(String value) {
		try{
		    return Enum.valueOf(classe, value);
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(T value) {
		return value.name();
	}
	

}
