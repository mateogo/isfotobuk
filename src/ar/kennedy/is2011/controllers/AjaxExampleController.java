package ar.kennedy.is2011.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import ar.kennedy.is2011.session.Session;



public class AjaxExampleController extends AbstractController{

	private static final long serialVersionUID = 1L;
	
	public AjaxExampleController() {
        super();
    }


	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("****************************************************");
		log.debug("Ajax EXAMPLE controller : begin");
		
			PrintWriter out = response.getWriter();
			out.println("<h1>catch the callback!</h1>");
	}
		
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
}