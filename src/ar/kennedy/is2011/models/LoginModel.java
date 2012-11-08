package ar.kennedy.is2011.models;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.models.AccountModel;
//import ar.kennedy.is2011.db.exception.EntityNotFoundException;
//import ar.kennedy.is2011.exception.UserNotExistException;
import ar.kennedy.is2011.exception.ValidateMandatoryParameterException;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.Aleatory;
import ar.kennedy.is2011.utils.WebUtils;

/**
 * @author mlabarinas
 */
public class LoginModel extends AbstractModel {

	AccountModel model= new AccountModel();
	User user;
	
	public LoginModel() {
		super();
	}
	
	public Boolean validateLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {	
			WebUtils.validateMandatoryParameters(request, new String[] {"username", "password"});
			String username = WebUtils.getParameter(request, "username");
			String password = WebUtils.getParameter(request, "password");
		
			if (validateLogin(request, response, username, password)){
				if(publishNewSession(request, response)){
					return true;					
				}else{
					log.debug("Login user fail");			
					errors.put("login_fail", "Fallo en la autenticacion");
					return false;
				}
			}else{
				log.debug("Login user fail");				
				errors.put("login_fail", "El usuario no existe");
				return false;
			}
		} catch(ValidateMandatoryParameterException e) {
			errors.put("login_fail", "Faltan parametros obligatorios");
			return false;
		}
	}

		
	public Boolean publishNewSession(HttpServletRequest request, HttpServletResponse response){
		Session userSession = createLoginSession(request, response);
		userSession.setElement("user", model.getUser());
		SessionManager.save(request, userSession);
		
		/** For compatibility with old login */
		request.getSession().setAttribute("usuarioLogeado", model.getUser());

		log.debug("Login user OK");		
		return true;
	}

	private Boolean validateLogin(HttpServletRequest request, HttpServletResponse response, String accountId, String password) throws Exception {
		log.debug("Validating login: Search user for account: " + accountId);		
		if(model.getUserForAccount(accountId)==null){
			log.debug("Validating login: warn: USER-NOT-EXIST" + accountId);		
			return false;
		}
		log.debug("Validating login: user found!! [" + model.getUser().getUserName()+"]");		
		if(validateAccountPassword(model.getPassword(),password)){
			return true;
		}else{
			return false;
		}
	}

	private Boolean validateAccountPassword(String accPassword, String password) throws Exception{
		return WebUtils.decrypt(accPassword).equals(password);
	}

	
	protected Session createLoginSession(HttpServletRequest request, HttpServletResponse response) {
		Session userSession = null;
		String sessionIdentificator = WebUtils.createSessionIdentificator();
		String sessionValidate = Aleatory.getAleatoryString(10);
		
		log.debug("Creating user session");
		
		WebUtils.setCookie(response, new Cookie("sessionIdentificator", WebUtils.encrypt(sessionIdentificator)));
		WebUtils.setCookie(response, new Cookie("sessionValidate", WebUtils.encrypt(sessionValidate)));
		
		userSession = SessionManager.create(request, sessionIdentificator);
		userSession.setElement("sessionValidate", sessionValidate);
		
		return userSession;
	}
	
}