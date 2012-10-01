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
public class ForwardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected final Logger log = Logger.getLogger(getClass());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForwardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Start ForwardServlet - doPost");
		String pagina = request.getParameter("misFotos");
		if(pagina ==null) pagina="misFotos";
		   
		  HttpSession session = request.getSession();	
		  Usuario usr = (Usuario)session.getAttribute("usuarioLogeado");
		  
		  if(usr != null){
			log.debug("ForwardServlet - usuario notNull");
			if(pagina.equals("misFotos")){
				log.debug("ForwardServlet - redirecciono a secure/main.jsp");
				request.getRequestDispatcher("secure/main.jsp").forward(request, response);
			}
			  
		  }else{
				log.debug("ForwardServlet - usuario no encontrado");
				session.invalidate();
				request.setAttribute("iniciarSesion","Debe iniciar sesion nuevamente");
				request.getRequestDispatcher("index.jsp").forward(request, response);
		  }		
	}
}
