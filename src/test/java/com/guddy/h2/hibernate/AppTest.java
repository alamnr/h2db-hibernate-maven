package com.guddy.h2.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.infiniteskills.data.HibernateUtil;
import com.infiniteskills.data.entities.Account;
import com.infiniteskills.data.entities.Address;
import com.infiniteskills.data.entities.Bank;
import com.infiniteskills.data.entities.Budget;
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
    
    
    private static final SessionFactory  sessionFactory = HibernateUtil.getSessionFactory();
    
    @Test
    public void openCloseSession() {
    	Session session =sessionFactory.openSession();
    	session.beginTransaction();
    	assertEquals(true,session.isOpen() );
    	
    	session.close();
    	
    	assertEquals(session.isOpen(), false);   	
    	
    }
    
    @Test
    public void testBankPersistence() {
		 
		Bank bank = null;
		Address address;
		address =  new Address("addressline1", "addressline2", "California", "Ohio", "1234");
		 Map<String,String> mapValueType = new HashMap<>();
		  mapValueType.put("Manager", "Jagan"); mapValueType.put("Neta", "Samvu");
		 
		bank = new Bank(null, "Shaoa Bank", address, false, new Date(), "chodna", new Date(), "madna",
				mapValueType);	
		
		Session session = sessionFactory.openSession();
		
		
		try {
			session.beginTransaction();
			/*
			 * List<String> collect = new ArrayList<>(); collect.add("A");
			 * collect.add("Computer"); collect.add("Portal"); collect.add("for");
			 * collect.add("Geeks");			 */			
			
			
			session.save(bank);
			
			session.getTransaction().commit();		
			
		} catch (Exception e) {
			session.beginTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
			
		}	
		
		assertNotNull(bank.getBankId());
	}
    
    @Test
    public void  testUserPersistence()
    {
    	
    	Session session = sessionFactory.openSession();
    	User user;
    	Address address;
    	Credential credential;
    	
    	address = new Address("addressline1", "addressline2", "California", "Ohio", "1234");
		List<Address> addresses = new ArrayList<>();
		Address address1 =new Address("addressline3", "addressline4", "Utah", "UT", "6789");
		addresses.add(address1);
		addresses.add(address);
		credential = new Credential();
		credential.setUserName("chodna");
		credential.setPassword("madna");
		user = createUser("Ganesh", "Thappa", "ganesh@gmail.com");
		credential.setUser(user);
           	
		try {
			session.beginTransaction();	
			
			//session.save(user);
			session.save(credential);
						   	
			session.getTransaction().commit();
			
			/*
			 * session.beginTransaction(); User dbUser = session.get(User.class,
			 * user.getUserId()); user.setFirstName("Jamila"); session.update(user);
			 * session.getTransaction().commit();
			 */
			
			//session.refresh(user);
			
			 Credential dbCredential = session.get(Credential.class, credential.getCredentialId());
		
			System.out.println(dbCredential.getPassword());
			System.out.println(dbCredential.getUser().getEmailAddress());
			assertNotNull(credential.getCredentialId());
			
		} catch (Exception e) {
			session.beginTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
			
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
    	Session session= sessionFactory.openSession();
    	User user = createUser("Modan","Chohan","modan@gmail.com");
    	Credential credential = new Credential();
    	
    	credential.setPassword("password");
    	credential.setUserName("John");
    	
    	user.setCredential(credential);
    	
    	System.out.println(session.contains(credential));
    	System.out.println(session.contains(user));
    	//credential.setUser(user);
    	Long credId;
    	try {
    		
			session.beginTransaction();
			session.save(user);
			

	    	System.out.println(session.contains(credential));
	    	System.out.println(session.contains(user));
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
			session = sessionFactory.openSession();
			
			Credential  credential2 = session.get(Credential.class, credId);
			System.out.println(credential2.getUser().getEmailAddress());
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		} finally {
			session.close();			
		}
    	
    	try {
    		session = sessionFactory.openSession();
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
    
    private User createUser(String fname,String lname,String email ) {
    	User user = new User();
    	user.setFirstName(fname);
    	user.setLastName(lname);
    	user.setBirthDate(getMyBirthDay());
    	user.setEmailAddress(email);
    	user.setCreatedDate(new Date());
    	user.setCreatedBy("gagan");
    	user.setLastUpdatedDate(new Date());
    	user.setLastUpdatedBy("jumal");
    	user.setValid(true);
    	user.setAge(35);
    	
    	return user;
    }
	
	
	@Test
	public void persistAcountAndTransaction() {
		
		Session session=null;
		String accountName = "Savings Account";
		Account account = createNewAccount(accountName);
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			account.getTransactions().add(createNewBeltPurchase(account));
			account.getTransactions().add(createNewShoePurchase(account));
			//session.save(account);
			session.save(account.getTransactions().get(0));
			session.save(account.getTransactions().get(1));
			session.getTransaction().commit();
			
			Transaction transaction = session.get(Transaction.class, account.getTransactions().get(0).getTransactionId());
			
			System.out.println(transaction.getAccount().getAccountName());
			assertEquals(accountName, transaction.getAccount().getAccountName());
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
			
		}
		
		
	}
	
	@Test
	public void testAccountAndUserPersistence() {
		
		Session session = sessionFactory.openSession();
		Account account1 = createNewAccount("Current Account");
		Account account2 = createNewAccount("fixed Deposit Account");
		User user1 = createUser("Modan","Chohan","modan@gmail.com");
		User user2 = createUser("Dabi","Khabir","dabir@gmail.com");
		//account1.setUsers(new HashSet<User>(Arrays.asList(user1,user2)));
		//account2.setUsers(new HashSet<User>(Arrays.asList(user1,user2)));
		account1.getUsers().add(user1);
		account1.getUsers().add(user2);
		user1.getAccounts().add(account1);
		user2.getAccounts().add(account1);
		//user1.setAccounts(new HashSet<Account>(Arrays.asList(account1,account2)));
		//user2.setAccounts(new HashSet<Account>(Arrays.asList(account1,account2)));
		
		
		account2.getUsers().add(user1);
		account2.getUsers().add(user2);
		user1.getAccounts().add(account2);
		user2.getAccounts().add(account2);
		
		
		try {
			
			session.beginTransaction();
			//session.save(account1);
			//session.save(account2);
			
			session.save(user1);
			session.save(user2);
			
			session.getTransaction().commit();
			
			//Account dbAccount = session.get(Account.class, account1.getAccountId());
			//assertEquals(2, dbAccount.getUsers().size());
			
			List<Account> accounts = session.createQuery("from Account").list();
			assertNotNull(accounts);
			
			
			
			for(Account account: accounts) {
				assertNotNull(account.getUsers().size());
				System.out.println(account.getUsers().size());
			}
			
			List<User> users = session.createQuery("from User").list();
			assertNotNull(users);
			
			
			
			for(User user: users) {
				assertNotNull(user.getAccounts().size());
				System.out.println(user.getAccounts().size());
			}
			
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		
	}
	
	@Test
	public void testBudgetPersistentJoinTableOperation() {
		Session session = sessionFactory.openSession();
		Account account = createNewAccount("Savings Account");
		Budget budget = new Budget();
		budget.setGoalAmount(new BigDecimal(1000));
		budget.setName("Emergency Fund");
		budget.setPeriod("Yearly");
		budget.getTransactions().add(createNewBeltPurchase(account));
		budget.getTransactions().add(createNewShoePurchase(account));
		
		try {
			session.beginTransaction();
			
			session.save(budget);
			
			session.getTransaction().commit();
			
			assertNotNull(budget.getBudgetId());
		} catch (HibernateException e) {
			session.getTransaction().commit();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}
	
	@Test(expected  = ObjectNotFoundException.class)
	public void testLoadApiError() {
		Session session = sessionFactory.openSession();
		
		try {			
					
			Bank bank1 = session.load(Bank.class, 23L);
			
			System.out.println("proxy first and upon invoke execute the query");
			
			System.out.println(bank1.getName());
			
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			session.close();
		}
	}
	
	
	@Test(expected  = NullPointerException.class)
	public void testGetApiError() {
		Session session = sessionFactory.openSession();
		
		try {
			
			Bank bank = session.get(Bank.class, 13L);
			System.out.println("executed query first");
			
			System.out.println(bank.getName());		
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetAndLoadApi() {
		Session session = sessionFactory.openSession();
		
		try {
			
			Bank bank = session.get(Bank.class, 1L);
			System.out.println("executed query first");
			assertNotNull(bank);
			System.out.println(bank.getName());
			
			Bank bank1 = session.load(Bank.class, 2L);
			
			System.out.println("proxy first and upon invoke execute the query");
			
			System.out.println(bank1.getName());
			assertNotNull(bank1);
			
		} catch (Exception e) {
			throw e;
			
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
	
	private Account createNewAccount(String name) {
		
		Account account = new Account();
		account.setAccountName(name);
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
	
	@BeforeClass
	public static void prepareDb() {
		Session session = sessionFactory.openSession();
		
		try {
			session.beginTransaction();
			for(int i=0;i<5;i++) {
				Bank bank  = createNewBank(i);
				session.save(bank);
				
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}
	
	private static Bank createNewBank(int i) {
		Bank bank = new Bank();
		bank.setCreatedBy("creator-"+i);
		bank.setCreatedDate(new Date());
		bank.setInternational(true);
		bank.setLastUpdatedDate(new Date());
		bank.setName("Bank-"+i);
		bank.setUpdatedBy("updater-"+i);
		return bank;
	}

	@AfterClass
	public static void tearDownAll() {
		sessionFactory.close();
	}
}


