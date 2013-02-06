
package ar.kennedy.is2011.db.entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.google.appengine.api.datastore.Key;

/**
 * @author mateogo
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "PERSON")
public class Person implements Serializable {

	private static final long serialVersionUID = -2548441551324306490L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;

	@Column(name = "NOMBRE_PERSON")
	private String nombrePerson;

	@Column(name = "DATOS_CONTACTO")
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<ContactosPerson> datosContacto;

	@Column(name = "LOCACIONES")
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Location> locations;
	
	@Column(name = "COMENT")
	private String coment;
	
	@Column(name = "OWNER")
	private long userId;
	
	@Column(name = "PICTURE")
	private String defaultImageId;

	@Column(name = "URL")
	private String defaultUrl;

	public String getNombrePerson() {
		return nombrePerson;
	}

	public void setNombrePerson(String nombrePerson) {
		this.nombrePerson = nombrePerson;
	}

	public String getComent() {
		return coment;
	}

	public void setComent(String coment) {
		this.coment = coment;
	}

	public Key getKey() {
		return key;
	}
	
	public String getId(){
		if(key==null) return "0";
		else return Long.toString(getKey().getId());
	}

	public List<ContactosPerson> getDatosContacto() {
		if (datosContacto==null) datosContacto = new ArrayList<ContactosPerson>();
		return datosContacto;
	}

	public void setDatosContacto(List<ContactosPerson> datosContacto) {
		this.datosContacto = datosContacto;
	}
	
	public void addContacto(ContactosPerson contacto){
		getDatosContacto().add(contacto);
	}

	public String  toString(){
		return getNombrePerson();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public String getDefaultImageId() {
		return defaultImageId;
	}

	public void setDefaultImageId(String defaultImageId) {
		this.defaultImageId = defaultImageId;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

}
