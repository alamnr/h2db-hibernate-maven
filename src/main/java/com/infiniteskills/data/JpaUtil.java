package com.infiniteskills.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JpaUtil {
	
	static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2");
	 
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
