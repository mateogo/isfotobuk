package ar.kennedy.is2011.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.models.PersonModel;
import ar.kennedy.is2011.session.Session;
//import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.db.dao.PersonBean;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.session.SessionManager;


public class PersonController extends AbstractController {

	private static final long serialVersionUID = 7995740723219513508L;
	private static String EDIT_USER_PERSON = "editUserPerson";
	private static String NEW_F_PERSON = "newFPerson";
	private static String UPDATE_F_PERSON = "update";
		
	private PersonBean pbean;
	private PersonModel model;
	private Boolean errorsDetected= false;
	private String action;
	private String personId;
	
	public PersonController() {
		// TODO Auto-generated constructor stub
		//<url-pattern>/editPerson</url-pattern>
	}

	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("****************************************************");
		log.debug("PersonController.action: begin");

		initAction(request);
	
		if(!errorsDetected){
			request.setAttribute("pbean", pbean);
		}

		if(!errorsDetected){
			if(action.equals(EDIT_USER_PERSON)) {
				request.getRequestDispatcher("/secure/editFPerson.jsp").forward(request, response);
			}
			if(action.equals(NEW_F_PERSON)) {
				request.getRequestDispatcher("/secure/editFPerson.jsp").forward(request, response);
			}
			if(action.equals(UPDATE_F_PERSON)) {
				request.getRequestDispatcher("/secure/editFPerson.jsp").forward(request, response);
			}
		}
	}	

	private void initAction (HttpServletRequest request){
		this.action = request.getParameter("action");
		if (this.action==null) this.action="newFPerson";
		
		log.debug("PersonController.action: begin. action:["+action+"]");

		if(this.action.equals(EDIT_USER_PERSON)){
			errorsDetected = beanFactory(request, this.action);
			if(!errorsDetected){
				pbean.setFperson(model.getPersonFromUser());
				pbean.setContactos(model.getContactList());
				pbean.setLocations(model.getLocationList());
				
				log.debug("PersonController.action: INITializing pbean - ["+pbean.getContactos()+"]");
			}
		}else if (this.action.equals(NEW_F_PERSON)){
			errorsDetected = beanFactory(request,this.action);
		
		} else if (this.action.equals(UPDATE_F_PERSON)){
			this.personId = request.getParameter("personid");
			log.debug("PersonController.action: begin. action:["+action+"] personId: ["+personId+"]");

			long pId= 0;
			if (this.personId==null) errorsDetected=true;
			if (!errorsDetected) pId=Long.parseLong(personId);
			if (pId<=0) errorsDetected=true;
			if (!errorsDetected){

				errorsDetected = beanFactory(request,this.action);

				if(!errorsDetected){
					pbean.setFperson(model.getFpersonById(pId));
					pbean.setContactos(model.getContactList());
					pbean.setLocations(model.getLocationList());		
					log.debug("PersonController.action: pbean - ["+pbean.getFperson().getNombrePerson()+"]");
				}
			}
		}
	}

	public Boolean beanFactory(HttpServletRequest request, String action){
		Boolean errors=false;

		User user = getUserFromSession(request);
		if (user!=null){
			this.model = new PersonModel(user);
			this.pbean = new PersonBean(user,action);
		}else errors=true;
	
		return errors;	
	}
	
	
	private User getUserFromSession(HttpServletRequest request){
		return  (User) SessionManager.getCurrentUser(request);
	}

	
	
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}


	
}
