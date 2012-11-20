package ar.kennedy.is2011.db.entities;

import java.util.Date;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ar.kennedy.is2011.utils.WebUtils;

import com.google.appengine.api.datastore.Key;

@Entity
@Table(name = "ENTITY_RELATIONS")
public class EntityRelations implements Serializable {

	private static final long serialVersionUID = -2548441551324306490L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	@Column(name = "RELHEADER")
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	private EntityRelationHeader erelation;
	
	@Column(name = "ETYPE")
	private String entityType;

	@Column(name = "EID")
	private Long entityId;
	
	@Column(name = "CARDINALITY")
	private Integer cardinality;
	
	@Column(name = "COMMENT")
	private String comment;
	
	@Column(name = "STATUS")
	private Boolean status;
	
	@Column(name = "CDATE")
	private Date cdate;

	@Column(name = "UDATE")
	private Date udate;	
	

	public EntityRelations() {
		// TODO Auto-generated constructor stub
		
	}


	public Key getKey() {
		return key;
	}


	public void setKey(Key key) {
		this.key = key;
	}


	public EntityRelationHeader getErelation() {
		return erelation;
	}


	public void setErelation(EntityRelationHeader erelation) {
		this.erelation = erelation;
	}


	public String getEntityType() {
		return entityType;
	}


	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}


	public Long getEntityId() {
		return entityId;
	}


	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}


	public Integer getCardinality() {
		return cardinality;
	}


	public void setCardinality(Integer cardinality) {
		this.cardinality = cardinality;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
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
		return getId()+": "+getComment();
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
	
}
