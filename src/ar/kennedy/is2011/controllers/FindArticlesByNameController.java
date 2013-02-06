package ar.kennedy.is2011.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.kennedy.is2011.db.dao.ArticleDao;
import ar.kennedy.is2011.db.dao.TaskDao;
import ar.kennedy.is2011.db.dao.PersonDao;
import ar.kennedy.is2011.db.dao.PictureDao;


public class FindArticlesByNameController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	protected final Logger log = Logger.getLogger(getClass());
	


	//AbstractDao<Article> articleDAO = new AbstractDao<Article>();
	ArticleDao  articleDao =  new ArticleDao();
	TaskDao     taskDao    =  new TaskDao();
	PersonDao   personDao  =  new PersonDao();
	PictureDao  pictureDao =  new PictureDao();
	
	String entityList = "";
	String responseString = "";
	
	public FindArticlesByNameController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("query")+"%";
		String type = request.getParameter("type");
		log.debug("******************************************Search Article: ["+name+"] ["+type+"]");

		if(type.equals("ARTICLE")){
			responseString=buildJasonResponse(buildArticleList(name));

		}else if (type.equals("TASK")){
			responseString=buildJasonResponse(buildTaskList(name));
			
		}else if (type.equals("PF")){
			responseString=buildJasonResponse(buildFPersonList(name));
			
		}else if (type.equals("PI")){
			responseString=buildJasonResponse(buildIPersonList(name));

		}else if (type.equals("PICTURE")){
			responseString=buildJasonResponse(buildPictureList(name));

		}
		
		log.debug("*******response:"+responseString);
		
		response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(responseString);
	}

	
	
	/** ============ builders  =============================*/
	public String buildArticleList(String name) {
		entityList = articleDao.fetchArticleNamesLikeName(name);
		return entityList;
	}

	public String buildTaskList(String name) {
		entityList = taskDao.fetchTaskLocatorsLikeLocator(name);
		return entityList;
	}

	public String buildFPersonList(String name) {
		entityList = personDao.fetchFNamesLike(name);
		return entityList;
	}

	public String buildIPersonList(String name) {
		entityList = personDao.fetchINamesLike(name);
		return entityList;
	}

	public String buildPictureList(String name) {
		entityList = pictureDao.fetchPictureNamesLike(name);
		return entityList;
	}
	
	
	
	public String buildJasonResponse(String list) {
		//return "{\n \"options\": [ \n" + list + " \"teresita\"\n]\n}";
		return "{\n \"options\": [ \n" + list + " \n]\n}";
	}

	public String buildPlainResponse(String list) {
		//return "{\n \"options\": [ \n" + list + " \"teresita\"\n]\n}";
		//list= "[" + list + " \"teresita\"]";
		return "[ \n" + list + " \n]";
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action (request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action (request, response);
	}

}