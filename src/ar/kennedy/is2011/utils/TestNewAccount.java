package ar.kennedy.is2011.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import ar.kennedy.is2011.db.dao.AbstractDao;
import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.db.entities.Location;
import ar.kennedy.is2011.db.entities.ContactosPerson;
import ar.kennedy.is2011.db.entities.Account;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.AlbumEy;
import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.models.AccountModel;
import ar.kennedy.is2011.models.AlbumModel;


/**
 * @author mgo
 */
public class TestNewAccount extends HttpServlet {
//public abstract class AbstractController extends HttpServlet implements ControllerItf, HttpJspPage {

	public static final Boolean CREATE_USER_ACCOUNT     = false;
	public static final Boolean CREATE_PERSONA_IDEAL    = false;
	public static final Boolean CREATE_PERSONA_FISICA   = false;
	public static final Boolean CHANGE_DEFAULT_ACCOUNT  = false;
	public static final Boolean CREATE_ALBUM            = false;

	public static final Boolean QUERYING_USERS          = true;
	public static final Boolean QUERYING_ACCOUNTS       = true;
	public static final Boolean QUERYING_ENTITIES       = false;
	public static final Boolean DUMP_USER_SESSION       = false;
	public static final Boolean REPARING_ALBUMES        = false;
	public static final String USER_ID = "1";

	private static final long serialVersionUID = 7320911254853012236L;

	protected final Logger log = Logger.getLogger(getClass());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.action(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.action(request, response);
	}

	//public void action(HttpServletRequest request, HttpServletResponse response) throws Exception {
	public void action(HttpServletRequest request, HttpServletResponse response)  {
		log.debug("Action started");
		if (CREATE_PERSONA_FISICA) createPersonaFisica(request, response);
		if (CREATE_PERSONA_IDEAL) createPersonaIdeal(request, response);
		if (CREATE_USER_ACCOUNT) creatAccount(request, response);
		if (CHANGE_DEFAULT_ACCOUNT) changeDefaultAccount(request,response);
		if (CREATE_ALBUM) creatAlbum(request, response);
		
		if (QUERYING_USERS) queriyngUsers(request, response);
		if (QUERYING_ACCOUNTS) queriyngAccounts(request, response);
		if (QUERYING_ENTITIES) queriyngEntities(request,response);
		if (REPARING_ALBUMES) repairAlbum(request,response);
		if (DUMP_USER_SESSION) dumpUserData(request,response);
		
		
	}

	
	public void dumpUserData(HttpServletRequest request, HttpServletResponse response)  {

		HttpSession session = request.getSession();
		
		User usuario = buscarUsuario(USER_ID);
		if(usuario != null) {
			log.debug("User: usu:   ["+usuario.getUserName()+"]");
			log.debug("User: mail1: ["+usuario.getMail()+"]");
			log.debug("User: feNac: ["+usuario.getFechaNacimiento()+"]");
			log.debug("User: sex:   ["+usuario.getSexo()+"]");
			log.debug("User: pregs: ["+usuario.getIdPreguntaSecreta()+"]");
			log.debug("User: resps: ["+usuario.getRespuestaPregunta()+"]");
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
	
	public User buscarUsuario(String nombreUsr) {
		log.debug("buscarUsuario: ["+nombreUsr+"]");
		
		AbstractDao<User> userDao = new AbstractDao<User>();
		try {
			return userDao.findById(User.class, nombreUsr);
			
		} catch(Exception e) {
			log.debug("Exception buscandoUsuario: ["+nombreUsr+"]");
			return null;
		}
	}
	
	
	public void createPersonaFisica(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Create PERSONA FISICA to start");
		
		AbstractDao<PersonaFisica> personDao = new AbstractDao<PersonaFisica>();
		//AbstractDao<Location> locationDao = new AbstractDao<Location>();
		PersonaFisica camilo = new PersonaFisica();
		Location camiloLocation = new Location();
		log.debug("=== new persona fisica created");

		camiloLocation.setPais("Argentina");
		camiloLocation.setDescr("Lugar de nacimiento");
		camiloLocation.setProvincia("CABA");
		camilo.setNombre("Camilo");
		camilo.setApellido("Gomez Ortega");
		camilo.setNacimLocation(camiloLocation);
		try{
			log.debug("=== trying persistence Camilo");
			personDao.persist(camilo);
			log.debug("*** Persisting entities, Done. camilo:["+camilo.getNombre()+"]");

		} catch (Exception e) {
			log.error(e.getMessage());
			//throw new PersistException("Fail to delete entity in database");

		}finally{
			
		}
		
		//response.sendRedirect("index.jsp");
	}

	
	public void createPersonaIdeal(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Create PERSONA IDEAL to start");
		
		AbstractDao<PersonaIdeal> personDao = new AbstractDao<PersonaIdeal>();
		//AbstractDao<ContactosPerson> contactosDao = new AbstractDao<ContactosPerson>();
		
		ContactosPerson tel1 = new ContactosPerson();
		tel1.setConType("TEL");
		tel1.setDescr("telefono para llamadas urgentes");
		tel1.setValue("11 4422 5555");
		log.debug("=== Contacto: "+tel1.toString());

		ContactosPerson mail1 = new ContactosPerson();
		mail1.setConType("MAIL");
		mail1.setUseType("PARTICULAR");
		mail1.setDescr("el que mas leo");
		mail1.setValue("suigeneris@gmail.com");
		log.debug("=== Contacto: "+mail1.toString());

		
		PersonaIdeal suigeneris = new PersonaIdeal();
		suigeneris.setNombrePerson("Sui Generis");
		suigeneris.setComent("Creada por Chary Garcia");
		suigeneris.addContacto(tel1);
		suigeneris.addContacto(mail1);
		try{
			log.debug("=== trying persistence SuiGeneris");
			personDao.persist(suigeneris);
			log.debug("***Persisting entities, Done:["+suigeneris.toString()+"]");

		} catch (Exception e) {
			log.error(e.getMessage());
			//throw new PersistException("Fail to delete entity in database");

		}finally{
			
		}
		
		//response.sendRedirect("index.jsp");
	}

		

	public void creatAlbum(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Create USER-ALBUM to start");
		
		AbstractDao<AlbumEy> albumDAO = new AbstractDao<AlbumEy>();
		
		AlbumEy familia = new AlbumEy();
		familia.setAlbumName("vacaciones");
		familia.setAlbumId("vacaciones");
		familia.setOwner("mateogo");
		familia.setVisibility("public");
		
		try{
			log.debug("=== trying persistence album:familia");
			albumDAO.persist(familia);
			log.debug("***** Persisting entities, Done:["+familia.toString()+"]");

		} catch (Exception e) {
			albumDAO.rollBackTx();
			log.error(e.getMessage());
			//throw new PersistException("Fail to delete entity in database");

		}finally{
		}
	}

	public void repairAlbum(HttpServletRequest request, HttpServletResponse response) {
		log.debug("******* Create QUERIyng ALBUM to start *******");
		AlbumModel model = new AlbumModel();
		List<AlbumEy> albumes = model.getAllAlbumes();
		
		log.debug("query for ALBUM size:["+albumes.size()+"]");
		if(albumes.size()>0){
			for(AlbumEy album:albumes){
				//oJo:**********************
				model.repairAlbum(album);
				//*************************
				log.debug("Iterating==>Album: ["+album.toString()+"]");
				log.debug("                          Key: ["+album.getKey()+"]");
				log.debug("                           Id: ["+album.getKey().getId()+"]");
				log.debug("                         Kind: ["+album.getKey().getKind()+"]");
				log.debug("                         Name: ["+album.getKey().getName()+"]");
				log.debug("                    NameSpace: ["+album.getKey().getNamespace()+"]");
				log.debug("                     hashCode: ["+album.getKey().hashCode()+"]");
			}
			log.debug("===>ALBUM querying:[end]");
		}
	}

	
	public void creatAccount(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Create USER-ACCOUNT to start");
		
		AbstractDao<User> userDAO = new AbstractDao<User>();
		
		Location userLocation = new Location("Argentina","CABA","Villa Crespo");
		//ACCOUNT SETTINGS
		Account unaCuenta = new Account();
		unaCuenta.setAccountName("mateogo");
		unaCuenta.setMail("mateogoxx@gmail.com");
		unaCuenta.setPasswd("holamundo");
		unaCuenta.setAuthProvider("THIS");

		Account otraCuenta = new Account();
		otraCuenta.setAccountName("imago");
		otraCuenta.setMail("imagoxx@gmail.com");
		otraCuenta.setPasswd("holamundo");
		otraCuenta.setAuthProvider("FACEBOOK");

		//USER SETTINGS
		User elUsuario = new User();
		elUsuario.setUserName("mgomezortega");
		elUsuario.setMail(unaCuenta.getMail());
		elUsuario.setAppRole("ADMINISTRADOR");
		elUsuario.setFechaNacimiento(new Date());
		elUsuario.setIdPreguntaSecreta(2);
		elUsuario.setRespuestaPregunta("grego");
		elUsuario.setSexo("MASCULINO");
		elUsuario.setLocacion(userLocation);
		//USER-ACCOUNT
		unaCuenta.setUser(elUsuario);
		otraCuenta.setUser(elUsuario);
		List<Account>accounts = new ArrayList<Account>();
		accounts.add(unaCuenta);
		accounts.add(otraCuenta);
		elUsuario.setAccounts(accounts);
		elUsuario.setDefaultAccount(unaCuenta);
		
		try{
			log.debug("=== trying persistence elUsuario:User");
			userDAO.persist(elUsuario);
			log.debug("***** Persisting entities, Done:["+elUsuario.toString()+"]");

		} catch (Exception e) {
			userDAO.rollBackTx();
			log.error(e.getMessage());
			//throw new PersistException("Fail to delete entity in database");

		}finally{
		}
	}
	
	
	
	public void queriyngEntities(HttpServletRequest request, HttpServletResponse response) {
		log.debug("******* Create QUERIyng PERSON FISICA to start *******");		
		AbstractDao<PersonaFisica> personaDao = new AbstractDao<PersonaFisica>();
		try{
			log.debug("Trying: personas");
			List<PersonaFisica> personas= personaDao.select(PersonaFisica.class);
			log.debug("Trying: locations SUCCEED!");
			for(PersonaFisica person:personas){
				log.debug("Personas: ["+person.getNombre()+"] pais:["+person.getApellido()+"]");
				Location loc = person.getNacimLocation();
				log.debug("===>Locations: ["+loc.getDescr()+"] pais:["+loc.getPais()+"]");
			}
		} catch (EntityNotFoundException e) {
			log.error(e.getMessage());
			//throw new PersistException("Fail to delete entity in database");

		}finally{
		}
		
		log.debug("******* Create QUERIyng LOCATION to start *******");
		//AbstractDao<PersonaFisica> personDao = new AbstractDao<PersonaFisica>();
		AbstractDao<Location> locationDao = new AbstractDao<Location>();
		
		try{
			log.debug("Trying: locations");
			List<Location> locations= locationDao.select(Location.class);
			log.debug("Trying: locations SUCCEED!");
			for(Location loc:locations){
				log.debug("Locations: ["+loc.getDescr()+"] pais:["+loc.getPais()+"]");
			}

		} catch (EntityNotFoundException e) {
			log.error(e.getMessage());
			//throw new PersistException("Fail to delete entity in database");

		}finally{
		}

		log.debug("******* Create QUERIyng PERSON IDEAL to start *******");		
		AbstractDao<PersonaIdeal> piDao = new AbstractDao<PersonaIdeal>();
		try{
			log.debug("Trying: personas ideales");
			List<PersonaIdeal> personas= piDao.select(PersonaIdeal.class);
			log.debug("Trying: locations SUCCEED!");
			for(PersonaIdeal person:personas){
				log.debug("Personas: ["+person.toString()+"]");
				for (ContactosPerson cp:person.getDatosContacto()){
					log.debug("===>Datos de contacto: ["+cp.toString()+"]");					
				}
			}
		} catch (EntityNotFoundException e) {
			log.error(e.getMessage());
			//throw new PersistException("Fail to delete entity in database");

		}finally{
		}
	}

	public void queriyngUsers(HttpServletRequest request, HttpServletResponse response) {
		log.debug("******* Create QUERIyng USERS to start *******");
		AccountModel model = new AccountModel();
		List<User> users = model.getUsersByName("mgomezortega");
		//List<User> users = model.getAllUsers();
		log.debug("query for user size:["+users.size()+"]");
		if(users.size()>0){
			for(User user:users){
				log.debug("Iterating==>User: ["+user.toString()+"]");
			}
			log.debug("===>User:[end]");								
		}
	}

	public void changeDefaultAccount(HttpServletRequest request, HttpServletResponse response) {
		log.debug("******* Change Default Account to start *******");
		AccountModel model = new AccountModel();
		List<Account> accounts = model.getAccountsByName("imago");
		log.debug("query for account size:["+accounts.size()+"]");
		if(accounts.size()==1){
			model.changeDefaultAccount(accounts.get(0));
			log.debug("******* change default account: Succeed!");
		}
	}
	
	
	public void queriyngAccounts(HttpServletRequest request, HttpServletResponse response) {
		log.debug("******* Create QUERIyng ACCOUNTS to start *******");
		AccountModel model = new AccountModel();
		//List<Account> accounts = model.getAccountByName("mateogo");
		List<Account> accounts = model.getAllAccounts();
		log.debug("query for account size:["+accounts.size()+"]");
		if(accounts.size()>0){
			for(Account account:accounts){
				log.debug("Iterating==>Account: ["+account.toString()+"]");
				User us = model.getUserForAccount(account);
				log.debug("Iterating==>User from Account: ["+us.toString()+"]");
				log.debug("              Default Account: ["+us.getDefaultAccount()+"]");
				log.debug("                          Key: ["+us.getKey()+"]");
				log.debug("                           Id: ["+us.getKey().getId()+"]");
				log.debug("                         Kind: ["+us.getKey().getKind()+"]");
				log.debug("                         Name: ["+us.getKey().getName()+"]");
				log.debug("                    NameSpace: ["+us.getKey().getNamespace()+"]");
				log.debug("                     hashCode: ["+us.getKey().hashCode()+"]");
				
				User mismo = model.getUserById(us.getKey());
				log.debug("==>Reading UserById: ["+mismo.toString()+"]");

				//changeDefaultAccount
				
				
			}
			log.debug("===>Querying accounts:[end]");								
		}
	}

	
	public boolean validateLogin(HttpServletRequest request) {
		return false;
	}
	
}