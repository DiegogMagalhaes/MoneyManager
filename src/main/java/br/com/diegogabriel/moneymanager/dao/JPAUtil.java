package br.com.diegogabriel.moneymanager.dao;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class JPAUtil implements Serializable {

	private static final long serialVersionUID = 2044325755597007666L;
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("moneymanager");

	public JPAUtil() {
		// TODO Auto-generated constructor stub
	}
	
    @Produces
    @RequestScoped
    public static EntityManager getEntityManager() {
    	return emf.createEntityManager();
    }
    
    public void close(@Disposes EntityManager em) {
        em.close();
    }

	
}
