package br.com.rafael.yaquisobra.controller;

import java.util.List;

import br.com.rafael.yaquisobra.dao.ItemDao;
import br.com.rafael.yaquisobra.domain.model.Item;

public class ItemController {
	
	ItemDao dao = new ItemDao();

	public void incluir(Item item) {
		dao.incluir(item);
	}
	
	public List<Item> listarItems() {
		return dao.listarItems();
	}
	
	public void excluir(Item item) {
		dao.excluir(item);
	}
	
	public void alterar(Item item) {
		dao.alterar(item);
	}
	
}
