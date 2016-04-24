package br.ueg.openodonto.controle.servico;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.vitulus.simple.jdbc.Entity;
import br.ueg.openodonto.controle.listagem.ListagemProcedimento;
import br.ueg.openodonto.controle.listagem.ListagemQuestionarioAnamnese;
import br.ueg.openodonto.servico.listagens.core.AbstractLista;
import br.ueg.openodonto.servico.listagens.core.ListaDominio;
import br.ueg.openodonto.servico.listagens.core.ListaTipo;

public class ManageListagem implements Serializable {

	private static final long serialVersionUID = 7484584628436233933L;
	private static Map<Class<?>, AbstractLista<?>> 			cache;
	private static Map<String,AbstractLista<?>>             aliasMap;

	public static final Object[][] ALIAS = {
	    {"ALIAS_DENTE" , "br.ueg.openodonto.dominio.constante.Dente"},
	    {"ALIAS_FACE" , "br.ueg.openodonto.dominio.constante.FaceDente"},
	    {"ALIAS_UF" , "br.ueg.openodonto.dominio.constante.TiposUF"},
	    {"ALIAS_TIPO_TEL","br.ueg.openodonto.dominio.constante.TiposTelefone"},
	    {"ALIAS_CAT","br.ueg.openodonto.dominio.constante.CategoriaProduto"},
	    {"ALIAS_RESP_ANAMN","br.ueg.openodonto.dominio.constante.TiposRespostaAnamnese"},	    
	    {"ALIAS_PROC",new ListagemProcedimento()},
	    {"ALIAS_QUEST",new ListagemQuestionarioAnamnese()},
	    {"ALIAS_STATUS_PROC","br.ueg.openodonto.dominio.constante.TipoStatusProcedimento"}};
	
	static{
	    aliasMap = new HashMap<String, AbstractLista<?>>();
		cache = new HashMap<Class<?>, AbstractLista<?>>() {
			private static final long serialVersionUID = 4625686751396609699L;

			@Override
			public AbstractLista<?> get(Object arg) {
				if (arg instanceof String)
					return getLista((String) arg);
				else
					return super.get(arg);
			}

		};
		configureAlias();
	}	

	private static void configureAlias(){
	    for(Object[] alias : ALIAS){
	    	Object value = alias[1];
	    	AbstractLista<?> abl;
	    	if(value instanceof String){
	    		abl = getLista((String)value);
	    	}else if(value instanceof AbstractLista<?>){
	    		abl = (AbstractLista<?>) value;	    		
	    	}else{
	    		abl = null;
	    	}
	        aliasMap.put((String)alias[0], abl);
	    }
	}
	
	private static <T> AbstractLista<T> getListaTipo(Class<T> classe) {
		return new ListaTipo<T>(classe);
	}

	private static <T extends Entity> AbstractLista<T> getListaDominio(Class<T> classe) {
		return new ListaDominio<T>(classe);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> AbstractLista<T> getLista(Class<T> classe) {
		AbstractLista<T> lista = null;
		if (cache.get(classe) == null) {
			if (classe.isEnum()){
				lista = getListaTipo(classe);
			}else{
				lista = getListaDominio(classe);
			}
			cache.put(classe, lista);
		} else {
			lista = (AbstractLista<T>) cache.get(classe);
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> AbstractLista<?> getLista(String className) {
		if(aliasMap.containsKey(className)){
			return aliasMap.get(className);
		}else{
			Class<T> classe = (Class<T>) resolveClass(className);
			return getLista(classe);
		}
		
	}

	private static Class<?> resolveClass(String className) {
		Class<?> classe;
		try {
			classe = Class.forName(className);
		} catch (ClassNotFoundException e) {			
			throw new RuntimeException("Classe não encontrada", e);
		}
		return classe;
	}

	public Map<Class<?>, AbstractLista<?>> getCache() {
		return cache;
	}

}
