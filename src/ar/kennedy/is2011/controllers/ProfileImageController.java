package ar.kennedy.is2011.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.models.AccountModel;
import ar.kennedy.is2011.session.SessionManager;


public class ProfileImageController extends AbstractController{

	private static final long serialVersionUID = 1L;
	private User user;
	private Boolean errorsDetected=false;
	private AccountModel model= new AccountModel();
	
	public ProfileImageController() {
        super();
    }


	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("****************************************************");
		log.debug("ProfileImageControlelr.action: begin");
		
		user= getUserFromSession(request);
		if(user==null) errorsDetected=true;
		
		if(!errorsDetected){
			updateProfileImageForUser(request);
		}

		if(!errorsDetected){
			response.sendRedirect("/secure/main.jsp");		
		} else {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}
	
	private Boolean updateProfileImageForUser(HttpServletRequest request){
		String picId = request.getParameter("pictureid");
		if(picId==null || picId=="") errorsDetected=true;
		if(!errorsDetected){
			if (model.getUserById(user.getKey())==null) errorsDetected=true;		
		}
		if(!errorsDetected){
			model.setProfileImageToUser(picId);
			model.update();
		}
		return errorsDetected;
	}
	
	private User getUserFromSession(HttpServletRequest request){
		return (User) SessionManager.getCurrentUser(request);
	}
		
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}
}