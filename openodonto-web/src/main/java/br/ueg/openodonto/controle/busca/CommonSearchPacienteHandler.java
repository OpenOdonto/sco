package br.ueg.openodonto.controle.busca;

import br.ueg.openodonto.dominio.Paciente;
import br.ueg.openodonto.persistencia.dao.DaoPaciente;

public class CommonSearchPacienteHandler extends CommonSearchBeanHandler<Paciente>{

	private String[] showColumns = {"codigo", "nome", "email", "cpf"};
	
	public CommonSearchPacienteHandler() {
		super(Paciente.class, new DaoPaciente());
	}

	@Override
	public String[] getShowColumns() {
		return showColumns;
	}

}
