package br.ueg.openodonto.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ueg.openodonto.controle.context.ApplicationContext;
import br.ueg.openodonto.dominio.Usuario;

public class WebContext implements ApplicationContext {

	private static final long serialVersionUID = -7688766269073741104L;

	protected HttpServletRequest getRequest() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		return (HttpServletRequest) externalContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		return (HttpServletResponse) externalContext.getResponse();
	}

	@Override
	public Usuario getUsuarioSessao() {
		return getAttribute("autenticacao", Usuario.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String name, Class<T> classe) {
		Object bean = getRequest().getSession().getAttribute(name);
		if (bean != null) {
			return (T) bean;
		} else {
			return null;
		}
	}

	@Override
	public String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAttributes() {
		Map<String, Object> attributtes = new HashMap<String, Object>();
		Enumeration<String> names = getRequest().getSession()
				.getAttributeNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			attributtes.put(name, getRequest().getSession().getAttribute(name));
		}
		return attributtes;
	}

	@Override
	public void removeAttribute(String name) {
		getRequest().getSession().removeAttribute(name);
	}

	@Override
	public void addAttribute(String name, Object value) {
		getRequest().getSession().setAttribute(name, value);
	}

	@Override
	public Locale getClientLocale() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getViewRoot().getLocale();
	}

}
