package ar.kennedy.is2011.models;

import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.db.dao.AbstractDao;
import ar.kennedy.is2011.db.dao.UserAccount;
import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.Account;
import ar.kennedy.is2011.db.entities.Location;
import ar.kennedy.is2011.utils.WebUtils;

import com.google.appengine.api.datastore.Key;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;



public class AccountModel extends AbstractModel {

	private static final String USER_BY_NAME = "SELECT e FROM User e WHERE e.userName = :1";
	private static final String ALL_USERS =    "SELECT e FROM User e ";	

	private static final String ACCOUNT_BY_NAME = "SELECT e FROM Account e WHERE e.accountName = :1";
	private static final String ALL_ACCOUNTS =    "SELECT e FROM Account e ";	

	
	AbstractDao<Account> accountDAO = new AbstractDao<Account>();
	AbstractDao<User> userDAO = new AbstractDao<User>();
	AbstractDao<Location> locDAO = new AbstractDao<Location>();
	
	private User user;
	private Account account;
	//private Location location;
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

	public AccountModel(User us) {
		this();
		setUser(us);
		// TODO Auto-generated constructor stub
	}

	
	public User changeDefaultAccount(Account ac){
		if(ac==null) return new User();
		user = getUserForAccount(ac);
		user.setDefaultAccount(ac);
		updateUser(user);
		return user;
		
	}
	public void update(){
		try{
			log.debug("=== trying update this.user");
			userDAO.persist(getUser());
			log.debug("****** updateUser:OK ********");

		} catch (Exception e) {
			userDAO.rollBackTx();
			log.error(e.getMessage());
			//throw new PersistException("Fail to delete entity in database");

		}finally{
		}

	}

	public void updateUser(User us){
		try{
			log.debug("=== trying updateUser");
			userDAO.persist(us);
			log.debug("****** updateUser:OK ********");

		} catch (Exception e) {
			userDAO.rollBackTx();
			log.error(e.getMessage());
			//throw new PersistException("Fail to delete entity in database");

		}finally{
		}

	}
	public void updateLocation(Location loc){
		try{
			log.debug("=== trying updateLocation");
			locDAO.persist(loc);
			log.debug("****** update Location:OK ********");

		} catch (Exception e) {
			locDAO.rollBackTx();
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
		if(key==null) return null;
		try {
			setUser(userDAO.findById(User.class, key.getId()));
			return getUser();
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public User getUserForAccount(String accName){
		if(getAccountByName(accName)==null) return null;
		return getUserForAccount(getAccount());
	}
	
	public User getUserForAccount(Account ac){
		if(ac==null) return null;
		setUser(ac.getUser());
		return user;
	}
	
	public Long getPersonIdFromUser(){
		if(getUser()==null) return null;
		return getUser().getPersonId();
	}

	public void updatePersonFromUser(PersonaFisica person){
		if(getUser()==null) return;
		getUser().setPersonId(person.getKey().getId());
		update();
		return;
	}

	/**
	 * user conoce su lista de accounts.
	 * este metodo busca en dicha lista la existencia
	 * de un Account con id:accName y lo devuelve.
	 * Caso contrario, devuelve null
	 * @param user, instancia del usuario sobre el que se busca el account
	 * @param accName
	 * @return
	 */
	public Account chooseAccountFromUser(User user, String accName) {
		log.debug("----------------- chooseAccountFormUser: begin");
		if(user==null) return null;
		if(accName==null) return null;
		if(getUserById(user.getKey())==null) return null;
		
		setAccounts(getUser().getAccounts());

		if(getAccounts().isEmpty()) return null;

		for (Account acc:getAccounts()){
			if(acc.getAccountName().equals(accName)){
				log.debug("----------------- Retrieve user ok, searching accname: found ["+accName+"]");
				setAccount(acc);
				return getAccount();
			}
		}
		log.debug("vvvvvvvvvvvvvvvvvfail....");
		return null;
	}

	
	public Account getAccountByName(String name) {
		if(!getAccountsByName(name).isEmpty()){
			setAccount(getAccounts().get(0));
			return getAccount();
		}else{
			setAccount(null);
			return null;
		}
	}
	
	public String getPassword(){
		if(getAccount()==null)return "";
		return getAccount().getPasswd();
	}

	public List<Account> getAccountsByName(String name) {
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

	public Boolean ifExistUserByName(String name) {
		if(getUsersByName(name).isEmpty()) return false;
		else return true;
	}
	public Boolean ifExistAccountByName(String name) {
		if(getAccountsByName(name).isEmpty()) return false;
		else return true;
	}
	
	public User getUserByName(String name) {
		List<User> users = getUsersByName(name);
		if(users.isEmpty()) return null;
		return users.get(0);
	}

	public List<User> getUsersByName(String name) {
		this.users = null;
		
		try {
			users = userDAO.createCollectionQuery(USER_BY_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
		
			return users;
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<User>();
		}
	}
	/**
	 * TO-DO
	 * @param user
	 * @return
	 */
	public boolean deleteUser(User user) {
		return false;
	}

	public static void userAccountFactory(HttpServletRequest request, User user){
		UserAccount userprofile = new UserAccount();
		if (user!=null){
			userprofile.setUser(user);
			userprofile.setAccounts(user.getAccounts());
			userprofile.setAcountDefault(user.getDefaultAccount());
			request.setAttribute("useraccount", userprofile);
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
	
	public Boolean initNewAccount(String accName, String accMail, String authProvider, String password){
		Boolean errors=false;
		setAccount(new Account());
		getAccount().setAccountName(accName);
		getAccount().setMail(accMail);
		getAccount().setAuthProvider(authProvider);		
		getAccount().setUser(getUser());
		return errors;
	}
	
	public void addNewAccountToUser(){
		List<Account> accounts = getUser().getAccounts();
		if(accounts==null || accounts.isEmpty()){
			accounts = new ArrayList<Account>();
			accounts.add(getAccount());
			getUser().setDefaultAccount(getAccount());
			getUser().setAccounts(accounts);			
		}else{
			accounts.add(getAccount());
		}
	}

	private void setAccountPassword(String passwd) {
		getAccount().setPasswd(WebUtils.encrypt(passwd));
	}
	public void setProfileImageToUser(String picId){
		getUser().setProfileImageId(picId);
	}
	
}
