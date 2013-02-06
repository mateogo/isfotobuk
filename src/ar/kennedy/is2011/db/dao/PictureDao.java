package ar.kennedy.is2011.db.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


import ar.kennedy.is2011.db.dao.AbstractDao;
import ar.kennedy.is2011.db.entities.AlbumEy;
import ar.kennedy.is2011.db.entities.PictureEy;
import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.utils.WebUtils;

/**
 * @author mgo
 */
public class PictureDao {
	
	private static final String PICTURE_BY_USER_QUERY     = "SELECT e FROM PictureEy e WHERE e.username = :1";
	private static final String PICTURE_BY_ALBUM_QUERY    = "SELECT e FROM PictureEy e WHERE e.albumId = :1";
	private static final String LAST_PICTURE_BY_USER      = "SELECT e FROM PictureEy e WHERE e.username = :1 ORDER BY e.dateCreated DESC";
	private static final String PICTURES_BY_NAME          = "SELECT e FROM PictureEy e WHERE e.pictureName = :1";
	private static final String PICTURES_LIKE_NAME        = "SELECT e FROM PictureEy e WHERE e.pictureName LIKE :1";
	
	private static final String ALBUMS_BY_VISIBILITY      = "SELECT a FROM AlbumEy a WHERE a.visibility = :1";
	private static final String ALBUMS_BY_OWNER           = "SELECT a FROM AlbumEy a WHERE a.owner = :1";
	

	private AbstractDao<PictureEy> pictureDao;
	private AbstractDao<AlbumEy> albumDao;

	
	public PictureDao() {
		super();
		
		this.pictureDao = new AbstractDao<PictureEy>();
		this.albumDao = new AbstractDao<AlbumEy>();
	}
	
	public List<PictureEy> fetchPicturesByUsername(String username) {
		
		try {
			return pictureDao.createCollectionQuery(PICTURE_BY_USER_QUERY, new Vector<Object>(Arrays.asList(new String[] {username})));
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	
	public PictureEy fetchLastPictureUploadByUser(String username) {
		List<PictureEy> pictures = null;
		
		try {
			pictures = pictureDao.createCollectionQuery(LAST_PICTURE_BY_USER, new Vector<Object>(Arrays.asList(new String[] {username})));
		
			if(pictures == null) return null;
			return pictures.size() > 0 ? pictures.get(0) : null;
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}


	public PictureEy fetchPicture(String pictureId) {
		if(WebUtils.isNull(pictureId)) return null;

		try {
			return pictureDao.findById(PictureEy.class, pictureId);

		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	

	public List<AlbumEy> fetchAlbumsByUser(String username) {
		if(WebUtils.isNull(username)) return null;

		try {
			return albumDao.createCollectionQuery(ALBUMS_BY_OWNER, new Vector<Object>(Arrays.asList(new String[] {username})));
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	
	public List<AlbumEy> fetchAlbumsByVisibility(String visibility) {
		if(WebUtils.isNull(visibility)) return null;

		try {
			return albumDao.createCollectionQuery(ALBUMS_BY_VISIBILITY, new Vector<Object>(Arrays.asList(new String[] {visibility})));
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public List<AlbumEy> fetchPublicAlbums() {
		return fetchAlbumsByVisibility("public");
	}

		
	
	public List<AlbumEy> fetchAllAlbums() {
		
		try {
			return albumDao.select(AlbumEy.class);
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	
	
	public List<PictureEy> fetchPicturesByAlbum(String albumId) {
		List<PictureEy> pictures = null;

		try {
			pictures = pictureDao.createCollectionQuery(PICTURE_BY_ALBUM_QUERY, new Vector<Object>(Arrays.asList(new String[] {albumId})));

		} catch (EntityNotFoundException e) {
			return null;
		}

		return pictures;
	}
	
	public List<PictureEy> fetchPicturesByName(String name) {
		try {
			return pictureDao.createCollectionQuery(PICTURES_BY_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
		
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	
	
	public List<PictureEy> fetchPicturesLikeName(String name) {
		try {
			return pictureDao.createCollectionQuery(PICTURES_LIKE_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
		
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	
	public List<GenericEntity> fetchEntitiesLikeName(String name) {
		List<PictureEy> pictures= fetchPicturesLikeName (name);
		
		if(pictures==null) return null;
		if(pictures.isEmpty()) return null;
	
		return buildEntityList(pictures);
	}

	public String fetchPictureNamesLike(String name) {
		List<PictureEy> pictures= fetchPicturesLikeName (name);
		
		if(pictures==null) return null;
		if(pictures.isEmpty()) return null;
	
		return buildStringList(pictures);
	}
	
	private String buildStringList(List<PictureEy>  pictures){
		StringBuilder st = new StringBuilder();
		String response;
		for(PictureEy picture: pictures){
			st.append('\"');
			st.append(picture.getPictureName());
			st.append('\"');
			st.append(", ");
		}
		response = st.toString();
		return response.substring(0,response.length()-2);
	}
	
	private List<GenericEntity> buildEntityList(List<PictureEy>  pictures) {
		List<GenericEntity> entities = new ArrayList<GenericEntity>();
		for(PictureEy ar:pictures){
			GenericEntity ge = new GenericEntity();
			ge.setPicture(ar);
			ge.setEntityDescription(ar.getPictureName());
			ge.setEntityStringId(ar.getPictureId());
			ge.setEntityName(ar.getPictureName());
			ge.setEntityType("PICTURE");
			entities.add(ge);
		}
		return entities;		
	}
	
}