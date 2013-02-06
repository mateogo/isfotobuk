package ar.kennedy.is2011.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.db.entities.Article;
import ar.kennedy.is2011.db.entities.Task;
import ar.kennedy.is2011.db.entities.EntityRelationHeader;

import ar.kennedy.is2011.models.PersonModel;
import ar.kennedy.is2011.models.ArticleModel;
import ar.kennedy.is2011.models.TaskModel;

import ar.kennedy.is2011.session.Session;


public class SelectEntityController extends AbstractController{

	private static final long serialVersionUID = 1L;
	
	String responseData = "[iajuu]";
	PersonModel model;
	ArticleModel articleModel;
	TaskModel taskModel;
	Boolean errorsDetected = false;
	String entityName = "";
	
	public SelectEntityController() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws  ServletException, IOException, Exception {
		//URL: /selectentity
		String entityId = request.getParameter("id");
		String entityType = request.getParameter("type");
		String entityName = request.getParameter("name");
		String target     = request.getParameter("target");
		log.debug("************ AJAX *************SELECT: ["+entityType+"] ["+entityId+"]  ["+entityName+"]");

        if(entityType.equals("REL")) errorsDetected = !addRelationToSession(request,entityId);

        if(entityType.equals("PF")) errorsDetected  = !addFpersonToSession(request,entityId);

        if(entityType.equals("PI")) errorsDetected  = !addIpersonToSession(request,entityId);

        if(entityType.equals("ARTICLE")) errorsDetected  = !addArticleToSession(request,entityId);

        if(entityType.equals("TASK")) errorsDetected  = !addTaskToSession(request,entityId);

        if(errorsDetected) responseData="failed" ;
    	else responseData="success" ;

    	if(true){
    		log.debug("*  BYE!!! *requestDispatcher :["+entityName+"]"+"target: ["+target+"]");
    		log.debug("* *************************************************");
    		//response.setContentType("text/plain");
    		//response.setHeader("Cache-Control", "no-cache");
    		//response.getWriter().write(responseData);
			//request.getRequestDispatcher("/secure/browseArticle.jsp").forward(request, response);
			//request.getRequestDispatcher("/article?action=browseArticle&arName="+entityName).forward(request, response);
			//request.getRequestDispatcher("/secure/browseArticle.jsp").forward(request, response);
    		if(target.equals("taskbrowser"))
    			response.sendRedirect("/task?action=browseTask&taskLocator="+entityName);
    		else if(target.equals("articlebrowser"))
    			response.sendRedirect("/article?action=browseArticle&arName="+entityName);
    	}
	}


	public Boolean addArticleToSession(HttpServletRequest request, String id){
		Boolean success=false;
		if(id==null) return success;
		Long entityId =Long.parseLong(id);
		if(entityId<=0) return success;
		articleModel = new ArticleModel();

		Article article= articleModel.fetchArticle(entityId);

		if(article==null) return success;
		
		request.getSession().setAttribute("artselected", article);
		request.getSession().setAttribute("etype", "ARTICLE");
		entityName=article.getName();

		return true;
	}	

	public Boolean addTaskToSession(HttpServletRequest request, String id){
		Boolean success=false;
		if(id==null) return success;
		Long entityId =Long.parseLong(id);
		if(entityId<=0) return success;
		taskModel = new TaskModel();

		Task task= taskModel.fetchTask(entityId);

		if(task==null) return success;
		
		request.getSession().setAttribute("taskselected", task);
		request.getSession().setAttribute("etype", "TASK");
		entityName=task.getLocator();

		return true;
	}	
	
	public Boolean addRelationToSession(HttpServletRequest request, String id){
		Boolean success=false;
		if(id==null) return success;
		Long entityId =Long.parseLong(id);
		if(entityId<=0) return success;

		model = new PersonModel();
		EntityRelationHeader erh= model.getERelationById(entityId);

		if(erh==null) return success;
		
		request.getSession().setAttribute("relselected", erh);
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
		
		request.getSession().setAttribute("pfselected", person);
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
		
		request.getSession().setAttribute("piselected", person);
		request.getSession().setAttribute("etype", "PI");
		return true;
	}	

	
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
}