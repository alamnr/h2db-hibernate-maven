package com.guddy.h2.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.SystemException;

import org.hibernate.Session;
import org.junit.Test;

import com.infiniteskills.data.HibernateUtil;
import com.infiniteskills.data.entities.Address;
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
    	User user;
    	Address address;
		try {
			session.beginTransaction();
			address = new Address("addressline1", "addressline2", "California", "Ohio", "1234");
			user = new User(null,"Modan","Chodan",getMyBirthDay(),"mc@chu.cu",new Date(),"gagan",new Date(),"jumal",true,0,address);
			session.save(user);
			session.getTransaction().commit();
			
			/*
			 * session.beginTransaction(); User dbUser = session.get(User.class,
			 * user.getUserId()); user.setFirstName("Jamila"); session.update(user);
			 * session.getTransaction().commit();
			 */
			
			session.refresh(user);
			System.out.println(user.getAge());
			assertNotNull(user.getUserId());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
			HibernateUtil.getSessionFactory().close();
		}
    	
    }
    
    private Date getMyBirthDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1982);
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
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
