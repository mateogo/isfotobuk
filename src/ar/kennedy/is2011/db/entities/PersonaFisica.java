/**
 * 
 */
package ar.kennedy.is2011.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import java.util.Date;

/**
 * @author mateogo
 *
 */
@Entity
@Table(name = "PERSONA_FISICA")
public class PersonaFisica extends Person {
	
	private static final long serialVersionUID = -2548441551324306490L;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "APELLIDO")
	private String apellido;
	
	@Column(name = "FECHA_NACIMIENTO")
	private Date fechaNacimiento;
	
	@Column(name = "SEXO")
	private String sexo;
	
	@OneToOne(fetch=FetchType.LAZY, optional=true, cascade=CascadeType.ALL)
	@Column(name = "LOC_NACIMIENTO")
	private Location nacimLocation;
	

	public PersonaFisica() {	
		// TODO Auto-generated constructor stub
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		if(nombre==null) nombre = getNombrePerson();
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(Date fechaNacimiento) {
		if(fechaNacimiento==null) fechaNacimiento = new Date();
		this.fechaNacimiento = fechaNacimiento;
	}


	public String getSexo() {
		return sexo;
	}


	public void setSexo(String sexo) {
		if(sexo==null) sexo="S/D";
		this.sexo = sexo;
	}


	public Location getNacimLocation() {
		return nacimLocation;
	}


	public void setNacimLocation(Location nacimLocation) {
		if(nacimLocation == null){
			nacimLocation=new Location();
			nacimLocation.setProvincia("CABA");
			nacimLocation.setPais("ARGENTINA");
		}
		nacimLocation.setLocType("EVENTO");
		nacimLocation.setEventoType("NACIMIENTO");
		
		this.nacimLocation = nacimLocation;
	}

	
}
