package br.ueg.openodonto.persistencia.dao;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import br.com.vitulus.simple.jdbc.annotation.Dao;
import br.com.vitulus.simple.jdbc.orm.old.DaoCrud;
import br.ueg.openodonto.dominio.Colaborador;
import br.ueg.openodonto.dominio.ColaboradorProduto;
import br.ueg.openodonto.dominio.Produto;

@Dao(classe=Produto.class)
public class DaoProduto extends DaoCrud<Produto>{

	private static final long serialVersionUID = 4731561150550714997L;
	
	public DaoProduto() {
		super(Produto.class);
	}	

	@Override
	public Produto getNewEntity() {
		return new Produto();
	}

	public void updateRelationshipColaborador(Colaborador colaborador) throws Exception{
		Long idColaborador = colaborador.getCodigo();
		List<Produto> produtos = colaborador.getProdutos();
		if(produtos != null){
			DaoColaboradorProduto daoCP = new DaoColaboradorProduto();
			List<ColaboradorProduto> cps =  daoCP.getCPRelationshipColaborador(idColaborador);
			for(ColaboradorProduto cp : cps){
				if(!containsCPRelationship(produtos,cp)){
					daoCP.remover(cp);
				}
			}
			for(Produto produto : produtos){
				if(!containsPRelationship(cps,produto)){
					daoCP.inserir(new ColaboradorProduto(idColaborador, produto.getCodigo()));
				}
			}
		}
	}
	
	private boolean containsPRelationship(List<ColaboradorProduto> cps,Produto produto){
		for(Iterator<ColaboradorProduto> iterator = cps.iterator();iterator.hasNext();){
			if(iterator.next().getProdutoIdProduto().equals(produto.getCodigo())){
				return true;
			}
		}
		return false;
	}	
	
	private boolean containsCPRelationship(List<Produto> produtos,ColaboradorProduto cp){
		for(Iterator<Produto> iterator = produtos.iterator();iterator.hasNext();){
			if(iterator.next().getCodigo().equals(cp.getProdutoIdProduto())){
				return true;
			}			
		}
		return false;
	}
	
	public List<Produto> getProdutosRelationshipColaborador(Long idColaborador)throws SQLException {
		DaoColaboradorProduto dao = new DaoColaboradorProduto();
		return dao.getProdutos(idColaborador);
	}	

	
}
