package com.infiniteskills.data;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.infiniteskills.data.entities.User;



public class HibernateUtil {
	
	private static final SessionFactory SESSION_FACTORY = buildSessionFactory();
	private static ServiceRegistry serviceRegistry = null;

	private static SessionFactory buildSessionFactory() {
		try {
			
			
			// For hibernate.cfg.hbm file
			// https://stackoverflow.com/a/48434528/4412389 
				//  for hibernate.cfg.xml in hibernate-core-5.4.29.Final.jar  
			
			 serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml") .build();
			 return new Configuration().buildSessionFactory( serviceRegistry );
			 
			
			// For hibernate.ptoperties  file
			
			//return new Configuration().addAnnotatedClass(User.class).buildSessionFactory(new StandardServiceRegistryBuilder().build());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}
	
	public static void shutdown() {
		getSessionFactory().close();
		serviceRegistry.close();
	}
}
