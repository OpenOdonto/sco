package br.ueg.openodonto.controle.context;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import br.ueg.openodonto.dominio.Usuario;

public interface ApplicationContext extends Serializable{

    Usuario getUsuarioSessao();

    String getParameter(String name);    

    Map<String, Object> getAttributes();
    
    Locale getClientLocale();

    void removeAttribute(String name);

    void addAttribute(String name, Object value);

    <T> T getAttribute(String name, Class<T> classe);

}
