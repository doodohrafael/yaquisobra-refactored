package br.com.rafael.yaquisobra.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.rafael.yaquisobra.connection.ConnectionFactory;
import br.com.rafael.yaquisobra.domain.model.FormaPagamento;
import br.com.rafael.yaquisobra.domain.model.Item;
import br.com.rafael.yaquisobra.domain.model.Venda;

public class VendaDao {
	
	EntityManager manager;
	CriteriaBuilder criteriaBuilder;
	List<Venda> vendas;
	CriteriaQuery<Venda> query;
	Root<Venda> root;

	public void incluir(Venda venda) {
		manager = new ConnectionFactory().getConnection();

		try {
			manager.getTransaction().begin();
			manager.persist(venda);
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			manager.close();
		}
	}

	public List<Venda> listarVendas() {
		manager = new ConnectionFactory().getConnection();
		vendas = new ArrayList<Venda>();
		try {
			criteriaBuilder = manager.getCriteriaBuilder();
			query = criteriaBuilder.createQuery(Venda.class);
			root = query.from(Venda.class);
			query.select(root);
			query.orderBy(criteriaBuilder.desc(root.get("dataHoraCadastro")));
			vendas = manager.createQuery(query).getResultList();
			
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return vendas;
	}

	public boolean procurarVenda(Long codigo) {
		manager = new ConnectionFactory().getConnection();
		boolean existeVenda;

		try {
			manager.getTransaction().begin();
			Query query = manager.createQuery("select codigo from " + "Venda v where v.codigo" + " = :codigo");

			query.setParameter("codigo", codigo);

			existeVenda = query.getResultList().isEmpty();
			manager.getTransaction().commit();

		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}

		return existeVenda;
	}

	public void excluir(Venda venda) {
		manager = new ConnectionFactory().getConnection();

		try {
			manager.getTransaction().begin();
			manager.remove(manager.merge(venda));
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}

	}

	public void alterar(Venda venda) {
		manager = new ConnectionFactory().getConnection();

		try {
			manager.getTransaction().begin();
			manager.merge(manager.merge(venda));
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}

	}

	public List<Venda> listarVendasPorData(Date dataInicial, Date dataFinal) {
		manager = new ConnectionFactory().getConnection();
		List<Venda> vendas = null;
		try {
			criteriaBuilder = manager.getCriteriaBuilder();
			query = criteriaBuilder.createQuery(Venda.class);
			vendas = manager.createQuery(query).getResultList();

		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}
		return vendas;
	}
	
	public List<FormaPagamento> listarFormaPagamentos() {
		manager = new ConnectionFactory().getConnection();
		List<FormaPagamento> formaPagamentos = new ArrayList<>();
		try {
			CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
			CriteriaQuery<FormaPagamento> query = criteriaBuilder.createQuery(FormaPagamento.class);
			Root<FormaPagamento> root = query.from(FormaPagamento.class);
			query.select(root);
			query.orderBy(criteriaBuilder.desc(root.get("descricao")));
			formaPagamentos = manager.createQuery(query).getResultList();

		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}
		return formaPagamentos;
	}
	
	public void incluirItem(Item item) {
		manager = new ConnectionFactory().getConnection();

		try {
			manager.getTransaction().begin();
			manager.persist(item);
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			manager.close();
		}
	}
	
}
