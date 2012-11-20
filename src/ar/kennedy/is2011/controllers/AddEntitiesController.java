package ar.kennedy.is2011.controllers;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.db.entities.EntityRelationHeader;
import ar.kennedy.is2011.db.entities.EntityRelations;

import ar.kennedy.is2011.models.PersonModel;

import ar.kennedy.is2011.session.Session;


public class AddEntitiesController extends AbstractController{

	private static final long serialVersionUID = 1L;
	
	String responseData = "failed";
	PersonModel model;
	Boolean errorsDetected = false;
	EntityRelationHeader erelation;
	
	HttpServletRequest request;
	String entityListString;
	
	public AddEntitiesController() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws  ServletException, IOException, Exception {
		//URL: /addentities
		this.request = request;

        this.errorsDetected= !addEntitiesToRelation();
 
        if(errorsDetected){
        	responseData="failed" ;
        }else{
   // 		log.debug("***ready to update relation w/new items: ["+model.getErelation().getErelations()+"]");
    		
    		model.updateERelation();
       		responseData="success" ;
    	}
 
    	if(true){
    		response.setContentType("text/plain");
    		response.setHeader("Cache-Control", "no-cache");
    		response.getWriter().write(responseData);
		}
	}

	public Boolean addEntitiesToRelation(){
		this.entityListString = this.request.getParameter("listids");
		log.debug("ADD ENTITIES TO RELATION: begin: ["+entityListString+"]");
		if(this.entityListString==null || !this.entityListString.endsWith(",")) return false;

		model = new PersonModel();

		if(!getRelationFromSession()) return false;
		
		log.debug("ADD ENTITIES TO RELATION: to parseStringList");
		if(!parseStringList()) return false;
		
		log.debug("ADD ENTITIES TO RELATION: to to addPersonsToRelation");
		addFPersonsToRelation();
		addIPersonsToRelation();

		return true;
	}	

	private Boolean getRelationFromSession(){

		String relType = (String) this.request.getSession().getAttribute("etype");
		if(relType==null) return false;
		if(!relType.equals("REL")) return false;
		
		EntityRelationHeader erh = (EntityRelationHeader) this.request.getSession().getAttribute("eselected");
		log.debug("From Session: ["+erh+"] : ["+relType+"]");

		if(erh==null) return false;
		
		model.setErelation(model.getERelationById(erh.getKey().getId()));
		if(model.getErelation()==null) return false;

		return true;
	}

	private Boolean parseStringList(){
		// so de split
		
		String[] tokens = entityListString.split("[,]+");
		
		List<PersonaFisica> fpersons = new ArrayList<PersonaFisica>();
		List<PersonaIdeal> ipersons = new ArrayList<PersonaIdeal>();
		
		if(tokens==null || tokens.length==0) return false;
		
		for (String item:tokens){
			String person = item.trim();
			while(person.endsWith(";")) person=person.substring(0,person.length());

			String id = person.substring(person.indexOf(":")+1);
			Long personid= Long.parseLong(id);
			log.debug("id: ["+id+"]  long personid:["+personid+"]");
		
			if(person.startsWith("PF") && personid>0){
				fpersons.add(model.getFpersonById(personid));
				log.debug("Success:PF id: ["+personid+"]  long personid:["+person+"]");
			
			}else if(person.startsWith("PI") && personid>0){
				ipersons.add(model.getIpersonById(personid));
				log.debug("Success:PI id: ["+personid+"]  long personid:["+person+"]");
							
			}
		}
		model.setFpersons(fpersons);
		model.setIpersons(ipersons);
		return true;
	}

	
	private Boolean addFPersonsToRelation(){
		if(model.getFpersons()!=null && !model.getFpersons().isEmpty()){
		log.debug("Trying to add Fpersons .... ");
		for(PersonaFisica person:model.getFpersons()){
			log.debug("ready to add ["+person+"]  to:["+model.getErelation()+"]");

			EntityRelations erelation = model.fetchRelationForEntityId(person.getKey().getId());
			
			if(erelation == null){
				addPersonToRelation(person.getKey().getId(),"PF");	
			}else{
				updatePersonToRelation(erelation,person.getKey().getId(),"PF");
			}
		}
		}else return false;
		
		//fetchRelationForEntityId
		return true;
	}

	private Boolean addIPersonsToRelation(){
		if(model.getIpersons()!=null && !model.getIpersons().isEmpty()){
		log.debug("Trying to add Ipersons .... ");
		for(PersonaIdeal person:model.getIpersons()){
			log.debug("ready to add ["+person+"]  to:["+model.getErelation()+"]");

			EntityRelations erelation = model.fetchRelationForEntityId(person.getKey().getId());
			
			if(erelation == null){
				addPersonToRelation(person.getKey().getId(),"PI");	
			}else{
				updatePersonToRelation(erelation,person.getKey().getId(),"PI");
			}
		}
		}else return false;
		
		//fetchRelationForEntityId
		return true;
	}


	private void updatePersonToRelation(EntityRelations er,Long pId, String type){
		log.debug("Update relation .... ");
		er.setCardinality(1);
		er.setUdate(new Date());
		er.setComment(model.getErelation().getSubject());
		er.setEntityId(pId);
		er.setEntityType(type);
		er.setStatus(true);
	}

	
	private void addPersonToRelation(Long pId, String type){
		log.debug("Add relation .... ");
		EntityRelations er = new EntityRelations();
		er.setCardinality(1);
		er.setCdate(new Date());
		er.setUdate(er.getCdate());
		er.setComment(model.getErelation().getSubject());
		er.setEntityId(pId);
		er.setEntityType(type);
		er.setStatus(true);
		er.setErelation(model.getErelation());
		
		model.addRelationItem(er);
	}
	
	
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
}