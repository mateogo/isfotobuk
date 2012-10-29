package ar.kennedy.is2011.models;

import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.db.dao.AbstractDao;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.AlbumEy;

import com.google.appengine.api.datastore.Key;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;



public class AlbumModel extends AbstractModel {

	private static final String ALBUM_BY_NAME = "SELECT e FROM AlbumEy e WHERE e.albumName = :1";
	private static final String ALBUM_BY_ID   = "SELECT e FROM AlbumEy e WHERE e.albumId = :1";
	private static final String ALL_ALBUMS    = "SELECT e FROM AlbumEy e ";	
	private static final String ALBUMS_BY_VISIBILITY = "SELECT e FROM AlbumEy e WHERE e.visibility = :1";
	private static final String ALBUMS_BY_OWNER =      "SELECT e FROM AlbumEy e WHERE e.owner = :1";
	
	private AbstractDao<AlbumEy> albumDAO = new AbstractDao<AlbumEy>();
	private AlbumEy album;
	private List<AlbumEy> albumes;
	
	public AlbumModel() {
		// TODO Auto-generated constructor stub
		super();
	}
	public AlbumModel(AlbumEy al) {
		this();
		setAlbum(al);
		// TODO Auto-generated constructor stub
	}

	public AlbumEy changeAlbumOwner(User us){
		if(us==null) return getAlbum();
		getAlbum().setOwner(us.getUserName());
		updateAlbum();
		return getAlbum();
		
	}
	public void updateAlbum(){
		try{
			log.debug("=== trying updateAlbum");
			albumDAO.persist(album);
			log.debug("****** udateAlbum:OK ********");

		} catch (Exception e) {
			albumDAO.rollBackTx();
			log.error(e.getMessage());
			//throw new PersistException("Fail to delete entity in database");

		}finally{
		}

	}
	public void updateAlbum(AlbumEy elAlbum){
		setAlbum(elAlbum);
		updateAlbum();
	}
	public void repairAlbum(AlbumEy elAlbum){
		setAlbum(elAlbum);
		if(getAlbum().getAlbumId()==null)   getAlbum().setAlbumId  (getAlbum().getKey().getName());
		if(getAlbum().getAlbumName()==null) getAlbum().setAlbumName(getAlbum().getKey().getName());
		//if(getAlbum().getVisibility()==null)   getAlbum().setVisibility  ("public");
		//if(getAlbum().getAlbumName()==null) getAlbum().setAlbumName(getAlbum().getAlbumId());
		updateAlbum();
	}

	public AlbumEy getUserById(Key key){
		if(key==null) return null;
		try {
			album = albumDAO.findById(AlbumEy.class, key.getId());
			return album;
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	public List<AlbumEy> getAlbumesByName(String name) {
		this.albumes = null;
		
		try {
			albumes = albumDAO.createCollectionQuery(ALBUM_BY_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
			return albumes;
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<AlbumEy>();
		}
	}
	
	public List<AlbumEy> getAlbumesByVisibility(String name) {
		this.albumes = null;
		
		try {
			albumes = albumDAO.createCollectionQuery(ALBUMS_BY_VISIBILITY, new Vector<Object>(Arrays.asList(new String[] {name})));
			return albumes;
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<AlbumEy>();
		}
	}
	public List<AlbumEy> getAlbumesByOwner(String name) {
		this.albumes = null;
		
		try {
			albumes = albumDAO.createCollectionQuery(ALBUMS_BY_OWNER, new Vector<Object>(Arrays.asList(new String[] {name})));
			return albumes;
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<AlbumEy>();
		}
	}

	public List<AlbumEy> getAlbumesByID(String idName) {
		this.albumes = null;
		
		try {
			albumes = albumDAO.createCollectionQuery(ALBUM_BY_ID, new Vector<Object>(Arrays.asList(new String[] {idName})));
			return albumes;
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<AlbumEy>();
		}
	}
/**
 * 
 * @param idName: id del album buscado
 * @param user: opcional, si existen varios albumes, retorno aquel en que el user sea dueno.
 * @return el album encontrado.
 */
	public AlbumEy getAlbumByID(String idName, String user) {
		this.albumes = null;
		
		try {
			albumes = albumDAO.createCollectionQuery(ALBUM_BY_ID, new Vector<Object>(Arrays.asList(new String[] {idName})));
			return selectBestAlbumFromList(albumes,user);
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	private AlbumEy selectBestAlbumFromList(List<AlbumEy> albumes,String user){
		if(albumes==null || albumes.isEmpty()) return null;
		AlbumEy elAlbum = albumes.get(0);
		
		log.debug("selectBestAblum: user:["+user+"] sizeOf albumes:["+albumes.size()+"]");
		for (AlbumEy al:albumes){
			log.debug("=== iterando: owner:["+al.getOwner()+"]");
			if(al.getOwner()==user) elAlbum=al;
		}
		return elAlbum;
	}
	
	
	public List<AlbumEy> getAllAlbumes() {
		this.albumes = null;
		
		try {
			albumes = albumDAO.createCollectionQuery(ALL_ALBUMS, null);
			return getAlbumes();
		} catch(EntityNotFoundException e) {
			return getAlbumes();
		}
	}



	
	
	public AlbumEy getAlbum() {
		if(album==null) album = new AlbumEy();
		return album;
	}

	public void setAlbum(AlbumEy al) {
		this.album = al;
	}

	public List<AlbumEy> getAlbumes() {
		return albumes;
	}

	public void setAlbumes(List<AlbumEy> als) {
		this.albumes = als;
	}


}
