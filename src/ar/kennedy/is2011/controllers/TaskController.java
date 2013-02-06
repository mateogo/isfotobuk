package ar.kennedy.is2011.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.models.TaskModel;
import ar.kennedy.is2011.models.RelationModel;

import ar.kennedy.is2011.db.dao.ArticlePortlet;
import ar.kennedy.is2011.db.dao.TaskBean;
import ar.kennedy.is2011.db.dao.TaskDao;
import ar.kennedy.is2011.db.dao.ArticleDao;
import ar.kennedy.is2011.db.dao.RelationDao;
import ar.kennedy.is2011.db.dao.PersonDao;
import ar.kennedy.is2011.db.dao.PictureDao;

import ar.kennedy.is2011.db.entities.EntityRelations;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.Task;

import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.WebUtils;

//import ar.kennedy.is2011.utils.WebUtils;
//import org.apache.commons.lang.StringUtils;
//import ar.kennedy.is2011.session.SessionManager;
//import java.util.Enumeration;


public class TaskController extends AbstractController {

	private static final long serialVersionUID = 7995740723219513508L;

//	private static String NEW_ARTICLE    = "newArticle";
//	private static String UPDATE_ARTICLE = "updateArticle";
//	private static String ADD_RELATION   = "addRelation";
//	private static String ADD_RELATION_ITEM = "addRelationItem";
//	private static String BROWSE_ARTICLE = "browseArticle";
//	private static String BROWSE_ENTITY  = "browseEntity";

	private static String NEW_TASK    = "newTask";
	private static String UPDATE_TASK = "updateTask";
	private static String ADD_RELATION   = "addRelation";
	private static String ADD_RELATION_ITEM = "addRelationItem";
	private static String BROWSE_TASK = "browseTask";
	private static String BROWSE_ENTITY  = "browseEntity";

    private HttpServletRequest request;
    
	private TaskBean pbean;
	private TaskModel model;
	private RelationModel relationModel;

	private RelationDao relationDao;
	private TaskDao taskDao;
	private ArticleDao articleDao;
	private PersonDao personDao;
	private PictureDao pictureDao;

	
	private String action;
	private String taskId;
	private String entityName;
	
	public TaskController() {
		// TODO Auto-generated constructor stub
		//<url-pattern>/editPerson</url-pattern>
	}

	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		this.request = request;
		
		this.action = request.getParameter("action");
		this.taskId = request.getParameter("taskid");
		
		if (this.action==null) this.action="newTask";
		
		log.debug("T A S K      C O N T R O L L E R (/task): BEGIN ["+this.action+"] ["+this.taskId+"]");
		
		if(action.equals(NEW_TASK)) {
			if(newTask()){
				request.setAttribute("pbean", pbean);
				request.getRequestDispatcher("/secure/editTask.jsp").forward(request, response);
			}
		}

		if(action.equals(UPDATE_TASK)) {
			if(updateTask()){
				request.setAttribute("pbean", pbean);
				request.getRequestDispatcher("/secure/editTask.jsp").forward(request, response);
			}
		}


		if(action.equals(BROWSE_TASK)) {
			if(browseTask()){
				log.debug("browseTask: OKKKK");
				request.setAttribute("pbean", pbean);
				request.getRequestDispatcher("/secure/browseTask.jsp").forward(request, response);
			}
		}

		if(action.equals(BROWSE_ENTITY)) {
			if(browseEntity()){
				request.setAttribute("pbean", pbean);
				request.getRequestDispatcher("/secure/browseTask.jsp").forward(request, response);
			}
		}

		
		if(action.equals(ADD_RELATION)) {
			if(addNewRelation()){
				response.sendRedirect("/task?action=updateTask&taskid="+this.taskId);
			}
		}

		if(action.equals(ADD_RELATION_ITEM)) {
			if(addNewRelationItem()){
				String responseText = "success";
				response.setContentType("text/plain");
		        response.setHeader("Cache-Control", "no-cache");
		        response.getWriter().write(responseText);
			}
		}
	
	}	

	private Boolean newTask (){
		
		if(!beanFactory()) return false;
		
		model.initNewTask();
		pbean.setTask(model.getTask());

		return true;
	}


	private Boolean updateTask (){
		
		if(!beanFactory()) return false;

		this.taskId = this.request.getParameter("taskid");
		log.debug("TaskController.action: UPDATE TASK: INITializing pbean action:["+action+"] taskId: [" + this.taskId +"]");

		pbean.setTask(model.selectTask(this.taskId));
		if(pbean.getTask()==null) return false;
		
		pbean.setErelations(relationDao.fetchRelationsForEntity("TASK",pbean.getTask().getKey().getId()));

		log.debug("PersonController.action:UPDATE F PERSON: INITializing pbeann - ["+pbean.getTask().toString()+"]");
		return true;
	}
	
	
	
	private Boolean browseTask (){
		
		if(!beanFactory()) return false;
		
		this.entityName = request.getParameter("taskLocator");
		
		log.debug("TaskController.action: BROWSE TASK: INITializing pbean action:["+action+"] entityName: ["+this.entityName+"]");

		if(this.entityName!=null){
			if(WebUtils.isNotNull(this.entityName)){

				pbean.setTasks(taskDao.fetchTasksLikeLocator(this.entityName+"%"));
			}
		}

		
		return true;
	}
	
		
	private Boolean browseEntity (){
		
		if(!beanFactory()) return false;
		
		this.entityName = request.getParameter("enName");
		String type = request.getParameter("enType");
		
		log.debug("TaskController.action: BROWSE ENTITY: INITializing pbean action:["+action+"] ["+type+"]:["+this.entityName+"]");
		if(WebUtils.isNull(this.entityName)) return false;
		if(WebUtils.isNull(type)) return false;
		pbean.setBrowsertab("");
		pbean.setViewertab("active");

		if(type.equals("ARTICLE")){
			log.debug("TaskController.action: BROWSE ARTICLE:");
			pbean.setEntities(articleDao.fetchEntitiesLikeName(this.entityName+"%"));

		}else if(type.equals("TASK")){
			log.debug("TaskController.action: BROWSE TASK:");
			pbean.setEntities(taskDao.fetchEntitiesLikeLocator(this.entityName+"%"));	
	
		}else if(type.equals("PF")){
			log.debug("TaskController.action: BROWSE PF:");
			pbean.setEntities(personDao.fetchFEntitiesLike(this.entityName+"%"));
			
		}else if(type.equals("PI")){
			log.debug("TaskController.action: BROWSE PI:");
			pbean.setEntities(personDao.fetchIEntitiesLike(this.entityName+"%"));
			
		}else if(type.equals("PICTURE")){
			log.debug("TaskController.action: BROWSE PICTURE:");
			pbean.setEntities(pictureDao.fetchEntitiesLikeName(this.entityName+"%"));
			
		}
		
		return true;
	}
	
	
	public Boolean beanFactory(){
		User user = getUserFromSession(request);
		log.debug(" Bean factory user: ["+user.getDisplayName()+"]");

		this.model = new TaskModel(user);
		this.pbean = new TaskBean(user,action);
		
		this.relationModel = new RelationModel(user);
		
		this.relationDao = new RelationDao(user);
		this.taskDao = new TaskDao(user);
		this.personDao = new PersonDao();
		this.pictureDao = new PictureDao();
		
		this.pbean.setSelectedTask( (Task) this.request.getSession().getAttribute("taskselected"));
		if(this.pbean.getSelectedTask()!=null){
			log.debug(" Bean factory SelectedTask: ["+this.pbean.getSelectedTask().getLocator()+"]");
			this.pbean.setErelations(relationDao.fetchRelationsForEntity("TASK",this.pbean.getSelectedTask().getKey().getId()));
			buildTaskView();

		}
		this.pbean.setBrowsertab("active");
		this.pbean.setViewertab("");

		return true;
	}
	public void buildTaskView(){
		ArticlePortlet portlet = new ArticlePortlet();

		portlet.setTask(taskDao.fetchTask(Long.parseLong(pbean.getSelectedTask().getId())));
		if(portlet.getTask()==null) return;

		getLocationRelations(portlet);

		getTaskRelations(portlet);

		pbean.setArticlePortlet(portlet);

	}

	private void getTaskRelations(ArticlePortlet portlet){
		if(pbean.getErelations()==null) return;
		List<RelationModel.TaskRelation> taskRelations = relationModel.renderTaskPredicate(pbean.getErelations());
		portlet.setTaskrelations(taskRelations);
	}

	private void getLocationRelations(ArticlePortlet portlet){
		if(pbean.getErelations()==null) return;
		List<RelationModel.LocationRelation> locations = relationModel.renderLocationPredicate(pbean.getErelations());
		portlet.setLocations(locations);
	}


	
	public Boolean addNewRelationItem(){
		log.debug(" ADD RELATION ITEM to begin");

		if(!beanFactory()) return false;

		String relationId   = request.getParameter("relationId");
		String entityType    = request.getParameter("entityType");
		String entityId   = request.getParameter("entityId");
		String entityName   = request.getParameter("entityName");
		String itemDescr   = request.getParameter("itemDescr");

		
		log.debug(" ADD RELATION ITEM :["+relationId+"]["+entityType+"]["+entityId+"]["+entityName+"]["+itemDescr+"]");
		
		relationModel.setErelation(relationDao.fetchERelationById(relationId));
		if(relationModel.getErelation()==null) return false;
	
		EntityRelations erelationItem = relationModel.fetchRelationForEntityId (entityType,entityId);
		
		if(erelationItem == null){
			log.debug("****** relation item: IS BRAND-NEW RECORD!");
			EntityRelations er = new EntityRelations();
			er.setCardinality(1);
			er.setCdate(new Date());
			er.setUdate(er.getCdate());
			er.setComment(itemDescr);
			er.setEntityType(entityType);

			if(entityType.equals("PICTURE")) er.setPictureId(entityId);
			else er.setEntityId(Long.parseLong(entityId));

			er.setStatus(true);
			er.setErelation(relationModel.getErelation());
			
			relationModel.addRelationItem(er);

		}else{
			log.debug("****** update relation: UPDATE RECORD FOR THIS GUY!");
			erelationItem.setCardinality(1);
			erelationItem.setUdate(new Date());
			erelationItem.setComment(itemDescr);
			erelationItem.setStatus(true);

		}
		relationModel.updateRelation();

		return true;
	}

	
	
	public Boolean addNewRelation(){
		log.debug(" ADD RELATION to begin");

		if(!beanFactory()) return false;

		log.debug(" ADD RELATION searching task");
		String entityId   = request.getParameter("entityId");
		this.taskId    = entityId;
		String entityType = request.getParameter("entityType");
		String relationId = request.getParameter("relationId");

		pbean.setTask(model.selectTask(entityId));

		if(model.getTask()==null) return false;
		
		log.debug(" ADD RELATION init Relation");
		relationModel.initEntityRelation(relationId);
		if(relationModel.getErelation()==null) return false;

		if(true){
			log.debug(" ADD RELATION ready to update");
			String predicate       = request.getParameter("relPredicate");
			String descr      = request.getParameter("relDescr");
			String subject   = request.getParameter("relSubject");
			
			relationModel.getErelation().setSubject(subject);
			relationModel.getErelation().setDescription(descr);
			relationModel.getErelation().setPredicate(predicate);
			
			relationModel.getErelation().setEntityType(entityType);
			relationModel.getErelation().setEntityId(model.getTask().getKey().getId());

			relationModel.initNewEntityRelationItems();
			
			relationModel.updateRelation();
			pbean.setErelation(relationModel.getErelation());

		}
		return true;
	}

	
	
	
	
	private User getUserFromSession(HttpServletRequest request){
		return  (User) SessionManager.getCurrentUser(request);
	}


	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}
}
