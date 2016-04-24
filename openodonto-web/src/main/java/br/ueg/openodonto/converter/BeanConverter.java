package br.ueg.openodonto.converter;

public interface BeanConverter<T> {

	public T getAsObject(String value);
	public String getAsString(T value);
	
}
