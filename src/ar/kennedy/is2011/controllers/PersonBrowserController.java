package ar.kennedy.is2011.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.dao.PersonBean;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.models.PersonModel;




public class PersonBrowserController extends AbstractController{

	private static final long serialVersionUID = 1L;

	private User user;
	private Boolean errorsDetected=false;

	private PersonModel model;
	private PersonBean pbean;

	private String ipersonId;
	private String action;
	
	public PersonBrowserController() {
        super();
    }


	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("****************************************************");
		log.debug("****************************************************");

		this.ipersonId = request.getParameter("ipersonId");
		if (this.ipersonId==null) this.ipersonId="0";

		this.action = request.getParameter("action");
		if (this.action==null) action="browsePerson";

		log.debug("PERSON BROWSER begin. action:["+action+"] ipersonID:["+ipersonId+"]");

		this.user= getUserFromSession(request);
		model = new PersonModel(this.user);
		

		//if(!errorsDetected) errorsDetected = beanFactory(request, this.action);

		
		//if(action.equals("browsePerson")){
		if(true){
			errorsDetected = browsePERSON(request,response,userSession);

		}

		if(!errorsDetected){
			request.setAttribute("pbean", pbean);
		}

		if(!errorsDetected){
			request.getRequestDispatcher("/secure/browsePerson.jsp").forward(request, response);
			//response.sendRedirect("/userprofile");
		} else {
			request.getRequestDispatcher("/editPerson").forward(request, response);
		}

	}


	private Boolean browsePERSON(HttpServletRequest request, HttpServletResponse response, Session userSession){
		String personName = request.getParameter("pDenom");
		log.debug("********************* BROWSE person: begin ["+personName+"]");
		//model.setPersonsByName(personName);
		log.debug("BROWSE person end!");
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