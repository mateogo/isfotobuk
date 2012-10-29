package ar.kennedy.is2011.views;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

		sb.append("<div class='header-generales' align=Center>");
		
		if (request.getAttribute("album") != null)
		{
			sb.append("Album " + request.getAttribute("album").toString() + " de " + user.getNombreUsr());
		}
		else
		{
			sb.append("<h1>");
			sb.append("Perfil publico de " + user.getNombreUsr());
			sb.append("<h1/>");
		}
		
		sb.append("</div>");
		return sb.toString();
	}
	
	public String getHTMLContent_SubEncabezado(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div class='page-header'>");
		sb.append("<h1>");
		
		if (request.getAttribute("album") != null)
		{
			sb.append("Fotos");
		}
		else
		{ 		
			sb.append("Generales");									
		} 	
		
		sb.append("</h1>");
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
			sb.append("<div class='user-data-container'>");
			log.debug("Request has NOT ALBUM...");
			sb.append("<li>Nombre: " +  user.getNombre() + "</li>"); 
			sb.append("<li>Apellido: " +  user.getApellido() + "</li>");
			sb.append("<li>Fecha de nacimiento: " +  user.getFechaNacimiento() + "</li>");
			sb.append("<li>Email: " +  user.getMail() + "</li>");
			//Separador HTML
			sb.append("<hr size=10 />");
			sb.append("</div>");
		}	
		
		return sb.toString();
	}
	
	public String getHTMLContent_FotoDePerfil(){
		SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
		StringBuilder sb = new StringBuilder();
		
		
		
		if (request.getAttribute("album") == null)
		{
			sb.append("<div class='profile-photo'>");
			sb.append("<h2>Foto De Perfil</h2>");
			PictureEy lastImageUpload = searchPicturesModel.getLastPictureUploadByUser(user.getNombreUsr());						
			
			sb.append("<a href='/secure/pictureView.jsp?pictureid=" + lastImageUpload.getPictureId() + "'>");
			sb.append("<img class='thumbnail' src='/image?pictureid="+ lastImageUpload.getPictureId() + "&version=I'");
			sb.append(" width='150' height='150' alt=''></a>");

			//Separador HTML
			sb.append("<hr size=10 />");
			sb.append("</div>");
		}					
		return sb.toString();
	}
	
	public String getHTMLContent_MasDatos(){
		StringBuilder sb = new StringBuilder();
		String UsrSexo = "No especificado";
		
		if (request.getAttribute("album") == null)
		{
			if (user.getSexo().equals("M"))
			{
				UsrSexo = "Masculino";
			}
			else if(user.getSexo().equals("F"))
			{
				UsrSexo = "Femenino";
			}
			
			sb.append("<div class='datos-user'>");
			sb.append("<h3>Otra informacion</h3>");
			sb.append("<ul>");
			sb.append("<li>Sexo: " + UsrSexo + "</li>");
			sb.append("<li>Pais: " + getPaisById(user.getPais()) + "</li>");
			sb.append("<li>Provincia: " + getProvinciaById(user.getIdProvicia()) + "</li>");
			sb.append("</ul>");
			//Separador HTML
			sb.append("<hr size=10 />");
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	public String getHTMLContent_Albums(){
		SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div class='Albums-user'>");
		
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
	
	public String getHTMLContent_TopBarFormat()
	{
		/* Esta función se encargará de setear el botón de INICIO o MI PERFIL en el TOP Bar
		 * junto con las opciones disponibles para usuarios logueados y usuarios no logueados.
		 * Si el usuario está logueado, la topbar tendrá las opciones de "Mi Perfil", "Acciones", etc
		 * Si el usuario no está logueado estas opciones no serán accesibles y solo se leerán las
		 * opciones "INICIO" y "REGISTRARSE".
		 */
		
		StringBuilder sb = new StringBuilder();
		HttpSession session = request.getSession();
		Usuario usr = (Usuario)session.getAttribute("usuarioLogeado");
		
        sb.append("<ul class='nav'>");
        
		if(usr != null)
		{
			//Usuario logueado.
			sb.append("<li class='active'><a href='/secure/main.jsp'>Mi Perfil</a></li>");
			sb.append("<li class='dropdown' >");
			sb.append("<a href='#' class='dropdown-toggle' data-toggle='dropdown'>Acciones<b class='caret'></b></a>");
            sb.append("<ul class='dropdown-menu' >");
            sb.append("<li><a href='/secure/imageUpload.jsp'>Subir imagen</a></li>");
            sb.append("<li class='divider'></li>");
            sb.append("<li class='nav-header'>Visualizar por...</li>");
            sb.append("<li><a href='/secure/albums.jsp'>Album</a></li>");
            sb.append("<li><a href='/secure/search.jsp'>Buscar</a></li>");
            sb.append("</ul>");
            sb.append("</li>");
            sb.append("</ul>");
            sb.append("<div class='pull-right'>");
            sb.append("<ul class='nav'>");
            sb.append("<li class='dropdown' >");
            sb.append("<a href='#' class='dropdown-toggle' data-toggle='dropdown'>" + usr.getNombreUsr().toString() + "<b class='caret'></b></a>");
            sb.append("<ul class='dropdown-menu' >");
            sb.append("<li><a href='/secure/editarCuentaUsuario.jsp'>Editar perfil</a></li>");
            sb.append("<li class='divider'></li>");
            sb.append("<li><a href='/logout'>Cerrar sesion</a></li>");
            sb.append("</ul>");
            sb.append("</li>");
            sb.append("</ul>");
            sb.append("</div> ");	
		}
		else
		{
			//Usuario No logueado.
            sb.append("<li class='active'><a href='/index.jsp'>Inicio</a></li>");
			sb.append("<li class='dropdown' >");
			sb.append("</form>");
            sb.append("</li>");
            sb.append("</ul>");
            sb.append("<div class='pull-right'>");
            sb.append("<ul class='nav'>");
            sb.append("Usuario No Logueado");
            sb.append("</ul>");
            sb.append("</div> ");
            //Este form es el del botón de login.
			sb.append("<form method='post' action='login' class='pull-right'>");
			sb.append("<input class='input-small' type='text'     name='username' placeholder='Usuario'>");
			sb.append("<input class='input-small' type='password' name='password' placeholder='Contrase&ntilde;a'>");
			sb.append("<button class='btn' type='submit'>Entrar</button>");
		}
		
		return sb.toString();
	}
	
	private String getProvinciaById(String idProvincia)
	{
		/*El JSP editarCuentaUsuario.jsp no presenta un acceso a la base de datos para buscar las
		 * posibles provincias que el sistema admite. Es por eso que esta función se basará en los
		 * mismos códigos descriptos en dicho JSP.
		 */
		
		String TxtProvincia = "";
		
		if(idProvincia.equals("1")) TxtProvincia = "Buenos Aires";
		if(idProvincia.equals("2")) TxtProvincia = "Córdoba";
		if(idProvincia.equals("3")) TxtProvincia = "Santa Fé";
		if(idProvincia.equals("4")) TxtProvincia = "Salta";
		
		log.debug("Provincia del usuario es: " + TxtProvincia);
		return TxtProvincia;
	}
	
	private String getPaisById(String idPais)
	{
		/*El JSP editarCuentaUsuario.jsp no presenta un acceso a la base de datos para buscar los
		 * posibles países que el sistema admite. Es por eso que esta función se basará en los
		 * mismos códigos descriptos en dicho JSP.
		 */
		String TxtPais = "";
		
		if (idPais.equals("1")) TxtPais = "Argentina";
		if (idPais.equals("2")) TxtPais = "Brasil";
		if (idPais.equals("3")) TxtPais = "Chile";
		
		log.debug("País del usuario es: " + TxtPais);
		return TxtPais;
	}
}
