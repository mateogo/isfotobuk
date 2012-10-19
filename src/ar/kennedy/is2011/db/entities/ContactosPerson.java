/**
 * 
 */
package ar.kennedy.is2011.db.entities;

import java.io.Serializable;

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
@Table(name = "CONTACTOS_PERSON")
public class ContactosPerson implements Serializable {


	private static final long serialVersionUID = -2548441551324306490L;

	private final String[] conTypes = {"MAIL","TEL","CEL","RSOC","VOIP","FAX"};
	private final String[] useTypes = {"PARTICULAR","LABORAL","OTRA"};
	private final String[] rsocProviders ={"FACEBOOK","TWITTER","LINKEDIN","OTRO"};
	private final String[] celProviders ={"MOVISTAR","PERSONAL","CLARON","OTRO"};
	private final String[] telProviders ={"TELEFONICA","TELECOM","OTRO"};
	private final String[] voipProviders ={"SKYPE","METROTEL","IPLAN","OTRO"};

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;

	@Column(name = "VALUE")
	private String value ;
	
	@Column(name = "PERSON")
	private String person ;

	@Column(name = "DESCR")
	private String descr ;
	@Column(name = "CON_TYPE")
	private String conType ;
	@Column(name = "USE_TYPE")
	private String useType ;
	@Column(name = "PROVIDER")
	private String provider ;
	@Column(name = "PROTOCOL")
	private String protocol ;
	
	/**
	 * 
	 */
	public ContactosPerson() {
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String toString(){
		return getConType()+": ["+getValue()+"] ["+getUseType()+"]";
	}

}
