package ar.kennedy.is2011.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.session.Session;
//import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.db.dao.UserAccount;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.session.SessionManager;


public class UserAccountController extends AbstractController {

	private static final long serialVersionUID = 7995740723219513508L;
	private UserAccount userprofile;
	
	public UserAccountController() {
		// TODO Auto-generated constructor stub
	}

	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("****************************************************");
		log.debug("UserAccountController.action: begin");
		if(beanFactory(request)!=null){
			request.setAttribute("profile", userprofile);
		}
		if(true) {
			request.getRequestDispatcher("/secure/editProfile.jsp").forward(request, response);
		}
	}
	
	
	
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}
	public UserAccount beanFactory(HttpServletRequest request){
		log.debug("**** beanFactory: begin");
		
		userprofile = new UserAccount();
		User user = getUserFromSession(request);
		if (user!=null){
			userprofile.setUser(user);
			userprofile.setAccounts(user.getAccounts());
			userprofile.setAcountDefault(user.getDefaultAccount());
			log.debug("**** beanFactory: user:["+userprofile.getUser().getUserName()+"]");
		}
		return userprofile;
	}

	private User getUserFromSession(HttpServletRequest request){
		return  (User) SessionManager.getCurrentUser(request);
	}
}
