package ar.kennedy.is2011.templates;

//import java.io.*;
import java.io.IOException;
import java.io.Writer;

//import java.util.*;
//import java.lang.reflect.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.WebUtils;
import ar.kennedy.is2011.utils.TemplateUtils;


import freemarker.template.*;

/**
 * <p>This is very very primitive MVC Controller servlet base class, based
 * on example 1. The application specific controller servlet should extend
 * this class.
 */
public abstract class AbstractTemplateController extends HttpServlet {
    protected Configuration cfg; 

    private static final long serialVersionUID = 7320911254853012236L;

	protected final Logger log = Logger.getLogger(getClass());
	
	protected String action = "";
	
	protected Session userSession = null;
	
    protected TemplateUtils page;
    
    protected String userName;



	
    
    public void init() {
        // Initialize the FreeMarker configuration;
        // - Create a configuration instance
        cfg = ConfigSingleton.getConfiguration();
    
        // - Templates are stored in the WEB-INF/templates directory of the Web app.
        cfg.setServletContextForTemplateLoading(
                getServletContext(), ConfigSingleton.TEMPLATE_DIR);

        page = new TemplateUtils();
    }
 
    
	
	protected void internalAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("******* Abstract template begin ************");
		
		//Agrega clave=valor en response: response.setHeader("Cache-Control", "no-cache");
		setHttpHeaders(response);
		//recupera la session del usuario, desde la base de datos
		this.userSession = getSession(request, response);
		// cada concrete-controller establece si valida el login
		if(validateLogin(request)) {
			if(!validateUserLogin(request, response, this.userSession)){
				log.debug("====== validateLogin: FAIL redirect to home");
				response.sendRedirect("/index.jsp");
			}
		}
		log.debug("======= ValidateLogin: OK or Not required");
		
		processAction(request, response);
		
	}
    
	
    protected void processAction(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    	
    	this.action = parseUrl(req);
        log.debug("action parsed:["+this.action+"]");

        
        try {
        	
            //*******************************
            action(req, resp);
            //*******************************

        } catch (Exception e) {
            throw new ServletException(
                    "Error while processing template action", e);
        }

        
        // Set the request charset to the same as the output charset,
        // because HTML forms normally send parameters encoded with that.
        log.debug("========= back in ABSTRACT TEMPLATE CONTROLLER");
        req.setCharacterEncoding(cfg.getOutputEncoding());
        
        // Call the action method
        
        if (page.getTemplate() != null) { // show a page with a template
            log.debug("========= trying template processing");
            // Get the template object
            Template t = cfg.getTemplate(page.getTemplate());
            
            // Prepare the HTTP response:
            // - Set the MIME-type and the charset of the output.
            //   Note that the charset should be in sync with the output_encoding setting.
            resp.setContentType("text/html; charset=" + cfg.getOutputEncoding());
            // - Prevent browser or proxy caching the page.
            //   Note that you should use it only for development and for interactive
            //   pages, as it significantly slows down the Web site.
            resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
            resp.setHeader("Pragma", "no-cache");
            resp.setHeader("Expires", "Thu, 01 Dec 1994 00:00:00 GMT");
            Writer out = resp.getWriter();
            
            // Merge the data-model and the template
            try {
                log.debug("========= now process page");
                t.process(page.getRoot(), out);
                log.debug("========= then process page");
                
            } catch (Exception e) {
                throw new ServletException(
                        "Error while processing FreeMarker template", e);
            }
        } else if (page.getForward() != null) { // forward request
            log.debug("========= now page forward");
            RequestDispatcher rd = req.getRequestDispatcher(page.getForward());
            rd.forward(req, resp);
        } else {
            throw new ServletException("The action didn't specified a command.");
        }
    }
    
    
    private String getActionLabel(HttpServletRequest req){
        // Choose action method
        String action = req.getServletPath();
        log.debug("action servletPath:["+action+"]");
        
		String url = req.getRequestURI().toString().trim();
		log.debug("URL recibida: [" + url+"]");


        if (action == null) action = "index";
        if (action.startsWith("/")) action = action.substring(1);
        if (action.indexOf("/") != -1) action = action.substring(0, action.lastIndexOf("/"));
        if (action.lastIndexOf(".") != -1) action = action.substring(0, action.lastIndexOf("."));

        return action;
    	
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		internalAction(request, response);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		internalAction(request, response);
	}

	public void action(HttpServletRequest request, HttpServletResponse response) throws Exception {
	}
	
	public abstract boolean validateLogin(HttpServletRequest request);
	
	protected Boolean validateUserLogin(HttpServletRequest request, HttpServletResponse response, Session userSession) {
		return WebUtils.validateUserLogin(request, response, userSession);
	}
	
	protected Session getSession(HttpServletRequest request, HttpServletResponse response) {
		Session userSession = null;
		// Busca el identificador de session en las cookies
		String sessionIdentificator = getSessionIdentificator(request);
		
		if(sessionIdentificator != null) {
			//recupera la session de la base de datos
			userSession = SessionManager.get(request, sessionIdentificator);
		}

		if (userSession!=null) this.userName = getUserFromSession(request).getUserName();
		else this.userName = "usuario";

		return userSession;
	}
	
	private String parseUrl(HttpServletRequest request){
		try
		{
			String requestUri = request.getRequestURI();
			String contextPath = request.getContextPath();
			String servletPath = request.getServletPath();
			String pathInfo = request.getPathInfo();
			String pathTranslated = request.getPathTranslated();
	
			String url = pathInfo;
			
			log.debug("UserHomePage: requestUri: [" + requestUri+"] contextPath:["+contextPath+"]  servletPath:["+servletPath+"]");
			log.debug("============:   pathInfo: [" + pathInfo+"] pathTranslated:["+pathTranslated+"]");
	
			if (url!=null && !url.isEmpty()){
				String[] urls = url.split("/");			
				log.debug("parsing URL elements: " + urls.length+ ": "+urls);
								
				for (String item:urls){
					log.debug("url  elements: ["+ item+"]");
				}
				
				return urls[1];
			}
			return "default";
	
		}
		catch (Exception ex)
		{
			return null;
		}	

	}
	
	
	protected String getSessionIdentificator(HttpServletRequest request) {
		return WebUtils.getSessionIdentificator(request);
	}
   
	protected void setHttpHeaders(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
	}
	
	protected User getUserFromSession(HttpServletRequest request){
		return (User) SessionManager.getCurrentUser(request);
	}

}