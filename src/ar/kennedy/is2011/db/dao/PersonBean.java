package ar.kennedy.is2011.db.dao;

import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.db.entities.Person;
import ar.kennedy.is2011.db.entities.ContactosPerson;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.Location;
import ar.kennedy.is2011.db.entities.EntityRelationHeader;
import ar.kennedy.is2011.models.PersonModel;

import java.util.List;


public class PersonBean {

	private User user;
	private PersonaFisica fperson;
	private PersonaIdeal iperson;
	private EntityRelationHeader erelation;
	
	private List<PersonaFisica> fpersons;
	private List<PersonaIdeal> ipersons;
	private List<ContactosPerson> contactos;
	private List<Location> locations;
	private List<EntityRelationHeader> erelations;

	private String action;
	private String personToEditId;

	public PersonBean() {
		// TODO Auto-generated constructor stub
	}

	public PersonBean(User us, String ac) {
		// TODO Auto-generated constructor stub
		setUser(us);
		setAction(ac);
	}

	public String fetchPersonName(EntityRelationHeader relation){
		PersonModel pmodel = new PersonModel();
		return  pmodel.fetchPersonNameFromRelation(relation);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PersonaFisica getPerson() {
		return fperson;
	}

	public void setPerson(PersonaFisica person) {
		this.fperson = person;
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

	public PersonaFisica getFperson() {
		return fperson;
	}

	public void setFperson(PersonaFisica fperson) {
		this.fperson = fperson;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPersonToEditId() {
		return personToEditId;
	}

	public void setPersonToEditId(String personToEditId) {
		this.personToEditId = personToEditId;
	}

	public List<ContactosPerson> getContactos() {
		return contactos;
	}

	public void setContactos(List<ContactosPerson> contactos) {
		this.contactos = contactos;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public PersonaIdeal getIperson() {
		return iperson;
	}

	public void setIperson(PersonaIdeal iperson) {
		this.iperson = iperson;
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

}
