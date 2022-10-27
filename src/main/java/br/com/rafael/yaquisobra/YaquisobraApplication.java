package br.com.rafael.yaquisobra;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//@SpringBootApplication
public class YaquisobraApplication {

	public static void main(String[] args) {
// 		SpringApplication.run(YaquisobraApplication.class, args);

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("yaquisobra");
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction();

	}
}
