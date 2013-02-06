package ar.kennedy.is2011.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

import org.apache.log4j.Logger;

import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.WebUtils;

/**
 * @author mlabarinas
 */
public abstract class AbstractController extends HttpServlet implements ControllerItf, HttpJspPage {
	
	private static final long serialVersionUID = 7320911254853012236L;
	
	protected final Logger log = Logger.getLogger(getClass());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		internalAction(request, response);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		internalAction(request, response);
	}
	
	protected void internalAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session userSession = null;
		log.debug("Start internal action");
		
		//Agrega clave=valor en response: response.setHeader("Cache-Control", "no-cache");
		setHttpHeaders(response);
		//recupera la session del usuario, desde la base de datos
		userSession = getSession(request, response);
		// cada concrete-controller establece si valida el login
		if(validateLogin(request)) {
			log.debug("validateLogin=True: validating login");
			if(validateUserLogin(request, response, userSession)){
				log.debug("validateLogin: OK ");
				forwardAction(request, response, userSession);
			} else {
				log.debug("validateLogin: FAIL redirect to home");
				response.sendRedirect("/index.jsp");
			}
		} else {
			log.debug("ValidateLogin=False: Request don't need validate login");
			forwardAction(request, response, userSession);
		}
	}
	
	private void forwardAction(HttpServletRequest request, HttpServletResponse response, Session userSession) throws ServletException,IOException {
		try {
			if(!isJspPage()) {
				log.debug("=========== SERVLET: action request=====================");
				action(request, response, userSession);
			} else {
				log.debug("============= JSP: return ===============================");
				_jspService(request, response);
			}
		} catch(Exception e) {
			log.error("Unexpected error", e);
			request.getSession(true).setAttribute("exception", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
	}
	
	public abstract boolean validateLogin(HttpServletRequest request);
	
	protected Boolean validateUserLogin(HttpServletRequest request, HttpServletResponse response, Session userSession) {
		log.debug("Validate user login");
		return WebUtils.validateUserLogin(request, response, userSession);
	}
	
	protected Session getSession(HttpServletRequest request, HttpServletResponse response) {
		Session userSession = null;
		// Busca el identificador de session en las cookies
		String sessionIdentificator = getSessionIdentificator(request);
		log.debug("Getting user session: ["+sessionIdentificator+"]");
		
		if(sessionIdentificator != null) {
			//recupera la session de la base de datos
			userSession = SessionManager.get(request, sessionIdentificator);
		}
		return userSession;
	}
	
	protected String getSessionIdentificator(HttpServletRequest request) {
		return WebUtils.getSessionIdentificator(request);
	}
	
	protected Boolean isJspPage() {
		return this.getClass().getName().endsWith("_jsp");
	}
	
	protected void setHttpHeaders(HttpServletResponse response) {
		log.debug("Setting default http headers: Cache-Control=no-cache");
		response.setHeader("Cache-Control", "no-cache");
	}
	
	public void _jspService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { }

	public void jspDestroy() { }

	public void jspInit() { }	
}