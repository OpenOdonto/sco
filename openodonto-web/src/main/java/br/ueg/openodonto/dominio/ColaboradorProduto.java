package br.ueg.openodonto.dominio;

import br.com.vitulus.simple.jdbc.Entity;
import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.ForwardKey;
import br.com.vitulus.simple.jdbc.annotation.Id;
import br.com.vitulus.simple.jdbc.annotation.Relationship;
import br.com.vitulus.simple.jdbc.annotation.Table;
import br.com.vitulus.simple.jdbc.annotation.type.Cardinality;
import br.com.vitulus.simple.jdbc.annotation.type.IdIncrementType;

@Table(name = "colaboradores_produtos")
public class ColaboradorProduto implements Entity{

	private static final long serialVersionUID = 7209381285412335343L;

	@Column(name="id")
	@Id(autoIncrement = IdIncrementType.IDENTITY)
	private Long codigo;
	
	@Id
	@Column(name="fk_pessoa")
	@Relationship(cardinality=Cardinality.OneToOne,joinFields={@ForwardKey(tableField="id_pessoa",foreginField="fk_pessoa")})
	private Long colaboradorIdPessoa;
	
	@Id
	@Column(name="fk_produto")
	@Relationship(cardinality=Cardinality.OneToOne,joinFields={@ForwardKey(tableField="id_produto",foreginField="fk_produto")})
	private Long produtoIdProduto;
		
	public ColaboradorProduto(Long colaboradorIdPessoa, Long produtoIdProduto) {
		this.colaboradorIdPessoa = colaboradorIdPessoa;
		this.produtoIdProduto = produtoIdProduto;
	}

	public ColaboradorProduto() {
	}
	
	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getColaboradorIdPessoa() {
		return colaboradorIdPessoa;
	}

	public void setColaboradorIdPessoa(Long colaboradorIdPessoa) {
		this.colaboradorIdPessoa = colaboradorIdPessoa;
	}

	public Long getProdutoIdProduto() {
		return produtoIdProduto;
	}

	public void setProdutoIdProduto(Long produtoIdProduto) {
		this.produtoIdProduto = produtoIdProduto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime
				* result
				+ ((colaboradorIdPessoa == null) ? 0 : colaboradorIdPessoa
						.hashCode());
		result = prime
				* result
				+ ((produtoIdProduto == null) ? 0 : produtoIdProduto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColaboradorProduto other = (ColaboradorProduto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (colaboradorIdPessoa == null) {
			if (other.colaboradorIdPessoa != null)
				return false;
		} else if (!colaboradorIdPessoa.equals(other.colaboradorIdPessoa))
			return false;
		if (produtoIdProduto == null) {
			if (other.produtoIdProduto != null)
				return false;
		} else if (!produtoIdProduto.equals(other.produtoIdProduto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ColaboradorProduto [codigo=" + codigo
				+ ", colaboradorIdPessoa=" + colaboradorIdPessoa
				+ ", produtoIdProduto=" + produtoIdProduto + "]";
	}	
	
}
