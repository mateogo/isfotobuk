package ar.kennedy.is2011.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.entities.EntityRelations;
import ar.kennedy.is2011.db.entities.Person;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.models.PersonModel;
import ar.kennedy.is2011.models.SearchPicturesModel;


import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.WebUtils;

public class SelectPersonFromDialogServlet extends AbstractController{

	private static final long serialVersionUID = 1L;

	private Boolean success = true;
	//private List  fpersons;
	//private List  ipersons;
	private User user;
	private Person person;
	private String personType;
	private String responseText;
	private String nullJason="{ }";
	
	private HttpServletRequest request;
	private String action;
	private String qperson;
	private PersonModel model;
	private SearchPicturesModel picmodel = new SearchPicturesModel();
	
	public SelectPersonFromDialogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		this.request = request;
		this.action = this.request.getParameter("action");
		this.user=  (User) SessionManager.getCurrentUser(request);
		this.model = new PersonModel(this.user);

		log.debug("******SELECT PERSON FROM DIALLOG: action:["+action+"]");
		
		if(action.equals("fetchPerson")){
			success = fetchPerson();
			
			if(!success) responseText=nullJason;
			
			response.setContentType("text/plain");
	        response.setHeader("Cache-Control", "no-cache");
	        response.getWriter().write(responseText);
		}


		if(action.equals("addRelationToImage")){
			success = addRelationToImage();
			
			if(success) {
	    		model.updateERelation();
			}else{
				responseText=nullJason;				
			}
			
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


	/** ======URL: /selectaperson  ADD NEW RELATION PERON - IMAGE =======*/
	private Boolean addRelationToImage(){
		String personId = this.request.getParameter("personId");
		String personType = this.request.getParameter("personType");
		String imageId = this.request.getParameter("imageId");	
		log.debug("******ADD NEW RELATION TO IMAGE to begin:["+action+"] ["+personId+"] ["+personType+"] ["+personId+"]["+imageId+"]");

		if(!fetchPersonById(personId,personType)) return false;
		
		if(!fetchPictureById(imageId)) return false;
		
		if(!updateRelation()) return false;

		return true;
	}

	
	private Boolean updateRelation(){
		/*
		private Key key;
		private String subject;
		private String description;
		private String predicate;
		private Date cdate;
		private Date udate;
		private Key ownerkey;
		private String entityType;
		private Key entitykey;
		private String pictureId;
		private List<EntityRelations> erelations;
		*/
		model.fetchRelationForPicture(this.user,this.picmodel.getPicture().getPictureId());
	
		if(model.getErelation()==null){
			model.initEntityRelation("0");
			model.initNewRelationList();
			log.debug("****** update relation: is A NEW RELATION!");
			
		}else{
			log.debug("****** update relation: OLDER RELATION: FOUND!");
		}
		
		String subject    = "Vinculado en foto";
		String description= "Relaci—n establecida por el usuario entre foto y persona";
		String predicate  = "VINCULADO_EN_FOTO";
		String coment   = request.getParameter("comment");
		
		model.getErelation().setSubject(subject);
		model.getErelation().setDescription(description);
		model.getErelation().setPredicate(predicate);
		
		model.getErelation().setEntityType("PICTURE");
		model.getErelation().setPictureId(picmodel.getPicture().getPictureId());
		//model.getErelation().setEntitykey(null);

		EntityRelations erelationItem;
		if(this.personType.equals("PF")){
			erelationItem = model.fetchRelationForEntityId(model.getFperson().getKey().getId());
		}else{
			erelationItem = model.fetchRelationForEntityId(model.getIperson().getKey().getId());
		}

		if(erelationItem == null){
			log.debug("****** relation item: IS BRAND-NEW RECORD!");
			addPersonToRelationList(coment);	
		}else{
			log.debug("****** update relation: UPDATE RECORD FOR THIS GUY!");
			updatePersonToRelation(erelationItem,coment);
		}
		return true;
	}
	private void updatePersonToRelation(EntityRelations er, String coment){
		log.debug("Update relation .... ");
		er.setCardinality(1);
		er.setUdate(new Date());
		er.setComment(model.getErelation().getSubject());
		er.setEntityType(this.personType);
		if(this.personType.equals("PF")){
			er.setEntityId(model.getFperson().getKey().getId());
		}else{
			er.setEntityId(model.getIperson().getKey().getId());
		}
		er.setStatus(true);
	}

	private void addPersonToRelationList(String coment){
		log.debug("Add relation .... ");
		EntityRelations er = new EntityRelations();
		er.setCardinality(1);
		er.setCdate(new Date());
		er.setUdate(er.getCdate());
		er.setComment(coment);
		er.setEntityType(this.personType);
		if(this.personType.equals("PF")){
			er.setEntityId(model.getFperson().getKey().getId());
		}else{
			er.setEntityId(model.getIperson().getKey().getId());
		}
		er.setStatus(true);
		er.setErelation(model.getErelation());
		
		model.addRelationItem(er);
	}
	/** ===============  SET DEFAULT IMAGE - END ====================*/
	
	
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
		stb.append(person.toString()+"\"");

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

	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}


}