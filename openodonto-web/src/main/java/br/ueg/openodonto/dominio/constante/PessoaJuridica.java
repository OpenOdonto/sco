package br.ueg.openodonto.dominio.constante;

import br.com.vitulus.simple.jdbc.Entity;

public interface PessoaJuridica<T extends Entity> {
	String getCnpj();
}
