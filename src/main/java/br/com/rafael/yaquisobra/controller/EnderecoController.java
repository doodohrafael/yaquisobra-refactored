package br.com.rafael.yaquisobra.controller;

import java.util.List;

import br.com.rafael.yaquisobra.dao.EnderecoDao;
import br.com.rafael.yaquisobra.dao.ProdutoDao;
import br.com.rafael.yaquisobra.domain.model.Endereco;
import br.com.rafael.yaquisobra.domain.model.Produto;

public class EnderecoController {
	
	ProdutoDao dao = new ProdutoDao();
	EnderecoDao daoE = new EnderecoDao();

	public void incluir(Endereco endereco) {
		daoE.incluir(endereco);
	}
	
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
