package ar.kennedy.is2011.controllers;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.models.RelationModel;
import ar.kennedy.is2011.models.RelationModel.PicturesRelation;

import ar.kennedy.is2011.db.dao.ArticleBean;
import ar.kennedy.is2011.db.dao.RelationDao;
import ar.kennedy.is2011.db.dao.EntityDao;
import ar.kennedy.is2011.db.dao.ArticleDao;

import ar.kennedy.is2011.db.dao.ArticlePortlet;

import ar.kennedy.is2011.db.entities.EntityRelations;
import ar.kennedy.is2011.db.entities.EntityRelationHeader;

import ar.kennedy.is2011.db.entities.Article;
import ar.kennedy.is2011.db.entities.PictureEy;

import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.utils.WebUtils;

//import ar.kennedy.is2011.utils.WebUtils;
//import ar.kennedy.is2011.models.ArticleModel;
//import ar.kennedy.is2011.db.dao.PersonDao;
//import ar.kennedy.is2011.db.dao.PictureDao;

//import ar.kennedy.is2011.utils.WebUtils;
//import org.apache.commons.lang.StringUtils;
//import ar.kennedy.is2011.session.SessionManager;
//import java.util.Enumeration;


public class HomeArticlesController extends AbstractController {

	private static final long serialVersionUID = 7995740723219513508L;
	private static final String DESTACADOS_REL_PREDICATE = "RENDERIZA_CON";
	private static final String DESTACADOS_REL_SUBJECT = "home.destacados";

	private static final String TRIPLETS_REL_PREDICATE = "RENDERIZA_CON";
	private static final String TRIPLETS_REL_SUBJECT = "home.triplets";

	private static final String RENDER         = "home";
	private static final String ARTICLE_VIEW  = "article";
	
    private HttpServletRequest request;
    
	private ArticleBean pbean;
	private RelationModel relationModel;

	private RelationDao relationDao;
	private EntityDao entityDao;

	
	private String action;
	private List<EntityRelationHeader> erelations;
	private List<EntityRelationHeader> articleERH;
	
	private List<EntityRelations> erelationitems;

	private ArticleDao articleDao;
	//private PersonDao personDao;
	//private PictureDao pictureDao;
	//private String articleId;
	//private String entityName;

	
	public HomeArticlesController() {
		// TODO Auto-generated constructor stub
		//<url-pattern>/editPerson</url-pattern>
	}

	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		this.request = request;
		
		this.action = request.getParameter("action");
		
		if (this.action==null) this.action=RENDER;
		
		log.debug("H O M E    A R T I C L E     C O N T R O L L E R (/home): BEGIN ["+this.action+"]");
		

		if(action.equals(RENDER)) {
			if(homeFactory()){
				log.debug("homeFactory: READY TO LAUNCH MYFOTOBUK");
				request.setAttribute("pbean", pbean);
				request.getRequestDispatcher("/secure/myFotobuk.jsp").forward(request, response);
			}
		}

		if(action.equals(ARTICLE_VIEW)) {
			if(articleView()){
				log.debug("article render:: READY TO LAUNCH ARTICLE VIEW");
				request.setAttribute("pbean", pbean);
				request.getRequestDispatcher("/secure/articleRender.jsp").forward(request, response);
			}
		}
	}	

	private Boolean articleView (){
		log.debug("articleView: BEGIN ");
		
		if(!beanFactory()) return false;
	
		if(!getArticleData()) return false;
		
		return true;
	}

	private Boolean getArticleData(){
		log.debug("getArticleData from relation: BEGIN ");
		String articleId = (String) request.getParameter("articleid");
		if(WebUtils.isNull(articleId)) return false;

		List<ArticlePortlet> articles = new ArrayList<ArticlePortlet>();
		ArticlePortlet portlet = new ArticlePortlet();

		portlet.setArticle(articleDao.fetchArticle(Long.parseLong(articleId)));
		if(portlet.getArticle()==null) return false;
		pbean.setArticle(portlet.getArticle());

		//EntityRelationHeader erelation = relationDao.fetchERelationById((String) request.getParameter("relationid"));
		//if(erelation==null) return false;
		//portlet.setErelation(erelation);
		//portlet.setPicture(getPictureFromRelationItem(erelation));

		getArticleRelations(portlet);

		articles.add(portlet);
		pbean.setDestacados(articles);
		return true;
	}

	
	private void getArticleRelations(ArticlePortlet portlet){
		setArticleERH(relationDao.fetchRelationsForEntity("ARTICLE", portlet.getArticle().getKey().getId()));
		if(getArticleERH()==null) return;
		getLocations(portlet);
		getPictures(portlet);
	}

	private void getLocations(ArticlePortlet portlet){
		List<RelationModel.LocationRelation> locations = relationModel.renderLocationPredicate(getArticleERH());
		portlet.setLocations(locations);
	}

	private void getPictures(ArticlePortlet portlet){
		List<RelationModel.PicturesRelation> pictures = relationModel.retrievePicturesFromRelations(getArticleERH());
		portlet.setPictures(pictures);
		if(pictures==null) return;
		for (PicturesRelation pic:pictures){
			log.debug("pictures found: ["+pic.getRelationPredicate()+"] ["+pic.getRelationSubject()+"]");
			if(portlet.getPicture()==null) portlet.setPicture(pic.getPicture());
			if(pic.getRelationSubject().equals("home.destacados")) portlet.setPicture(pic.getPicture());
			else  portlet.setPictureBranding(pic.getPicture());
		}
	}
	
	
	private Boolean homeFactory (){
		log.debug("homeFactory: BEGIN ");
		
		if(!beanFactory()) return false;
	
		log.debug("homeFactory: getPortletList: BEGIN");
		if(!getPortletList()) return false;
		
		return true;
	}


	private Boolean getPortletList(){		
		pbean.setDestacados(buildHomeList(DESTACADOS_REL_PREDICATE,DESTACADOS_REL_SUBJECT));
		pbean.setTriplets  (buildHomeList(TRIPLETS_REL_PREDICATE,TRIPLETS_REL_SUBJECT));
		return true;
	}
	
	private List<ArticlePortlet> buildHomeList(String predicate, String subject){
		this.erelations = relationDao.fetchRelationsForPredicateSubject(predicate,subject);
		log.debug("homeFactory: getPortletList: erelations:["+erelations+"]");

		if(this.erelations==null) return null;
		Boolean haveAtLeastOnePortlet = false;
		Boolean haveProperStuff = false;
		
		List<ArticlePortlet> homeArticles = new ArrayList<ArticlePortlet>();
		
		for(EntityRelationHeader ey:this.erelations){
			log.debug("homeFactory: home-article-list: iteration:["+ey+"]");
			this.erelationitems = ey.getErelations();
			haveProperStuff = true;

			ArticlePortlet portlet = new ArticlePortlet();
			
			portlet.setErelation(ey);			
			portlet.setArticle(getArticleFromRelation(ey));
			portlet.setPicture(getPictureFromRelationItem(ey));
			
			if(portlet.getArticle()==null) haveProperStuff=false;
			if(portlet.getPicture()==null) haveProperStuff=false;
			
			if(haveProperStuff){
				homeArticles.add(portlet);
				haveAtLeastOnePortlet=true;
			}
		}
		log.debug("homeFactory: buildDestacadosList: END!!["+haveAtLeastOnePortlet+"]");
		if(!haveAtLeastOnePortlet) return null;
		return homeArticles;
	}


	private Article getArticleFromRelation(EntityRelationHeader relation){
		return entityDao.fetchArticle(relation);
	}

	private PictureEy getPictureFromRelationItem(EntityRelationHeader relation){
		if(erelationitems==null) return null;
		if(erelationitems.isEmpty()) return null;
		for(EntityRelations item: erelationitems){
			if(item.getEntityType().equals("PICTURE")){
				return entityDao.fetchPicture(item);
			}
		}
		return null;
	}

	
	private Boolean beanFactory(){
		log.debug(" Bean factory: ");

		//this.model = new ArticleModel();
		this.pbean = new ArticleBean();
		
		this.relationModel = new RelationModel();
		
		this.relationDao = new RelationDao();
		this.articleDao = new ArticleDao();
		//this.personDao = new PersonDao();
		//this.pictureDao = new PictureDao();
		this.entityDao = new EntityDao();
		
		return true;
	}
	

	
	
	
	

	public List<EntityRelationHeader> getArticleERH() {
		return articleERH;
	}

	public void setArticleERH(List<EntityRelationHeader> articleERH) {
		this.articleERH = articleERH;
	}

	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
}
