package ar.kennedy.is2011.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.WebUtils;
import ar.kennedy.is2011.models.PersonModel;

import org.apache.commons.lang.StringUtils;



public class IPersonEditController extends AbstractController{

	private static final long serialVersionUID = 1L;
	private User user;
	private PersonaIdeal iperson;
	private String ipersonId;
	private Boolean errorsDetected=false;
	private PersonModel model= new PersonModel();
	
	public IPersonEditController() {
        super();
    }


	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("****************************************************");
		log.debug("****************************************************");

		this.ipersonId = request.getParameter("ipersonId");
		if (this.ipersonId==null) this.ipersonId="0";

		this.user= getUserFromSession(request);
		model = new PersonModel(this.user);
		
		String action = request.getParameter("action");
		log.debug("IPerson.action: begin. action:["+action+"] ipersonID:["+ipersonId+"]");

		if(action.equals("updateIPerson")){
			errorsDetected = updateIPERSON(request,response,userSession);

		}else if (action.equals("newIPerson")){
			errorsDetected = newIPERSON(request,response,userSession);
		}

		if(!errorsDetected){
			response.sendRedirect("/userprofile");
		} else {
			request.getRequestDispatcher("/editPerson").forward(request, response);
		}

	}

	private Boolean newIPERSON(HttpServletRequest request, HttpServletResponse response, Session userSession){

		model.initNewIperson();
		String personName = request.getParameter("pDenom");

		log.debug("********************* NEWIPERSON person: begin ["+personName+"]");

		if(!errorsDetected){
			log.debug("NEW F PERSON: ready to update");
			String denom       = request.getParameter("pDenom");
			String coment      = request.getParameter("pComent");
			String fcreacion   = request.getParameter("pFechaCreacion");
			
			if(StringUtils.isNotBlank(denom)) model.getIperson().setNombrePerson(denom);
			if(StringUtils.isNotBlank(coment)) model.getIperson().setComent(coment);
			if(StringUtils.isNotBlank(fcreacion)) model.getIperson().setFechaCreacion(WebUtils.getDateFromString(fcreacion));

			model.getIperson().setUserId(this.user.getKey().getId());

			model.updateIPerson();
			log.debug("********************* NEWIPERSON person: end!! ["+personName+"]");
		}
		return errorsDetected;
	}

	private Boolean updateIPERSON(HttpServletRequest request, HttpServletResponse response, Session userSession){
		
		String personName = request.getParameter("pDenom");
		log.debug("********************* UPDATE person: begin ["+personName+"]");

		if (getPersonToEdit(user, personName)==null) errorsDetected=true;

		if(!errorsDetected){
			log.debug("Update Person:Person to edit: found! ready to update");
			String denom       = request.getParameter("pDenom");
			String coment      = request.getParameter("pComent");
			String fcreacion   = request.getParameter("pFechaCreacion");
			
			if(StringUtils.isNotBlank(denom)) model.getIperson().setNombrePerson(denom);
			if(StringUtils.isNotBlank(coment)) model.getIperson().setComent(coment);
			if(StringUtils.isNotBlank(fcreacion)) model.getIperson().setFechaCreacion(WebUtils.getDateFromString(fcreacion));

			model.getIperson().setUserId(this.user.getKey().getId());

			model.updateIPerson();
			
			log.debug("IPERSON model Updated!");
		}
		  
		return errorsDetected;
	}

	private User getUserFromSession(HttpServletRequest request){
		return (User) SessionManager.getCurrentUser(request);
	}

	private PersonaIdeal getPersonToEdit(User user, String name){
		log.debug("Searching PersonToEdit: begin");
		
		long pId = Long.parseLong(this.ipersonId);
	
		this.iperson=model.getIpersonById(pId);
		return this.iperson;
	}
	
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}
}