package ar.kennedy.is2011.models;

import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.db.dao.AbstractDao;
import ar.kennedy.is2011.db.dao.UserAccount;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.Account;
import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.db.entities.Person;
import ar.kennedy.is2011.db.entities.EntityRelationHeader;
import ar.kennedy.is2011.db.entities.EntityRelations;

import ar.kennedy.is2011.db.entities.Location;
import ar.kennedy.is2011.db.entities.ContactosPerson;
import ar.kennedy.is2011.utils.WebUtils;

import com.google.appengine.api.datastore.Key;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;


public class PersonModel extends AbstractModel {

	private static final String FPERSON_BY_NAME =  "SELECT e FROM PersonaFisica e WHERE e.nombrePerson = :1";
	private static final String FPERSON_LIKE_NAME ="SELECT e FROM PersonaFisica e WHERE e.nombrePerson LIKE :1";
	private static final String FPERSON_BY_OWNER=  "SELECT e FROM PersonaFisica e WHERE e.userId = :1";

	private static final String RELATIONS_FOR_FPERSON =  "SELECT e FROM EntityRelationHeader e WHERE e.fpersonkey = :1";
	private static final String RELATIONS_FOR_IPERSON =  "SELECT e FROM EntityRelationHeader e WHERE e.ipersonkey = :1";

	private static final String ALL_FPERSONS =     "SELECT e FROM PersonaFisica e ";

	private static final String IPERSON_BY_NAME   = "SELECT e FROM PersonaIdeal e WHERE e.nombrePerson = :1";
	private static final String IPERSON_BY_OWNER  = "SELECT e FROM PersonaIdeal e WHERE e.userId = :1";
	private static final String IPERSON_LIKE_NAME = "SELECT e FROM PersonaIdeal  e WHERE e.nombrePerson LIKE :1";

	//private static final String ALL_IPERSONS =    "SELECT e FROM PersonaIdeal e ";	

	//private static final String IPERSON_BY_NAME = "SELECT e FROM PersonaIdeal e WHERE e.nombrePerson = :1";
	//private static final String ALL_IPERSONS =    "SELECT e FROM PersonaIdeal e ";	

	
	AbstractDao<Account> accountDAO = new AbstractDao<Account>();
	AbstractDao<User> userDAO = new AbstractDao<User>();
	AbstractDao<Location> locDAO = new AbstractDao<Location>();
	AbstractDao<PersonaFisica> fpersonDAO = new AbstractDao<PersonaFisica>();
	AbstractDao<PersonaIdeal> ipersonDAO = new AbstractDao<PersonaIdeal>();
	AbstractDao<EntityRelationHeader> erelationDAO = new AbstractDao<EntityRelationHeader>();
	
	
	private User user;
	private AccountModel accountModel;

	private PersonaFisica fperson;
	private PersonaIdeal  iperson;
	private List<PersonaFisica> fpersons;
	private List<PersonaIdeal> ipersons;
	
	private EntityRelationHeader erelation;
	private List<EntityRelationHeader> erelations;
	private List<EntityRelations> erelitems;
	
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

	public void updateERelation(){
		getErelation().setUdate(new Date());
		try{
			log.debug("=== trying update this.erelation");
			erelationDAO.persist(getErelation());
			log.debug("****** update-erelation:OK ********");
		} catch (Exception e) {
			erelationDAO.rollBackTx();
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
		this.fperson=null;
		try {
			setFperson(fpersonDAO.findById(PersonaFisica.class, key.getId()));
			return getFperson();
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	
	public void searchPersonById(String personId){
		if(personId==null) return;
		if(WebUtils.isNotNull(personId)){
			this.iperson=null;
			this.fperson=null;
			long pId = Long.parseLong(personId);
			if (pId<=0) return;
			getFpersonById(pId);
			getIpersonById(pId);
		}	
	}
	
	public PersonaFisica getFpersonById(Long id){
		if(id<=0) return null;
		this.fperson=null;
		try {
			setFperson(fpersonDAO.findById(PersonaFisica.class, id));
			return getFperson();
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	public PersonaFisica fetchFpersonById(Long id){
		if(id<=0) return null;
	
		try {
			return fpersonDAO.findById(PersonaFisica.class, id);

		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public PersonaIdeal getIpersonById(Long id){
		if(id==0) return null;
		this.iperson=null;
		try {
			setIperson(ipersonDAO.findById(PersonaIdeal.class, id));
			return getIperson();
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	public PersonaIdeal fetchIpersonById(Long id){
		if(id==0) return null;

		try {
			return ipersonDAO.findById(PersonaIdeal.class, id);
	
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public PersonaIdeal getIperson(Key key){
		if(key==null) return null;
		this.iperson=null;
		try {
			setIperson(ipersonDAO.findById(PersonaIdeal.class, key.getId()));
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

	public EntityRelationHeader initEntityRelation(String relId){
		if(WebUtils.isNotNull(relId)){
			Long relationId= Long.parseLong(relId);
			if(relationId<=0){
				return initNewEntityRelation();
			}else{
				return getERelationById(relationId);
			}
		}
		return initNewEntityRelation();
	}

	private EntityRelationHeader initNewEntityRelation(){
		setErelation( new EntityRelationHeader(getUser()));
		getErelation().setCdate(new Date());
		getErelation().setUdate(getErelation().getCdate());
		getErelation().setPredicate("CONOCE_A");
				
		return getErelation();
	}

	public void initNewEntityRelationItems(){
		//getErelation().setErelations(new ArrayList<EntityRelations>());
		//setErelitems(getErelation().getErelations());
	}

	public EntityRelationHeader getERelation(Key key){
		if(key==null) return null;
		this.erelation=null;
		try {
			setErelation(erelationDAO.findById(EntityRelationHeader.class, key));			
			return getErelation();
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public EntityRelationHeader getERelationById(long id){
		if(id==0) return null;
		this.erelation = null;
		try {
			setErelation(erelationDAO.findById(EntityRelationHeader.class, id));
			return getErelation();
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	public EntityRelationHeader fetchERelationById(long id){
		if(id<=0) return null;
		try {
			return erelationDAO.findById(EntityRelationHeader.class, id);

		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	private Boolean ifExistERelationItems(){
		if(getErelitems()!=null){
			//ya est‡ inicializada la lista de items
			if(!getErelitems().isEmpty()) return true;
		}
		//inicializa la lista de items
		if(getErelation()==null) return false;
		setErelitems(getErelation().getErelations());
		if(getErelitems()==null) return false;
		if(getErelitems().isEmpty()) return false;
		return true;
	}	

	public EntityRelations fetchRelationForEntityId(long id){
		if(id<=0) return null;
		if(!ifExistERelationItems()) return null;
		for(EntityRelations eri: getErelitems() ){
			if(eri.getEntityId()==id) return eri;	
		}
		return null;
	}

	
	public PersonaIdeal initNewIperson(){
		setIperson(new PersonaIdeal());
		getIperson().setUserId(getUser().getKey().getId());
		return getIperson();
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
	public Boolean addRelationItem(EntityRelations ritem){
		log.debug("MODEL ADD ENTITY RELATION ITEM TO HEADER");
		//List<EntityRelations> erlist;
		if(ifExistERelationItems()){
			getErelation().getErelations().add(ritem);
			log.debug("MODEL ADD ENTITY RELATION ITEM TO HEADER: relitems already exist");
		}else{
			getErelation().setErelations( new ArrayList<EntityRelations>());
			getErelation().getErelations().add(ritem);

			//getErelation().setErelations(erlist);
			setErelitems(getErelation().getErelations());
			log.debug("MODEL ADD ENTITY RELATION ITEM TO HEADER: relitems initialized");
		}
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

	public Person fetchPersonFromRelationItem(EntityRelations eritem){
		if(eritem==null)return null;
		if(eritem.getEntityType().equals("PF")){
			return fetchFpersonById(eritem.getEntityId());
		}
		if(eritem.getEntityType().equals("PI")){
			return fetchIpersonById(eritem.getEntityId());
		}
		return null;		
	}
	
	public PersonaFisica fetchFpersonFromRelationm(){
		if(getErelation()==null)return null;
		if(getErelation().getFpersonkey()==null) return null;
		return fetchFpersonById(getErelation().getFpersonkey().getId());
	}
	public PersonaIdeal fetchIpersonFromRelationm(){
		if(getErelation()==null) return null;
		if(getErelation().getIpersonkey()==null) return null;
		return fetchIpersonById(getErelation().getIpersonkey().getId());
	}

	
	
	public List<EntityRelationHeader> getRelationsForPersons() {
		this.erelations = new ArrayList<EntityRelationHeader>();
		log.debug("getRelations for Person");
		log.debug("Fpersons:[" + getFpersons() + "]");
		log.debug("Ipersons:[" + getIpersons() + "]");

		if(getFpersons()==null){
			if(getFperson()!=null) {
				setFpersons(new ArrayList<PersonaFisica>());
				getFpersons().add(getFperson());
			}			
		}
		if(getIpersons()==null){
			if(getIperson()!=null) {
				setIpersons(new ArrayList<PersonaIdeal>());
				getIpersons().add(getIperson());
			}			
		}

		try{
			
			if(getFpersons()!=null && !getFpersons().isEmpty()){
				for(PersonaFisica pf: getFpersons()){
					Vector<Object> fvector = new Vector<Object>();
					fvector.add(pf.getKey());
					log.debug("VECTOR: Fpersons:[" + fvector + "]");
					erelations.addAll(erelationDAO.createCollectionQuery(RELATIONS_FOR_FPERSON,fvector ));
				}
			}
			if(getIpersons()!=null  && !getIpersons().isEmpty()) {
				for(PersonaIdeal pi: getIpersons()){
					Vector<Object> ivector = new Vector<Object>();
					ivector.add(pi.getKey());
					log.debug("VECTOR: Ipersons:[" + ivector + "]");
					erelations.addAll(erelationDAO.createCollectionQuery(RELATIONS_FOR_IPERSON, ivector));
				}
			}

		} catch(EntityNotFoundException e) {
		}
		return this.erelations;
		
	}

	
	public void findPersonsLikeName(String name) {
		name = name + "%";
		try {
			setIpersons(ipersonDAO.createCollectionQuery(IPERSON_LIKE_NAME, new Vector<Object>(Arrays.asList(new String[] {name}))));
			setFpersons(fpersonDAO.createCollectionQuery(FPERSON_LIKE_NAME, new Vector<Object>(Arrays.asList(new String[] {name}))));
			
		} catch(EntityNotFoundException e) {

		}
	}

	public PersonaIdeal getIPersonByName(String name) {
		this.iperson = null;
		
		if( !getIPersonsByName(name).isEmpty()){
			setIperson(getIpersons().get(0));
			return getIperson();
		}else{
			return null;
		}
	}
	
	public List<PersonaIdeal> getIPersonsByName(String name) {
		this.ipersons = null;
		
		try {
			setIpersons(ipersonDAO.createCollectionQuery(IPERSON_BY_NAME, new Vector<Object>(Arrays.asList(new String[] {name}))));
		
			return getIpersons();
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<PersonaIdeal>();
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
	
	public List<PersonaIdeal> getIPersonsByOwner(Long userId) {
		this.ipersons = null;
		
		try {
			setIpersons(ipersonDAO.createCollectionQuery(IPERSON_BY_OWNER, new Vector<Object>(Arrays.asList(new Long[] {userId}))));
	
			return getIpersons();
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<PersonaIdeal>();
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

	public List<PersonaIdeal> getIpersonsOwnedByUser(){
		if(getUser()==null) return null;
		List<PersonaIdeal> persons = new ArrayList<PersonaIdeal>();
		persons.addAll(getIPersonsByOwner(getUser().getKey().getId()));
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
	public EntityRelationHeader getErelation() {
		return erelation;
	}
	public void setErelation(EntityRelationHeader erelation) {
		this.erelation = erelation;
	}
	public List<EntityRelationHeader> getErelations() {
		return erelations;
	}
	public void setErelations(List<EntityRelationHeader> erelations) {
		this.erelations = erelations;
	}
	public List<EntityRelations> getErelitems() {
		return erelitems;
	}
	public void setErelitems(List<EntityRelations> erelitems) {
		this.erelitems = erelitems;
	}

}
