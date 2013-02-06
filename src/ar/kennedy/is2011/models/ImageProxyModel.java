package ar.kennedy.is2011.models;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.constants.Constants;
import ar.kennedy.is2011.db.dao.AbstractDao;
import ar.kennedy.is2011.db.entities.AlbumEy;
import ar.kennedy.is2011.db.entities.PictureEy;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.exception.PermissionDeniedException;
import ar.kennedy.is2011.exception.PictureNotFoundException;
import ar.kennedy.is2011.models.AlbumModel;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.utils.WebUtils;

/**
 * @author mlabarinas
 */
public class ImageProxyModel extends AbstractModel {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Session userSession;
	private AbstractDao<PictureEy> pictureDao;
	private AlbumModel albumModel;
	private PictureEy picture;
	
	public ImageProxyModel(HttpServletRequest request, HttpServletResponse response, Session userSession) {
		super();
		
		this.request = request;
		this.response = response;
		this.userSession = userSession;
		this.pictureDao = new AbstractDao<PictureEy>();
		this.albumModel = new AlbumModel();
	}
	
	public void getPicture() throws Exception {
		WebUtils.validateMandatoryParameters(request, new String[] {"pictureid", "version"});
		String picId= WebUtils.getParameter(request, "pictureid");
		String version = WebUtils.getParameter(request, "version");
		//if() 
		picture = pictureDao.findById(PictureEy.class, picId);
		
		if(picture != null) {
			if(validate(picture)) {
				if(picture.getContent() != null) {
					response.setContentType(picture.getContentType());
					
					if("O".equals(version)) {
						log.debug("0.version begin ["+picture.getPictureName()+"]");
						response.getOutputStream().write(picture.getContent().getBytes());
						log.debug("0.version end   ["+picture.getPictureName()+"]");
										
					} else {
						log.debug("else.version begin ["+picture.getPictureName()+"]");
						response.getOutputStream().write(WebUtils.resize(picture.getContent().getBytes(), getVersionWidth(version), getVersionHeight(version)));
						log.debug("else.version end   ["+picture.getPictureName()+"]");
					}
				
				} else {
					response.sendRedirect(picture.getUrl());
				}
			
			} else {
				throw new PermissionDeniedException("User can't access to picture");
			}
		
		} else {
			throw new PictureNotFoundException((new StringBuilder()).append("Picture with id: ").append(picId).append(" not found").toString());
		}
	}
	
	private Boolean validate(PictureEy picture) throws Exception {
		AlbumEy album = albumModel.getAlbumByID(picture.getAlbumId(), picture.getUsername());
		
		return Constants.PUBLIC_VISIBILITY.equals(album.getVisibility()) ? true : ((User) userSession.getElement("user")).getUserName().equals(picture.getUsername());
	}
	
	private Integer getVersionWidth(String version) {
		if("N".equals(version)) {
			return 90;
		
		} else if("I".equals(version)) {
			return 150;

		} else if("F".equals(version)) {
			return 1500;
		
		} else {
			return 100;
		}
	}
	
	private Integer getVersionHeight(String version) {
		if("N".equals(version)) {
			return 90;
		
		} else if("I".equals(version)) {
			return 150;
		
		} else if("F".equals(version)) {
			return 550;

		} else {
			return 100;
		}
	}
	
}