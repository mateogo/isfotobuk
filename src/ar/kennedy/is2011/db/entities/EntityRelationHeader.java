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
	private Long ownerId;

	@Column(name = "ETYPE")
	private String entityType;

	@Column(name = "EID")
	private Long entityId;

	@Column(name = "PICTURE_ID")
	private String pictureId;
	
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
		if(owner==null) setOwnerId(null);
		else setOwnerId(owner.getKey().getId());
	}



	/** ===== properties-getters and setters =========== */
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
		setUdate(this.cdate);
	}

	public Date getUdate() {
		return udate;
	}

	public void setUdate(Date udate) {
		this.udate = udate;
	}

	public List<EntityRelations> getErelations() {
		return erelations;
	}

	public void setErelations(List<EntityRelations> erelations) {
		this.erelations = erelations;
	}


	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public void setEntityId(PersonaFisica fperson) {
		if(fperson==null) this.entityId = null;
		else setEntityId(fperson.getKey().getId());
	}
	public void setEntityId(PersonaIdeal iperson) {
		if(iperson==null) this.entityId = null;
		else setEntityId(iperson.getKey().getId());
	}

	
	
	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	
}
