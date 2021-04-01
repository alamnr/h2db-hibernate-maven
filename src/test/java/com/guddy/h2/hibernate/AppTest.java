package com.guddy.h2.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import com.infiniteskills.data.HibernateUtil;

import com.infiniteskills.data.entities.User;




/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    
    @Test
    public void openCloseSession() {
    	Session session =HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	assertEquals(true,session.isOpen() );
    	
    	session.close();
    	
    	assertEquals(session.isOpen(), false);   	
    	
    }
    
    @Test
    public void  testUserPersistence()
    {
    	
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	User user = new User(null,"Modan","Chodan",new Date(),"mc@chu.cu",new Date(),"gagan",new Date(),"jumal");
    	session.save(user);
    	session.getTransaction().commit();
    	session.close();
    	
    	assertNotNull(user.getUserId());
    }
    
    @Test
    public void persistAccountType() throws IllegalStateException, SystemException {
    	
		/*
		 * AccountType accountType1 = new AccountType(null, "muksed", new Date(), new
		 * Date(), "vuksed"); AccountType accountType2 = new AccountType(null, "chudna",
		 * new Date(), new Date(), "madna"); List<AccountType> accList = new
		 * ArrayList<>();
		 * 
		 * try(Session session = HibernateUtil.getSessionFactory().openSession()) {
		 * session.beginTransaction();
		 * 
		 * session.save(accountType1); session.save(accountType2);
		 * 
		 * session.getTransaction().commit();
		 * 
		 * } catch (Exception e) { throw e; }
		 * 
		 * System.out.println(accList.size());
		 * 
		 * try(Session session = HibernateUtil.getSessionFactory().openSession()) {
		 * 
		 * accList = session.createQuery("from AccountType",AccountType.class).list();
		 * 
		 * } catch (Exception e) { throw e; }
		 * 
		 * System.out.println(accList.size()); assertEquals(accList.size(),2);
		 */
    	
    }
}
