package br.com.rafael.yaquisobra.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.rafael.yaquisobra.connection.ConnectionFactory;
import br.com.rafael.yaquisobra.domain.model.Endereco;
import br.com.rafael.yaquisobra.domain.model.Produto;

public class EnderecoDao {
	
	public void incluir(Endereco endereco) {
		EntityManager manager = new ConnectionFactory().getConnection();

		try {
			manager.getTransaction().begin();
			manager.persist(endereco);
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}
	}
	
	public List<Produto> listarProdutos() {
		EntityManager manager = new ConnectionFactory().getConnection();
		List<Produto> produtos = new ArrayList<Produto>();
		try {
			CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
			CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
			Root<Produto> root = query.from(Produto.class);
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
		EntityManager manager = new ConnectionFactory().getConnection();
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
		 EntityManager manager = new ConnectionFactory().getConnection();
		 
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
		 EntityManager manager = new ConnectionFactory().getConnection();
		 
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
