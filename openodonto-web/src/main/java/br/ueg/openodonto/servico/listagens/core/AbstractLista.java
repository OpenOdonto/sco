package br.ueg.openodonto.servico.listagens.core;

import java.util.List;

/**
 * @author vinicius.rodrigues
 * 
 * @param <T>
 */
public abstract class AbstractLista<T> {

    private List<T> dominioCache;

    protected Class<T> classe;

    private long lastUpdate;

    public AbstractLista(Class<T> classe) {
        this.classe = classe;
        lastUpdate = 0L;
    }

    public abstract boolean isChangeable();
    
    public abstract List<T> getRefreshDominio();
    
    public void refreshDominio(){
		dominioCache = getRefreshDominio();
		lastUpdate = System.currentTimeMillis();
    }
    
    public List<T> getDominio(){
    	if(this.dominioCache == null || (isOld() && isChangeable())){
    		refreshDominio();
    	}
   		return dominioCache;
    }

    public boolean isOld() {
        return lastUpdate + 10000L < System.currentTimeMillis();
    }

    public Class<T> getClasse() {
        return classe;
    }

}
