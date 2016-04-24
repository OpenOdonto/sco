package br.ueg.openodonto.converter;

public class BeanConverterFactory {

	private static BeanConverter<Integer>       integerConv;
	private static BeanConverter<Long>          longConv;
	
	static{
		integerConv = new IntegerBeanConverter();
		longConv = new LongBeanValidator();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> BeanConverter<T> getConverter(Class<T> type){
		if(type.equals(Integer.class)){
			return (BeanConverter<T>)integerConv;
		}else if(type.equals(Long.class)){
			return (BeanConverter<T>) longConv;
		}else if(type.isEnum()){
			return new EnumConverter(type);
		}else{
			return null;
		}
	}
	
}
