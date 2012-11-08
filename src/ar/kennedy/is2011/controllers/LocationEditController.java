package ar.kennedy.is2011.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.entities.Location;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.models.PersonModel;
import ar.kennedy.is2011.session.SessionManager;
import org.apache.commons.lang.StringUtils;
import java.util.Date;



public class LocationEditController extends AbstractController{

	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

	private User user; //el usuario duenio de la sesion
	private PersonaFisica fperson; // la person fisica sobre la que se actualizan datos
	private Location location; // el location a dar de alta o actualizar

	private PersonModel model= new PersonModel();

	private Boolean isNewLocation = true;
	private Boolean errorsDetected=false;
	
	public LocationEditController() {
        super();
    }


	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		
		String action = request.getParameter("action");
		String personId = request.getParameter("locPersonId");
		this.request = request;

		log.debug("****************************************************");
		log.debug("LOCATION EDIT CONTROLLER: begin. action:["+action+"] personId:["+personId+"]");
			
		if(!errorsDetected) initModel();

		if(!errorsDetected) initLocation();
		
		if(!errorsDetected) locationFactory();
		
		if(!errorsDetected){
			errorsDetected = updateLocation(request,response,userSession);	
		}


		if(!errorsDetected){
			log.debug("LOCATION EDIT CONTROLLER: bye OK");
			response.sendRedirect("/userprofile");
		} else {
			log.debug("LOCATION EDIT CONTROLLER: bye but... errorsDetected");
			request.getRequestDispatcher("/editPerson").forward(request, response);
		}

	}

	private void initModel(){
		/**
		 * Inicializa PersonModel con el user activo 
		 * e instancia la PersonaFisica sobre la que se
		 * actualizan datos.
		 */
		this.user= getUserFromSession(request);
		String personId = request.getParameter("locPersonId");
		log.debug("********************* INIT MODEL: begin ["+personId+"]");
		
		model = new PersonModel(this.user);
		model.setFperson(model.getFpersonById(Long.parseLong(personId)));
		
		this.fperson = model.getFperson();
		if (this.fperson==null) errorsDetected=true;
	}
	
	private void initLocation(){
		String locId       = request.getParameter("locLocationId");
		log.debug("********************* INIT LOCATION: data:�["+locId+"]");
		
		long locationId = Long.parseLong(locId);
		if(locationId<=0){
			this.location = new Location();
			this.isNewLocation = true;
		}else{
			this.isNewLocation = false;
			this.location = model.getLocationFromFPersonById(locationId);
			
			if (this.location == null){
				errorsDetected=true;
				log.debug("********************* INIT LOCATION:oops! no se encontr��["+locationId+"]");
			}
		}
	}

	private void locationFactory(){
		String locDescripcion = request.getParameter("locDescripcion");
		log.debug("********************* LOCATION FACTORY: begin ["+locDescripcion+"] person:["+model.getFperson().getNombrePerson()+"]");
		
		String locCalle1    = request.getParameter("locCalle1");
		String locCalle2    = request.getParameter("locCalle2");
	
		log.debug("********************* LOCATION FACTORY: data:�["+locCalle1+"] / ["+locCalle2+"] / ["+locDescripcion+"]");
		
		if (this.location ==null) {
			errorsDetected=true;
			return;
		}

		if(StringUtils.isNotBlank(locCalle1))    location.setCalle1(locCalle1);
		if(StringUtils.isNotBlank(locCalle2))    location.setCalle2(locCalle2);
		location.setCpostal(request.getParameter("locCPostal"));
		location.setLocalidad(request.getParameter("locLocalidad"));
		location.setProvincia(request.getParameter("locProvincia"));
		location.setPais(request.getParameter("locPais"));
		location.setDescr(locDescripcion);
		location.setSedeType(request.getParameter("locSedeType"));
		location.setEventoType(request.getParameter("locEvento"));
	
		location.setFechaLocacion(new Date());

		location.setPersonId(model.getFperson().getKey().getId());
	}
	

	private Boolean updateLocation(HttpServletRequest request, HttpServletResponse response, Session userSession){
		if(isNewLocation){
			errorsDetected= !model.addLocation(this.location);
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