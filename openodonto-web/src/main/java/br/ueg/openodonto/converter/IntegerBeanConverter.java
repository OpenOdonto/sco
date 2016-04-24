package br.ueg.openodonto.converter;

public class IntegerBeanConverter implements BeanConverter<Integer> {

	@Override
	public String getAsString(Integer value) {
		return value.toString();
	}

	@Override
	public Integer getAsObject(String value) {
		return Integer.valueOf(value);
	}

}
