package com.infiniteskills.data.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import com.infiniteskills.data.HibernateUtil;
import com.infiniteskills.data.JpaUtil;
import com.infiniteskills.data.dao.interfaces.Dao;

public class AbstractJpaDao<T,ID extends Serializable> implements Dao<T,ID> {

	private Class<T> persistentClass;
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public AbstractJpaDao(){
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void setEntityManager(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	protected EntityManager getEntityManager(){
		if(this.entityManager == null){
			this.entityManager = JpaUtil.getEntityManager();
		}
		return this.entityManager;
	}
	
	public Class<T> getPersistentClass(){
		return persistentClass;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T findById(ID id) {
		return (T) getEntityManager().find(this.getPersistentClass(), id);
	}

	@Override
	public List<T> findAll() {
		return this.findByCriteria();
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion ... criterion){
		//Criteria criteria = this.getSession().createCriteria(this.getPersistentClass());
		
		//CriteriaBuilder builder = this.getSession().getCriteriaBuilder();

        // Create CriteriaQuery
        //CriteriaQuery<T> criteria = builder.createQuery(this.getPersistentClass());

        // Specify criteria root
       // Root<T> root = criteria.from(this.getPersistentClass());
		
		/*
		 * for(Criterion c: criterion){ criteria.add(c); }
		 */	
		
		// return (List<T>) criteria.list();
		
		// To do
		
		return null;
		
	}
	@Override
	public T save(T entity) {
		this.getEntityManager().merge(entity);
		return entity;
	}

	@Override
	public void delete(T entity) {
		this.getEntityManager().remove(entity);
	}

	@Override
	public void flush() {
		this.getEntityManager().flush();
	}

	@Override
	public void clear() {
		this.getEntityManager().clear();
	}

	@Override
	public void setSession(Session session) {
		// TODO Auto-generated method stub
		
	}

	

}
