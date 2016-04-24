package br.ueg.openodonto.controle.servico;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import br.ueg.openodonto.dominio.Produto;
import br.ueg.openodonto.dominio.constante.CategoriaProduto;
import br.ueg.openodonto.util.WordFormatter;
import br.ueg.openodonto.visao.ApplicationView;

public class ManageProduto {

	private List<Produto>     produtos;
	private Produto           produto;
	private CategoriaProduto  categoriaColaborador;
	private ApplicationView   view;
	private String            saidaCategoria;
	
	public ManageProduto(List<Produto> produtos,CategoriaProduto categoriaProduto,ApplicationView  view) {
		this.produtos = produtos;
		this.categoriaColaborador =  categoriaProduto;
		this.view = view;
	}
	
	private void buildSaidaCategoria(){
		this.saidaCategoria =    this.view.getProperties().get("formularioSaida") + ":" + "messageCategoria";
	}
	
	public void acaoRemoverProduto(){
		getProdutos().remove(getProduto());
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}	
	
	public CategoriaProduto getCategoriaColaborador() {
		return categoriaColaborador;
	}

	public void setCategoriaColaborador(CategoriaProduto categoriaColaborador) {
		this.categoriaColaborador = categoriaColaborador;
	}

	public void resyncByCategoria(){
		List<Produto> remove = new LinkedList<Produto>();
		for(Produto produto : produtos){
			if(!categoriaColaborador.equals(produto.getCategoria())){
				remove.add(produto);
			}
		}
		this.produtos.removeAll(remove);
		if(remove.size() > 0){
			showTypeConflictMessage("Os não " +
					getCategoriaColaborador() +
					"s deste "+getCategoriaColaborador().getColaborador()+
					" foram desassociados.",remove);
		}
	}
	
	public void associarProdutos(List<Produto> produtos){
		ProdutoComparator comparator = new ProdutoComparator();
		List<Produto> conflic = new LinkedList<Produto>();
		for(Produto produto : produtos){
			if(categoriaColaborador.equals(produto.getCategoria())){
				Collections.sort(this.produtos,comparator);
				if(Collections.binarySearch(this.produtos, produto, comparator) < 0){
					this.produtos.add(produto);
				}
			}else{
				conflic.add(produto);
			}
		}
		if(conflic.size() > 0){
			showTypeConflictMessage("Somente " + getCategoriaColaborador() + "s serão associados.",conflic);
		}
	}
	
	private void showTypeConflictMessage(String message,List<Produto> produtos){
		this.view.showPopUp(message);
		this.view.addLocalDynamicMenssage(message, "saidaPadrao", true);
		for(Produto produto : produtos){
			this.view.addLocalDynamicMenssage("* " +
					WordFormatter.abstractStr(produto.getNome(), 20) +
					" : não foi associado." ,getSaidaCategoria(),false);
		}
	}
	
	private class ProdutoComparator implements Comparator<Produto>{
		@Override
		public int compare(Produto o1, Produto o2) {
			return o1.getCodigo().compareTo(o2.getCodigo());
		}
		
	}
	
	public String getSaidaCategoria() {
		if(saidaCategoria == null){
			buildSaidaCategoria();
		}
		return saidaCategoria;
	}
	
}
