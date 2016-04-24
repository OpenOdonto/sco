package br.com.simple.jdbc.dao;

import br.ueg.openodonto.dominio.Colaborador;
import br.ueg.openodonto.dominio.Produto;
import br.ueg.openodonto.persistencia.dao.DaoColaboradorProduto;




public class ManterProdutoTest {

	public static void main(String[] args) throws Exception{
		TestSetup.setup();
		/*
		EntityManager<Produto> dao = DaoFactory.getInstance().getDao(Produto.class);
		Produto produto = new Produto();
		produto.setCategoria(CategoriaProduto.PRODUTO);
		produto.setDescricao("Leite");
		produto.setNome("Leite");
		dao.alterar(produto);
		*/
		/*
		EntityManager<Colaborador> dao = DaoFactory.getInstance().getDao(Colaborador.class);
		Colaborador colaborador = new Colaborador();
		colaborador.setCategoria(CategoriaProduto.PRODUTO);
		colaborador.setDataCadastro(new Date(System.currentTimeMillis()));
		colaborador.setEstado(TiposUF.GO);
		colaborador.setCidade("GOIANIA");
		colaborador.setNome("JOSE MARIA");		
		dao.alterar(colaborador);
		*/
		/*
		EntityManager<ColaboradorProduto> dao = DaoFactory.getInstance().getDao(ColaboradorProduto.class);
		ColaboradorProduto colaboradorProduto = new ColaboradorProduto();
		colaboradorProduto.setColaboradorIdPessoa(4L);
		colaboradorProduto.setProdutoIdProduto(3L);
		dao.alterar(colaboradorProduto);
		*/
		
		DaoColaboradorProduto dao = new DaoColaboradorProduto();
		
		for(Produto produto : dao.getProdutos(4L)){
			System.out.println(produto);
		}
		
		for(Colaborador colaborador : dao.getColaboradores(2L)){
			System.out.println(colaborador);
		}
		
		
		/*
		Colaborador colaborador = new Colaborador();
		colaborador.setNome("JOSE MARIA");
		Produto produto = new Produto();
		produto.setNome("Fruta");
		Set<Map<String,Object>> results = new HashSet<Map<String,Object>>(dao.getUntypeProdutos(colaborador,produto));
		for(Map<String,Object> i : results){
			System.out.println(i);
		}
		*/
		
		/*
		Produto produto = new Produto();
		produto.setNome("Fruta");
		Colaborador colaborador = new Colaborador();
		colaborador.setNome("JOSE MARIA");
		Set<Map<String,Object>> results = new HashSet<Map<String,Object>>(dao.getUntypeColaboradores(produto,colaborador));
		for(Map<String,Object> i : results){
			System.out.println(i);
		}
		*/
		
	
	}

}
