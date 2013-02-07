package ar.kennedy.is2011.scheduler;


public class SchedulerEvent {

	public int event_id;
	public Long db_id;
	public String start_date;
	public String end_date;
	public String event_name;
	public String event_locator="";
	public String event_text="";
	public String rec_type;
	public String location;
	public String details = "sera esto?";
	public String status= "";
	
	public SchedulerEvent(int id, Long dbid,String recType,String start, String end, String text, String loc) {
		event_id = id;
		db_id = dbid;
		start_date = start;
		end_date = end;
		event_name = text;
		rec_type = recType;
		location = loc;
	}

	public SchedulerEvent(int id, Long dbid,String recType,String start, String end) {
		event_id = id;
		db_id = dbid;
		start_date = start;
		end_date = end;
		rec_type = recType;
	}


	
	/**  === AUTOGENERATEDE GETTERS - SETTERS: BEGIN ========  */

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public Long getDb_id() {
		return db_id;
	}

	public void setDb_id(Long db_id) {
		this.db_id = db_id;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public String getRec_type() {
		return rec_type;
	}

	public void setRec_type(String rec_type) {
		this.rec_type = rec_type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public String getEvent_locator() {
		return event_locator;
	}



	public void setEvent_locator(String event_locator) {
		this.event_locator = event_locator;
	}



	public String getEvent_text() {
		return event_text;
	}



	public void setEvent_text(String event_text) {
		this.event_text = event_text;
	}
	
	
	
	/**  === AUTOGENERATEDE GETTERS - SETTERS: END ========  */

	
}
