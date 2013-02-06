package ar.kennedy.is2011.scheduler;


public class SchedulerEvent {

	public int event_id;
	public Long db_id;
	public String start_date;
	public String end_date;
	public String event_name;
	public String rec_type;
	public String location;
	public String details = "sera esto?";
	
	public SchedulerEvent(int id, Long dbid,String recType,String start, String end, String text, String loc) {
		event_id = id;
		db_id = dbid;
		start_date = start;
		end_date = end;
		event_name = text;
		rec_type = recType;
		location = loc;
	}
	
}
