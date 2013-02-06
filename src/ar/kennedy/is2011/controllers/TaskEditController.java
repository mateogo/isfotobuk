package ar.kennedy.is2011.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.WebUtils;
import ar.kennedy.is2011.models.TaskModel;

import com.google.appengine.api.datastore.Text;

//import ar.kennedy.is2011.utils.WebUtils;
//import org.apache.commons.lang.StringUtils;





public class TaskEditController extends AbstractController{

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;

	private User user;
	private TaskModel model ;
	private Boolean success = true;
	private String action;
	private String taskId;

//	private Task task;
//	private List<Task> tasks;
//	private TaskDao taskDao;

	public TaskEditController() {
        super();
    }


	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("****************TASK EDIT CONTROLLER *****************");
		this.request = request;
		
		this.taskId = request.getParameter("taskId");
		if (this.taskId==null) this.taskId="0";

		this.user= getUserFromSession(request);

		this.model = new TaskModel(this.user);
		
		this.action = request.getParameter("action");
		log.debug("Task edit controller: begin -  action:["+action+"] taskId:[" + taskId + "]");

		
		if(this.action.equals("newTask")){
			success = newTask();
			
			if(success){
				
				//response.setContentType("text/plain");
		        //response.setHeader("Cache-Control", "no-cache");
		        //response.getWriter().write("CONCLUIDO CON EXITO ["+model.getTask().getId()+"]");

				response.sendRedirect("/task?action=browseTask&taskName="+model.getTask().getLocator());
			} else {
				response.sendRedirect("/task?action=newTask");
			}
		}


		if(this.action.equals("updateTask")){
			success = updateTask();
					
			if(success){
				response.sendRedirect("/task?action=browseTask&taskName="+model.getTask().getLocator());
			} else {
				response.sendRedirect("/task?action=updateTask&taskid="+model.getTask().getId());
			}
		}	

	}

	private Boolean newTask(){

		model.initNewTask();

		if(retrieveTaskData()) {
			model.updateTask();
			log.debug("********************* NEW TASK: end!! ["+model.getTask()+"]");
			return true;
			
		}

		return false;
	}


	private Boolean retrieveTaskData(){
		String name = request.getParameter("taskLocator");

		log.debug("********************* NEW TASK ready to update: begin ["+name+"]");

		String text         = request.getParameter("taskText");
		String subject      = request.getParameter("taskSubject");
		String visibility   = request.getParameter("taskVisibility");
		String status       = request.getParameter("taskStatus");
		String isActive     = request.getParameter("taskIsActive");
		String type         = request.getParameter("taskType");
		String dueDate      = request.getParameter("taskDueDate");
		
		model.getTask().setLocator(name);
		model.getTask().setSubject(subject);
		model.getTask().setText(new Text(text));
		model.getTask().setVisibility(Integer.parseInt(visibility));
		model.getTask().setStatus(Integer.parseInt(status));
		model.getTask().setIsActive(Integer.parseInt(isActive));
		model.getTask().setType(type);
		model.getTask().setDuedate(WebUtils.getDateFromString(dueDate));
		
		return true;
	}

	private Boolean updateTask(){
		
		String name = request.getParameter("taskLocator");
		log.debug("********************* UPDATE TASK ready to update: begin ["+name+"]");

		if (getTaskToEdit()==null) return false;
		log.debug("Update Person:Person to edit: found! ready to update");

		if(retrieveTaskData()) {
			model.updateTask();

			log.debug("********************* UPDATE TASK: end!! ["+model.getTask()+"]");
		}
		  
		return true;
	}

	private User getUserFromSession(HttpServletRequest request){
		return (User) SessionManager.getCurrentUser(request);
	}

	private Boolean getTaskToEdit(){
		log.debug("Searching TaskToEdit: begin");
		
		long taskId = Long.parseLong(this.taskId);
	
		model.selectTask(taskId);
	
		if(model.getTask()==null) return false;

		return true;
	}
	
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}
}