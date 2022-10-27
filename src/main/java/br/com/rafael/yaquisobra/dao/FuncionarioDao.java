package br.com.rafael.yaquisobra.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.rafael.yaquisobra.connection.ConnectionFactory;
import br.com.rafael.yaquisobra.domain.exception.NegocioException;
import br.com.rafael.yaquisobra.domain.model.Funcionario;

public class FuncionarioDao {
	
	private EntityManager manager;
	private CriteriaBuilder criteriaBuilder;
	private Funcionario funcionario;
	private CriteriaQuery<Funcionario> query;
	private Root<Funcionario> root;
	List<Funcionario> funcionarios;

	public void incluir(Funcionario funcionario) {
		manager = new ConnectionFactory().getConnection();
		try {
			manager.getTransaction().begin();
			manager.persist(funcionario);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			manager.close();
		}
	}

	public List<Funcionario> listarFuncionarios() {
		manager = new ConnectionFactory().getConnection();
		funcionarios = new ArrayList<Funcionario>();
		try {
			criteriaBuilder = manager.getCriteriaBuilder();
			query = criteriaBuilder.createQuery(Funcionario.class);
			root = query.from(Funcionario.class);
			query.select(root);
			query.orderBy(criteriaBuilder.asc(root.get("nome")));
			funcionarios = manager.createQuery(query).getResultList();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}
		return funcionarios;
	}

	public boolean procurarPorCpf(String cpf) {
		manager = new ConnectionFactory().getConnection();
		boolean existeFuncionario;
		try {
			manager.getTransaction().begin();
			Query query = manager
					.createQuery("select cpf from " + "Funcionario f where f.cpf" + " = :cpf");
			query.setParameter("cpf", cpf);
			existeFuncionario = !query.getResultList().isEmpty();
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}

		return existeFuncionario;
	}

	public void excluir(Funcionario funcionario) {
		manager = new ConnectionFactory().getConnection();
		try {
			manager.getTransaction().begin();
			manager.remove(manager.merge(funcionario));
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}
	}

	public void alterar(Funcionario funcionario) {
		manager = new ConnectionFactory().getConnection();
		try {
			manager.getTransaction().begin();
			manager.merge(manager.merge(funcionario));
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			manager.close();
		}
	}
	
	public Funcionario funcionarioPorCodigo(Long codigo) {
		manager = new ConnectionFactory().getConnection();
		funcionario = new Funcionario();
		try {
			criteriaBuilder = manager.getCriteriaBuilder();
			query = criteriaBuilder.createQuery(Funcionario.class);
			root = query.from(Funcionario.class);
			query.select(root);
			query.where(criteriaBuilder.equal(root.get("codigo"), codigo));

			funcionario = manager.createQuery(query).getSingleResult();
			if (!funcionario.getId().equals(codigo)) {

				funcionario = new Funcionario();
				return funcionario;
			}
			return funcionario;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException(e.getMessage());
		} finally {
			manager.close();
		}
//		return funcionario;
	}
	
	public Funcionario autenticarFuncionario(String cpf, String senha) {
//		manager = ConnectionFactory.getConnection();
		manager = new ConnectionFactory().getConnection();
		funcionario = new Funcionario();

		try {
			criteriaBuilder = manager.getCriteriaBuilder();
			query = criteriaBuilder.createQuery(Funcionario.class);
			root = query.from(Funcionario.class);
			query.select(root);
			query.where(criteriaBuilder.equal(root.get("cpf"), cpf));

			funcionario = manager.createQuery(query).getSingleResult();

//			if (!funcionario.getCpf().equals(cpf) || 
//					!funcionario.getSenha().equals(senha)) {
//
//				funcionario = new Funcionario();
//				return funcionario;
//			}
			return funcionario;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		funcionario = new Funcionario();
		return funcionario;
	}

}
