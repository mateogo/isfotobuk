package ar.kennedy.is2011.views;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ar.kennedy.is2011.db.dao.AdministrarRegistracionUsuarioDAOImpl;
import ar.kennedy.is2011.db.entities.*;
import ar.kennedy.is2011.models.SearchPicturesModel;

public class PublicProfileView {	

	private HttpServletRequest request;
	private Usuario user;
	protected final Logger log = Logger.getLogger(getClass());
		
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<PictureEy> getAllPicturesListByAlbumId(String albumId) {
				
		SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
		return searchPicturesModel.getPictureByAlbum(albumId);
	}
	
	public String getHTMLContent_Encabezado(){
		AdministrarRegistracionUsuarioDAOImpl datos = new AdministrarRegistracionUsuarioDAOImpl();
		StringBuilder sb = new StringBuilder();
		user = datos.buscarUsuario(request.getAttribute("usuario").toString()); 

		sb.append("<div class=''>");
		
		if (request.getAttribute("album") != null)
		{
			sb.append("Album " + request.getAttribute("album").toString() + " de " + user.getNombreUsr());
		}
		else
		{
			sb.append("Perfil publico de " + user.getNombreUsr());
		}
		
		sb.append("</div>");
		return sb.toString();
	}
	
	public String getHTMLContent_SubEncabezado(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div class=''>");
		
		if (request.getAttribute("album") != null)
		{
			sb.append("Fotos");
		}
		else
		{ 		
			sb.append("Generales");									
		} 	
		
		sb.append("</div>");
		return sb.toString();
	}
	
	public String getHTMLContent_Datos()
	{		
		log.debug("*********************************************");
		StringBuilder sb = new StringBuilder();
		
		if (request.getAttribute("album") != null){
			log.debug("Request has ALBUM...");
		 	List<PictureEy> pictures = this.getAllPicturesListByAlbumId(request.getAttribute("album").toString());
		 	
		 	for (PictureEy picture : pictures) {	
		 		sb.append("<div class='well'>");
		 		sb.append("<ul class='media-grid'>");
		 		sb.append("<li>");
		 		sb.append("<div class='row'>");
		 		sb.append("<div class='span3'>");
	
				log.debug("            Found! pictureid:["+picture.getPictureId()+"]");
				
				sb.append("<a href='/secure/pictureView.jsp?pictureid=");
				sb.append(picture.getPictureId());sb.append("'>");
				
				sb.append("<img class='thumbnail' src='/image?pictureid=");
				sb.append(picture.getPictureId());sb.append("&version=H' alt='' width='90' height='90'> </a>");

				sb.append("</div>");
				sb.append("</div>");
				sb.append("</li>");
				sb.append("</ul>");
				sb.append("</div>");
		 	}
		}else{ 		
			sb.append("<div class=''>");
			log.debug("Request has NOT ALBUM...");
			sb.append("<li>Nombre: " +  user.getNombre() + "</li>"); 
			sb.append("<li>Apellido: " +  user.getApellido() + "</li>");
			sb.append("<li>Fecha de nacimiento: " +  user.getFechaNacimiento() + "</li>");
			sb.append("<li>Email: " +  user.getMail() + "</li>");
			sb.append("</div>");
		}	
		
		return sb.toString();
	}
	
	public String getHTMLContent_FotoDePerfil(){
		SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div class=''>");
		
		if (request.getAttribute("album") == null)
		{									
			PictureEy lastImageUpload = searchPicturesModel.getLastPictureUploadByUser(user.getNombreUsr());						
			
			sb.append("<a href='/secure/pictureView.jsp?pictureid=" + lastImageUpload.getPictureId() + "'>");
			sb.append("<img class='thumbnail' src='/image?pictureid="+ lastImageUpload.getPictureId() + "&version=I'");
			sb.append(" width='150' height='150' alt=''></a>");				
		}					
	
		sb.append("</div>");
		return sb.toString();
	}
	
	public String getHTMLContent_MasDatos(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div class=''>");
		
		if (request.getAttribute("album") == null)
		{
			sb.append("<h3>Otra informacion</h3>");
			sb.append("<ul>");
			sb.append("<li>Sexo: " + user.getSexo() + "</li>");
			sb.append("<li>Pais: " + user.getPais() + "</li>");
			sb.append("<li>Provincia: " + user.getIdProvicia() + "</li>");
			sb.append("</ul>");
		}
		
		sb.append("</div>");
		return sb.toString();
	}
	
	public String getHTMLContent_Albums(){
		SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div class=''>");
		
		if (request.getAttribute("album") == null)
		{
			sb.append("<h3>Albums</h3>");
			sb.append("<ul>");
		
			Set<AlbumEy> albums = searchPicturesModel.getAlbumsToBeDisplayedByUser(user.getNombreUsr());

				for (AlbumEy album : albums) {								
					sb.append("<li>");							
					sb.append("<a href=");							
					sb.append(request.getAttribute("url") + "/" + album.getAlbumId());														
					sb.append(">");		
					sb.append(album.getAlbumId());
					sb.append("</a>");
					sb.append("</li>");
					}		
		}
		
		sb.append("</div>");
		return sb.toString();
	}	
}
