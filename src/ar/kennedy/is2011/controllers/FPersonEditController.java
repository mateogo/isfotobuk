package ar.kennedy.is2011.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.models.PersonModel;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.WebUtils;

import org.apache.commons.lang.StringUtils;



public class FPersonEditController extends AbstractController{

	private static final long serialVersionUID = 1L;
	private User user;
	private PersonaFisica fperson;
	private String fpersonId;
	private Boolean errorsDetected=false;
	private PersonModel model= new PersonModel();
	
	public FPersonEditController() {
        super();
    }


	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("****************************************************");
		log.debug("****************************************************");

		this.fpersonId = request.getParameter("fpersonId");
		if (this.fpersonId==null) this.fpersonId="0";
		
		String action = request.getParameter("action");
		log.debug("FPerson.action: begin. action:["+action+"] fpersonID:["+fpersonId+"]");

		if(action.equals("update")){
			errorsDetected = updateFPERSON(request,response,userSession);
		
		}else if (action.equals("newFPerson")){
			errorsDetected = newFPERSON(request,response,userSession);			
		
		}else if (action.equals("editUserPerson")){
			errorsDetected = updateFPERSONFromUser(request,response,userSession);			
		}

		if(!errorsDetected){
			response.sendRedirect("/userprofile");
		} else {
			request.getRequestDispatcher("/editPerson").forward(request, response);
		}

	}

	private Boolean newFPERSON(HttpServletRequest request, HttpServletResponse response, Session userSession){
		this.user= getUserFromSession(request);
		model = new PersonModel(this.user);
		
		model.initNewFperson();
		
		String personName = request.getParameter("pDenom");

		log.debug("********************* NEWFPERSON person: begin ["+personName+"]");

		if(!errorsDetected){
			log.debug("NEW F PERSON: ready to update");
			String denom       = request.getParameter("pDenom");
			String coment      = request.getParameter("pComent");
			String name        = request.getParameter("pName");
			String apellido    = request.getParameter("pApellido");
			String fnacim      = request.getParameter("pFechaNacim");
			String genero      = request.getParameter("pGender");
			
			if(StringUtils.isNotBlank(denom)) model.getFperson().setNombrePerson(denom);
			if(StringUtils.isNotBlank(coment)) model.getFperson().setComent(coment);
			if(StringUtils.isNotBlank(name)) model.getFperson().setNombre(name);
			if(StringUtils.isNotBlank(apellido)) model.getFperson().setApellido(apellido);
			if(StringUtils.isNotBlank(genero)) model.getFperson().setSexo(genero);
			if(StringUtils.isNotBlank(fnacim)) model.getFperson().setFechaNacimiento(WebUtils.getDateFromString(fnacim));

			model.getFperson().setUserId(this.user.getKey().getId());

			model.updateFPerson();
			log.debug("********************* NEWFPERSON person: end!! ["+personName+"]");
		}
		  
		return errorsDetected;
	}
	
	
	private Boolean updateFPERSON(HttpServletRequest request, HttpServletResponse response, Session userSession){
		this.user= getUserFromSession(request);
		model = new PersonModel(this.user);
		
		String personName = request.getParameter("pDenom");
		log.debug("********************* UPDATE person: begin ["+personName+"]");

		if (getPersonToEdit(user, personName)==null) errorsDetected=true;

		if(!errorsDetected){
			log.debug("Update Person:Person to edit: found! ready to update");
			String denom       = request.getParameter("pDenom");
			String coment      = request.getParameter("pComent");
			String name        = request.getParameter("pName");
			String apellido    = request.getParameter("pApellido");
			String fnacim      = request.getParameter("pFechaNacim");
			String genero      = request.getParameter("pGender");
			
			if(StringUtils.isNotBlank(denom)) model.getFperson().setNombrePerson(denom);
			if(StringUtils.isNotBlank(coment)) model.getFperson().setComent(coment);
			if(StringUtils.isNotBlank(name)) model.getFperson().setNombre(name);
			if(StringUtils.isNotBlank(apellido)) model.getFperson().setApellido(apellido);
			if(StringUtils.isNotBlank(genero)) model.getFperson().setSexo(genero);
			if(StringUtils.isNotBlank(fnacim)) model.getFperson().setFechaNacimiento(WebUtils.getDateFromString(fnacim));

			model.getFperson().setUserId(this.user.getKey().getId());

			model.updateFPerson();
			
			log.debug("FPERSON model Updated!");
		}
		  
		return errorsDetected;
	}
	
	private Boolean updateFPERSONFromUser(HttpServletRequest request, HttpServletResponse response, Session userSession){
		this.user= getUserFromSession(request);
		model = new PersonModel(this.user);
		
		String personName = request.getParameter("pDenom");
		log.debug("********************* UPDATE person: begin ["+personName+"]");

		if (getPersonToEditFromUser(user, personName)==null) errorsDetected=true;

		if(!errorsDetected){
			log.debug("Update Person:Person to edit: found! ready to update");
			String denom       = request.getParameter("pDenom");
			String coment      = request.getParameter("pComent");
			String name        = request.getParameter("pName");
			String apellido    = request.getParameter("pApellido");
			String fnacim      = request.getParameter("pFechaNacim");
			String genero      = request.getParameter("pGender");
			
			if(StringUtils.isNotBlank(denom)) model.getFperson().setNombrePerson(denom);
			if(StringUtils.isNotBlank(coment)) model.getFperson().setComent(coment);
			if(StringUtils.isNotBlank(name)) model.getFperson().setNombre(name);
			if(StringUtils.isNotBlank(apellido)) model.getFperson().setApellido(apellido);
			if(StringUtils.isNotBlank(genero)) model.getFperson().setSexo(genero);
			if(StringUtils.isNotBlank(fnacim)) model.getFperson().setFechaNacimiento(WebUtils.getDateFromString(fnacim));

			model.getFperson().setUserId(this.user.getKey().getId());

			model.updateFPerson();

			model.updatePersonFromUser(model.getFperson());
			log.debug("Update account: Account to edit: model Updated!");
		}
		  
		return errorsDetected;
	}
	

	
	private User getUserFromSession(HttpServletRequest request){
		return (User) SessionManager.getCurrentUser(request);
	}

	private PersonaFisica getPersonToEditFromUser(User user, String name){
		log.debug("Update person: PersonToEdit: begin");
		this.fperson=null;
		
		if(name!=null && user!=null){
			log.debug("Update person: PersonToEdit: trying to choose Person");			
			this.fperson=model.getPersonFromUser();
		}
		return this.fperson;
	}

	private PersonaFisica getPersonToEdit(User user, String name){
		log.debug("Searching PersonToEdit: begin");
		
		long pId = Long.parseLong(this.fpersonId);
	
		this.fperson=model.getFpersonById(pId);
		return this.fperson;
	}

	
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}
}