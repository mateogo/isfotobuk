package ar.kennedy.is2011.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.Location;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.utils.WebUtils;
import ar.kennedy.is2011.models.AccountModel;
import ar.kennedy.is2011.session.SessionManager;


public class UserEditController extends AbstractController{

	private static final long serialVersionUID = 1L;
	private User user;
	private Boolean errorsDetected=false;
	private AccountModel model ;
	
	public UserEditController() {
        super();
    }


	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("****************************************************");
		log.debug("UserEditController.action: begin");
		this.user= getUserFromSession(request);

		if(this.user==null) errorsDetected=true;
		
		if(!errorsDetected){
			model = new AccountModel(this.user);
			model.getUserById(this.user.getKey());
			
			String userName          = request.getParameter("userId");
			String mail              = request.getParameter("userMail");
			String fechaNacimiento   = request.getParameter("userFechaNacim");
			String sexo              = request.getParameter("userGender");
			String pais              = request.getParameter("userPais");
			String prov              = request.getParameter("userProvincia");
			String appRole           = request.getParameter("userAppRole");
			String idPreguntaSecreta = request.getParameter("userSecretQ");
			String respuestaPregunta = request.getParameter("userAnswer");
			
			model.getUser().setUserName(userName);
			model.getUser().setMail(mail);
			model.getUser().setFechaNacimiento(WebUtils.getDateFromString(fechaNacimiento));
			model.getUser().setSexo(sexo);
			model.getUser().setAppRole(appRole);
			model.getUser().setIdPreguntaSecreta(Integer.parseInt(idPreguntaSecreta));
			model.getUser().setRespuestaPregunta(respuestaPregunta);
			
			updateLocation(user, pais,prov);
			
			log.debug(userName+"/"+mail+"/"+fechaNacimiento+"/"+sexo+"/"+pais+"/"+prov+"/"+appRole+"/"+idPreguntaSecreta+"/"+respuestaPregunta);

			model.update();
	
			WebUtils.updateUserSession(request, userSession, model);
			
		}
		
		 // sesion.setAttribute("usuarioLogeado", usuario);
		  
	
		if(!errorsDetected){
			response.sendRedirect("/secure/main.jsp");
		} else {
			response.sendRedirect("/index.jsp");
		}

	}
	private void updateLocation(User user, String pais, String prov){
		//
		Location loc = model.getUser().getLocacion();
		if(loc==null) loc = new Location();
		loc.setPais(pais);
		loc.setProvincia(prov);
		//model.updateLocation(loc);
		model.getUser().setLocacion(loc);
		log.debug("update location: ok");
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