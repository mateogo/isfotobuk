package ar.kennedy.is2011.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.models.ArticleModel;
import ar.kennedy.is2011.models.RelationModel;

import ar.kennedy.is2011.db.dao.ArticleBean;
import ar.kennedy.is2011.db.dao.ArticleDao;
import ar.kennedy.is2011.db.dao.TaskDao;
import ar.kennedy.is2011.db.dao.RelationDao;
import ar.kennedy.is2011.db.dao.PersonDao;
import ar.kennedy.is2011.db.dao.PictureDao;

import ar.kennedy.is2011.db.entities.EntityRelations;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.Article;

import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.WebUtils;

//import ar.kennedy.is2011.utils.WebUtils;
//import org.apache.commons.lang.StringUtils;
//import ar.kennedy.is2011.session.SessionManager;
//import java.util.Enumeration;


public class ArticleController extends AbstractController {

	private static final long serialVersionUID = 7995740723219513508L;

	private static String NEW_ARTICLE    = "newArticle";
	private static String UPDATE_ARTICLE = "updateArticle";
	private static String ADD_RELATION   = "addRelation";
	private static String ADD_RELATION_ITEM = "addRelationItem";
	private static String BROWSE_ARTICLE = "browseArticle";
	private static String BROWSE_ENTITY  = "browseEntity";

    private HttpServletRequest request;
    
	private ArticleBean pbean;
	private ArticleModel model;
	private RelationModel relationModel;

	private RelationDao relationDao;
	private ArticleDao articleDao;
	private PersonDao personDao;
	private PictureDao pictureDao;
	private TaskDao taskDao;

	
	private String action;
	private String articleId;
	private String entityName;
	
	public ArticleController() {
		// TODO Auto-generated constructor stub
		//<url-pattern>/editPerson</url-pattern>
	}

	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		this.request = request;
		
		this.action = request.getParameter("action");
		this.articleId = request.getParameter("articleid");
		
		if (this.action==null) this.action="newArticle";
		
		log.debug("A R T I C L E     C O N T R O L L E R (/article): BEGIN ["+this.action+"] ["+this.articleId+"]");
		
		if(action.equals(NEW_ARTICLE)) {
			if(newArticle()){
				request.setAttribute("pbean", pbean);
				request.getRequestDispatcher("/secure/editArticle.jsp").forward(request, response);
			}
		}

		if(action.equals(UPDATE_ARTICLE)) {
			if(updateArticle()){
				request.setAttribute("pbean", pbean);
				request.getRequestDispatcher("/secure/editArticle.jsp").forward(request, response);
			}
		}


		if(action.equals(BROWSE_ARTICLE)) {
			if(browseArticle()){
				request.setAttribute("pbean", pbean);
				request.getRequestDispatcher("/secure/browseArticle.jsp").forward(request, response);
			}
		}

		if(action.equals(BROWSE_ENTITY)) {
			if(browseEntity()){
				request.setAttribute("pbean", pbean);
				request.getRequestDispatcher("/secure/browseArticle.jsp").forward(request, response);
			}
		}

		
		if(action.equals(ADD_RELATION)) {
			if(addNewRelation()){
				response.sendRedirect("/article?action=updateArticle&articleid="+this.articleId);
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

	private Boolean newArticle (){
		
		if(!beanFactory()) return false;
		
		model.initNewArticle();
		pbean.setArticle(model.getArticle());
		pbean.setErelations(null);

		return true;
	}


	private Boolean updateArticle (){
		
		if(!beanFactory()) return false;

		this.articleId = this.request.getParameter("articleid");
		log.debug("ArticleController.action: UPDATE ARTICLE: INITializing pbean action:["+action+"] articleId: [" + this.articleId +"]");

		pbean.setArticle(model.selectArticle(this.articleId));
		if(pbean.getArticle()==null) return false;
		
		pbean.setErelations(relationDao.fetchRelationsForEntity("ARTICLE",pbean.getArticle().getKey().getId()));

		log.debug("PersonController.action:UPDATE F PERSON: INITializing pbeann - ["+pbean.getArticle().getName()+"]");
		return true;
	}
	
	
	
	private Boolean browseArticle (){
		
		if(!beanFactory()) return false;
		
		this.entityName = request.getParameter("arName");
		
		log.debug("ArticleController.action: BROWSE ARTICLE: INITializing pbean action:["+action+"] entityName: ["+this.entityName+"]");

		if(this.entityName!=null){
			if(WebUtils.isNotNull(this.entityName)){

				pbean.setArticles(articleDao.fetchArticlesLikeName(this.entityName+"%"));
			}
		}

		
		return true;
	}
	
		
	private Boolean browseEntity (){
		
		if(!beanFactory()) return false;
		
		this.entityName = request.getParameter("enName");
		String type = request.getParameter("enType");
		
		log.debug("ArticleController.action: BROWSE ENTITY: INITializing pbean action:["+action+"] ["+type+"]:["+this.entityName+"]");
		if(WebUtils.isNull(this.entityName)) return false;
		if(WebUtils.isNull(type)) return false;
		pbean.setBrowsertab("");
		pbean.setViewertab("active");

		if(type.equals("ARTICLE")){
			log.debug("ArticleController.action: BROWSE ARTICLE:");
			pbean.setEntities(articleDao.fetchEntitiesLikeName(this.entityName+"%"));
	
		}else if(type.equals("TASK")){
			log.debug("TaskController.action: BROWSE TASK:");
			pbean.setEntities(taskDao.fetchEntitiesLikeLocator(this.entityName+"%"));	
	
		}else if(type.equals("PF")){
			log.debug("ArticleController.action: BROWSE PF:");
			pbean.setEntities(personDao.fetchFEntitiesLike(this.entityName+"%"));
			
		}else if(type.equals("PI")){
			log.debug("ArticleController.action: BROWSE PI:");
			pbean.setEntities(personDao.fetchIEntitiesLike(this.entityName+"%"));
			
		}else if(type.equals("PICTURE")){
			log.debug("ArticleController.action: BROWSE PICTURE:");
			pbean.setEntities(pictureDao.fetchEntitiesLikeName(this.entityName+"%"));
			
		}
		
		return true;
	}
	
	
	public Boolean beanFactory(){
		User user = getUserFromSession(request);
		log.debug(" Bean factory user: ["+user.getDisplayName()+"]");

		this.model = new ArticleModel(user);
		this.pbean = new ArticleBean(user,action);
		
		this.relationModel = new RelationModel(user);
		
		this.relationDao = new RelationDao(user);
		this.articleDao = new ArticleDao(user);
		this.personDao = new PersonDao();
		this.pictureDao = new PictureDao();
		
		pbean.setSelectedArticle( (Article) this.request.getSession().getAttribute("artselected"));
		if(pbean.getSelectedArticle()!=null){
			pbean.setErelations(relationDao.fetchRelationsForEntity("ARTICLE",pbean.getSelectedArticle().getKey().getId()));

		}
		pbean.setBrowsertab("active");
		pbean.setViewertab("");

		return true;
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

		log.debug(" ADD RELATION searching article");
		String entityId   = request.getParameter("entityId");
		this.articleId    = entityId;
		String entityType = request.getParameter("entityType");
		String relationId = request.getParameter("relationId");

		pbean.setArticle(model.selectArticle(entityId));

		if(model.getArticle()==null) return false;
		
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
			relationModel.getErelation().setEntityId(model.getArticle().getKey().getId());

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
