package ar.kennedy.is2011.db.entities;

import java.util.List;
import java.util.Date;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
//import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ar.kennedy.is2011.utils.WebUtils;

import com.google.appengine.api.datastore.Key;
//import com.google.appengine.datanucleus.annotations.Unowned;


/**
 * @author mateogo
 *
 */
@Entity
@Table(name = "ERELATIONAL_HEADER")
public class EntityRelationHeader implements Serializable{

	private static final long serialVersionUID = -254844113243999490L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	@Column(name = "SUBJECT", length=50)
	private String subject;

	@Column(name = "DESCRIPTION", length=140)
	private String description;
	
	@Column(name = "PREDICATE")
	private String predicate;
	
	@Column(name = "CDATE")
	private Date cdate;

	@Column(name = "UDATE")
	private Date udate;

	//ManyToOne(fetch=FetchType.LAZY)

	@Column(name = "OWNER")
	@Basic()
	private Key ownerkey;
	
	@Column(name = "FPERSON")
	@Basic()
	private Key fpersonkey;
	
	@Column(name = "IPERSON")
	@Basic()
	private Key ipersonkey;
	
	@Column(name = "ERITEMS")
	@OneToMany(fetch=FetchType.LAZY,mappedBy="erelation", orphanRemoval=false, cascade=CascadeType.ALL)
	private List<EntityRelations> erelations;
	

	public EntityRelationHeader() {
		// TODO Auto-generated constructor stub
	}

	public EntityRelationHeader(User user){
		this();
		setOwner(user);
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public Date getUdate() {
		return udate;
	}

	public void setUdate(Date udate) {
		this.udate = udate;
	}

	public String toString(){
		return "REL: "+getSubject();
	}
	
	public String getId(){
		if(this.key==null) return "0";
		else return Long.toString(key.getId());
	}

	public String getCDateAsText() {
		return WebUtils.getFormatedDate(getCdate());
	}

	public String getUDateAsText() {
		return WebUtils.getFormatedDate(getUdate());
	}

	public void setOwner(User owner) {
		if(owner==null) setOwnerkey(null);
		else setOwnerkey(owner.getKey());
	}


	public void setFperson(PersonaFisica fperson) {
		if(fperson==null) setFpersonkey(null);
		else setFpersonkey(fperson.getKey());
	}


	public void setIperson(PersonaIdeal iperson) {
		if(iperson==null) setIpersonkey(null);
		else setIpersonkey(iperson.getKey());
	}

	public List<EntityRelations> getErelations() {
		return erelations;
	}

	public void setErelations(List<EntityRelations> erelations) {
		this.erelations = erelations;
	}

	public Key getOwnerkey() {
		return ownerkey;
	}

	public void setOwnerkey(Key ownerkey) {
		this.ownerkey = ownerkey;
	}

	public Key getFpersonkey() {
		return fpersonkey;
	}

	public void setFpersonkey(Key fpersonkey) {
		this.fpersonkey = fpersonkey;
	}

	public Key getIpersonkey() {
		return ipersonkey;
	}

	public void setIpersonkey(Key ipersonkey) {
		this.ipersonkey = ipersonkey;
	}

	
}
