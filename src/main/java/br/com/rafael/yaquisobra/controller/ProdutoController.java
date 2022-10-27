package br.com.rafael.yaquisobra.controller;

import java.util.List;

import br.com.rafael.yaquisobra.dao.ProdutoDao;
import br.com.rafael.yaquisobra.domain.model.Produto;

public class ProdutoController {
	
	ProdutoDao dao = new ProdutoDao();

	public void incluir(Produto produto) {
		dao.incluir(produto);
	}
	
	public List<Produto> listarProdutos() {
		return dao.listarProdutos();
	}
	
	public void excluir(Produto produto) {
		dao.excluir(produto);
	}
	
	public void alterar(Produto produto) {
		dao.alterar(produto);
	}
	
	public boolean procurarProduto(Long codigo) {
		return dao.procurarProduto(codigo);
	}
	
}
