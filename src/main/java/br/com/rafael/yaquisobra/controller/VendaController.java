package br.com.rafael.yaquisobra.controller;

import java.util.Date;
import java.util.List;

import br.com.rafael.yaquisobra.dao.EnderecoDao;
import br.com.rafael.yaquisobra.dao.VendaDao;
import br.com.rafael.yaquisobra.domain.model.Endereco;
import br.com.rafael.yaquisobra.domain.model.FormaPagamento;
import br.com.rafael.yaquisobra.domain.model.Item;
import br.com.rafael.yaquisobra.domain.model.Venda;

public class VendaController {
	
	VendaDao dao = new VendaDao();

	public void incluir(Venda venda) {
		dao.incluir(venda);
	}
	
	public List<Venda> listarVendas() {
		return dao.listarVendas();
	}
	
	public void excluir(Venda venda) {
		dao.excluir(venda);
	}
	
	public void alterar(Venda venda) {
		dao.alterar(venda);
	}
	
	//Endereco
	EnderecoDao daoE = new EnderecoDao();
	public void incluir(Endereco endereco) {
		daoE.incluir(endereco);
	}
	
	public List<Venda> listarVendasPorData(Date dataInicial, Date dataFinal) {
		return dao.listarVendasPorData(dataInicial, dataFinal);
	}
	
	// FormaPagamento
	public List<FormaPagamento> listarFormaPagamentos() {
		return dao.listarFormaPagamentos();
	}
	
	public void incluirItem(Item item) {
		dao.incluirItem(item);
	}
	
}
