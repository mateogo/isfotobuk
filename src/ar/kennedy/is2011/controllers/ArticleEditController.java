package ar.kennedy.is2011.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.models.ArticleModel;

import com.google.appengine.api.datastore.Text;

//import ar.kennedy.is2011.utils.WebUtils;
//import org.apache.commons.lang.StringUtils;





public class ArticleEditController extends AbstractController{

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;

	private User user;
	private ArticleModel model ;
	private Boolean success = true;
	private String action;
	private String articleId;

//	private Article article;
//	private List<Article> articles;
//	private ArticleDao articleDao;

	public ArticleEditController() {
        super();
    }


	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("****************ARTICLE EDIT CONTROLLER *****************");
		this.request = request;
		
		this.articleId = request.getParameter("articleId");
		if (this.articleId==null) this.articleId="0";

		this.user= getUserFromSession(request);

		this.model = new ArticleModel(this.user);
		
		this.action = request.getParameter("action");
		log.debug("Article edit controller: begin -  action:["+action+"] articleId:[" + articleId + "]");

		
		if(this.action.equals("newArticle")){
			success = newArticle();
			
			if(success){
				
				//response.setContentType("text/plain");
		        //response.setHeader("Cache-Control", "no-cache");
		        //response.getWriter().write("CONCLUIDO CON EXITO ["+model.getArticle().getId()+"]");

				response.sendRedirect("/article?action=browseArticle&arName="+model.getArticle().getName());
			} else {
				response.sendRedirect("/article?action=newArticle");
			}
		}


		if(this.action.equals("updateArticle")){
			success = updateArticle();
					
			if(success){
				response.sendRedirect("/article?action=browseArticle&arName="+model.getArticle().getName());
			} else {
				response.sendRedirect("/article?action=updateArticle&articleid="+model.getArticle().getId());
			}
		}	

	}

	private Boolean newArticle(){

		model.initNewArticle();

		if(retrieveArticleData()) {
			model.updateArticle();
			log.debug("********************* NEW ARTICLE: end!! ["+model.getArticle()+"]");
			return true;
			
		}

		return false;
	}


	private Boolean retrieveArticleData(){
		String name = request.getParameter("arName");

		log.debug("********************* NEW ARTICLE ready to update: begin ["+name+"]");

		String mainTitle    = request.getParameter("arMainT");
		String secondTitle  = request.getParameter("arSectondT");
		String slug         = request.getParameter("arSlug");
		String text         = request.getParameter("arText");
		String visibility   = request.getParameter("arVisibility");
		String isActive     = request.getParameter("arIsActive");
		String type         = request.getParameter("arType");
		String section      = request.getParameter("arSection");
		String rendering    = request.getParameter("arRendering");
//		String publishfdate = request.getParameter("arPublishFD");
//		String publishtdate = request.getParameter("arPublishTD");
		
		model.getArticle().setName(name);
		model.getArticle().setMainTitle(mainTitle);
		model.getArticle().setSecondTitle(secondTitle);
		model.getArticle().setSlug(slug);
		model.getArticle().setText(new Text(text));
		model.getArticle().setVisibility(Integer.parseInt(visibility));
		model.getArticle().setIsActive(Integer.parseInt(isActive));
		model.getArticle().setType(type);
		model.getArticle().setSection(section);
		model.getArticle().setRendering(rendering);
		model.getArticle().setPublishFromDate(new Date());
		model.getArticle().setPublishUpToDate(new Date());
		
		return true;
	}

	private Boolean updateArticle(){
		
		String name = request.getParameter("arName");
		log.debug("********************* UPDATE ARTICLE ready to update: begin ["+name+"]");

		if (getArticleToEdit()==null) return false;
		log.debug("Update Person:Person to edit: found! ready to update");

		if(retrieveArticleData()) {
			model.updateArticle();

			log.debug("********************* UPDATE ARTICLE: end!! ["+model.getArticle()+"]");
		}
		  
		return true;
	}

	private User getUserFromSession(HttpServletRequest request){
		return (User) SessionManager.getCurrentUser(request);
	}

	private Boolean getArticleToEdit(){
		log.debug("Searching ArticleToEdit: begin");
		
		long articleId = Long.parseLong(this.articleId);
	
		model.selectArticle(articleId);
	
		if(model.getArticle()==null) return false;

		return true;
	}
	
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}
}