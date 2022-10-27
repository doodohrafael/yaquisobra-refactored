package br.com.rafael.yaquisobra.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.rafael.yaquisobra.connection.ConnectionFactory;
import br.com.rafael.yaquisobra.domain.model.Usuario;

public class UsuarioDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	private Usuario usuario;
	List<Usuario> usuarios;
	private CriteriaBuilder criteriaBuilder;
	private CriteriaQuery<Usuario> query;
	private Root<Usuario> root;

	public Usuario autenticarUsuario(String cpf, String senha) {
	    manager = new ConnectionFactory().getConnection();
	    usuario = new Usuario();
	    
	    try {
			usuario = manager.createQuery("from Usuario where cpf = :cpf", Usuario.class)
					.setParameter("cpf", cpf).getSingleResult();
	        
	        if (!usuario.getCpf().equals(cpf) || 
	                !usuario.getSenha().equals(senha)) {

	            usuario = new Usuario();
	            return usuario;
	        }
	        return usuario;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        this.manager.close();
	    }
	    usuario = new Usuario();
	    return usuario;
	}
	
	public List<Usuario> listarUsuarios() {
		manager = new ConnectionFactory().getConnection();
		usuarios = new ArrayList<Usuario>();
		try {
			criteriaBuilder = manager.getCriteriaBuilder();
			query = criteriaBuilder.createQuery(Usuario.class);
			root = query.from(Usuario.class);
			query.select(root);
			query.orderBy(criteriaBuilder.asc(root.get("nome")));
			usuarios = manager.createQuery(query).getResultList();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}
		return usuarios;
	}

	public void incluir(Usuario usuario) {
		manager = new ConnectionFactory().getConnection();
		try {
			manager.getTransaction().begin();
			manager.persist(usuario);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			manager.close();
		}		
	}
	
	public void alterar(Usuario usuario) {
		manager = new ConnectionFactory().getConnection();
		try {
			manager.getTransaction().begin();
			manager.merge(manager.merge(usuario));
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			manager.close();
		}
	}
	
	public boolean procurarPorCpf(String cpf) {
		manager = new ConnectionFactory().getConnection();
		boolean existeUsuario;
		try {
			manager.getTransaction().begin();
			Query query = manager
					.createQuery("select cpf from Usuario u where u.cpf = :cpf");
			query.setParameter("cpf", cpf);
			existeUsuario = !query.getResultList().isEmpty();
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}

		return existeUsuario;
	}
	
	public void excluir(Usuario usuario) {
		manager = new ConnectionFactory().getConnection();
		try {
			manager.getTransaction().begin();
			manager.remove(manager.merge(usuario));
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
			throw e;
		} finally {
			manager.close();
		}
	}
}	
