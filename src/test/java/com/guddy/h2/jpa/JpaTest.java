package com.guddy.h2.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.infiniteskills.data.HibernateUtil;
import com.infiniteskills.data.JpaUtil;
import com.infiniteskills.data.dao.UserHibernateDao;
import com.infiniteskills.data.dao.UserJpaDao;
import com.infiniteskills.data.dao.interfaces.UserDao;
import com.infiniteskills.data.entities.Account;
import com.infiniteskills.data.entities.AccountType;
import com.infiniteskills.data.entities.Address;
import com.infiniteskills.data.entities.Bank;
import com.infiniteskills.data.entities.Bond;
import com.infiniteskills.data.entities.Currency;
import com.infiniteskills.data.entities.Investment;
import com.infiniteskills.data.entities.Market;
import com.infiniteskills.data.entities.Portfolio;
import com.infiniteskills.data.entities.Stock;
import com.infiniteskills.data.entities.Transaction;
import com.infiniteskills.data.entities.User;
import com.infiniteskills.data.entities.Ids.CurrencyId;
import com.infiniteskills.data.view.UserCredentialView;

public class JpaTest {
	
	 static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2");
	 
	 @BeforeClass
	 public static void initialize() {
		
		 EntityManager em = emf.createEntityManager(); 
		 try {
			 	
			 	em.getTransaction().begin();
			 	Account account = createNewAccount("Ganesh");
			 	Transaction transaction;
				for(int i=1;i<13;i++) {
					Bank bank  = createNewBank(i);
					em.persist(bank);
					switch(i) {
					case 1: 
						transaction = createBonusTransaction(account);
						em.persist(transaction); break;
					case 2:
						transaction = createGroceriesTransaction(account);
						em.persist(transaction); break;
					case 3:
						transaction = createLunchTransaction(account);
						em.persist(transaction); break;
					case 4:
						transaction = createNewBeltPurchase(account);
						em.persist(transaction); break;
					case 5:
						transaction = createNewBreakfastPurchase(account);
						em.persist(transaction); break;
					case 6:
						transaction = createNewDinner(account);
						em.persist(transaction); break;
					case 7:
						transaction = createNewPantPurchase(account);
						em.persist(transaction); break;
					case 8:
						transaction = createNewShirtPurchase(account);
						em.persist(transaction); break;
					case 9:
						transaction = createNewShoePurchase(account);
						em.persist(transaction); break;
					case 10:
						transaction = createNewSocksPurchase(account);
						em.persist(transaction); break;
					case 11:
						transaction = createNewTiePurchase(account);
						em.persist(transaction); break;
					case 12:
						transaction = createPayCheckTransaction(account);
						em.persist(transaction); break;
						
						
					}
					
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
	 
	 @Test
	 public void testView() {
		 EntityManager entityManager = emf.createEntityManager();
		 
		 try {
			entityManager.getTransaction().begin();
			 UserCredentialView view = entityManager.find(UserCredentialView.class, 1L);
				/*
				 * System.out.println(view.getFirstName());
				 * System.out.println(view.getLastName());
				 */
			 assertNull(view);
			 
			 entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();
		}
		 
	 }
	 
	    @Test
		 public void testPersistenceLayerDAO() {
			 EntityManager entityManager= JpaUtil.getEntityManager();
			 UserDao userDao = new UserJpaDao();
			 userDao.setEntityManager(entityManager);
			 
			 try {
				 entityManager.getTransaction().begin();;
				 User user = createUser("Chudna", "Hugur", "chudna@hugur.com");
				 userDao.save(user);
				 entityManager.getTransaction().commit();
			} catch (Exception e) {
				entityManager.getTransaction().rollback();
				throw e;
			} finally {
				entityManager.close();
			}
			 
		 }
	    
	    @Test
		public void testJpql() {
	    	
	    	EntityManager entityManager = emf.createEntityManager();
	    	
	    	try {
				entityManager.getTransaction().begin();
				
				//Query query = entityManager.createQuery("from Bank b order by b.name");
				TypedQuery<Transaction> query = entityManager.createQuery(" from Transaction t order by t.title", Transaction.class);
				List<Transaction> transactions = query.getResultList();
				for (Transaction transaction : transactions) {
					System.out.println(transaction.getTitle());
				}
				
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				entityManager.getTransaction().rollback();
				throw e;
			} finally {
				entityManager.close();
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
		
	    private Date getMyBirthDay() {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, 1982);
			calendar.set(Calendar.MONTH, 11);
			calendar.set(Calendar.DATE, 1);
			return calendar.getTime();
		}
	 
	 private static Transaction createNewBeltPurchase(Account account) {
			
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
		
		private static Transaction createNewShoePurchase(Account account) {
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
		private static Transaction createBonusTransaction(Account account) {
			Transaction transaction = new Transaction();
			transaction.setAccount(account);
			transaction.setTitle("Bonus");
			transaction.setAmount(new BigDecimal(75.00));
			transaction.setClosingBalance(new BigDecimal(0.00));
			transaction.setCreatedBy("modna");
			transaction.setCreatedDate(new Date());
			transaction.setInitialBalance(new BigDecimal(0.00));
			transaction.setLastUpdatedBy("chodna");
			transaction.setLastUpdatedDate(new Date());
			transaction.setNotes("new bonus");
			transaction.setTransactionType("Credit");
			
			return transaction;	
		}
		
		private static Transaction createNewBreakfastPurchase(Account account) {
			Transaction transaction = new Transaction();
			transaction.setAccount(account);
			transaction.setTitle("Breakfast");
			transaction.setAmount(new BigDecimal(25.00));
			transaction.setClosingBalance(new BigDecimal(0.00));
			transaction.setCreatedBy("modna");
			transaction.setCreatedDate(new Date());
			transaction.setInitialBalance(new BigDecimal(0.00));
			transaction.setLastUpdatedBy("chodna");
			transaction.setLastUpdatedDate(new Date());
			transaction.setNotes("breakfast");
			transaction.setTransactionType("Debit");
			
			return transaction;	
		}
		
		private static Transaction createNewDinner(Account account) {
			Transaction transaction = new Transaction();
			transaction.setAccount(account);
			transaction.setTitle("Dinner");
			transaction.setAmount(new BigDecimal(45.00));
			transaction.setClosingBalance(new BigDecimal(0.00));
			transaction.setCreatedBy("modna");
			transaction.setCreatedDate(new Date());
			transaction.setInitialBalance(new BigDecimal(0.00));
			transaction.setLastUpdatedBy("chodna");
			transaction.setLastUpdatedDate(new Date());
			transaction.setNotes("dinner");
			transaction.setTransactionType("credit");
			
			return transaction;	
		}
		
		private  static Transaction createGroceriesTransaction(Account account) {
			Transaction transaction = new Transaction();
			transaction.setAccount(account);
			transaction.setTitle("Groceries");
			transaction.setAmount(new BigDecimal(95.00));
			transaction.setClosingBalance(new BigDecimal(0.00));
			transaction.setCreatedBy("modna");
			transaction.setCreatedDate(new Date());
			transaction.setInitialBalance(new BigDecimal(0.00));
			transaction.setLastUpdatedBy("chodna");
			transaction.setLastUpdatedDate(new Date());
			transaction.setNotes("groceries");
			transaction.setTransactionType("credit");
			
			return transaction;	
		}
		
		private static Transaction createLunchTransaction(Account account) {
			Transaction transaction = new Transaction();
			transaction.setAccount(account);
			transaction.setTitle("Lunch");
			transaction.setAmount(new BigDecimal(45.00));
			transaction.setClosingBalance(new BigDecimal(0.00));
			transaction.setCreatedBy("modna");
			transaction.setCreatedDate(new Date());
			transaction.setInitialBalance(new BigDecimal(0.00));
			transaction.setLastUpdatedBy("chodna");
			transaction.setLastUpdatedDate(new Date());
			transaction.setNotes("lunch");
			transaction.setTransactionType("credit");
			
			return transaction;	
		}
		
		private static Transaction createNewPantPurchase(Account account) {
			Transaction transaction = new Transaction();
			transaction.setAccount(account);
			transaction.setTitle("Pants");
			transaction.setAmount(new BigDecimal(145.00));
			transaction.setClosingBalance(new BigDecimal(0.00));
			transaction.setCreatedBy("modna");
			transaction.setCreatedDate(new Date());
			transaction.setInitialBalance(new BigDecimal(0.00));
			transaction.setLastUpdatedBy("chodna");
			transaction.setLastUpdatedDate(new Date());
			transaction.setNotes("pants");
			transaction.setTransactionType("credit");
			
			return transaction;	
		}
		
		private static Transaction createPayCheckTransaction(Account account) {
			Transaction transaction = new Transaction();
			transaction.setAccount(account);
			transaction.setTitle("Pay Check");
			transaction.setAmount(new BigDecimal(88.00));
			transaction.setClosingBalance(new BigDecimal(0.00));
			transaction.setCreatedBy("modna");
			transaction.setCreatedDate(new Date());
			transaction.setInitialBalance(new BigDecimal(0.00));
			transaction.setLastUpdatedBy("chodna");
			transaction.setLastUpdatedDate(new Date());
			transaction.setNotes("pay check");
			transaction.setTransactionType("credit");
			
			return transaction;	
		}
		
		private static Transaction createNewShirtPurchase(Account account) {
			Transaction transaction = new Transaction();
			transaction.setAccount(account);
			transaction.setTitle("Shirt");
			transaction.setAmount(new BigDecimal(65.00));
			transaction.setClosingBalance(new BigDecimal(0.00));
			transaction.setCreatedBy("modna");
			transaction.setCreatedDate(new Date());
			transaction.setInitialBalance(new BigDecimal(0.00));
			transaction.setLastUpdatedBy("chodna");
			transaction.setLastUpdatedDate(new Date());
			transaction.setNotes("shirt");
			transaction.setTransactionType("Withdrawl");
			
			return transaction;	
		}
		
		private static Transaction createNewSocksPurchase(Account account) {
			Transaction transaction = new Transaction();
			transaction.setAccount(account);
			transaction.setTitle("Shocks");
			transaction.setAmount(new BigDecimal(50.00));
			transaction.setClosingBalance(new BigDecimal(0.00));
			transaction.setCreatedBy("modna");
			transaction.setCreatedDate(new Date());
			transaction.setInitialBalance(new BigDecimal(0.00));
			transaction.setLastUpdatedBy("chodna");
			transaction.setLastUpdatedDate(new Date());
			transaction.setNotes("shocks");
			transaction.setTransactionType("Withdrawl");
			
			return transaction;	
		}
		
		private static Transaction createNewTiePurchase(Account account) {
			Transaction transaction = new Transaction();
			transaction.setAccount(account);
			transaction.setTitle("Tie");
			transaction.setAmount(new BigDecimal(77.00));
			transaction.setClosingBalance(new BigDecimal(0.00));
			transaction.setCreatedBy("modna");
			transaction.setCreatedDate(new Date());
			transaction.setInitialBalance(new BigDecimal(0.00));
			transaction.setLastUpdatedBy("chodna");
			transaction.setLastUpdatedDate(new Date());
			transaction.setNotes("tie");
			transaction.setTransactionType("credit");
			
			return transaction;	
		}
		
		private static Account createNewAccount(String name) {
			
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

		@Test
		public void testExpressionAndOperator() {
			
			EntityManager entityManager = emf.createEntityManager();
			Scanner scanner = new Scanner(System.in);
			
			try {
				//Query query = entityManager.createQuery("from Transaction t where t.amount < 100 and t.transactionType='Withdrawl'");
				/*
				 * TypedQuery<Transaction> query =
				 * entityManager.createQuery("from Transaction t " +
				 * " where (t.amount between ?1 and ?2) " + " and t.title like '%s' " +
				 * " order by t.title", Transaction.class);
				 */
				
				TypedQuery<Account> query = entityManager.createQuery("select distinct a from Transaction t "
						+ " join  t.account a "
						+ " where t.amount > 5 and t.transactionType = 'credit' ", Account.class);
				//System.out.println("Please provide the first amount: ");
				
				//query.setParameter(1, new BigDecimal(scanner.next()));
				//query.setParameter(1, new BigDecimal(10));
				//System.out.println("Please provide the first amount: ");
				//query.setParameter(2, new BigDecimal(scanner.next()));
				//query.setParameter(2, new BigDecimal(90));
				
				//List<Transaction>  transactions = query.getResultList();
				List<Account>  accounts = query.getResultList();
				//System.out.println(transactions.size());
				System.out.println(accounts.size());
				//assertEquals(2, transactions.size());
				//assertEquals(4, transactions.size());
				//assertNotNull(transactions);
				assertNotNull(accounts);
				/*
				 * for (Transaction transaction : transactions) {
				 * System.out.println(transaction.getTitle()); }
				 */
				for (Account account : accounts) {
					System.out.println(account.getAccountName());
				}
			} catch (Exception e) {
				throw e;
			} finally {
				entityManager.close();
			}
		}
		
		
}
