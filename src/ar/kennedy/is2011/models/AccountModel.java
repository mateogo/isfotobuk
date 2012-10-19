package ar.kennedy.is2011.models;

import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.db.dao.AbstractDao;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.Account;

import com.google.appengine.api.datastore.Key;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;



public class AccountModel extends AbstractModel {

	private static final String USER_BY_NAME = "SELECT e FROM User e WHERE e.userName = :1";
	private static final String ALL_USERS =    "SELECT e FROM User e ";	

	private static final String ACCOUNT_BY_NAME = "SELECT e FROM Account e WHERE e.accountName = :1";
	private static final String ALL_ACCOUNTS =    "SELECT e FROM Account e ";	

	
	AbstractDao<Account> accountDAO = new AbstractDao<Account>();
	AbstractDao<User> userDAO = new AbstractDao<User>();
	
	private User user;
	private Account account;
	private List<Account> accounts;
	private List<User> users;
	
	public AccountModel() {
		// TODO Auto-generated constructor stub
		super();
	}
	public AccountModel(Account ua) {
		this();
		setAccount(ua);
		// TODO Auto-generated constructor stub
	}

	public User changeDefaultAccount(Account ac){
		if(ac==null) return new User();
		user = getUserForAccount(ac);
		user.setDefaultAccount(ac);
		updateUser(user);
		return user;
		
	}
	public void updateUser(User us){
		try{
			log.debug("=== trying updateUser");
			userDAO.persist(us);
			log.debug("****** udateUser:OK ********");

		} catch (Exception e) {
			userDAO.rollBackTx();
			log.error(e.getMessage());
			//throw new PersistException("Fail to delete entity in database");

		}finally{
		}

	}
	public List<Account> getAllAccounts() {
		this.accounts = null;
		
		try {
			accounts = accountDAO.createCollectionQuery(ALL_ACCOUNTS, null);
			return accounts;

		} catch(EntityNotFoundException e) {
			return new ArrayList<Account>();
		}
	}
	public User getUserById(Key key){
		if(key==null) return new User();
		try {
			user = userDAO.findById(User.class, key.getId());
			return user;
			
		} catch(EntityNotFoundException e) {
			return new User();
		}
	}

	
	public User getUserForAccount(Account ac){
		if(ac==null) return new User();
		if(ac.getUser()==null) return new User();
		user = ac.getUser();
		return user;
	}

	public List<Account> getAccountByName(String name) {
		this.accounts = null;
		
		try {
			accounts = accountDAO.createCollectionQuery(ACCOUNT_BY_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
		
			return accounts;
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<Account>();
		}
	}

	
	
	public List<User> getAllUsers() {
		this.users = null;
		
		try {
			users = userDAO.createCollectionQuery(ALL_USERS, null);
			return users;

		} catch(EntityNotFoundException e) {
			return new ArrayList<User>();
		}
	}

	public List<User> getUserByName(String name) {
		this.users = null;
		
		try {
			users = userDAO.createCollectionQuery(USER_BY_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
		
			return users;
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<User>();
		}
	}


	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}


}
