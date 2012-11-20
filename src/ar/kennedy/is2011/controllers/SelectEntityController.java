package ar.kennedy.is2011.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.db.entities.Person;
import ar.kennedy.is2011.db.entities.EntityRelationHeader;

import ar.kennedy.is2011.models.PersonModel;
import ar.kennedy.is2011.db.dao.AbstractDao;

import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.utils.WebUtils;


public class SelectEntityController extends AbstractController{

	private static final long serialVersionUID = 1L;
	
	String responseData = "[iajuu]";
	PersonModel model;
	Boolean errorsDetected = false;
	
	public SelectEntityController() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws  ServletException, IOException, Exception {
		//URL: /selectentity
		String entityId = request.getParameter("id");
		String entityType = request.getParameter("type");
		String entityName = request.getParameter("name");
		log.debug("************ AJAX *************SELECT: ["+entityType+"] ["+entityId+"]  ["+entityName+"]");

        if(entityType.equals("REL")) errorsDetected = !addRelationToSession(request,entityId);

        if(entityType.equals("PF")) errorsDetected  = !addFpersonToSession(request,entityId);

        if(entityType.equals("PI")) errorsDetected  = !addIpersonToSession(request,entityId);

    	if(errorsDetected) responseData="failed" ;
    	else responseData="success" ;

    	if(true){
    		response.setContentType("text/plain");
    		response.setHeader("Cache-Control", "no-cache");
    		response.getWriter().write(responseData);
		}
	}

	
	public Boolean addRelationToSession(HttpServletRequest request, String id){
		Boolean success=false;
		if(id==null) return success;
		Long entityId =Long.parseLong(id);
		if(entityId<=0) return success;

		model = new PersonModel();
		EntityRelationHeader erh= model.getERelationById(entityId);

		if(erh==null) return success;
		
		request.getSession().setAttribute("eselected", erh);
		request.getSession().setAttribute("etype", "REL");

		return true;
	}	
	

	public Boolean addFpersonToSession(HttpServletRequest request, String id){
		Boolean success=false;
		if(id==null) return success;
		Long entityId =Long.parseLong(id);
		if(entityId<=0) return success;

		model = new PersonModel();
		PersonaFisica person= model.getFpersonById(entityId);

		if(person==null) return success;
		
		request.getSession().setAttribute("eselected", person);
		request.getSession().setAttribute("etype", "PF");
		return true;
	}	

	public Boolean addIpersonToSession(HttpServletRequest request, String id){
		Boolean success=false;
		if(id==null) return success;
		Long entityId =Long.parseLong(id);
		if(entityId<=0) return success;

		model = new PersonModel();
		PersonaIdeal person= model.getIpersonById(entityId);

		if(person==null) return success;
		
		request.getSession().setAttribute("eselected", person);
		request.getSession().setAttribute("etype", "PI");
		return true;
	}	

	
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
}