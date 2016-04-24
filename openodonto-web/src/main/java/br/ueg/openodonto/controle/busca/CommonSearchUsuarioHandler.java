package br.ueg.openodonto.controle.busca;

import br.ueg.openodonto.dominio.Usuario;
import br.ueg.openodonto.persistencia.dao.DaoUsuario;

public class CommonSearchUsuarioHandler extends CommonSearchBeanHandler<Usuario>{
	private String[] showColumns = {"codigo", "nome", "user",};	
	public CommonSearchUsuarioHandler() {
		super(Usuario.class, new DaoUsuario());
	}
	@Override
	public String[] getShowColumns() {
		return showColumns;
	}

}
