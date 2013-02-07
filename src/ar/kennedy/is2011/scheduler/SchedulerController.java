package ar.kennedy.is2011.scheduler;

import org.apache.log4j.Logger;

import ar.kennedy.is2011.scheduler.SchedulerEvent;
import ar.kennedy.is2011.models.TaskModel;
import com.dhtmlx.connector.ConnectorBehavior;
import com.dhtmlx.connector.SchedulerConnector;
import com.dhtmlx.connector.DataAction;
//import com.dhtmlx.connector.BaseConnector;
import java.util.HashMap;
import java.util.Iterator;
import java.text.DateFormat;


public class SchedulerController extends ConnectorBehavior {

	
	protected final Logger log = Logger.getLogger(getClass());

	private SchedulerConnector conn;
	//private BaseConnector bc;

	public SchedulerController() {
		// TODO Auto-generated constructor stub
	}

	public SchedulerController(SchedulerConnector conn) {
		// TODO Auto-generated constructor stub
        this.conn = conn;
	}

	@Override
	public void beforeProcessing(DataAction action) {
		log.debug(".");
		log.debug(".");
		log.debug(".");
		log.debug("=================beforeProcesing=====================");
		String status = action.get_status();
		String newId = action.get_new_id();

		String id = action.get_id();
		String db_id=action.get_value("db_id");
		String type = action.get_value("rec_type");
		String startDate = action.get_value("start_date");
		String endDate = action.get_value("end_date");
		String text = action.get_value("event_name");
		String locator = action.get_value("event_locator");

		log.debug("beforeProcessing status: ["+status+"] newid:["+newId+"] id:["+id+"] id:["+db_id+"]  type: ["+type+"]");
		log.debug("                         ["+startDate+"] :["+endDate+"] text:["+text+"]");
	
		//SchedulerEvent schev = new SchedulerEvent(Integer.parseInt(id),Long.parseLong(db_id),type, startDate,endDate,    text,"");

		SchedulerEvent schev = new SchedulerEvent(Integer.parseInt(id),Long.parseLong(db_id),type, startDate, endDate);
		schev.setStatus(status);
		schev.setEvent_name(text);
		schev.setEvent_locator(locator);
		schev.setEvent_text(text);
		schev.setLocation("futuro");
		schev.setDetails("futuro");
		
		TaskModel tskModel = new TaskModel();
		Boolean ok = tskModel.updateTask(schev);

		log.debug("TaskModel Update: ["+ok+"] ["+DateFormat.getTimeInstance(DateFormat.LONG).format(tskModel.getTask().getDuedate())+"]");
		
//		HashMap<String, String> data = action.get_data();
//		Iterator<String> it = data.keySet().iterator(); 
//		while (it.hasNext()){
//			String key = it.next();
//			String value = data.get(key);
//			log.debug("data iterator: key:["+key+"] ["+value+"]");
//		}

	
		action.success(); //if you have made custom update - mark operation as finished
		log.debug("************beforeProcesing: ENDED***************");
		log.debug(".");
		log.debug(".");
		log.debug(".");

	}
	 
	@Override
	public void beforeUpdate(DataAction action) {
		log.debug("beforeUpdate!!");
		String status = action.get_status();
		String type = action.get_value("rec_type");
		String pid = action.get_value("event_pid");
		String id = action.get_id();
		String newId = action.get_new_id();

		log.debug("beforeUpdate status: ["+status+"] pid:["+pid+"]  type: ["+type+"] id:["+ id +"]  newId:["+newId+"]");
	
		HashMap<String, String> data = action.get_data();
		Iterator<String> it = data.keySet().iterator(); 
		while (it.hasNext()){
			String key = it.next();
			String value = data.get(key);
			log.debug("data iterator: key:["+key+"] ["+value+"]");
		}
		
	
		action.success(); //if you have made custom update - mark operation as finished
	}

	public SchedulerConnector getConn() {
		return conn;
	}

	public void setConn(SchedulerConnector conn) {
		this.conn = conn;
	}
	
	
	
}
