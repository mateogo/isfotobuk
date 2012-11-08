package ar.kennedy.is2011.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import ar.kennedy.is2011.constants.Constants;
import ar.kennedy.is2011.db.dao.AbstractDao;
import ar.kennedy.is2011.db.entities.AlbumEy;
import ar.kennedy.is2011.db.entities.PictureEy;
import ar.kennedy.is2011.db.entities.User;
//import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.exception.ValidateMandatoryParameterException;
import ar.kennedy.is2011.picture.MultiPartRequest;
import ar.kennedy.is2011.picture.UploadedFile;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.Aleatory;
import ar.kennedy.is2011.utils.WebUtils;

import com.google.appengine.api.datastore.Blob;

/**
 * @author mlabarinas
 */
public class ImageUploaderModel extends AbstractModel {

	private HttpServletRequest request;
	private Session userSession;
	private AbstractDao<PictureEy> pictureDao;
	private AbstractDao<AlbumEy> albumDao;
	private String action;
	private Map<String, Object> formErrors;
	private PictureEy picture;
	private MultiPartRequest multiPartRequest;
	private AlbumModel albumModel;
	private User user;
	
	
	public ImageUploaderModel(HttpServletRequest request, Session userSession, String action) {
		super();
		this.request = request;
		this.userSession =  userSession;
		this.pictureDao = new AbstractDao<PictureEy>();
		this.albumDao = new AbstractDao<AlbumEy>();
		this.action = action;
		this.formErrors = new HashMap<String, Object>();
		this.albumModel = new AlbumModel();
		this.user = SessionManager.getCurrentUser(request);
	}
	/**
	 * Puede ser una imagen nueva o una pre-existente.
	 * 	si ya existe, viene como request.id o como usersession.picture
	 * @return
	 * @throws Exception
	 */
	public Boolean validate() throws Exception {
		this.picture = "add".equals(action) ? new PictureEy() : 
			pictureDao.findById(PictureEy.class, WebUtils.getParameter(request, "pictureid"));

		this.multiPartRequest = new MultiPartRequest(request, (Constants.FILE_UPLOAD_MAX_SIZE * 1024 * 1024));
		log.debug("validate ImageUploader");
		
		try {
			//(i): Contamos con los parametros picture_name y album_id
			WebUtils.validateMandatoryParameters(multiPartRequest, new String[] {"picture_name", "album_id"});
			validatePictureValidName();
			
			//(iii): valido el album
			String albumId = WebUtils.getParameter(multiPartRequest, "album_id");
			log.debug("validate ImageUploader: album:["+albumId+"]");
			updateAlbum(albumId);

			//(iv): valido URL
			String url = WebUtils.getParameter(multiPartRequest, "url");
			validatePictureValidURL(url);

			//(iv): Upload de la imagen, si viene de cuerpo presente
			uploadContentPicture();

			//(v): incorporo los tags que se pudieran haber cargado en el form
			this.picture.setTags(WebUtils.getParameter(multiPartRequest, "tags"));
			
			//(vi): Picture content o URL
			if(this.picture.getContent() == null && StringUtils.isBlank(this.picture.getUrl())) {
				formErrors.put("add_url_or_file", "Debes cargar una imagen o asociar una URL que contenga una");
			}
			
		} catch(ValidateMandatoryParameterException e) {
			formErrors.put("mandatory_parameters", "Faltan parametros obligatorios");
		}
		
		if(!formErrors.isEmpty()) {
			errors.put("form_errors", formErrors);			
			if(!formErrors.containsKey("mandatory_parameters")) {
				userSession.setElement("picture", this.picture);
			}
			userSession.setElement("errors", errors);
			SessionManager.save(request, userSession);
			return false;
		}else{
			userSession.setElement("picture", this.picture);
			SessionManager.save(request, userSession);			
			return true;
		}
	}
	private void validatePictureValidName(){
		//(ii): picture name is alfanumeric
		String pictureName = WebUtils.getParameter(this.multiPartRequest, "picture_name");
		log.debug("validate ImageUploader: imagen:["+pictureName+"]");
		if(!StringUtils.isAlphanumericSpace(pictureName)) {
			this.formErrors.put("picture_name", "El campo debe ser alfanumerico");
		} else {
			this.picture.setPictureName(pictureName);
		}		
	}
	private void validatePictureValidURL(String url){
		if(StringUtils.isNotBlank(url) && !WebUtils.validateUrl(url)) {
			this.formErrors.put("url", "Url invalida");
		} else {
			this.picture.setUrl(url);
		}
	}
	private void uploadContentPicture(){
		if(this.multiPartRequest.getFiles().hasMoreElements()) {
			log.debug("Upload ContentPicture: begin");
			UploadedFile uploadPicture = (UploadedFile) multiPartRequest.getFiles().nextElement();
			if(uploadPicture.getContent().size() < Constants.ENTITY_WEIGHT) {
				log.debug("Upload ContentPicture: begin-1");
				this.picture.setContentType(uploadPicture.getContentType());
				this.picture.setContent(new Blob(((UploadedFile) multiPartRequest.getFiles().nextElement()).getContent().toByteArray()));
			} else {
				log.debug("Upload ContentPicture: begin-2");
				this.picture.setContentType(uploadPicture.getContentType());
				this.picture.setContent(new Blob(WebUtils.resize(((UploadedFile) multiPartRequest.getFiles().nextElement()).getContent().toByteArray(), 800, 600)));
			}
		}
	}
	

	
	
	private void updateAlbum(String albumId) throws Exception{
		log.debug("**********  updateAlbum:begin albumId:["+albumId+"]");
		
		if("Elegir".equals(albumId)) {
			this.formErrors.put("album_id", "Debe asociar seleccionar un album");
			return;
		}
		
		String albumName=albumId;
		String user =this.user.getUserName();

		AlbumEy elAlbum = albumModel.getAlbumByID(albumName,user);
		
		if(elAlbum==null){
			//Album-no-existe
			log.debug("updateAlbum: album-no-existe, se dar‡ de alta");
			createAlbum(albumId,user);
		}else{
			log.debug("updateAlbum: album-si-existe: ["+elAlbum.getAlbumId()+"]");
			this.picture.setAlbumId(elAlbum.getAlbumId());
		}
	}
	
	private void createAlbum(String albumId, String user) throws Exception {
		
		AlbumEy nuevoAlbum = new AlbumEy();
		
		nuevoAlbum.setAlbumId(albumId);
		nuevoAlbum.setOwner(user);
		nuevoAlbum.setAlbumName(WebUtils.getParameter(this.multiPartRequest, "newalbumname"));
		nuevoAlbum.setVisibility(WebUtils.getParameter(this.multiPartRequest, "newalbumscope"));

		log.debug("Ready to persist new album:["+nuevoAlbum.getAlbumId()+"] ["+nuevoAlbum.getAlbumName()+"] ["+nuevoAlbum.getOwner()+"] ["+nuevoAlbum.getVisibility()+"]");

		this.albumDao.persist(nuevoAlbum);
		this.picture.setAlbumId(albumId);
	}
	
	public void save() throws Exception {
		log.debug("IMAGE UPLOADER: ready to SAVE");

		//PictureEy picture = (PictureEy) userSession.getElement("picture");
		
		this.picture.setPictureId(Aleatory.getAleatoryString(15));
		this.picture.setUsername(this.user.getUserName());
		this.picture.setDateCreated(new Date());
		this.picture.setDateUpdated(this.picture.getDateCreated());
		
		log.debug("SAVE data:["+this.picture.getAlbumId()+"]  ["+this.picture.getPictureId()+"]  ["+this.picture.getTags()+"]  ["+this.picture.getUsername()+"]  ["+this.picture.getPictureName()+"]");
		
		pictureDao.persist(this.picture);
		
		log.debug("SAVE OK");
	}
	
	public void update() throws Exception {
		//PictureEy picture = (PictureEy) userSession.getElement("picture");
		this.picture.setDateUpdated(new Date());
		
		pictureDao.persist(this.picture);
	}
	
	public void delete() throws Exception {
		WebUtils.validateMandatoryParameters(request, new String[] {"pictureid"});
		
		pictureDao.remove(PictureEy.class, WebUtils.getParameter(request, "pictureid"));
	}
	
}