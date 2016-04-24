package br.ueg.openodonto.persistencia.dao;

import br.com.vitulus.simple.jdbc.annotation.Dao;
import br.ueg.openodonto.dominio.Pessoa;

@Dao(classe=Pessoa.class)
public class DaoPessoa extends DaoAbstractPessoa<Pessoa>{

	private static final long serialVersionUID = -4663199413291562756L;
	
	public DaoPessoa() {
		super(Pessoa.class);
	}
	
	@Override
	public Pessoa getNewEntity() {
		return new Pessoa();
	}
}
