package com.guddy.h2.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import com.infiniteskills.data.HibernateUtil;
import com.infiniteskills.data.entities.Account;
import com.infiniteskills.data.entities.Address;
import com.infiniteskills.data.entities.Bank;
import com.infiniteskills.data.entities.Credential;
import com.infiniteskills.data.entities.Transaction;
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
    public void testBankPersistence() {
		 
		Bank bank = null;
		Address address;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			address =  new Address("addressline1", "addressline2", "California", "Ohio", "1234");
			/*
			 * List<String> collect = new ArrayList<>(); collect.add("A");
			 * collect.add("Computer"); collect.add("Portal"); collect.add("for");
			 * collect.add("Geeks");
			 */
			
			  Map<String,String> mapValueType = new HashMap<>();
			  mapValueType.put("Manager", "Jagan"); mapValueType.put("Neta", "Samvu");
			 
			
			bank = new Bank(null, "Shaoa Bank", address, false, new Date(), "chodna", new Date(), "madna",
					mapValueType);
			session.save(bank);
			session.getTransaction().commit();
			
			
		
			
		}catch (Exception e) {
			session.beginTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
			//HibernateUtil.getSessionFactory().close();
		}	
		
		assertNotNull(bank.getBankId());
	}
    
    @Test
    public void  testUserPersistence()
    {
    	
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	User user;
    	Address address;
    	Credential credential;
		try {
			session.beginTransaction();
			address = new Address("addressline1", "addressline2", "California", "Ohio", "1234");
			List<Address> addresses = new ArrayList<>();
			Address address1 =new Address("addressline3", "addressline4", "Utah", "UT", "6789");
			addresses.add(address1);
			addresses.add(address);
			credential = new Credential();
			credential.setUserName("chodna");
			credential.setPassword("madna");
			user = new User(null,"Modan","Chodan",getMyBirthDay(),"mc@chu.cu",new Date(),"gagan",new Date(),
							"jumal",true,0,addresses,credential);
			credential.setUser(user);
			
			session.save(user);
			//session.save(credential);
			session.getTransaction().commit();
			
			/*
			 * session.beginTransaction(); User dbUser = session.get(User.class,
			 * user.getUserId()); user.setFirstName("Jamila"); session.update(user);
			 * session.getTransaction().commit();
			 */
			
			//session.refresh(user);
			
			 Credential credential1 = session.get(Credential.class, credential.getCredentialId());
		
			System.out.println(credential1.getPassword());
			System.out.println(credential1.getUser().getEmailAddress());
			assertNotNull(credential.getCredentialId());
			
		} catch (Exception e) {
			session.beginTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
			//HibernateUtil.getSessionFactory().close();
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
    public void testOwningEntityCascadeOperation() {
    	Session session = null; 
    	User user = createUser();
    	Credential credential = new Credential();
    	
    	credential.setPassword("password");
    	credential.setUserName("John");
    	
    	user.setCredential(credential);
    	//credential.setUser(user);
    	Long credId;
    	try {
    		session= HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			
			assertNotNull(user.getCredential().getCredentialId());		
		
			
			credId = user.getCredential().getCredentialId();		
			
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new  RuntimeException(e);
		} finally {
			session.close();
		}
    	
    	try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			Credential  credential2 = session.get(Credential.class, credId);
			System.out.println(credential2.getUser().getEmailAddress());
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
    	
    	try {
    		session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.remove(user);
			session.getTransaction().commit();			
			assertNull(session.get(Credential.class, credId));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
    	
    	
    }
    
    private User createUser() {
    	User user = new User();
    	user = new User(null,"Modan","Chodan",getMyBirthDay(),"mc@chu.cu",new Date(),"gagan",new Date(),
				"jumal",true,0,null,null);
    	return user;
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
	
	@Test
	public void persistAcountAndTransaction() {
		
		Session session=null;
		Account account = createNewAccount();
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			account.getTransactions().add(createNewBeltPurchase(account));
			account.getTransactions().add(createNewShoePurchase(account));
			session.save(account);
			session.getTransaction().commit();
			
			Transaction transaction = session.get(Transaction.class, account.getTransactions().get(0).getTransactionId());
			
			System.out.println(transaction.getAccount().getAccountName());
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		
		
	}
	
	
	private Transaction createNewBeltPurchase(Account account) {
		
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setTitle("Dress Belt");
		transaction.setAmount(new BigDecimal(50.00));
		transaction.setClosingBalance(new BigDecimal(0.00));
		transaction.setCreatedBy("modna");
		transaction.setCreatedDate(new Date());
		transaction.setInitialBalance(new BigDecimal(0.00));
		transaction.setLastUpdatedBy("chodna");
		transaction.setLastUpdatedDate(new Date());
		transaction.setNotes("new dress belt");
		transaction.setTransactionType("Debit");
		
		return transaction;	
	}
	
	private Transaction createNewShoePurchase(Account account) {
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setTitle("Work Shoes");
		transaction.setAmount(new BigDecimal(100.00));
		transaction.setClosingBalance(new BigDecimal(0.00));
		transaction.setCreatedBy("modna");
		transaction.setCreatedDate(new Date());
		transaction.setInitialBalance(new BigDecimal(0.00));
		transaction.setLastUpdatedBy("chodna");
		transaction.setLastUpdatedDate(new Date());
		transaction.setNotes("new work shoes");
		transaction.setTransactionType("Debit");
		
		return transaction;	
	}
	
	private Account createNewAccount() {
		
		Account account = new Account();
		account.setAccountName("Savings Account");
		account.setCeatedDate(new Date());
		account.setCloseDate(new Date());
		account.setCreatedBy("Hori");
		account.setCurrentBalance(new BigDecimal(1000.00));
		account.setInitialBalance(new BigDecimal(1200.00));
		account.setLastUpdateDate(new Date());
		account.setLastUpdatedBy("Rani");
		account.setOpenDate(new Date());
		return account;	
		
	}
}
