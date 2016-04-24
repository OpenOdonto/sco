package br.ueg.openodonto.web.tags;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class HtmlMap extends UIOutput{

	private String name;
	
	public HtmlMap() {
	}
	
    public HtmlMap(String name) {
		super();
		this.name = name;
	}

	@Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }
    
    @Override
    public void encodeBegin(FacesContext ctx) throws IOException {
		ResponseWriter writer = ctx.getResponseWriter();
		writer.startElement("map", null);
		writer.writeAttribute("name", this.getName(), null);
		writer.writeAttribute("id", this.getId(), null);
    }
    
    @Override
    public void encodeEnd(FacesContext ctx) throws IOException {
		ResponseWriter writer = ctx.getResponseWriter();
		writer.endElement("map");
		writer.flush();
    }

	@Override
	public void restoreState(FacesContext facesContext, Object state) {
		Object[] values = (Object[]) state;
		super.restoreState(facesContext, values[0]);
		setName((String) values[1]);
		setId((String) values[2]);
	}

	@Override
	public Object saveState(FacesContext facesContext) {
		Object[] values = new Object[3];
		values[0] = super.saveState(facesContext);
		values[1] = getName();
		values[2] = getId();
		return (Object) values;
	}
    
	public String getName() {
	    if(name != null){
	        return name;
	    }
	    ValueExpression ve = getValueExpression("name");
	    if (ve != null) {
	        return (String) ve.getValue(getFacesContext().getELContext());
	    }
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
