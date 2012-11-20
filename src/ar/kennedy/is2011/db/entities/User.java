package ar.kennedy.is2011.db.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ar.kennedy.is2011.utils.WebUtils;
import org.apache.commons.lang.StringUtils;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;

@Entity
@Table(name = "USER")
public class User implements Serializable {
	
	private static final long serialVersionUID = -2548441551324306490L;
	/** constantes:
		private final String[] sexos ={"MASCULINO","FEMENINO","S/D"};
		private final String[] appRoles ={"EDITOR","EDITOR_RESP","PRODUCTOR","ADMINISTRADOR","ROOT","S/D"};
		private final String[] pregSecretas ={
				"Nombre de tu mascota",
				"Nombre de tu abuela materna",
				"Apellido de soltera de tu madre",
				"Nombre de tu comida favorita",
				"Fecha de casamiento",
				"Nombre del primer colegio",
		};
	*/
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;

	@Column(name = "ACCOUNTS")
	@OneToMany(fetch=FetchType.LAZY,mappedBy="user", orphanRemoval=false, cascade=CascadeType.ALL)
	private List<Account> accounts;

	@Column(name = "DEFAULT_ACCOUNT")
	@OneToOne(fetch=FetchType.LAZY, optional=true, cascade=CascadeType.ALL)
	private Account defaultAccount;

	
	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "MAIL")
	private String mail;
		
	@Column(name = "FECHA_NACIMIENTO")
	private Date fechaNacimiento;
	
	@Column(name = "SEXO")
	private String sexo;
	
	@Column(name = "LOCACION")
	@OneToOne(fetch=FetchType.LAZY, optional=true, cascade=CascadeType.ALL)
	private Location locacion;
	
	@Column(name = "APP_ROLE")
	private String appRole;

	@Column(name = "PICTURE")
	private String profileImageId;

	@Column(name = "PERSON")
	private long personId;
	
	@Column(name = "ID_PREGUNTA_SECRETA")
	private Integer idPreguntaSecreta;
	
	@Column(name = "RESPUESTA_SECRETA")
	private String respuestaPregunta;

	public User() {
		super();
	}

	public User(String nomUsr,String mail,int pSecreta,String respuesta){
	   	this();
		
		this.userName = nomUsr;
	   	this.mail = mail;
	   	this.idPreguntaSecreta = pSecreta;
	   	this.respuestaPregunta = respuesta;
	}

	
	public String toString() {
		if(StringUtils.isNotBlank(this.getUserName()) && StringUtils.isNotBlank(this.getMail())) {
			return new StringBuilder().append("@").append(this.getUserName()).append(" mailto:").append(this.getMail()).toString();
		} else {
			return this.getMail();
		}		
	}


	public Key getKey() {
		return key;
	}


	public void setKey(Key key) {
		this.key = key;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public String getFeNacimAsText() {
		return WebUtils.getFormatedDate(getFechaNacimiento());
	}
	

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public String getSexo() {
		return sexo;
	}


	public void setSexo(String sexo) {
		this.sexo = sexo;
	}


	public Location getLocacion() {
		return locacion;
	}


	public void setLocacion(Location locacion) {
		this.locacion = locacion;
	}


	public String getAppRole() {
		return appRole;
	}


	public void setAppRole(String appRole) {
		this.appRole = appRole;
	}


	public Integer getIdPreguntaSecreta() {
		return idPreguntaSecreta;
	}


	public void setIdPreguntaSecreta(Integer idPreguntaSecreta) {
		this.idPreguntaSecreta = idPreguntaSecreta;
	}


	public String getRespuestaPregunta() {
		return respuestaPregunta;
	}


	public void setRespuestaPregunta(String respuestaPregunta) {
		this.respuestaPregunta = respuestaPregunta;
	}


	public List<Account> getAccounts() {
		return accounts;
	}


	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}


	public Account getDefaultAccount() {
		return defaultAccount;
	}


	public void setDefaultAccount(Account defaultAccount) {
		this.defaultAccount = defaultAccount;
	}

	// HASTA QUE TERMINEMOS DE IMPLEMENTAR PERSONAS, LO AGREGO PARA COMPATIBILIDAD
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	public String getProfileImageId() {
		return profileImageId;
	}

	public void setProfileImageId(String userPictureId) {
		this.profileImageId = userPictureId;
	}

	public long getPersonId() {
		//if(this.personId==0) personId=0;
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}
	


}
	
