package br.com.rafael.yaquisobra.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.rafael.yaquisobra.connection.ConnectionFactory;
import br.com.rafael.yaquisobra.domain.model.Produto;

public class ProdutoDao {
	
	EntityManager manager;
	CriteriaBuilder criteriaBuilder;
	List<Produto> produtos;
	CriteriaQuery<Produto> query;
	Root<Produto> root;
	
	public void incluir(Produto produto) {
		manager = new ConnectionFactory().getConnection();

		try {
			manager.getTransaction().begin();
			manager.persist(produto);
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}
	}
	
	public List<Produto> listarProdutos() {
		manager = new ConnectionFactory().getConnection();
		produtos = new ArrayList<Produto>();
		try {
			criteriaBuilder = manager.getCriteriaBuilder();
			query = criteriaBuilder.createQuery(Produto.class);
			root = query.from(Produto.class);
			query.select(root);
			query.orderBy(criteriaBuilder.asc(root.get("nome"))); 
			produtos = manager.createQuery(query).getResultList();

		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}
		return produtos;
	}
	
	public boolean procurarProduto(Long codigo) {
		manager = new ConnectionFactory().getConnection();
		boolean existeProduto;
		
		try {
			manager.getTransaction().begin();
			Query query = manager.createQuery("select codigo from "
					+ "Produto p where p.codigo"
					+ " = :codigo");
			
			query.setParameter("codigo", codigo);
			
			
			existeProduto = !query.getResultList().isEmpty();
			manager.getTransaction().commit();
			
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}
		
		return existeProduto;
	}
	
	public void excluir(Produto produto) {
		 manager = new ConnectionFactory().getConnection();
		 
		 try {
			manager.getTransaction().begin();
			manager.remove(manager.merge(produto));
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}
		 
	}
	
	public void alterar(Produto produto) {
		 manager = new ConnectionFactory().getConnection();
		 
		 try {
			manager.getTransaction().begin();
			manager.merge(manager.merge(produto));
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}

	}

}
