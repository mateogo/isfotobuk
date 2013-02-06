package ar.kennedy.is2011.scheduler;

import org.apache.log4j.Logger;

import com.dhtmlx.connector.ConnectorBehavior;
import com.dhtmlx.connector.SchedulerConnector;
import com.dhtmlx.connector.DataAction;
import com.dhtmlx.connector.BaseConnector;
import java.util.HashMap;
import java.util.Iterator;


public class SchedulerController extends ConnectorBehavior {

	
	protected final Logger log = Logger.getLogger(getClass());

	private SchedulerConnector conn;
	private BaseConnector bc;

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
		String type = action.get_value("rec_type");
		String pid = action.get_value("event_pid");
		String id = action.get_id();
		String newId = action.get_new_id();
		

		log.debug("beforeProcessing status: ["+status+"] pid:["+pid+"]  type: ["+type+"] id:["+ id +"]  newId:["+newId+"]");
	
		HashMap<String, String> data = action.get_data();
		Iterator<String> it = data.keySet().iterator(); 
		while (it.hasNext()){
			String key = it.next();
			String value = data.get(key);
			log.debug("data iterator: key:["+key+"] ["+value+"]");
		}
		
	
		action.success(); //if you have made custom update - mark operation as finished
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
	
	
	
}
