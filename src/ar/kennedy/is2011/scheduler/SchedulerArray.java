package ar.kennedy.is2011.scheduler;
/*
 * Copyright (c) 2009 - DHTMLX, All rights reserved
 */


import com.dhtmlx.connector.ConnectorServlet;
import com.dhtmlx.connector.SchedulerConnector;

import ar.kennedy.is2011.models.TaskModel;

import java.util.ArrayList;
import java.util.HashMap;

//import java.util.List;

import org.apache.log4j.Logger;

public class SchedulerArray extends ConnectorServlet {

	private static final long serialVersionUID = 7995740723339513508L;
	protected final Logger log = Logger.getLogger(getClass());
	public static final HashMap<String,String> types;
	static{
		HashMap<String,String> m = new HashMap<String,String>();
		m.put("MILESTONE","hito");
		m.put("TASK","tarea");
		m.put("PROJECT","proyecto");
		m.put("OTHER","otros");
		types = m;
	}

	@Override
	protected void configure() {
		//ArrayList<Object> data = new ArrayList<Object>();
		//ArrayList<Object> data = buildSchedulerFromTasks();
		//data.add(new SchedulerEvent(3, "2013-02-04 17:00", "2013-02-04 18:00", "Cepia"));
		//data.add(new SchedulerEvent(4, "2013-02-05 15:00", "2013-02-05 17:00", "Reunion"));

		//SchedulerConnector c = new SchedulerConnector(null);
		//c.render_array(data, "event_id","start_date,end_date,event_mame");
		log.debug(".");
		log.debug("=========== schedule array config ===============");
		SchedulerConnector c = new SchedulerConnector(null);

		log.debug("attach controller");
		c.event.attach(new SchedulerController(c));
		
		log.debug("now building scheduler from tasks");
		ArrayList<Object> data = buildSchedulerFromTasks();
		//ArrayList<Object> data = new ArrayList<Object>();
		data.add(new SchedulerEvent(3,Long.valueOf(300),"MILESTONE", "2013-02-04 17:00", "2013-02-04 8:00", "Reunion en el CEPIA","Cepia"));
		//data.add(new SchedulerEvent(4, "2013-02-05 15:00", "2013-02-05 16:00", "second part"));

		log.debug("redy to render array!");
		c.set_options("toptions",types);
	
		c.render_array(data, "event_id","start_date,end_date,event_name,db_id,rec_type,details,location");

	}

	private ArrayList<Object>  buildSchedulerFromTasks(){
		TaskModel taskModel = new TaskModel();
		ArrayList<Object> data = taskModel.taskSchedulerList();
		for(Object obj:data){
			SchedulerEvent se = (SchedulerEvent) obj;
			log.debug("schedule: ["+se.event_id+"] ["+se.start_date+"]:["+se.end_date+"]  ["+se.event_name+"]");
		}
		return data;
	
	}

}