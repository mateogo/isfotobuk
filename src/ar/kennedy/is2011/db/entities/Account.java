/**
 * 
 */
package ar.kennedy.is2011.db.entities;

import java.io.Serializable;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.appengine.api.datastore.Key;

/**
 * @author mateogo
 *
 */
@Entity
@Table(name = "USER_ACCOUNT")
public class Account implements Serializable {

	private static final long serialVersionUID = -254411312341234123L;
	private final String[] authProviders ={"FACEBOOK","TWITTER","GOOGLE","THIS"};

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;

	@Column(name = "ID_NAME")
	private String accountName;

	@Column(name = "ID_MAIL")
	private String mail;

	@Column(name = "PASSWD")
	private String passwd;
	
	@Column(name = "AUTHENTICATION")
	private String authProvider;

	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@Column(name = "USER")
	private User user;

	/**
	 * 
	 */
	public Account() {
		// TODO Auto-generated constructor stub
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(String authProvider) {
		this.authProvider = authProvider;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String[] getAuthProviders() {
		return authProviders;
	}
	public String toString(){
		return getAccountName()+" - "+getMail();
	}

}
