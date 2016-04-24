package br.ueg.openodonto.converter;

public class LongBeanValidator implements BeanConverter<Long> {

	@Override
	public String getAsString(Long value) {
		return value.toString();
	}

	@Override
	public Long getAsObject(String value) {
		return Long.parseLong(value);
	}

}
