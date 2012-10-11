package ar.kennedy.is2011.controllers;

import java.util.Map;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

import ar.kennedy.is2011.constants.Constants;
import ar.kennedy.is2011.db.dao.AbstractDao;
import ar.kennedy.is2011.db.dao.AdministrarRegistracionUsuarioDAO;
import ar.kennedy.is2011.db.dao.AdministrarRegistracionUsuarioDAOImpl;
import ar.kennedy.is2011.db.entities.AlbumEy;
import ar.kennedy.is2011.db.entities.Usuario;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.utils.WebUtils;

/**
 * @author mlabarinas
 */
public class TestController extends AbstractController {

	private static final long serialVersionUID = 3707424606466635639L;
	public static final Boolean DUMP_USER_SESSION = false;
	public static final Boolean DUMP_USER_DATA = true;
	public static final Boolean CREATE_INITIAL_ENTITIES = false;

	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("Action started");
		//comentar / descomentar el metodo que se quiera ejecutar:

		// createInitialEntities OjO: este método crea nuevos contenidos
		if (CREATE_INITIAL_ENTITIES) createInitialEntities(request, response, userSession);
		// dump User session
		if (DUMP_USER_SESSION) dumpUserSession(request, response, userSession);
		// dump User session
		if (DUMP_USER_DATA) dumpUserData(request, response, userSession);
	}


	public void dumpUserSession(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		 Map<String, Object> mcontent = userSession.getContent();
		 //Iterator it = mcontent.;
		 for (Map.Entry<String, Object> node : mcontent.entrySet()){
				log.debug("UserSession: ["+node.getKey()+"] = ["+node.getValue()+"]");			 
		 }
	}
	
	public void dumpUserData(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		Map<String, Object> mcontent = userSession.getContent();
		AdministrarRegistracionUsuarioDAO userFactory = new AdministrarRegistracionUsuarioDAOImpl();

		HttpSession session = request.getSession();
		
		Usuario usuario = userFactory.buscarUsuario(((Usuario) session.getAttribute("usuarioLogeado")).getNombreUsr());
		if(usuario != null) {
			log.debug("User: usu:   ["+usuario.getNombreUsr()+"]");
			log.debug("User: nom:   ["+usuario.getNombre()+"]");
			log.debug("User: ape:   ["+usuario.getApellido()+"]");
			log.debug("User: mail1: ["+usuario.getMail()+"]");
			log.debug("User: mail2: ["+usuario.getMailSecundario()+"]");
			log.debug("User: feNac: ["+usuario.getFechaNacimiento()+"]");
			log.debug("User: sex:   ["+usuario.getSexo()+"]");
			log.debug("User: pais:  ["+usuario.getPais()+"]");
			log.debug("User: prov:  ["+usuario.getIdProvicia()+"]");
			log.debug("User: pregs: ["+usuario.getIdPreguntaSecreta()+"]");
			log.debug("User: resps: ["+usuario.getRespuestaPregunta()+"]");
		}
		log.debug("===================UserSession================================");
		 //Iterator it = mcontent.;
		for (Map.Entry<String, Object> node : mcontent.entrySet()){
			log.debug("UserSession: ["+node.getKey()+"] = ["+node.getValue()+"]");			 
		}
		log.debug("=====================Request.Session==============================");
		log.debug("Session            id: [" + session.getId() + "]" );
		log.debug("Session Creation time: [" + session.getCreationTime() + "]" );

		@SuppressWarnings("unchecked")
		Enumeration<String> names =  session.getAttributeNames();
		while (names.hasMoreElements()){
			String name = (String)names.nextElement();
			Object value = session.getAttribute(name);
			log.debug("Session name: [" + name + "] value = [" + value + "]");
		}
		
		log.debug("========Request.Session.Context=====");
		ServletContext scontext= session.getServletContext();
		@SuppressWarnings("unchecked")
		Enumeration<String> ctx =  scontext.getAttributeNames();
		while (ctx.hasMoreElements()){
			String name = (String)ctx.nextElement();
			//Object value = session.getAttribute(name);
			log.debug("SessionContext name: [" + name + "]");
		}
		log.debug("========Request=====");
		log.debug("Request authType      : [" + request.getAuthType() + "]" );
		log.debug("Request charEncoding  : [" + request.getCharacterEncoding() + "]" );
		log.debug("Request contextType   : [" + request.getContentType() + "]" );
		log.debug("Request contextPath   : [" + request.getContextPath() + "]" );
		log.debug("Request localAddr     : [" + request.getLocalAddr() + "]" );
		log.debug("Request localName     : [" + request.getLocalName() + "]" );
		log.debug("Request localPort     : [" + request.getLocalPort() + "]" );
		log.debug("Request method        : [" + request.getMethod() + "]" );
		log.debug("Request pathInfo      : [" + request.getPathInfo() + "]" );
		log.debug("Request pathTranslated: [" + request.getPathTranslated() + "]" );
		log.debug("Request protocol      : [" + request.getProtocol() + "]" );
		log.debug("Request queryString   : [" + request.getQueryString() + "]" );
		log.debug("Request remoteAddr    : [" + request.getRemoteAddr() + "]" );
		log.debug("Request remoteHost    : [" + request.getRemoteHost() + "]" );
		log.debug("Request remoteUser    : [" + request.getRemoteUser() + "]" );
		log.debug("Request sessionId     : [" + request.getRequestedSessionId() + "]" );
		log.debug("Request scheme        : [" + request.getScheme() + "]" );
		log.debug("Request serverName    : [" + request.getServerName() + "]" );
		log.debug("Request serverPath    : [" + request.getServletPath() + "]" );
	}
	public void createInitialEntities(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("Create basic entities to start");
		
		AbstractDao<Usuario> userDao = new AbstractDao<Usuario>();
		AbstractDao<AlbumEy> albumDao = new AbstractDao<AlbumEy>();
		Usuario user = new Usuario();
		AlbumEy album = new AlbumEy();
		
		user.setNombreUsr("jdropes");
		user.setClave(WebUtils.encrypt("12344321"));
		
		album.setAlbumId("otros");
		album.setVisibility(Constants.PUBLIC_VISIBILITY);
		album.setOwner("jdropes");
		
		userDao.persist(user);
		albumDao.persist(album);
		
		response.sendRedirect("index.jsp");
	}
	
	public boolean validateLogin(HttpServletRequest request) {
		return false;
	}
	
}