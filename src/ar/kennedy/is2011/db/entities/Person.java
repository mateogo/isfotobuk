/**
 * 
 */
package ar.kennedy.is2011.db.entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
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

	@ManyToOne(fetch=FetchType.LAZY, optional=true, cascade=CascadeType.ALL)
	private List<ContactosPerson> datosContacto;
	
	@Column(name = "COMENT")
	private String coment;

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

}
