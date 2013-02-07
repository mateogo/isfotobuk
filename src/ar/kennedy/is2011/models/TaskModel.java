package ar.kennedy.is2011.models;

import ar.kennedy.is2011.db.dao.TaskDao;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.Task;
import ar.kennedy.is2011.db.dao.AccountDao;
import ar.kennedy.is2011.scheduler.SchedulerEvent;

import java.text.DateFormat;
import java.util.ArrayList;


import ar.kennedy.is2011.utils.WebUtils;

import com.google.appengine.api.datastore.Key;
//import com.google.appengine.api.datastore.KeyFactory;

import java.util.List;
import java.util.Date;



public class TaskModel extends AbstractModel {


	//private static final String ALL_IPERSONS =    "SELECT e FROM PersonaIdeal e ";	

	//private static final String IPERSON_BY_NAME = "SELECT e FROM PersonaIdeal e WHERE e.nombrePerson = :1";
	//private static final String ALL_IPERSONS =    "SELECT e FROM PersonaIdeal e ";	
	private final String[] VISIBILIDAD ={"Privada","Publica"};
	private final String[]  ACTIVA={"ACTIVA","CERRADA","BAJA"};
	private final String[]  AVANCE={"Baja", "planificada", "asignada", "en curso", "cumplida", "demorada", "suspendida", "cancelada"};

	private static final Integer     ACTIVE =  1;
	private static final Integer     STATUS =  1;
	private static final Integer VISIBILITY =  1;
	private static final String       ETYPE = "TAREA";
	
	private User user;
	private Task task;
	private List<Task> tasks;
	private TaskDao taskDao;
	private AccountDao accDao;
	
	
	public TaskModel() {
		// TODO Auto-generated constructor stub
		super();
		taskDao = new TaskDao();
	}
	public TaskModel(User us) {
		this();
		setUser(us);
		taskDao = new TaskDao(us);
	}

	public void updateTask(){
		taskDao.update(getTask());

	}

	public User fetchOwner(Key key){
		return getAccDao().fetchUserById(key);
	}

	public String getOwnerName(Key key){
		return fetchOwner(key).getDisplayName();
	}


	public Task fetchTask(Key key){
		return taskDao.fetchTask(key);
	}

	public Task fetchTask(Long id){
		return taskDao.fetchTask(id);
	}

	public Task selectTask(Key key){
		setTask(fetchTask(key));
		return getTask();
	}

	public Task selectTask(Long id){
		setTask(fetchTask(id));
		return getTask();
	}
	
	public Task selectTask(String id){
		if(WebUtils.isNull(id)) return null;
		setTask(fetchTask(Long.parseLong(id)));
		return getTask();
	}

	public void initNewTask(){
		setTask(new Task());
		getTask().setCdate(new Date());
		getTask().setUdate(getTask().getCdate());
		getTask().setIsActive(ACTIVE);
		getTask().setVisibility(VISIBILITY);
		getTask().setType(ETYPE);
		getTask().setStatus(STATUS);
		getTask().setOwner(getUser().getKey());
	}
	
	public String getVisibilityLabel(Task t){
		if(t.getVisibility()==null) return "";
		return VISIBILIDAD[t.getVisibility()];
	}
	public String getActiveLabel(Task t){
		if(t.getIsActive()==null) return "";
		return ACTIVA[t.getIsActive()];
	}
	public String getStatusLabel(Task t){
		if(t.getStatus()==null) return "";
		return AVANCE[t.getStatus()];
	}
	public String getAsigneeLabel(Task t){
		//if(t.getAsignee()==null) return "";
		//return STATUS[getStatus()];
		return "JuanDeLosPalotes";
	}

	public String getOwnerLabel(Task t){
		if(t.getOwner()==null) return "";
		return fetchOwner(t.getOwner()).getUserName();
	}
	
	public boolean updateTask(SchedulerEvent schev){
		
		log.debug("======= process update task to begin ==========");
		String action = schev.getStatus();
		if(WebUtils.isNull(action)) return false;
		if(!WebUtils.compare(schev.getRec_type(), "TASK")) return false;

		if(action.equals("updated")) return updatedTask(schev);
		
		return false;
	}
	private boolean updatedTask(SchedulerEvent schev){
		log.debug("======= update Task ==========");
		setTask(fetchTask(schev.db_id));
		if(getTask()==null) return false;
		
		Date duedate = WebUtils.getDateFromYMD(schev.getEnd_date());
		Date begindate = WebUtils.getDateFromYMD(schev.getStart_date());
		
		log.debug("Update task:["+WebUtils.getYMDDate(begindate)+"] ["+WebUtils.getYMDDate(duedate)+"]");
		log.debug("Update task:["+DateFormat.getTimeInstance(DateFormat.LONG).format(begindate)+"] ["+DateFormat.getTimeInstance(DateFormat.LONG).format(duedate)+"]");
		
		
		getTask().setDuedate(duedate);
		getTask().setBegindate(begindate);
		
		updateTask();
		log.debug("======= task saved ==========");
		
		return true;
	}

	public ArrayList<Object> taskSchedulerList(){
		List<Task> tasks = taskDao.fetchAllTasks();
		ArrayList<Object> sch = new ArrayList<Object>();
		if(tasks==null || tasks.isEmpty()) return sch;
		int id=0;
		for(Task task:tasks){
			String start = WebUtils.getYMDDate(task.getBegindate());
			String end = WebUtils.getYMDDate(task.getDuedate());
			log.debug("taskSchedule list:["+DateFormat.getDateInstance(DateFormat.LONG).format(task.getBegindate())+"] ["+DateFormat.getDateInstance(DateFormat.LONG).format(task.getDuedate())+"]");
			log.debug("................>:["+DateFormat.getTimeInstance(DateFormat.LONG).format(task.getBegindate())+"] ["+DateFormat.getTimeInstance(DateFormat.LONG).format(task.getDuedate())+"]");
			log.debug("................>:["+start+"] ["+end+"]");
		
			String recType = "TASK";
			
			SchedulerEvent s = new SchedulerEvent(++id,task.getKey().getId(),recType, start, end);

			s.setEvent_name(task.getSubject());
			s.setEvent_locator(task.getLocator());
			s.setEvent_text(task.getTextData());
			s.setLocation("futuro");
			s.setDetails("futuro");
			
			sch.add(s);
		}
		return sch;
	}
	
	/**  === AUTOGENERATEDE GETTERS - SETTERS: BEGIN ======  */
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
	public TaskDao getTaskDao() {
		return taskDao;
	}
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public AccountDao getAccDao() {
		if(accDao==null) accDao = new AccountDao();
		return accDao;
	}

	/**  === AUTOGENERATEDE GETTERS - SETTERS: END ========  */

}
