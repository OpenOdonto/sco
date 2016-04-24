package br.ueg.openodonto.dominio;

import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.ForwardKey;
import br.com.vitulus.simple.jdbc.annotation.Inheritance;
import br.com.vitulus.simple.jdbc.annotation.Table;

@Table(name = "dentistas")
@Inheritance(joinFields = { @ForwardKey(tableField = "id_pessoa", foreginField = "id") })
public class Dentista extends Pessoa {

    private static final long serialVersionUID = 2662858236851341336L;

    @Column(name = "cro")
    private Long cro;

    private String especialidade;

    private String observacao;

    public String getEspecialidade() {
	return especialidade;
    }

    public void setEspecialidade(String especialidade) {
	this.especialidade = especialidade;
    }

    public String getObservacao() {
	return observacao;
    }

    public void setObservacao(String observacao) {
	this.observacao = observacao;
    }

    public Long getCro() {
	return cro;
    }

    public void setCro(Long cro) {
	if (cro == null || cro.equals(0)) {
	    return;
	}
	this.cro = cro;
    }

    @Override
    public String toString() {
	return "Dentista [cro=" + cro + ", especialidade=" + especialidade
		+ ", observacao=" + observacao + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((cro == null) ? 0 : cro.hashCode());
	result = prime * result
		+ ((especialidade == null) ? 0 : especialidade.hashCode());
	result = prime * result
		+ ((observacao == null) ? 0 : observacao.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Dentista other = (Dentista) obj;
	if (cro == null) {
	    if (other.cro != null)
		return false;
	} else if (!cro.equals(other.cro))
	    return false;
	if (especialidade == null) {
	    if (other.especialidade != null)
		return false;
	} else if (!especialidade.equals(other.especialidade))
	    return false;
	if (observacao == null) {
	    if (other.observacao != null)
		return false;
	} else if (!observacao.equals(other.observacao))
	    return false;
	return true;
    }

}
