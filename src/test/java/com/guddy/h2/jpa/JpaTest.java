package com.guddy.h2.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.infiniteskills.data.entities.Account;
import com.infiniteskills.data.entities.Bank;
import com.infiniteskills.data.entities.Transaction;

public class JpaTest {
	
	 static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2");
	 
	 @BeforeClass
	 public static void initialize() {
		
		 EntityManager em = emf.createEntityManager(); 
		 try {
			 	
			 	em.getTransaction().begin();
				for(int i=0;i<5;i++) {
					Bank bank  = createNewBank(i);
					em.persist(bank);
					
				}
				em.getTransaction().commit();
			} catch (Exception e) {
				em.getTransaction().rollback();
				throw new RuntimeException(e);
			} finally {
				em.close();
			}
	 }
	 
	 @AfterClass
	 public static void tearDown() {
		 emf.close();
	 }
	 
	 @Test
	 public void testJpa() {
		 EntityManager em = emf.createEntityManager();
		 
		 
		 try {
			em.getTransaction().begin();
			 
			 Bank  bank = createNewBank(5);
			 em.persist(bank);
			 em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
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


}
