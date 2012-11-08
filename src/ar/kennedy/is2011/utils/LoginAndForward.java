package ar.kennedy.is2011.utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.kennedy.is2011.controllers.AbstractController;
import ar.kennedy.is2011.models.LoginModel;
import ar.kennedy.is2011.session.Session;

/**
 * Servlet implementation class LoginUsuarioController
 */
public class LoginAndForward extends AbstractController {

	private static final long serialVersionUID = 7995740723219513508L;

	protected final Logger log = Logger.getLogger(getClass());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginAndForward() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
    		log.debug("****************************LoginAndForward.action: validate login");
    		/*
    		 * datos de login
    		 */
			WebUtils.validateMandatoryParameters(request, new String[] {"username", "password", "action"});
			String username = WebUtils.getParameter(request, "username");
			String password = WebUtils.getParameter(request, "password");
			String action = WebUtils.getParameter(request, "action");
			String actionURL = WebUtils.getParameter(request, "url");
			String forward = actionURL;
			if(WebUtils.isNotNull(action)){
				forward=forward+"?action="+action;
			}
    		LoginModel model = new LoginModel();
    		log.debug("LoginAndForward.action: validate login for: ["+username+"]:["+password+"] action:["+forward+"]");
    		
    		if( model.validateLogin(request, response)) {
    			response.sendRedirect(forward);
    		
    		} else {
    			request.getRequestDispatcher("/index.jsp?e=t").forward(request, response);
    		}
    	}
    	
    	public boolean validateLogin(HttpServletRequest request) {
    		return false;
    	}
}
