package ar.kennedy.is2011.templates;

import java.util.HashMap;
import java.util.Map;

//import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import ar.kennedy.is2011.session.Session;

public class ExampleAjaxTemplate extends AbstractTemplateController {

	private static final long serialVersionUID = 7320911254853012236L;
	
    private Map location = new HashMap();

    
   
	public void action(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("****************Ajax template *****************");
		if (action.equals("location")){
			templateAction();
		}
	}
		
	private void templateAction(){

		synchronized (location) {
            location.put("street", "ConcepcionCamargo 250");
            location.put("name", "Sandra Cirilliano");
            location.put("neighbour", "Billa Krespo");
            location.put("mailto", "sscirilliano@gmail.com");
        }
        page.put("location", location);
        page.setTemplate("/article/ajax/location.ftl");
	}

    public static String noNull(String s) {
        return s == null ? "" : s;
    }
    
    public static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0; 
    }
    
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
}
