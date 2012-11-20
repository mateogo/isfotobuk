package ar.kennedy.is2011.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.kennedy.is2011.db.entities.Person;
import ar.kennedy.is2011.models.PersonModel;
import ar.kennedy.is2011.models.SearchPicturesModel;


import ar.kennedy.is2011.utils.WebUtils;

public class SelectPersonFromDialogServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	protected final Logger log = Logger.getLogger(getClass());
	

	private Boolean success = true;
	//private List  fpersons;
	//private List  ipersons;
	private Person person;
	private String personType;
	private String responseText;
	private String nullJason="{ }";
	
	private HttpServletRequest request;
	private String action;
	private String qperson;
	private PersonModel model = new PersonModel();
	private SearchPicturesModel picmodel = new SearchPicturesModel();
	
	public SelectPersonFromDialogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		this.action = this.request.getParameter("action");

		log.debug("******SELECT PERSON FROM DIALLOG: action:["+action+"]");
		
		if(action.equals("fetchPerson")){
			success = fetchPerson();
			
			if(!success) responseText=nullJason;
			
			response.setContentType("text/plain");
	        response.setHeader("Cache-Control", "no-cache");
	        response.getWriter().write(responseText);
		}

		if(action.equals("setdefaultimage")){
			success = setDefaultImage();
			
			if(success) responseText="success";
			else responseText="failed";
			
			response.setContentType("text/plain");
	        response.setHeader("Cache-Control", "no-cache");
	        response.getWriter().write(responseText);
		}

	}

	/** ==============URL: /selectaperson  SET DEFAULT IMAGE ====================*/
	private Boolean setDefaultImage(){
		String personId = this.request.getParameter("personId");
		String personType = this.request.getParameter("personType");
		String imageId = this.request.getParameter("imageId");	
		log.debug("******Set default image to begin:["+action+"] ["+personId+"] ["+personType+"] ["+personId+"]["+imageId+"]");

		if(!fetchPersonById(personId,personType)) return false;
		
		if(!fetchPictureById(imageId)) return false;
		
		if(!updatePerson()) return false;
	
		return true;
	}

	private Boolean updatePerson(){
		if(personType.equals("PF")){
			model.getFperson().setDefaultImageId(picmodel.getPicture().getPictureId());
			model.updateFPerson();
			return true;
		}
		if(personType.equals("PI")){
			model.getIperson().setDefaultImageId(picmodel.getPicture().getPictureId());
			model.updateIPerson();
			return true;
		}
		return false;
	}
	
	private Boolean fetchPictureById(String iId){
		if( !WebUtils.isNotNull(iId) )   return false;
		this.picmodel = new SearchPicturesModel();
		picmodel.setPicture(picmodel.getPicture(iId));
		if(picmodel.getPicture()==null) return false;		

		return true;
	}

	private Boolean fetchPersonById(String pId, String pType){
		if( !WebUtils.isNotNull(pId) )   return false;
		if( !WebUtils.isNotNull(pType) ) return false;
		long personId = Long.parseLong(pId);
		if(personId<=0) return false;
		
		this.person=null;
		this.personType=null;
		
		if(pType.equals("PF")){
			person = model.getFpersonById(personId);
			personType="PF";
			if(model.getFperson()==null) return false;
			else return true;
		}
		if(pType.equals("PI")){
			person = model.getIpersonById(personId);
			personType="PI";
			if(model.getIperson()==null) return false;
			else return true;
		}
		return false;
	}
	/** ===============  SET DEFAULT IMAGE - END ====================*/

	
	
	
	/** ==============URL: /selectaperson  FETCH PERSON ====================*/
	private Boolean fetchPerson(){
		this.qperson = this.request.getParameter("qperson");
		log.debug("******Fetch perton to begin:["+action+"] ["+qperson+"]");

		this.person = selectOnePerson(qperson);
		if (this.person==null) return false;
		
		if (!buildResponse()) return false;
		
		return true;
	}
	
	private Boolean buildResponse(){
		StringBuilder stb = new StringBuilder();
		stb.append("{");
		
		stb.append("\"personType\": \"");
		stb.append(personType+"\",");

		stb.append("\"personId\": \"");
		stb.append(person.getId()+"\",");

		stb.append("\"personName\": \"");
		stb.append(person.getNombrePerson()+"\"");

		stb.append("}");
		responseText = stb.toString();
		
		return true;
	}
	
	private Person selectOnePerson(String name){
	
		if( !WebUtils.isNotNull(name) ) return null;
		model.getFPersonByName(name);
		if(model.getFperson()!=null){
			personType="PF";
			return model.getFperson();
		}

		model.getIPersonByName(name);
		if(model.getIperson()!=null){
			personType="PI";
			return model.getIperson();
		}
		
		return null;
	}
	/** ===============  FETCH PERSON - END ====================*/
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action (request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action (request, response);
	}


}