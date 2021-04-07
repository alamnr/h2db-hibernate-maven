package com.infiniteskills.data.dao.interfaces;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;

public interface Dao<T,ID> {

	public T findById(ID id);
	
	public List<T> findAll();
	
	public T save(T entity);
	
	public void delete(T entity);
	
	public void flush();
	
	public void clear();
	
	public void setSession(Session session);
	
	public void setEntityManager(EntityManager entityManager);
}
