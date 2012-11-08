package ar.kennedy.is2011.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ar.kennedy.is2011.models.AccountModel;
import ar.kennedy.is2011.models.PersonModel;
import ar.kennedy.is2011.db.entities.Account;
import ar.kennedy.is2011.db.entities.User;

//import ar.kennedy.is2011.db.entities.Usuario;
import ar.kennedy.is2011.utils.WebUtils;

public class RegistracionUsuarioServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	protected final Logger log = Logger.getLogger(getClass());
	
	private Boolean hasErrors = false;
	
	AccountModel accModel = new AccountModel();
	
	
	public RegistracionUsuarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.error("******************************************Registracion: to begin");
		String nombreUsr = request.getParameter("nombreUsuario");

		if(!isNameAlreadyUsed(nombreUsr)) {
			if(createEntities(request)){
				log.error("create entities OK");
				hasErrors=false;			
			}else{
				hasErrors=true;
				log.error("error servicio:AdministrarRegistracionUsuarioDAO no pudo concretar el alta ususario");
			}
		}else{
			hasErrors=true;
			log.info("El nombre de usuario existe!!");
	 		request.setAttribute("errors", "El nombre de usuario ya existe");
		}
		if(hasErrors){
			log.debug("Haserrors!");
	 		request.setAttribute("usuarioNoRegistrado", accModel.getUser());
			request.getRequestDispatcher("registracionRapida.jsp").forward(request, response);			
		}else{
			log.debug("Successss");
			HttpSession session = request.getSession();
			session.setAttribute("usuarioLogeado", accModel.getUser()); 	
			response.sendRedirect("index.jsp");
		}
	}

	private Boolean createEntities(HttpServletRequest request){
		log.debug("Create Entities for new User/Account");

		String nombreUsr = request.getParameter("nombreUsuario");
		String email = request.getParameter("email");
		String clave = request.getParameter("clave");
		String idPreg = request.getParameter("idPreguntaSecreta");
		String resp = request.getParameter("respuestaSecreta");

		log.debug("Create Entities for new User/Account ["+nombreUsr+email+clave+idPreg+resp+"]");

		if(nombreUsr==null || nombreUsr=="") return false;
		if(email==null || email=="") return false;

		log.debug("Create Entities go ahead! ["+nombreUsr+email+clave+idPreg+resp+"]");
		
		Account acc = new Account();
		acc.setAccountName(nombreUsr);
		acc.setMail(email);
		acc.setPasswd(WebUtils.encrypt(clave));
		acc.setAuthProvider("THIS");

		//USER SETTINGS
		User user = new User();
		user.setUserName(acc.getAccountName());
		user.setMail(acc.getMail());
		user.setAppRole("ADMINISTRADOR");
		user.setFechaNacimiento(new Date());
		user.setIdPreguntaSecreta(Integer.parseInt(idPreg));
		user.setRespuestaPregunta(resp);
		user.setSexo("S/D");

		//USER-ACCOUNT
		acc.setUser(user);
		List<Account>accounts = new ArrayList<Account>();
		accounts.add(acc);
		user.setAccounts(accounts);
		user.setDefaultAccount(acc);
		
		accModel.setUser(user);
		accModel.setAccount(acc);
		accModel.updateUser(user);

		//PERSON
		PersonModel pmodel = new PersonModel(user);
		pmodel.initNewFperson();

		pmodel.getFperson().setNombrePerson(user.getUserName());
		pmodel.getFperson().setComent("alta automatica");
		pmodel.getFperson().setNombre("nombre...");
		pmodel.getFperson().setApellido("apellido...");
		pmodel.getFperson().setSexo("S/D");
		
		pmodel.updateFPerson();
		accModel.updatePersonFromUser(pmodel.getFperson());

		return true;
	}

	private Boolean isNameAlreadyUsed(String name){
		if(accModel.ifExistUserByName(name)) return true;
		if(accModel.ifExistAccountByName(name)) return true;
		return false;
	}
}