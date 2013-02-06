package ar.kennedy.is2011.db.dao;

import ar.kennedy.is2011.db.entities.Task;
import ar.kennedy.is2011.db.entities.ContactosPerson;
import ar.kennedy.is2011.db.entities.Location;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.EntityRelationHeader;
import ar.kennedy.is2011.models.TaskModel;

import java.util.List;


/**
 * @author mateogo
 *
 */
public class TaskBean {

	private User user;
	private Task task;
	private Task selectedTask;
	private TaskModel taskModel;
	
	private EntityRelationHeader erelation;
	
	private List<Task> tasks;
	private List<EntityRelationHeader> erelations;

	private List<GenericEntity> entities;

	private List<ContactosPerson> contactos;
	private List<Location> locations;

	public ArticlePortlet articlePortlet;
	
	private String action;
	private String taskToEditId;
	private String browsertab="";
	private String viewertab="";

	public TaskBean() {
		// TODO Auto-generated constructor stub
	}

	public TaskBean(User us, String ac) {
		// TODO Auto-generated constructor stub
		setUser(us);
		setAction(ac);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public EntityRelationHeader getErelation() {
		return erelation;
	}

	public void setErelation(EntityRelationHeader erelation) {
		this.erelation = erelation;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<EntityRelationHeader> getErelations() {
		return erelations;
	}

	public void setErelations(List<EntityRelationHeader> erelations) {
		this.erelations = erelations;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTaskToEditId() {
		return taskToEditId;
	}

	public void setTaskToEditId(String taskToEditId) {
		this.taskToEditId = taskToEditId;
	}

	public List<ContactosPerson> getContactos() {
		return contactos;
	}

	public void setContactos(List<ContactosPerson> contactos) {
		this.contactos = contactos;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public Task getSelectedTask() {
		return selectedTask;
	}

	public String getSelectedTaskTechData(){
		if(getSelectedTask()==null) return "";
		StringBuilder stb = new StringBuilder();
		
		stb.append("<div><b>Creada por: </b>"); stb.append(getTaskModel().getOwnerLabel(getSelectedTask())); stb.append("</div>");
		stb.append("<div><b>Estado de avance: </b>"); stb.append(getTaskModel().getStatusLabel(getSelectedTask())); stb.append("</div>");
		stb.append("<div><b>Asignada a: </b>"); stb.append(getTaskModel().getAsigneeLabel(getSelectedTask())); stb.append("</div>");
		stb.append("<div><b>Estado: </b>"); stb.append(getTaskModel().getActiveLabel(getSelectedTask())); stb.append("</div>");
		stb.append("<div><b>Visibilidad: </b>"); stb.append(getTaskModel().getVisibilityLabel(getSelectedTask())); stb.append("</div>");
		
		return stb.toString();
	}
	
	public void setSelectedTask(Task selectedTask) {
		this.selectedTask = selectedTask;
	}

	public List<GenericEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<GenericEntity> entities) {
		this.entities = entities;
	}

	public String getBrowsertab() {
		return browsertab;
	}

	public void setBrowsertab(String browsertab) {
		this.browsertab = browsertab;
	}

	public String getViewertab() {
		return viewertab;
	}

	public void setViewertab(String viewertab) {
		this.viewertab = viewertab;
	}

	public TaskModel getTaskModel() {
		if(taskModel==null) taskModel = new TaskModel();
		return taskModel;
	}

	public ArticlePortlet getArticlePortlet() {
		return articlePortlet;
	}

	public void setArticlePortlet(ArticlePortlet articlePortlet) {
		this.articlePortlet = articlePortlet;
	}

}
