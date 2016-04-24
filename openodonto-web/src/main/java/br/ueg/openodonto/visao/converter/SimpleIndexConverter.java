package br.ueg.openodonto.visao.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.myfaces.custom.selectitems.UISelectItems;

public class SimpleIndexConverter implements Converter {

    private int index = -1;
    private Map<Object , Integer> cache;
    
    public SimpleIndexConverter() {
    	cache = new HashMap<Object, Integer>();
	}

    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
    	SelectItem selectedItem = this.getSelectedItemByIndex(component, value == null || value.isEmpty() ? -1 : Integer.parseInt(value));
    	if (selectedItem != null){
    		return selectedItem.getValue();
    	}
    	return null;
    }

    public String getAsString(FacesContext ctx, UIComponent component, Object value) {
    	Integer index;
    	if((index = cache.get(value)) == null){
    		index = ++this.index;
    		cache.put(value, index);    		
    	}    	
    	return String.valueOf(index);
    }

    /**
     * Obtem o SelecItem de acordo com a opção selecionada pelo usuário
     */
    protected SelectItem getSelectedItemByIndex(UIComponent component, int index) {

	List<SelectItem> items = this.getSelectItems(component);
	int size = items.size();

	if (index > -1 && size > index) {
	    return items.get(index);
	}

	return null;
    }

    protected List<SelectItem> getSelectItems(UIComponent component) {

	List<SelectItem> items = new ArrayList<SelectItem>();

	int childCount = component.getChildCount();
	if (childCount == 0)
	    return items;

	List<UIComponent> children = component.getChildren();
	for (UIComponent child : children) {
	    if (child instanceof UISelectItem) {
		this.addSelectItem((UISelectItem) child, items);
	    } else if (child instanceof UISelectItems) {
		this.addSelectItems((UISelectItems) child, items);
	    }
	}

	return items;
    }

    protected void addSelectItem(UISelectItem uiItem, List<SelectItem> items) {

	boolean isRendered = uiItem.isRendered();
	if (!isRendered) {
	    items.add(null);
	    return;
	}

	Object value = uiItem.getValue();
	SelectItem item;

	if (value instanceof SelectItem) {
	    item = (SelectItem) value;
	} else {
	    Object itemValue = uiItem.getItemValue();
	    String itemLabel = uiItem.getItemLabel();
	    // JSF throws a null pointer exception for null values and labels,
	    // which is a serious problem at design-time.
	    item = new SelectItem(itemValue == null ? "" : itemValue,
		    itemLabel == null ? "" : itemLabel, uiItem
			    .getItemDescription(), uiItem.isItemDisabled());
	}

	items.add(item);
    }

    @SuppressWarnings("unchecked")
    protected void addSelectItems(UISelectItems uiItems, List<SelectItem> items) {

	boolean isRendered = uiItems.isRendered();
	if (!isRendered) {
	    items.add(null);
	    return;
	}

	Object value = uiItems.getValue();
	if (value instanceof SelectItem) {
	    items.add((SelectItem) value);
	} else if (value instanceof Object[]) {
	    Object[] array = (Object[]) value;
	    for (int i = 0; i < array.length; i++) {
		// TODO test - this section is untested
		if (array[i] instanceof SelectItemGroup) {
		    resolveAndAddItems((SelectItemGroup) array[i], items);
		} else {
		    items.add((SelectItem) array[i]);
		}
	    }
	} else if (value instanceof Collection) {
	    Iterator<SelectItem> iter = ((Collection<SelectItem>) value)
		    .iterator();
	    SelectItem item;
	    while (iter.hasNext()) {
		item = iter.next();
		if (item instanceof SelectItemGroup) {
		    resolveAndAddItems((SelectItemGroup) item, items);
		} else {
		    items.add(item);
		}
	    }
	} else if (value instanceof Map) {
	    for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value)
		    .entrySet()) {
		Object label = entry.getKey();
		SelectItem item = new SelectItem(entry.getValue(),
			label == null ? (String) null : label.toString());
		// TODO test - this section is untested
		if (item instanceof SelectItemGroup) {
		    resolveAndAddItems((SelectItemGroup) item, items);
		} else {
		    items.add(item);
		}
	    }
	}
    }

    protected void resolveAndAddItems(SelectItemGroup group,
	    List<SelectItem> items) {
	for (SelectItem item : group.getSelectItems()) {
	    if (item instanceof SelectItemGroup) {
		resolveAndAddItems((SelectItemGroup) item, items);
	    } else {
		items.add(item);
	    }
	}
    }

}