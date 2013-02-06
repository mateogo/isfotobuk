package ar.kennedy.is2011.db.dao;

import ar.kennedy.is2011.db.entities.Person;
import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.db.entities.User;

import ar.kennedy.is2011.db.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import com.google.appengine.api.datastore.Key;


public class PersonDao {

	private static final Logger log = Logger.getLogger(AbstractDao.class);

	private static final String FPERSON_BY_NAME =  "SELECT e FROM PersonaFisica e WHERE e.nombrePerson = :1";
	private static final String FPERSON_LIKE_NAME ="SELECT e FROM PersonaFisica e WHERE e.nombrePerson LIKE :1";
	private static final String FPERSON_BY_OWNER=  "SELECT e FROM PersonaFisica e WHERE e.userId = :1";


	private static final String ALL_FPERSONS =     "SELECT e FROM PersonaFisica e ";

	private static final String IPERSON_BY_NAME   = "SELECT e FROM PersonaIdeal e WHERE e.nombrePerson = :1";
	private static final String IPERSON_LIKE_NAME = "SELECT e FROM PersonaIdeal  e WHERE e.nombrePerson LIKE :1";
	private static final String IPERSON_BY_OWNER  = "SELECT e FROM PersonaIdeal e WHERE e.userId = :1";
	
	
	private AbstractDao<PersonaFisica> fpersonDAO = new AbstractDao<PersonaFisica>();
	private AbstractDao<PersonaIdeal>  ipersonDAO = new AbstractDao<PersonaIdeal>();

	private User user;

	public PersonDao() {
		// TODO Auto-generated constructor stub
	}

	public PersonDao(User us) {
		// TODO Auto-generated constructor stub
		this();
		setUser(us);
	}

	public void updateFPerson(PersonaFisica persona){
		try{
			log.debug("=== trying update this.fperson");
			fpersonDAO.persist(persona);
	
		} catch (Exception e) {
			fpersonDAO.rollBackTx();
			log.error(e.getMessage());
	
		}finally{
		}
	}

	public void updateIPerson(PersonaIdeal persona){
		try{
			log.debug("=== trying update this.fperson");
			ipersonDAO.persist(persona);
	
		} catch (Exception e) {
			ipersonDAO.rollBackTx();
			log.error(e.getMessage());
	
		}finally{
		}
	}
	
	public List<PersonaFisica> fetchAllFPersons() {
		
		try {
			return fpersonDAO.createCollectionQuery(ALL_FPERSONS, null);

		} catch(EntityNotFoundException e) {
			return null;
		}
	}


	
	public PersonaFisica fetchFperson(Key key){
		if(key==null) return null;
		try {
			return fpersonDAO.findById(PersonaFisica.class, key);
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	

	public PersonaFisica fetchFpersonById(Long id){
		if(id==null) return null;
		if(id<=0) return null;

		try {
			return fpersonDAO.findById(PersonaFisica.class, id);
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public PersonaIdeal fetchIperson(Key key){
		if(key==null) return null;

		try {
			return ipersonDAO.findById(PersonaIdeal.class, key.getId());
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public PersonaIdeal fetchIpersonById(Long id){
		if(id==null) return null;
		if(id==0) return null;

		try {
			return ipersonDAO.findById(PersonaIdeal.class, id);
	
		} catch(EntityNotFoundException e) {
			return null;
		}
	}


	public List<PersonaFisica> fetchFPersonsByName(String name) {
		
		try {
			return fpersonDAO.createCollectionQuery(FPERSON_BY_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
		
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public List<PersonaFisica> fetchFPersonsLikeName(String name) {
		
		try {
			return fpersonDAO.createCollectionQuery(FPERSON_LIKE_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
		
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}


	public List<PersonaIdeal> fetchIPersonsByName(String name) {
		
		try {
			return ipersonDAO.createCollectionQuery(IPERSON_BY_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
		
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}



	public List<PersonaIdeal> fetchIPersonsLikeName(String name) {
		
		try {
			return ipersonDAO.createCollectionQuery(IPERSON_LIKE_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
		
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	
	public List<PersonaFisica> fetchFPersonsByOwner(Long userId) {

		try {
			return fpersonDAO.createCollectionQuery(FPERSON_BY_OWNER, new Vector<Object>(Arrays.asList(new Long[] {userId})));
				
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	
	public List<PersonaIdeal> fetchIPersonsByOwner(Long userId) {
		
		try {
			return ipersonDAO.createCollectionQuery(IPERSON_BY_OWNER, new Vector<Object>(Arrays.asList(new Long[] {userId})));
				
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

		
	public List<GenericEntity> fetchFEntitiesLike(String name) {
		List<PersonaFisica> persons= fetchFPersonsLikeName(name);
		log.debug("=== Fetch Fpersons: ["+persons+"]");
		
		if(persons==null) return null;
		if(persons.isEmpty()) return null;
	
		return buildEntityList(persons,"PF");
	}

	public String fetchFNamesLike(String name) {
		List<PersonaFisica> persons= fetchFPersonsLikeName (name);
		
		if(persons==null) return null;
		if(persons.isEmpty()) return null;
	
		return buildStringList(persons);
	}

	public List<GenericEntity> fetchIEntitiesLike(String name) {
		List<PersonaIdeal> persons= fetchIPersonsLikeName(name);
		
		if(persons==null) return null;
		if(persons.isEmpty()) return null;
	
		return buildEntityList(persons,"PI");
	}

	public String fetchINamesLike(String name) {
		List<PersonaIdeal> persons= fetchIPersonsLikeName (name);
		
		if(persons==null) return null;
		if(persons.isEmpty()) return null;
	
		return buildStringList(persons);
	}


	private List<GenericEntity> buildEntityList(List<? extends Person>  persons, String type) {
		List<GenericEntity> entities = new ArrayList<GenericEntity>();
		for(Person ar:persons){
			GenericEntity ge = new GenericEntity();
			ge.setPerson(ar);
			ge.setEntityDescription(ar.getComent());
			ge.setEntityLongId(ar.getKey().getId());
			ge.setEntityName(ar.getNombrePerson());
			ge.setEntityStringId(ar.getId());
			ge.setEntityType(type);
			entities.add(ge);
		}
		log.debug("=== GenericEntities: ["+entities+"]");

		return entities;		
	}
	
	private String buildStringList(List<? extends Person>  persons){
		StringBuilder st = new StringBuilder();
		String response;
		for(Person person: persons){
			st.append('\"');
			st.append(person.getNombrePerson());
			st.append('\"');
			st.append(", ");
		}
		response = st.toString();
		log.debug("=== responseString:["+response+"] ["+response.substring(0,response.length()-2)+"]");
		return response.substring(0,response.length()-2);
	}
	
	
	/**  === AUTOGENERATEDE GETTERS - SETTERS: BEGIN ======  */

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	/**  === AUTOGENERATEDE GETTERS - SETTERS: END ========  */

	
}
