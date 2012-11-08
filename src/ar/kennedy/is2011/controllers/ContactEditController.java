package ar.kennedy.is2011.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.entities.ContactosPerson;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.models.PersonModel;
import ar.kennedy.is2011.session.SessionManager;
import org.apache.commons.lang.StringUtils;



public class ContactEditController extends AbstractController{

	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

	private User user; //el usuario duenio de la sesion
	private PersonaFisica fperson; // la person fisica sobre la que se actualizan datos
	private ContactosPerson contacto; // el contacto a dar de alta o actualizar

	private PersonModel model= new PersonModel();

	private Boolean isNewContact = true;
	private Boolean errorsDetected=false;
	
	public ContactEditController() {
        super();
    }


	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		
		String action = request.getParameter("action");
		String personId = request.getParameter("ctPersonId");
		this.request = request;

		log.debug("****************************************************");
		log.debug("CONTACT EDIT CONTROLLER: begin. action:["+action+"] personId:["+personId+"]");
			
		if(!errorsDetected) initModel();

		if(!errorsDetected) initContact();
		
		if(!errorsDetected) contactFactory();
		
		if(!errorsDetected){
			errorsDetected = updateContact(request,response,userSession);	
		}


		if(!errorsDetected){
			log.debug("CONTACT EDIT CONTROLLER: bye OK");
			response.sendRedirect("/userprofile");
		} else {
			log.debug("CONTACT EDIT CONTROLLER: bye but... errorsDetected");
			request.getRequestDispatcher("/userprofile").forward(request, response);
		}

	}

	private void initModel(){
		/**
		 * Inicializa PersonModel con el user activo 
		 * e instancia la PersonaFisica sobre la que se
		 * actualizan datos.
		 */
		this.user= getUserFromSession(request);
		String personId = request.getParameter("ctPersonId");
		log.debug("********************* INIT MODEL: begin ["+personId+"]");
		
		model = new PersonModel(this.user);
		model.setFperson(model.getFpersonById(Long.parseLong(personId)));
		
		this.fperson = model.getFperson();
		if (this.fperson==null) errorsDetected=true;
	}
	
	private void initContact(){
		String ctId       = request.getParameter("ctContactId");
		log.debug("********************* INIT CONTACT: data:Ê["+ctId+"]");
		
		long contactId = Long.parseLong(ctId);
		if(contactId<=0){
			this.contacto = new ContactosPerson();
			this.isNewContact = true;
		}else{
			this.isNewContact = false;
			this.contacto = model.getContactFromFPersonById(contactId);
			
			if (this.contacto == null){
				errorsDetected=true;
				log.debug("********************* INIT CONTACT:oops! no se encontr—Ê["+contactId+"]");
			}
		}
	}

	private void contactFactory(){
		String ctDescr = request.getParameter("ctDescr");
		log.debug("********************* CONTACT FACTORY: begin ["+ctDescr+"] person:["+model.getFperson().getNombrePerson()+"]");
		
		String ctValue    = request.getParameter("ctValue");
		String ctProtocol = request.getParameter("ctProtocol");
		String ctProvider = request.getParameter("ctProvider");
		String ctUseType  = request.getParameter("ctUseType");
		String ctType     = request.getParameter("ctType");
		log.debug("********************* CONTACT FACTORY: data:Ê["+ctValue+"] / ["+ctProtocol+"] / ["+ctProvider+"] / ["+ctUseType+"] / ["+ctType);
		
		if (this.contacto ==null) {
			errorsDetected=true;
			return;
		}

		if(StringUtils.isNotBlank(ctValue))    contacto.setValue(ctValue);
		contacto.setProtocol(ctProtocol);
		if(StringUtils.isNotBlank(ctProvider)) contacto.setProvider(ctProvider);
		if(StringUtils.isNotBlank(ctUseType))  contacto.setUseType(ctUseType);
		if(StringUtils.isNotBlank(ctType))     contacto.setConType(ctType);
		contacto.setDescr(ctDescr);
		contacto.setPersonId(model.getFperson().getKey().getId());
	}
	

	private Boolean updateContact(HttpServletRequest request, HttpServletResponse response, Session userSession){
		if(isNewContact){
			errorsDetected= !model.addContact(this.contacto);
		}
		if(!errorsDetected){
			model.updateFPerson();
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