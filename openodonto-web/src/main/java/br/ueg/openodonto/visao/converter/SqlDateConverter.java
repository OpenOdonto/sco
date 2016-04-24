package br.ueg.openodonto.visao.converter;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class SqlDateConverter implements Converter {

	private static DateFormat dateFormat;

	static {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			try{
				StringBuilder sqlDateSttring = new StringBuilder();
				String[] date = value.split("/");
				sqlDateSttring.append(date[2]);
				sqlDateSttring.append("-");
				sqlDateSttring.append(date[1]);
				sqlDateSttring.append("-");
				sqlDateSttring.append(date[0]);
				return Date.valueOf(sqlDateSttring.toString());
			}catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,Object value) {
		Date date = (Date) value;
		return dateFormat.format(date);
	}

}
