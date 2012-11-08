package ar.kennedy.is2011.models;

import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.db.dao.AbstractDao;
import ar.kennedy.is2011.db.dao.UserAccount;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.Account;
import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.db.entities.Location;
import ar.kennedy.is2011.db.entities.ContactosPerson;

import com.google.appengine.api.datastore.Key;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;


public class PersonModel extends AbstractModel {

	private static final String FPERSON_BY_NAME = "SELECT e FROM PersonaFisica e WHERE e.nombrePerson = :1";
	private static final String FPERSON_BY_OWNER= "SELECT e FROM PersonaFisica e WHERE e.userId = :1";
	private static final String ALL_FPERSONS =    "SELECT e FROM PersonaFisica e ";	

	//private static final String IPERSON_BY_NAME = "SELECT e FROM PersonaIdeal e WHERE e.nombrePerson = :1";
	//private static final String ALL_IPERSONS =    "SELECT e FROM PersonaIdeal e ";	

	
	AbstractDao<Account> accountDAO = new AbstractDao<Account>();
	AbstractDao<User> userDAO = new AbstractDao<User>();
	AbstractDao<Location> locDAO = new AbstractDao<Location>();
	AbstractDao<PersonaFisica> fpersonDAO = new AbstractDao<PersonaFisica>();
	AbstractDao<PersonaIdeal> ipersonDAO = new AbstractDao<PersonaIdeal>();
	
	private User user;
	private AccountModel accountModel;

	private PersonaFisica fperson;
	private PersonaIdeal  iperson;
	private List<PersonaFisica> fpersons;
	private List<PersonaIdeal> ipersons;
	
	public PersonModel() {
		// TODO Auto-generated constructor stub
		super();
	}
	public PersonModel(User us) {
		this();
		setUser(us);
		accountModel=new AccountModel(us);
		// TODO Auto-generated constructor stub
	}

	public void updateFPerson(){
		try{
			log.debug("=== trying update this.fperson");
			fpersonDAO.persist(getFperson());
			log.debug("****** updatefperson:OK ********");

		} catch (Exception e) {
			fpersonDAO.rollBackTx();
			log.error(e.getMessage());
			//throw new PersistException("Fail to delete entity in database");

		}finally{
		}

	}

	public void updateIPerson(){
		try{
			log.debug("=== trying update this.iperson");
			ipersonDAO.persist(getIperson());
			log.debug("****** updatefperson:OK ********");

		} catch (Exception e) {
			ipersonDAO.rollBackTx();
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

	public PersonaFisica getFperson(Key key){
		if(key==null) return null;
		try {
			setFperson(fpersonDAO.findById(PersonaFisica.class, key.getId()));
			return getFperson();
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public PersonaFisica getFpersonById(long id){
		if(id==0) return null;
		try {
			setFperson(fpersonDAO.findById(PersonaFisica.class, id));
			return getFperson();
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public PersonaIdeal getIperson(Key key){
		if(key==null) return null;
		try {
			setIperson(ipersonDAO.findById(PersonaIdeal.class, key.getId()));
			return getIperson();
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public PersonaIdeal getIpersonById(String id){
		if(id==null) return null;
		try {
			setIperson(ipersonDAO.findById(PersonaIdeal.class, id));
			return getIperson();
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	
	public List<PersonaFisica> getAllFPersons() {
		this.fpersons = null;
		
		try {
			fpersons = fpersonDAO.createCollectionQuery(ALL_FPERSONS, null);
			return fpersons;

		} catch(EntityNotFoundException e) {
			return new ArrayList<PersonaFisica>();
		}
	}

	public PersonaFisica getPersonFromUser(){
		if(accountModel.getUser()==null) return null;
		long personId=accountModel.getUser().getPersonId();

		if(personId==0) return initDefaultPersonForUser();
		if(getFpersonById(personId)==null) return initDefaultPersonForUser();
		return getFperson();
	}
	private PersonaFisica initDefaultPersonForUser(){
		setFperson(new PersonaFisica());
		getFperson().setNombrePerson(getUser().getUserName());
		getFperson().setNombre(getUser().getUserName());
		getFperson().setSexo(getUser().getSexo());
		getFperson().setFechaNacimiento(getUser().getFechaNacimiento());
		getFperson().setUserId(getUser().getKey().getId());
		return getFperson();
	}
	
	public PersonaFisica initNewFperson(){
		setFperson(new PersonaFisica());
		getFperson().setUserId(getUser().getKey().getId());
		return getFperson();
		
	}
	
	public Boolean addContact(ContactosPerson ct){
		log.debug("MODEL ADD CONTACT:to begin");

		if(ct==null)return false;

		if(getFperson()==null) return false;
		
		List<ContactosPerson> contactos= getFperson().getDatosContacto();
		if(contactos==null) contactos = new ArrayList<ContactosPerson>();
		contactos.add(ct);
		getFperson().setDatosContacto(contactos);
		log.debug("MODEL ADD CONTACT: end");
		return true;
	}
	
	public Boolean addLocation(Location loc){
		log.debug("MODEL ADD LOCATION:to begin");

		if(loc==null)return false;

		if(getFperson()==null) return false;
		
		List<Location> locations= getFperson().getLocations();
		if(locations==null) locations = new ArrayList<Location>();
		locations.add(loc);
		getFperson().setLocations(locations);
		log.debug("MODEL ADD LOCATIONS: end");
		return true;
	}
	
	public Location getLocationFromFPersonById(Long locId){
		if(locId<=0) return null;
		
		List<Location> locations = getFperson().getLocations();
		if(locations==null) return null;
		if(locations.isEmpty()) return null;
		for(Location loc:locations){
			if(loc.getKey().getId()==locId) return loc;
		}
		return null;
	}

	
	
	public ContactosPerson getContactFromFPersonById(Long ctId){
		if(ctId<=0) return null;
		
		List<ContactosPerson> contactos = getFperson().getDatosContacto();
		if(contactos==null) return null;
		if(contactos.isEmpty()) return null;
		for(ContactosPerson ct:contactos){
			if(ct.getKey().getId()==ctId) return ct;
		}
		return null;
	}
	
	public PersonaFisica getFPersonByName(String name) {
		this.fperson = null;
		
		if( !getFPersonsByName(name).isEmpty()){
			setFperson(getFpersons().get(0));
			return getFperson();
		}else{
			return null;
		}
	}
	
	public List<PersonaFisica> getFPersonsByName(String name) {
		this.fpersons = null;
		
		try {
			setFpersons(fpersonDAO.createCollectionQuery(FPERSON_BY_NAME, new Vector<Object>(Arrays.asList(new String[] {name}))));
		
			return getFpersons();
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<PersonaFisica>();
		}
	}

	public List<PersonaFisica> getFPersonsByOwner(Long userId) {
		this.fpersons = null;
		
		try {
			setFpersons(fpersonDAO.createCollectionQuery(FPERSON_BY_OWNER, new Vector<Object>(Arrays.asList(new Long[] {userId}))));
	
			return getFpersons();
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<PersonaFisica>();
		}
	}
	
	
	public Boolean ifExistFPersonByName(String name) {
		if(getFPersonsByName(name).isEmpty()) return false;
		else return true;
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

	public void updatePersonFromUser(PersonaFisica fp){
		accountModel.updatePersonFromUser(fp);		
	}
	
	public List<ContactosPerson> getContactList(){
		if(getFperson()==null) return initContactList();

		List<ContactosPerson> contactos= getFperson().getDatosContacto();
		if(contactos==null) return initContactList();
		if(contactos.isEmpty()) return initContactList();
		
		return contactos;
	}

	public List<Location> getLocationList(){
		if(getFperson()==null) return initLocationList();

		List<Location> locations= getFperson().getLocations();
		
		if(locations==null)     return initLocationList();
		if(locations.isEmpty()) return initLocationList();
		
		return locations;
	}

	private List<Location> initLocationList(){
		List<Location> location = new ArrayList<Location>();
		
		Location loc = new Location();
		loc.setCalle1("calle1");
		loc.setDescr("ingresar nueva locacion");
		loc.setLocType("OTRA");
		loc.setEventoType("S/D");
		
		location.add(loc);
		
		return location;
	}

	
	private List<ContactosPerson> initContactList(){
		List<ContactosPerson> contactos = new ArrayList<ContactosPerson>();
		ContactosPerson ct = new ContactosPerson();
		ct.setConType("MAIL");
		ct.setDescr("nuevo dato de contacto");
		//ct.setPersonId(getFperson().getKey().getId());
		ct.setValue("sunombre@suproveedor.com");
		contactos.add(ct);
		return contactos;
	}
	
	
	public List<PersonaFisica> getFpersonsOwnedByUser(){
		if(getUser()==null) return null;
		List<PersonaFisica> persons = new ArrayList<PersonaFisica>();
		persons.addAll(getFPersonsByOwner(getUser().getKey().getId()));
		if(persons.isEmpty()){
			persons.add(getPersonFromUser());
		}
		return persons;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PersonaFisica getFperson() {
		return fperson;
	}
	public void setFperson(PersonaFisica fperson) {
		this.fperson = fperson;
	}
	public PersonaIdeal getIperson() {
		return iperson;
	}
	public void setIperson(PersonaIdeal iperson) {
		this.iperson = iperson;
	}
	public List<PersonaFisica> getFpersons() {
		return fpersons;
	}
	public void setFpersons(List<PersonaFisica> fpersons) {
		this.fpersons = fpersons;
	}
	public List<PersonaIdeal> getIpersons() {
		return ipersons;
	}
	public void setIpersons(List<PersonaIdeal> ipersons) {
		this.ipersons = ipersons;
	}

}
