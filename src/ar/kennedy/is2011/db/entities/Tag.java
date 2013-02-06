package ar.kennedy.is2011.db.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import com.google.appengine.api.datastore.Key;

/**
 * @author mgo
 */
@Entity
@Table(name = "TAG")
public class Tag implements Serializable {

	private static final long serialVersionUID = 7596454954378729377L;
	//private final String[] locTypes ={"GENERICA","ALBUM","GRUPO"};

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "NNAME")
	private String nname;

	@Column(name = "VISIBILITY")
	private Integer visibility;

	@Column(name = "ACTIVE")
	private Integer isActive;

	@Column(name = "OWNER")
	@Basic()
    private Key owner;
	
	@Column(name = "TYPE")
    private String type;

	@Column(name = "RENDERING")
    private String rendering;
	
	@Column(name = "UDATE")
    private Date udate;

	public Tag() {
		super();
	}
	public String toString(){
		return getName();
	}

	public String showDetails(){
		return getName()+":"+getType()+":"+getRendering();
	}

	/** ======= Autogenerated Getters y Setters =========== */
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNname() {
		return nname;
	}
	public void setNname(String nname) {
		this.nname = nname;
	}
	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}
	public Key getOwner() {
		return owner;
	}
	public void setOwner(Key owner) {
		this.owner = owner;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRendering() {
		return rendering;
	}
	public void setRendering(String rendering) {
		this.rendering = rendering;
	}
	public Date getUdate() {
		return udate;
	}
	public void setUdate(Date udate) {
		this.udate = udate;
	}
	
}