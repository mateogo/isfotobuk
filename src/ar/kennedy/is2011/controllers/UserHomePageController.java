package ar.kennedy.is2011.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ar.kennedy.is2011.db.entities.Usuario;

/**
 * Servlet implementation class LoginUsuarioController
 */
public class UserHomePageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected final Logger log = Logger.getLogger(getClass());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserHomePageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Start UserHomePage - doPost");

		HttpSession session = request.getSession();
		Usuario usr = (Usuario)session.getAttribute("usuarioLogeado");
		if(usr != null){
			log.debug("UserHomePage - usuario-sesion: ok");
			if (urlGetRESTParameters(request,response)){
				log.debug("UserHomePage - url procesada: ok");
				request.getRequestDispatcher("/secure/main.jsp").forward(request, response);
				//vease, como alternativa, usar reponse.sendRedirect
				//response.sendRedirect("/secure/main.jsp");
			}else{
				log.debug("UserHomePage: Fallo el procesamiento de la URL");
				//ver a donde redireccionar en caso de falla
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			//split URL
		}else{
			log.debug("UserHomePage: usuario no encontrado");
			session.invalidate();
			request.setAttribute("iniciarSesion","Debe iniciar sesion nuevamente");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}	
	}
	private Boolean urlGetRESTParameters(HttpServletRequest request, HttpServletResponse response){
		//TODO: parsear la URL; incorporar los parametros al request; retornar un ok
		return true;
	}
}
