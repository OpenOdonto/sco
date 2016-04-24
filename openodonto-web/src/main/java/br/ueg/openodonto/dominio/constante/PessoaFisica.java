package br.ueg.openodonto.dominio.constante;

import br.com.vitulus.simple.jdbc.Entity;

public interface PessoaFisica<T extends Entity> {
	String getCpf();
}
