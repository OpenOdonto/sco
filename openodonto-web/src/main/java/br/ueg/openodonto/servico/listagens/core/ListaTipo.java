package br.ueg.openodonto.servico.listagens.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author vinicius.rodrigues
 * 
 * @param <T>
 */
public class ListaTipo<T> extends AbstractLista<T> {

    public ListaTipo(Class<T> classe) {
        super(classe);
    }

    public List<T> getRefreshDominio(){
        List<T> lista = new ArrayList<T>();
        lista = Arrays.asList(classe.getEnumConstants());
        return lista;
    }

	@Override
	public boolean isChangeable() {
		return false;
	}
}
