package br.com.rafael.yaquisobra.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.rafael.yaquisobra.connection.ConnectionFactory;
import br.com.rafael.yaquisobra.domain.model.Cliente;

public class ClienteDao {
	
	EntityManager manager;
	CriteriaBuilder criteriaBuilder;
	List<Cliente> clientes;
	CriteriaQuery<Cliente> query;
	Root<Cliente> root;
	
	public void incluir(Cliente cliente) {
		manager = new ConnectionFactory().getConnection();

		try {
			manager.getTransaction().begin();
			manager.persist(cliente);
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			e.printStackTrace();;
		} finally {
			manager.close();
		}
	}
	
	public void excluir(Cliente cliente) {
		 EntityManager manager = new ConnectionFactory().getConnection();
		 
		 try {
			manager.getTransaction().begin();
			manager.remove(manager.merge(cliente));
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}
		 
	}
	
	public void alterar(Cliente cliente) {
		 EntityManager manager = new ConnectionFactory().getConnection();
		 
		 try {
			manager.getTransaction().begin();
			manager.merge(manager.merge(cliente));
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}

	}

	public List<Cliente> listarClientes() {
		manager = new ConnectionFactory().getConnection();
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
			CriteriaQuery<Cliente> query = criteriaBuilder.createQuery(Cliente.class);
			Root<Cliente> root = query.from(Cliente.class);
			query.select(root);
			query.orderBy(criteriaBuilder.asc(root.get("nome"))); 
			clientes = manager.createQuery(query).getResultList();

		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return clientes;
	}
	
}
