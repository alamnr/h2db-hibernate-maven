package com.guddy.h2.jpa;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.infiniteskills.data.entities.Account;
import com.infiniteskills.data.entities.Address;
import com.infiniteskills.data.entities.Bank;
import com.infiniteskills.data.entities.Bond;
import com.infiniteskills.data.entities.Currency;
import com.infiniteskills.data.entities.Investment;
import com.infiniteskills.data.entities.Market;
import com.infiniteskills.data.entities.Portfolio;
import com.infiniteskills.data.entities.Stock;
import com.infiniteskills.data.entities.Transaction;
import com.infiniteskills.data.entities.Ids.CurrencyId;

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
	 
	 @Test
	 public void testcompoundPrimaryKeyMapping() {
		 
		 EntityManager em = emf.createEntityManager();
		 
		 try {
			em.getTransaction().begin();
			 Currency currency = new Currency();
			 currency.setCountryName("USA");
			 currency.setName("Dollar");
			 currency.setSymbol("$");
			 em.persist(currency);		 
			 em.getTransaction().commit();
			 
			 em.getTransaction().begin();
			 Currency dbCurrency = em.find(Currency.class, new CurrencyId("Dollar","USA"));
			 System.out.println(dbCurrency.getName());
			 assertEquals("Dollar", dbCurrency.getName());
			 em.getTransaction().commit();
			 
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	 }
	 
	 @Test
	 public void testCompoundJoinColumn() {
		 EntityManager em = emf.createEntityManager();
		 
		 try {
			em.getTransaction().begin();
			 Currency currency = new Currency();
			 currency.setCountryName("England");
			 currency.setName("Pound");
			 currency.setSymbol("$");
			 
			 Market market = new Market();
			 market.setMarketName("Share Market");
			 market.setCurrency(currency);
			 em.persist(market);
			 
			 Market dbMarket = em.find(Market.class, market.getMarketId());
			 System.out.println(market.getCurrency().getName());
			 
			 em.getTransaction().commit();
			 
		
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	 }
	 
	 @Test
	 public void testMappedSuperClass() throws ParseException {
		 EntityManager em = emf.createEntityManager();
		 
		 try {
			em.getTransaction().begin();
			 Stock stock = createStock();
			 Bond bond = createBond();
			 
			 em.persist(bond);
			 em.persist(stock);
			 em.getTransaction().commit();
		} catch (ParseException e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
		 
	 }
	 
	 
	 
	 @Test
	 public void testTablePerClassInheritanceRelationShip() throws ParseException {
		 
		 EntityManager em = emf.createEntityManager();
		 
		 try {
			em.getTransaction().begin();
			 
			 Portfolio portfolio = new Portfolio();
			 portfolio.setName("First portfolio");
			 
			 Bond bond = createBond();
			 bond.setPortfolio(portfolio);
			 
			 Stock stock = createStock();
			 stock.setPortfolio(portfolio);
			 
			 portfolio.getInvestments().add(stock);
			 portfolio.getInvestments().add(bond);
			 
			 em.persist(stock);
			 em.persist(bond);
			 
			 em.getTransaction().commit();
			 
			 Portfolio dbPortfolio = em.find(Portfolio.class,  portfolio.getPortfolioId());
			 em.refresh(dbPortfolio);
			 
			 for (Investment investment : dbPortfolio.getInvestments()) {
				System.out.println(investment.getName());
			}
			 
		} catch (ParseException e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
		 
		 
	 }
	 
	 @Test
	 public void testSingleTableInheritanceRelationShip() throws ParseException {
		 
		 EntityManager em = emf.createEntityManager();
		 
		 try {
			 em.getTransaction().begin();
			 
			 Portfolio portfolio = new Portfolio();
			 portfolio.setName("First portfolio");
			 
			 Bond bond = createBond();
			 bond.setPortfolio(portfolio);
			 
			 Stock stock = createStock();
			 stock.setPortfolio(portfolio);
			 
			 portfolio.getInvestments().add(stock);
			 portfolio.getInvestments().add(bond);
			 
			 em.persist(stock);
			 em.persist(bond);
			 
			 em.getTransaction().commit();
			 
			 Portfolio dbPortfolio = em.find(Portfolio.class,  portfolio.getPortfolioId());
			 em.refresh(dbPortfolio);
			 
			 for (Investment investment : dbPortfolio.getInvestments()) {
				System.out.println(investment.getName());
			}
			 
		} catch (ParseException e) {
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
			Map<String,String> mapValueType = new HashMap<>();
			mapValueType.put("Manager", "Jagan"); mapValueType.put("Neta", "Samvu");
			bank.setContacts(mapValueType);
			bank.setAddress(createAddress(i));
			  
			return bank;
		}
		
		private static Address createAddress(int i) {
			return  new Address("addressline1-"+i, "addressline2-"+i, "City-"+i, "State-"+i, "zipcode-"+i);

		}
		
		private static Stock createStock() {
			
			Stock stock = new Stock();
			stock.setQuantity(50);
			stock.setSharePrice(new BigDecimal(95.0));
			stock.setIssuer("JP Morgan Chase");
			stock.setName("GP");
			stock.setPurchaseDate(new Date());
			return stock;
		}

		private static Bond createBond() throws ParseException {
			Bond bond = new  Bond();
			bond.setInterestRate(new BigDecimal(0.9));
			bond.setIssuer("PROB");
			bond.setMaturityDate(new SimpleDateFormat("dd-M-yyyy").parse("22-04-2030"));
			bond.setPurchaseDate(new Date());
			bond.setName("Sanchay");
			bond.setValue(new BigDecimal(300000));
			return bond;
		}


}
