package br.ueg.openodonto.dominio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.Enumerator;
import br.com.vitulus.simple.jdbc.annotation.ForwardKey;
import br.com.vitulus.simple.jdbc.annotation.Inheritance;
import br.com.vitulus.simple.jdbc.annotation.Relationship;
import br.com.vitulus.simple.jdbc.annotation.Table;
import br.com.vitulus.simple.jdbc.annotation.type.Cardinality;
import br.com.vitulus.simple.jdbc.annotation.type.EnumValueType;
import br.ueg.openodonto.dominio.constante.CategoriaProduto;


@Table(name = "colaboradores")
@Inheritance(joinFields = { @ForwardKey(tableField = "id_pessoa", foreginField = "id") })
public class Colaborador extends Pessoa {

	private static final long serialVersionUID = -5343524828130673767L;

	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@Column(name = "observacao")
	private String observacao;

	@Column(name = "categoria")
	@Enumerator(type = EnumValueType.ORDINAL)
	private CategoriaProduto categoria;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "cnpj")
	private String cnpj;

	@Relationship(cardinality = Cardinality.OneToMany)
	private List<Produto> produtos;

	public Colaborador(CategoriaProduto categoria) {
		this();
		this.categoria = categoria;
	}
	
	public Colaborador(Long codigo) {
		this();
		setCodigo(codigo);
	}		
	
	public Colaborador() {
		this.produtos = new ArrayList<Produto>();
	}
	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public CategoriaProduto getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaProduto categoria) {
		this.categoria = categoria;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public String toString() {
		return "Colaborador [categoria=" + categoria + ", cnpj=" + cnpj
				+ ", cpf=" + cpf + ", dataCadastro=" + dataCadastro
				+ ", observacao=" + observacao + ", produtos=" + produtos
				+ ", getCidade()=" + getCidade() + ", getCodigo()="
				+ getCodigo() + ", getEmail()=" + getEmail()
				+ ", getEndereco()=" + getEndereco() + ", getEstado()="
				+ getEstado() + ", getNome()=" + getNome() + ", getTelefone()="
				+ getTelefone() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result
				+ ((produtos == null) ? 0 : produtos.hashCode());
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
		Colaborador other = (Colaborador) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (produtos == null) {
			if (other.produtos != null)
				return false;
		} else if (!produtos.equals(other.produtos))
			return false;
		return true;
	}
}
