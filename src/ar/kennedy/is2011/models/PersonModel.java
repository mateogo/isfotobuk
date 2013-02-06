package ar.kennedy.is2011.models;

import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.db.dao.AbstractDao;
import ar.kennedy.is2011.db.dao.UserAccount;
import ar.kennedy.is2011.db.dao.PersonDao;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.db.entities.Person;
import ar.kennedy.is2011.db.entities.EntityRelationHeader;
import ar.kennedy.is2011.db.entities.EntityRelations;

import ar.kennedy.is2011.db.entities.Location;
import ar.kennedy.is2011.db.entities.ContactosPerson;
import ar.kennedy.is2011.utils.WebUtils;

import com.google.appengine.api.datastore.Key;
//import com.google.appengine.api.datastore.KeyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;


public class PersonModel extends AbstractModel {


	private static final String RELATIONS_FOR_PERSON  =  "SELECT e FROM EntityRelationHeader e WHERE e.entityType = :1 AND e.entityId = :2 ";
	private static final String RELATIONS_FOR_PICTURE =  "SELECT e FROM EntityRelationHeader e WHERE e.ownerId = :1 and e.pictureId = :2";

	
	private AbstractDao<User> userDAO = new AbstractDao<User>();
	private AbstractDao<Location> locDAO = new AbstractDao<Location>();
	private AbstractDao<EntityRelationHeader> erelationDAO = new AbstractDao<EntityRelationHeader>();

	private PersonDao persondao = new PersonDao();
	
	private User user;
	private AccountModel accountModel;

	private PersonaFisica fperson;
	private PersonaIdeal  iperson;
	private String        personType;

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

	public PersonaFisica getFpersonById(Long id){
		setFperson(persondao.fetchFpersonById(id));
		return getFperson();
	}

	public PersonaIdeal getIpersonById(Long id){
		setIperson(persondao.fetchIpersonById(id));
		return getIperson();
	}
	
	public void updateFPerson(){
		persondao.updateFPerson(getFperson());		
	}

	public void updateIPerson(){
		persondao.updateIPerson(getIperson());		
	}

	public void updatePerson(){
		if(getPersonType().equals("PF")) persondao.updateFPerson(getFperson());		
		if(getPersonType().equals("PI")) persondao.updateIPerson(getIperson());		
	}

	public void searchPersonById(String personId){
		if(personId==null) return;
		if(WebUtils.isNotNull(personId)){
			this.iperson=null;
			this.fperson=null;
			long pId = Long.parseLong(personId);
			if (pId<=0) return;
			setFperson(persondao.fetchFpersonById(pId));
			setIperson(persondao.fetchIpersonById(pId));
		}
	}
	
	
	public PersonaFisica getPersonFromUser(){
		if(accountModel.getUser()==null) return null;

		long personId=accountModel.getUser().getPersonId();
		if(personId<=0) return initDefaultPersonForUser();
	
		setFperson(persondao.fetchFpersonById(personId));
		if(getFperson()==null) return initDefaultPersonForUser();
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
		getErelation().setPredicate("CONOCE_A");
				
		return getErelation();
	}

	public void initNewEntityRelationItems(){
		//getErelation().setErelations(new ArrayList<EntityRelations>());
		//setErelitems(getErelation().getErelations());
	}
	public void initNewRelationList(){
		getErelation().setErelations(new ArrayList<EntityRelations>());
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

		if(getPerson()==null) return false;
		
		List<ContactosPerson> contactos= getPerson().getDatosContacto();
		if(contactos==null) contactos = new ArrayList<ContactosPerson>();
		contactos.add(ct);
		getPerson().setDatosContacto(contactos);
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

		if(getPerson()==null) return false;
		
		List<Location> locations= getPerson().getLocations();
		if(locations==null) locations = new ArrayList<Location>();
		locations.add(loc);
		getPerson().setLocations(locations);
		log.debug("MODEL ADD LOCATIONS: end");
		return true;
	}
	
	public Location getLocationFromPersonById(Long locId){
		if(locId<=0) return null;
		
		List<Location> locations = getPerson().getLocations();
		if(locations==null) return null;
		if(locations.isEmpty()) return null;
		for(Location loc:locations){
			if(loc.getKey().getId()==locId) return loc;
		}
		return null;
	}

	
	
	public ContactosPerson getContactFromPersonById(Long ctId){
		if(ctId<=0) return null;
		
		List<ContactosPerson> contactos = getPerson().getDatosContacto();
		if(contactos==null) return null;
		if(contactos.isEmpty()) return null;
		for(ContactosPerson ct:contactos){
			if(ct.getKey().getId()==ctId) return ct;
		}
		return null;
	}
	
	public PersonaFisica getFPersonByName(String name) {
		this.fperson = null;

		List<PersonaFisica> persons = persondao.fetchFPersonsByName(name);

		if(persons==null || persons.isEmpty()) return null;
		else setFperson(persons.get(0));
		
		return getFperson();
	}

	public EntityRelationHeader fetchRelationForPicture(User user, String pictureId) {
		this.erelation=null;
		getErelationsForPicture(user,pictureId);
		if(getErelations()==null|| getErelations().isEmpty()) return null;
		setErelation(getErelations().get(0));
		return getErelation();
	}

	public List<EntityRelationHeader> getErelationsForPicture(User user, String pictureId) {
		this.erelations = null;
		Vector<Object> fvec = new Vector<Object>();
		fvec.add(user.getKey().getId());
		fvec.add(pictureId);
		try {
			setErelations(erelationDAO.createCollectionQuery(RELATIONS_FOR_PICTURE, fvec));
			return getErelations();
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	
	public Person fetchPersonFromRelationItem(EntityRelations eritem){
		if(eritem==null)return null;
		if(eritem.getEntityType().equals("PF")){
			return persondao.fetchFpersonById(eritem.getEntityId());
		}
		if(eritem.getEntityType().equals("PI")){
			return persondao.fetchIpersonById(eritem.getEntityId());
		}
		return null;		
	}

	public String fetchPersonNameFromRelation(EntityRelationHeader relation){
		Person person = fetchPersonFromRelation(relation);
		if(person==null) return null;
		else return person.getNombrePerson();
	}

	public Person fetchPersonFromRelation(EntityRelationHeader relation){
		if(relation==null)return null;
		if(relation.getEntityType()==null) return null;
		
		if(relation.getEntityType().equals("PF")){
			return persondao.fetchFpersonById(relation.getEntityId());
		}
		if(relation.getEntityType().equals("PI")){
			return persondao.fetchIpersonById(relation.getEntityId());
		}
		return null;		
	}
	
	public PersonaFisica fetchFpersonFromRelation(){
		if(getErelation()==null)return null;
		if(getErelation().getEntityId()==null) return null;
		if(getErelation().getEntityType()==null) return null;
		if(!getErelation().getEntityType().equals("PF")) return null;

		return persondao.fetchFpersonById(getErelation().getEntityId());
	}

	public PersonaIdeal fetchIpersonFromRelation(){
		if(getErelation()==null)return null;
		if(getErelation().getEntityId()==null) return null;
		if(getErelation().getEntityType()==null) return null;
		if(!getErelation().getEntityType().equals("PI")) return null;

		return persondao.fetchIpersonById(getErelation().getEntityId());
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
					fvector.add("PF");
					fvector.add(pf.getKey().getId());
					log.debug("VECTOR: Fpersons:[" + fvector + "]");
					erelations.addAll(erelationDAO.createCollectionQuery(RELATIONS_FOR_PERSON,fvector ));
				}
			}
			if(getIpersons()!=null  && !getIpersons().isEmpty()) {
				for(PersonaIdeal pi: getIpersons()){
					Vector<Object> ivector = new Vector<Object>();
					ivector.add("PI");
					ivector.add(pi.getKey().getId());
					log.debug("VECTOR: Ipersons:[" + ivector + "]");
					erelations.addAll(erelationDAO.createCollectionQuery(RELATIONS_FOR_PERSON, ivector));
				}
			}

		} catch(EntityNotFoundException e) {
		}
		return this.erelations;
		
	}

	
	
	public Boolean ifExistFPersonByName(String name) {
		if(persondao.fetchFPersonsByName(name).isEmpty()) return false;
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
		if(getPerson()==null) return initContactList();

		List<ContactosPerson> contactos= getPerson().getDatosContacto();
		if(contactos==null) return initContactList();
		if(contactos.isEmpty()) return initContactList();
		
		return contactos;
	}

	public List<Location> getLocationList(){
		if(getPerson()==null) return initLocationList();

		List<Location> locations= getPerson().getLocations();
		
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
	
	public void findPersonsLikeName(String name) {
		name = name + "%";
		setIpersons(persondao.fetchIPersonsLikeName(name));
		setFpersons(persondao.fetchFPersonsLikeName(name));

	}

	public PersonaIdeal getIPersonByName(String name) {
		this.iperson = null;
		List<PersonaIdeal> persons = persondao.fetchIPersonsByName(name);
		
		if(persons==null || persons.isEmpty()) return null;
		else setIperson(persons.get(0));
		
		return getIperson();
	}


	
	private List<ContactosPerson> initContactList(){
		List<ContactosPerson> contactos = new ArrayList<ContactosPerson>();
		ContactosPerson ct = new ContactosPerson();
		ct.setConType("MAIL");
		ct.setDescr("nuevo dato de contacto");
		ct.setValue("sunombre@suproveedor.com");
		contactos.add(ct);
		return contactos;
	}
	
	
	public List<PersonaFisica> getFpersonsOwnedByUser(){
		if(getUser()==null) return null;

		List<PersonaFisica> persons = new ArrayList<PersonaFisica>();
		persons.addAll(persondao.fetchFPersonsByOwner(getUser().getKey().getId()));
		
		if(persons.isEmpty()){
			persons.add(getPersonFromUser());
		}
		return persons;
	}

	public List<PersonaIdeal> getIpersonsOwnedByUser(){
		if(getUser()==null) return null;

		List<PersonaIdeal> persons = new ArrayList<PersonaIdeal>();
		persons.addAll(persondao.fetchIPersonsByOwner(getUser().getKey().getId()));

		return persons;
	}

	public Person getPerson(){
		if(getFperson()==null && getIperson()==null) return null;
		if(getPersonType()==null){
			if(getFperson()!=null) return getFperson();
			if(getIperson()!=null) return getIperson();
		}else{
			if(getPersonType().equals("PF")) return getFperson();
			if(getPersonType().equals("PI")) return getIperson();
		}	
		return null;
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
		if(fperson!=null) setPersonType("PF");
		this.fperson = fperson;
	}
	public PersonaIdeal getIperson() {
		return iperson;
	}
	public void setIperson(PersonaIdeal iperson) {
		if(iperson!=null) setPersonType("PI");
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
	public String getPersonType() {
		return personType;
	}
	private void setPersonType(String personType) {
		this.personType = personType;
	}

}
