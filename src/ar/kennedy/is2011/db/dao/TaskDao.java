package ar.kennedy.is2011.db.dao;

import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.Task;
import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.utils.WebUtils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.Key;


public class TaskDao {

	private static final Logger log = Logger.getLogger(AbstractDao.class);
	private static final String TASKS_BY_LOCATOR   = "SELECT e FROM Task e WHERE e.locator = :1";
	private static final String TASKS_LIKE_LOCATOR = "SELECT e FROM Task  e WHERE e.locator LIKE :1";

	private AbstractDao<Task> taskDAO = new AbstractDao<Task>();

	private User user;

	private Task task;
	private List<Task> tasks;


	public TaskDao() {
		// TODO Auto-generated constructor stub
	}

	public TaskDao(User us) {
		// TODO Auto-generated constructor stub
		this();
		setUser(us);
	}

	
	public void update(Task task){
		try{
			log.debug("=== trying update this.task["+task+"]");
			taskDAO.persist(task);
			log.debug("****** update task:OK ********");

		} catch (Exception e) {
			//taskDAO.rollBackTx();
			log.debug("=== Persistence failed!!! :["+task.toString()+"]");
			log.error(e.getMessage());

		}finally{
		}

	}
	
	public Task fetchTask(Key key){
		if(key==null) return null;

		try {
			return taskDAO.findById(Task.class, key);
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public Task fetchTask(Long id){
		if(id==null) return null;
		if(id<=0) return null;

		try {
			return taskDAO.findById(Task.class, id);
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public List<Task> fetchTasksByLocator(String locator) {
		if(WebUtils.isNull(locator)) return null;
		
		try {
			return taskDAO.createCollectionQuery(TASKS_BY_LOCATOR, new Vector<Object>(Arrays.asList(new String[] {locator})));
		
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<Task>();
		}
	}

	public List<Task> fetchTasksLikeLocator(String locator) {
		try {
			return taskDAO.createCollectionQuery(TASKS_LIKE_LOCATOR, new Vector<Object>(Arrays.asList(new String[] {locator})));
		
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	
	public List<GenericEntity> fetchEntitiesLikeLocator(String locator) {
		List<Task> tasks= fetchTasksLikeLocator (locator);
		
		if(tasks==null) return null;
		if(tasks.isEmpty()) return null;
	
		return buildEntityList(tasks);
	}

	public String fetchTaskLocatorsLikeLocator(String locator) {
		List<Task> tasks= fetchTasksLikeLocator (locator);
		
		if(tasks==null) return null;
		if(tasks.isEmpty()) return null;
	
		return buildStringList(tasks);
	}
	
	private String buildStringList(List<Task>  tasks){
		StringBuilder st = new StringBuilder();
		String response;
		for(Task task: tasks){
			st.append('\"');
			st.append(task.getLocator());
			st.append('\"');
			st.append(", ");
		}
		response = st.toString();
		log.debug("=== responseString:["+response+"] ["+response.substring(0,response.length()-2)+"]");
		return response.substring(0,response.length()-2);
	}
	
	private List<GenericEntity> buildEntityList(List<Task>  tasks) {
		List<GenericEntity> entities = new ArrayList<GenericEntity>();
		for(Task ar:tasks){
			GenericEntity ge = new GenericEntity();
			ge.setTask(ar);
			ge.setEntityDescription(ar.toString());
			ge.setEntityLongId(ar.getKey().getId());
			ge.setEntityName(ar.getLocator());
			ge.setEntityStringId(ar.getId());
			ge.setEntityType("TASK");
			entities.add(ge);
		}
		return entities;		
	}
	
	public List<Task> fetchAllTasks() {
		
		try {
			return taskDAO.select(Task.class);
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	
	
	/**  === AUTOGENERATEDE GETTERS - SETTERS: BEGIN ======  */
	public AbstractDao<Task> getTaskDao() {
		return taskDAO;
	}

	public void setTaskDao(AbstractDao<Task> taskDao) {
		this.taskDAO = taskDao;
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

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	/**  === AUTOGENERATEDE GETTERS - SETTERS: END ========  */

	
}
