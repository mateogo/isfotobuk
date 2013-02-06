package ar.kennedy.is2011.controllers;

//import java.util.Enumeration;
import java.util.logging.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.lang.StringUtils;

import ar.kennedy.is2011.models.PersonModel;
import ar.kennedy.is2011.session.Session;
//import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.db.dao.PersonBean;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.WebUtils;


public class PersonController extends AbstractController {

	private static final long serialVersionUID = 7995740723219513508L;
	private static String EDIT_USER_PERSON = "editUserPerson";
	private static String NEW_F_PERSON = "newFPerson";
	private static String UPDATE_F_PERSON = "updateFPerson";
	private static String NEW_I_PERSON = "newIPerson";
	private static String UPDATE_I_PERSON = "updateIPerson";
	private static String BROWSE_PERSON = "browsePerson";
	private static String NEW_RELATION = "createRelation";
	private static String ADD_PERSON_TO_RELATION = "addPersonToRelation";
	

    private static final Logger gaelog = Logger.getLogger(PersonController.class.getName());
	
	private PersonBean pbean;
	private PersonModel model;

	private Boolean errorsDetected= false;

	private String action;
	private String personId;
	private String personName;
	private String personType;
	
	public PersonController() {
		// TODO Auto-generated constructor stub
		//<url-pattern>/editPerson</url-pattern>
	}

	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		gaelog.warning("P E R S O N    C O N T R O L L E R : BEGIN");

		this.action = request.getParameter("action");
		if (this.action==null) this.action="newFPerson";
		
		if(!errorsDetected) errorsDetected = beanFactory(request, this.action);

		if(!errorsDetected) initAction(request);

		if(!errorsDetected){
			if(action.equals(NEW_RELATION)) {
				createNewRelation(request, response);
			}
		}

		if(!errorsDetected){
			if (this.action.equals(ADD_PERSON_TO_RELATION)){
				addPersonsToRelation(request);
			}
		}

		if(!errorsDetected){
			request.setAttribute("pbean", pbean);
		}
				
		if(true){
			if(action.equals(EDIT_USER_PERSON)) {
				request.getRequestDispatcher("/secure/editFPerson.jsp").forward(request, response);
			}
			if(action.equals(NEW_F_PERSON)) {
				request.getRequestDispatcher("/secure/editFPerson.jsp").forward(request, response);
			}
			if(action.equals(UPDATE_F_PERSON)) {
				request.getRequestDispatcher("/secure/editFPerson.jsp").forward(request, response);
			}
			if(action.equals(NEW_I_PERSON)) {
				request.getRequestDispatcher("/secure/editIPerson.jsp").forward(request, response);
			}
			if(action.equals(UPDATE_I_PERSON)) {
				request.getRequestDispatcher("/secure/editIPerson.jsp").forward(request, response);
			}
			if(action.equals(BROWSE_PERSON)) {
				request.getRequestDispatcher("/secure/browsePerson.jsp").forward(request, response);
			}
			if(action.equals(NEW_RELATION)) {
				request.getRequestDispatcher("/secure/browsePerson.jsp").forward(request, response);
				//request.getRequestDispatcher("/secure/browsePerson.jsp").forward(request, response);
	    		//response.setContentType("text/plain");
	    		//response.setHeader("Cache-Control", "no-cache");
	    		//response.getWriter().write(stb.toString());
			}
			if(action.equals(ADD_PERSON_TO_RELATION)) {
				request.getRequestDispatcher("/secure/browsePerson.jsp").forward(request, response);
			}
		}
	}	

	private void initAction (HttpServletRequest request){
		log.debug("PersonController INIT ACTION BEGIN action:["+action+"]");

		if(this.action.equals(EDIT_USER_PERSON)){
			if(!errorsDetected){
				pbean.setFperson(model.getPersonFromUser());
				pbean.setContactos(model.getContactList());
				pbean.setLocations(model.getLocationList());
				
				log.debug("PersonController. EDIT USER PERSON: INITializing pbean - ["+pbean.getContactos()+"]");
			}
		}

		if (this.action.equals(NEW_F_PERSON)){
			log.debug("PersonController. NEW F PERSON: INITializing pbean - ["+"]");
			pbean.setFperson(model.initNewFperson());
		} 

		if (this.action.equals(UPDATE_F_PERSON)){

			this.personId = request.getParameter("personid");
			log.debug("PersonController.action: UPDATE F PERSON: INITializing pbean action:["+action+"] personId: ["+personId+"]");

			long pId= 0;
			if (this.personId==null) errorsDetected=true;
			if (!errorsDetected) pId=Long.parseLong(personId);
			if (pId<=0) errorsDetected=true;
			if (!errorsDetected){
				pbean.setFperson(model.getFpersonById(pId));
				pbean.setContactos(model.getContactList());
				pbean.setLocations(model.getLocationList());
				log.debug("PersonController.action:UPDATE F PERSON: INITializing pbeann - ["+pbean.getFperson().getNombrePerson()+"]");
			}
		}
		
		if (this.action.equals(NEW_I_PERSON)){
			log.debug("PersonController. NEW I PERSON: INITializing pbean - ["+"]");
		

		} 
		
		if (this.action.equals(UPDATE_I_PERSON)){

			this.personId = request.getParameter("personid");
			log.debug("PersonController.action: UPDATE I PERSON: INITializing pbean action:["+action+"] personId: ["+personId+"]");

			long pId= 0;
			if (this.personId==null) errorsDetected=true;
			if (!errorsDetected) pId=Long.parseLong(personId);
			if (pId<=0) errorsDetected=true;
			if (!errorsDetected){
				pbean.setIperson(model.getIpersonById(pId));
				pbean.setContactos(model.getContactList());
				pbean.setLocations(model.getLocationList());
				log.debug("PersonController.action:UPDATE F PERSON: INITializing pbeann - ["+pbean.getIperson().getNombrePerson()+"]");
			}
			
		}
		
		if (this.action.equals(NEW_RELATION)){
			this.personId = request.getParameter("personId");
			log.debug("PersonController.action: NEW RELATION: Initialization:["+action+"] personId: ["+personId+"]");			
		}
		
		if (this.action.equals(ADD_PERSON_TO_RELATION)){
			log.debug("PersonController.action: ADD PERSON TO RELATION: Initialization:["+action+"]");
		}
		
			//			for(Enumeration params = request.getParameterNames();params.hasMoreElements();){
			//				String param= (String) params.nextElement();
			//				log.debug("params element:["+param+"]  v:["+request.getParameter(param)+"]");
			//			}
			//			String[] values = request.getParameterValues("person");
			//			if (values !=null){
			//				for (String value: values){
			//					log.debug("-----Values: ["+value+"]");
			//				}
			//			}
		
		if (this.action.equals(BROWSE_PERSON)){
			this.personName = request.getParameter("pDenom");
			
			log.debug("PersonController.action: BROWSE PERSON: INITializing pbean action:["+action+"] personId: ["+personName+"]");
			if(this.personName!=null){
				if(WebUtils.isNotNull(this.personName)){
	
					getPersonsListFor(personName);
					pbean.setFpersons(model.getFpersons());
					pbean.setIpersons(model.getIpersons());
					pbean.setErelations(model.getRelationsForPersons());
				}
			}
		}
	}

	public void createNewRelation(HttpServletRequest request, HttpServletResponse response){
		this.personId     = request.getParameter("personId");
		String personType = request.getParameter("personType");
		String relationId = request.getParameter("relationId");

		gaelog.warning("PersonController.action: NEW RELATION: Creating relation:["+action+"] personId: ["+personId+"] relationId["+relationId+"]");
	
		if(!findPerson(personId,personType)){
			gaelog.warning("Find Person: FAILED");
			errorsDetected=true;
		}
		
		if(!errorsDetected){
			gaelog.warning("TRYING: initEntityRelation  ["+relationId+"]");
			model.initEntityRelation(relationId);
		}
		
		if(!errorsDetected){
			gaelog.warning("TRYING: getErelation  ["+model.getErelation()+"]");
			if(model.getErelation()==null) errorsDetected=true;
		}
		
		if(!errorsDetected){
			String predicate       = request.getParameter("relPredicate");
			String descr      = request.getParameter("relDescr");
			String subject   = request.getParameter("relSubject");
			
			gaelog.warning("NEW ENTITY RELATION: ready to update:Ê["+predicate+"]Ê["+descr+"]Ê["+subject+"]");
			model.getErelation().setSubject(subject);
			model.getErelation().setDescription(descr);
			model.getErelation().setPredicate(predicate);
			
			model.getErelation().setEntityType(this.personType);
			if(this.personType.equals("PF")){
				model.getErelation().setEntityId(model.getFperson());
			}else{
				model.getErelation().setEntityId(model.getIperson());
			}

			model.initNewEntityRelationItems();
			
			model.updateERelation();
			pbean.setErelation(model.getErelation());

			gaelog.warning("********************* NEW ENTITY RELATION HEADER: end!! ["+model.getErelation().getKey().getId()+"]");
		}
	}

	private void addPersonsToRelation(HttpServletRequest request){
		String relationId = request.getParameter("relationId");		
		log.debug("PersonController.action: ADD PERSONS TO  RELATION:["+action+"]  relationId["+relationId+"]");
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


	private Boolean getPersonsListFor(String personName){
		log.debug("********************* BROWSE person: begin ["+personName+"]");
		model.findPersonsLikeName(personName);
		log.debug("BROWSE person end!");
		return errorsDetected;
	}

	private Boolean findPerson(String personId,String personType){		
		gaelog.warning("Trying to Find Person: ["+personId+"]");

		model.searchPersonById(personId);
		gaelog.warning("Done:Trying to Find Person: ["+personId+"]");
		
		if(model.getFperson()==null && model.getIperson()==null) return false;

		log.debug("FIND PERSON: PF["+model.getFperson()+"]  pi:["+model.getIperson()+"]");
		if(model.getFperson()==null) this.personType="PI";
		else this.personType="PF";
		
		return true;
	}
	
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}
}
