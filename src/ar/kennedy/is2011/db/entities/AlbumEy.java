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
 * @author mlabarinas
 */
@Entity
@Table(name = "ALBUM")
public class AlbumEy implements Serializable {

	private static final long serialVersionUID = 7596454954378729377L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;

	@Column(name = "ALBUM_ID")
	private String albumId;
	
	@Column(name = "ALBUM_NAME")
	private String albumName;

	@Column(name = "VISIBILITY")
	private String visibility;
	
	@Column(name = "OWNER")
	private String owner;
	
	public AlbumEy() {
		super();
	}
	public String toString(){
		return "AlbumID: ["+getAlbumId()+"] Name:["+getAlbumName()+"]";
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String aName) {
		this.albumName = aName;
	}



	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	
}