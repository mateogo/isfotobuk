package ar.kennedy.is2011.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ar.kennedy.is2011.models.AccountModel;
import ar.kennedy.is2011.db.entities.User;


/**
 * Servlet implementation class LoginUsuarioController
 */
public class UserHomePageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected final Logger log = Logger.getLogger(getClass());
	private User user;
       
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
		this.user = (User)session.getAttribute("usuarioLogeado");
		
		if(this.user != null){
			log.debug("UserHomePage - usuario-sesion: ok");
			if (urlGetRESTParameters(request,response)){
				log.debug("UserHomePage - url procesada: ok");
				if(VerifyUser(request))
				{
					request.getRequestDispatcher("/publicProfile.jsp").forward(request, response);
				}
				else
				{
					request.getRequestDispatcher("/errorUsuarioInexistente.jsp").forward(request, response);
				}
				//request.getRequestDispatcher("/secure/main.jsp").forward(request, response);
				//vease, como alternativa, usar reponse.sendRedirect
				//response.sendRedirect("/secure/main.jsp");
			}else{
				log.debug("UserHomePage: Fallo el procesamiento de la URL");
				//ver a donde redireccionar en caso de falla
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			//split URL
		}else{				
			log.debug("UserHomePage - usuario-sesion: no hay sesion");
			
			if (urlGetRESTParameters(request,response)){
				//Verificación si usuario existe.
				if(VerifyUser(request))
				{
					log.debug("UserHomePage: requestDispatcher a publicProfile");
					request.getRequestDispatcher("/publicProfile.jsp").forward(request, response);
				}
				else
				{
					request.getRequestDispatcher("/errorUsuarioInexistente.jsp").forward(request, response);
				}
								
			}
			else
			{
				log.debug("UserHomePage: usuario no encontrado");
				session.invalidate();
				request.setAttribute("iniciarSesion","Debe iniciar sesion nuevamente");
				request.getRequestDispatcher("/index.jsp").forward(request, response);				
			}
		}	
	}
			
	private Boolean urlGetRESTParameters(HttpServletRequest request, HttpServletResponse response){
		//TODO: parsear la URL; incorporar los parametros al request; retornar un ok		
		try
		{
			String url = request.getRequestURI().toString().trim();
			log.debug("UserHomePage: URL recibida: [" + url+"]");
			Boolean retorno = false;						
			if (!url.isEmpty()){
				String[] urls = url.split("/");			
				log.debug("parsing URL elements: " + urls.length);
								
				for (int i=0;i < urls.length ;i++){
					
					if (request.getAttribute("url") == null){							
						request.setAttribute("url", urls[i]);
					}
					else
					{							
						String valor = request.getAttribute("url") + "/" + urls[i];
						request.setAttribute("url", valor);						
					}
				}	
				
				if(urls.length>2){
					if(urls.length>=3) {
						request.setAttribute("usuario", urls[2]);
						log.debug("Agrego al request el atributo: usuario ["+urls[2]+"]");
					}
					if(urls.length>=4) {
						request.setAttribute("album", urls[3]);
						log.debug("Agrego al request el atributo: album ["+urls[3]+"]");
					}
					if (urls.length>=5) {
						request.setAttribute("foto", urls[4]);
						log.debug("Agrego al request el atributo: foto ["+urls[4]+"]");
					}
					retorno = true;					
				}else{
					log.debug("UserHomePage: No entro");
					retorno = false;	
				}
			}		
						
			return retorno;
		}
		catch (Exception ex)
		{
			return false;
		}	
	}

	public boolean VerifyUser(HttpServletRequest request)
	{
		/* La siguiente función verifica la existencia del usuario pasado como parámetro en el URI.
		 * Si el usuario no existe, se dredis */

		AccountModel model = new AccountModel();
		return model.ifExistUserByName(request.getAttribute("usuario").toString());
	}
}
