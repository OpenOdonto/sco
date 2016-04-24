package br.ueg.openodonto.controle.busca;

import br.ueg.openodonto.dominio.Dentista;
import br.ueg.openodonto.persistencia.dao.DaoDentista;

public class CommonSearchDentistaHandler extends CommonSearchBeanHandler<Dentista>{

	private String[] showColumns = {"codigo", "nome", "cro", "especialidade"};
	
	public CommonSearchDentistaHandler() {
		super(Dentista.class, new DaoDentista());
	}

	@Override
	public String[] getShowColumns() {
		return showColumns;
	}

}
