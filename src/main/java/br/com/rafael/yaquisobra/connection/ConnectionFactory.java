package br.com.rafael.yaquisobra.connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static javax.persistence.Persistence.createEntityManagerFactory;

public class ConnectionFactory {

	private EntityManagerFactory entityManagerFactory = 
			createEntityManagerFactory("yaquisobra");

	public EntityManager getConnection() {
		return entityManagerFactory.createEntityManager();
	}

}
