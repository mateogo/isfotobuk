/**
 * 
 */
package ar.kennedy.is2011.db.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.appengine.api.datastore.Key;

/**
 * @author mateogo
 *
 */
@Entity
@Table(name = "LOCATION")
public class Location implements Serializable {

	private static final long serialVersionUID = -2548441551324306490L;

	private final String[] locTypes ={"PARTICULAR","SEDE","EVENTO"};
	private final String[] sedeTypes ={"LEGAL","ADMINISTRACION","DEPOSITO","SALA_PUBLICA","OTRA"};
	private final String[] eventoTypes ={"NACIMIENTO","MUERTE","ESPECTACULO","OTRO"};

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	
	@Column(name = "PAIS")
	private String pais;
	
	@Column(name = "PROVINCIA")
	private String provincia;

	@Column(name = "LOCALIDAD")
	private String localidad;

	@Column(name = "CPOSTAL")
	private String cpostal;

	@Column(name = "CALLE1")
	private String calle1;

	@Column(name = "CALLE2")
	private String calle2;

	@Column(name = "DESCR")
	private String descr ;

	@Column(name = "LOC_TYPE")
	private String locType ;
	@Column(name = "SEDE_TYPE")
	private String sedeType ;
	@Column(name = "EVENTO_TYPE")
	private String eventoType ;

	@Column(name = "FECHA_LOCACION")
	private Date fechaLocacion;

	
	/**
	 * 
	 */
	public Location() {
		// TODO Auto-generated constructor stub
	}

	public Location(String pais, String prov, String loc) {
		setPais(pais);
		setLocalidad(loc);
		setProvincia(prov);
		// TODO Auto-generated constructor stub
	}


	public String getPais() {
		return pais;
	}


	public void setPais(String pais) {
		this.pais = pais;
	}


	public String getProvincia() {
		return provincia;
	}


	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	public String getLocalidad() {
		return localidad;
	}


	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}


	public String getCpostal() {
		return cpostal;
	}


	public void setCpostal(String cpostal) {
		this.cpostal = cpostal;
	}


	public String getCalle1() {
		return calle1;
	}


	public void setCalle1(String calle1) {
		this.calle1 = calle1;
	}


	public String getCalle2() {
		return calle2;
	}


	public void setCalle2(String calle2) {
		this.calle2 = calle2;
	}


	public String getDescr() {
		return descr;
	}


	public void setDescr(String descr) {
		this.descr = descr;
	}


	public String getLocType() {
		return locType;
	}


	public void setLocType(String locType) {
		this.locType = locType;
	}


	public String getSedeType() {
		return sedeType;
	}


	public void setSedeType(String sedeType) {
		this.sedeType = sedeType;
	}


	public String getEventoType() {
		return eventoType;
	}


	public void setEventoType(String eventoType) {
		this.eventoType = eventoType;
	}


	public Date getFechaLocacion() {
		return fechaLocacion;
	}


	public void setFechaLocacion(Date fechaLocacion) {
		this.fechaLocacion = fechaLocacion;
	}


	public String[] getLocTypes() {
		return locTypes;
	}


	public String[] getSedeTypes() {
		return sedeTypes;
	}


	public String[] getEventoTypes() {
		return eventoTypes;
	}
	public String toString(){
		return "Locacion: ["+getDescr()+"]  pais:["+getPais()+"]";
	}

}
