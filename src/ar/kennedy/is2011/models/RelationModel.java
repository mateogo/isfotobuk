package ar.kennedy.is2011.models;

import ar.kennedy.is2011.db.dao.RelationDao;
import ar.kennedy.is2011.db.dao.ArticleDao;
import ar.kennedy.is2011.db.dao.PictureDao;
import ar.kennedy.is2011.db.dao.PersonDao;
import ar.kennedy.is2011.db.dao.TaskDao;
import ar.kennedy.is2011.models.PersonModel;
import ar.kennedy.is2011.db.entities.Location;
import ar.kennedy.is2011.db.entities.ContactosPerson;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.db.entities.Person;
import ar.kennedy.is2011.db.entities.EntityRelationHeader;
import ar.kennedy.is2011.db.entities.EntityRelations;
import ar.kennedy.is2011.db.entities.PictureEy;
import ar.kennedy.is2011.db.entities.Task;



import ar.kennedy.is2011.utils.WebUtils;

//import com.google.appengine.api.datastore.KeyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;



public class RelationModel extends AbstractModel {



	//private static final String[] entityTypes = {"RELATION","PF","PI","PICTURE","ARTICLE","TASK"};

	
	private User user;

	private PersonDao personDao;
	private RelationDao relDao;
	private ArticleDao articleDao;
	private PictureDao pictureDao;
	private TaskDao taskDao;
	private TaskModel taskModel;
	private PersonModel pmodel;

	private PersonaFisica fperson;
	private PersonaIdeal  iperson;
	private List<PersonaFisica> fpersons;
	private List<PersonaIdeal> ipersons;
	
	private EntityRelationHeader erelation;
	private List<EntityRelationHeader> erelations;
	private List<EntityRelations> erelitems;
	
	public RelationModel() {
		// TODO Auto-generated constructor stub
		super();
	}

	public RelationModel(User us) {
		setUser(us);
		personDao=new PersonDao(us);
		relDao = new RelationDao(us);
		articleDao = new ArticleDao(us);
		taskDao = new TaskDao(us);
		pmodel = new PersonModel(us);
		pictureDao = new PictureDao();
		// TODO OjO: ver constructor default
	}

	/**  ======  INIT RELATIONS =========== */
	public EntityRelationHeader initEntityRelation(String relId){
		if(WebUtils.isNotNull(relId)){
			Long relationId= Long.parseLong(relId);
			if(relationId<=0){
				return initNewEntityRelation();
			}else{
				setErelation(getRelDao().fetchERelationById(relationId));
				return getErelation();
			}
		}
		return initNewEntityRelation();
	}

	private EntityRelationHeader initNewEntityRelation(){
		setErelation( new EntityRelationHeader(getUser()));
		getErelation().setCdate(new Date());
		getErelation().setPredicate("CONOCE_A");
				
		return getErelation();
	}

	public void initNewEntityRelationItems(){
		//getErelation().setErelations(new ArrayList<EntityRelations>());
		//setErelitems(getErelation().getErelations());
	}

	public void initNewRelationList(){
		getErelation().setErelations(new ArrayList<EntityRelations>());
	}


	private Boolean ifExistERelationItems(){
		if(getErelitems()!=null){
			//ya est‡ inicializada la lista de items
			if(!getErelitems().isEmpty()) return true;
		}
		//inicializa la lista de items
		if(getErelation()==null) return false;
		setErelitems(getErelation().getErelations());
		if(getErelitems()==null) return false;
		if(getErelitems().isEmpty()) return false;
		return true;
	}

	/**  ======  CORE API =========== */
	public EntityRelations fetchRelationForEntityId(long id){
		if(id<=0) return null;
		if(!ifExistERelationItems()) return null;
		for(EntityRelations eri: getErelitems() ){
			if(eri.getEntityId()==id) return eri;
		}
		return null;
	}

	public EntityRelations fetchRelationForEntityId(String type, String id){
		if(WebUtils.isNull(id)) return null;
		if(WebUtils.isNull(type)) return null;
		
		if(!type.equals("PICTURE")) return fetchRelationForEntityId(type,Long.parseLong(id));

		if(!ifExistERelationItems()) return null;

		for(EntityRelations eri: getErelitems() ){
			if(eri.getEntityType().equals(type) && eri.getPictureId().equals(id)) return eri;
		}
		return null;
}

	public EntityRelations fetchRelationForEntityId(String type, long id){
		if(id<=0) return null;
		if(WebUtils.isNull(type)) return null;

		if(!ifExistERelationItems()) return null;

		for(EntityRelations eri: getErelitems() ){
			if(eri.getEntityId()==id && eri.getEntityType().equals(type)) return eri;
		}
		return null;
	}

	public void updateRelation(){
		getRelDao().updateRelation(getErelation());
	}

	public Boolean addRelationItem(EntityRelations ritem){
		log.debug("MODEL ADD ENTITY RELATION ITEM TO HEADER");
		//List<EntityRelations> erlist;
		if(ifExistERelationItems()){
			getErelation().getErelations().add(ritem);
			log.debug("MODEL ADD ENTITY RELATION ITEM TO HEADER: relitems already exist");
		}else{
			getErelation().setErelations( new ArrayList<EntityRelations>());
			getErelation().getErelations().add(ritem);

			//getErelation().setErelations(erlist);
			setErelitems(getErelation().getErelations());
			log.debug("MODEL ADD ENTITY RELATION ITEM TO HEADER: relitems initialized");
		}
		return true;
	}

	public Object fetchEntityFromRelationItem(EntityRelations eritem, String type){
		if(WebUtils.isNull(type)) return null;
		if(!eritem.getEntityType().equals(type)) return null;

		return fetchEntityFromRelationItem(eritem);

	}

	public Object fetchEntityFromRelationItem(EntityRelations eritem){
		if(eritem==null)return null;

		if(eritem.getEntityType().equals("PF")){
			return getPersonDao().fetchFpersonById(eritem.getEntityId());

		}else if(eritem.getEntityType().equals("PI")){
			return getPersonDao().fetchIpersonById(eritem.getEntityId());
		
		}else if(eritem.getEntityType().equals("ARTICLE")){
			return getArticleDao().fetchArticle(eritem.getEntityId());

		}else if(eritem.getEntityType().equals("TASK")){
			return getTaskDao().fetchTask(eritem.getEntityId());

		}else if(eritem.getEntityType().equals("PICTURE")){
			return getPictureDao().fetchPicture(eritem.getPictureId());

		}
		return null;		
	}

	

	/**  ======  PICTURE RELATED  API =========== */
	public EntityRelationHeader getRelationForPicture(User user, String pictureId) {
		this.erelation=null;
		setErelations(getRelDao().fetchRelationsForPicture(user,pictureId));
		if(getErelations()==null|| getErelations().isEmpty()) return null;
		setErelation(getErelations().get(0));
		return getErelation();
	}

	

	/**  ======  PERSON RELATED  API =========== */
	public Person fetchPersonFromRelationItem(EntityRelations eritem){
		if(eritem==null)return null;
		if(eritem.getEntityType().equals("PF")){
			return getPersonDao().fetchFpersonById(eritem.getEntityId());
		}
		if(eritem.getEntityType().equals("PI")){
			return getPersonDao().fetchIpersonById(eritem.getEntityId());
		}
		return null;		
	}

	public String fetchPersonNameFromRelation(EntityRelationHeader relation){
		Person person = fetchPersonFromRelation(relation);
		if(person==null) return null;
		else return person.getNombrePerson();
	}

	public Person fetchPersonFromRelation(EntityRelationHeader relation){
		if(relation==null)return null;
		if(relation.getEntityType()==null) return null;
		
		if(relation.getEntityType().equals("PF")){
			return getPersonDao().fetchFpersonById(relation.getEntityId());
		}
		if(relation.getEntityType().equals("PI")){
			return getPersonDao().fetchIpersonById(relation.getEntityId());
		}
		return null;		
	}
	
	public PersonaFisica fetchFpersonFromRelation(){
		if(getErelation()==null)return null;
		if(getErelation().getEntityId()==null) return null;
		if(getErelation().getEntityType()==null) return null;
		if(!getErelation().getEntityType().equals("PF")) return null;
		
		return getPersonDao().fetchFpersonById(getErelation().getEntityId());
	}
	
	public PersonaIdeal fetchIpersonFromRelation(){
		if(getErelation()==null)return null;
		if(getErelation().getEntityId()==null) return null;
		if(getErelation().getEntityType()==null) return null;
		if(!getErelation().getEntityType().equals("PI")) return null;

		return getPersonDao().fetchIpersonById(getErelation().getEntityId());
	}	
	
	public List<EntityRelationHeader> getRelationsForPersons() {
		this.erelations = new ArrayList<EntityRelationHeader>();
		log.debug("getRelations for Person");
		log.debug("Fpersons:[" + getFpersons() + "]");
		log.debug("Ipersons:[" + getIpersons() + "]");

		if(getFpersons()==null){
			if(getFperson()!=null) {
				setFpersons(new ArrayList<PersonaFisica>());
				getFpersons().add(getFperson());
			}			
		}
		if(getIpersons()==null){
			if(getIperson()!=null) {
				setIpersons(new ArrayList<PersonaIdeal>());
				getIpersons().add(getIperson());
			}			
		}

		if(getFpersons()!=null && !getFpersons().isEmpty()){
			for(PersonaFisica pf: getFpersons()){
				erelations.addAll(getRelDao().fetchRelationsForEntity("PF", pf.getKey().getId()));
			}
		}
		if(getIpersons()!=null  && !getIpersons().isEmpty()) {
			for(PersonaIdeal pi: getIpersons()){
				erelations.addAll(getRelDao().fetchRelationsForEntity("PI", pi.getKey().getId()));
			}
		}
		return this.erelations;
		
	}
	/** =========== browse ERH Collection, select some by criteria */
	
	public List<PicturesRelation>  retrievePicturesFromRelations(List<EntityRelationHeader> erh){
		log.debug("retrieve Pictures: Begin["+erh+"]");

		if(erh==null || erh.isEmpty()) return null;
		log.debug("Ready to browse relations: ");

		List<PicturesRelation> picturesData = new ArrayList<PicturesRelation>();

		for(EntityRelationHeader relation:erh){
			log.debug("Relation: ["+relation.getPredicate()+"]:["+relation.getSubject()+"]");
			List<EntityRelations> items = relation.getErelations();
			if(!(items==null || items.isEmpty())){
				log.debug("Ready to browse items: ");
				for(EntityRelations item :items){
					log.debug("Item: ["+item.getComment()+"]:["+item.getEntityType()+"]:["+item.getEntityId()+"]");
					if(item.getEntityType().equals("PICTURE")){
						buildPictureInfo(relation,item,picturesData);
					}
				}
			}
		}
		return picturesData;
	}
	private void buildPictureInfo (EntityRelationHeader relation,EntityRelations item, List<PicturesRelation> picturesData){
		PictureEy picture = (PictureEy) fetchEntityFromRelationItem(item);
		if(picture==null) return;

		PicturesRelation picrel = new PicturesRelation();
		picrel.setPicture(picture);
		picrel.setPictureId(picture.getPictureId());
		picrel.setRelationPredicate(relation.getPredicate());
		picrel.setRelationSubject(relation.getSubject());
		picturesData.add(picrel);

	}
	

	public List<EntityRelationHeader> browseByPredicate (List<EntityRelationHeader> erh, String search){
		if(erh==null) return null;
		if(erh.isEmpty()) return null;

		List<EntityRelationHeader> resultList = new ArrayList<EntityRelationHeader>();
		for(EntityRelationHeader relation:erh){
				if(relation.getPredicate().equals(search)) resultList.add(relation);
		}
		return resultList;
	}

	public List<LocationRelation>  renderLocationPredicate(List<EntityRelationHeader> erh){
		log.debug("renderLocationPredicate: Begin["+erh+"]");
		
		log.debug("Filter: LOCACION_DE: Begin");
		List<EntityRelationHeader> relations = browseByPredicate(erh,"LOCACION_DE");
		log.debug("Filter: LOCACION_DE: end ["+relations+"]");
		
		if(relations==null || relations.isEmpty()) return null;

		log.debug("Ready to browse relations: ");
		List<LocationRelation> locationData = new ArrayList<LocationRelation>();

		for(EntityRelationHeader relation:relations){
			log.debug("Relation: ["+relation.getPredicate()+"]:["+relation.getSubject()+"]");
			List<EntityRelations> items = relation.getErelations();
			if(!(items==null || items.isEmpty())){
				log.debug("Ready to browse items: ");
				for(EntityRelations item :items){
					log.debug("Item: ["+item.getComment()+"]:["+item.getEntityType()+"]:["+item.getEntityId()+"]");

					if(item.getEntityType().equals("PF")) getPmodel().getFpersonById(item.getEntityId());
					if(item.getEntityType().equals("PI")) getPmodel().getIpersonById(item.getEntityId());
					buildLocationData(item.getComment(),locationData);
									
				}
			}
		}
		return locationData;
	}


	
	public List<TaskRelation>  renderTaskPredicate(List<EntityRelationHeader> erh){
		log.debug("renderTaskPredicate: Begin["+erh+"]");
		
		log.debug("Filter: SUBTAREAS_DE: Begin");
		List<EntityRelationHeader> relations = browseByPredicate(erh,"SUBTAREAS_DE");
		log.debug("Filter: SUBTAREAS_DE: end ["+relations+"]");
		
		if(relations==null || relations.isEmpty()) return null;

		log.debug("Ready to browse relations: ");
		List<TaskRelation> taskData = new ArrayList<TaskRelation>();

		for(EntityRelationHeader relation:relations){
			log.debug("Relation: ["+relation.getPredicate()+"]:["+relation.getSubject()+"]");
			List<EntityRelations> items = relation.getErelations();
			if(!(items==null || items.isEmpty())){
				log.debug("Ready to browse items: ");
				for(EntityRelations item :items){
					log.debug("Item: ["+item.getComment()+"]:["+item.getEntityType()+"]:["+item.getEntityId()+"]");

					if(item.getEntityType().equals("TASK")) getTaskDao().fetchTask(item.getEntityId());
					buildTaskData(relation.getDescription(),item,taskData);
					
									
				}
			}
		}
		return taskData;
	}

	private void buildTaskData(String erDescr,EntityRelations eritem, List<TaskRelation> taskData){
		TaskRelation tr = new TaskRelation();
		Task task = (Task)fetchEntityFromRelationItem(eritem);
		//tr.setAsignee(task.getAsignee());
		tr.setDuedate(task.getDueDateAsText());
		tr.setBegindate(task.getBeginDateAsText());
		tr.setHeading(erDescr);
		tr.setOwner(getTaskModel().getOwnerLabel(task));
		tr.setRelationheading(erDescr);
		tr.setStatus(getTaskModel().getStatusLabel(task));
		tr.setSubject(task.getSubject());
		tr.setText(task.getTextData());
		tr.setLocator(task.getLocator());
		taskData.add(tr);
	}
	
	
	private void buildLocationData(String coment, List<LocationRelation> locationData){
	
		List<Location> plocations = getPmodel().getLocationList();
		log.debug("Build Location data");

		if(!(plocations==null || plocations.isEmpty())){
			log.debug("Build Location data: LOCATIONS");
			for(Location loc:plocations){
				LocationRelation lr = new LocationRelation();
				lr.locationHeading = coment;
				lr.personName = getPmodel().getPerson().toString();
				lr.locationDescr = loc.getDescr();
				lr.location1 = WebUtils.defaultStr(loc.getCalle1())+" "+WebUtils.defaultStr(loc.getCalle2());
				lr.location2 = WebUtils.defaultStr(loc.getLocalidad())+" - "+WebUtils.defaultStr(loc.getProvincia());
				lr.location = loc;
				lr.url = getPmodel().getPerson().getDefaultUrl();
				locationData.add(lr);			
			}
		}
		
		List<ContactosPerson> contactos = getPmodel().getContactList();
		log.debug("Build Contact data");

		if(!(contactos==null || contactos.isEmpty())){
			log.debug("Build Location data: CONTACTS");
			for(ContactosPerson ct:contactos){
				LocationRelation lr = new LocationRelation();
				StringBuilder st = new StringBuilder();
				lr.locationHeading = WebUtils.defaultStr(ct.getDescr());
				st.append(WebUtils.defaultStr(ct.getProtocol()));
				st.append(" ");
				st.append(WebUtils.defaultStr(ct.getValue()));
				lr.locationDescr = st.toString();
				locationData.add(lr);
			}
		}
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PersonaFisica getFperson() {
		return fperson;
	}
	public void setFperson(PersonaFisica fperson) {
		this.fperson = fperson;
	}
	public PersonaIdeal getIperson() {
		return iperson;
	}
	public void setIperson(PersonaIdeal iperson) {
		this.iperson = iperson;
	}
	public List<PersonaFisica> getFpersons() {
		return fpersons;
	}
	public void setFpersons(List<PersonaFisica> fpersons) {
		this.fpersons = fpersons;
	}
	public List<PersonaIdeal> getIpersons() {
		return ipersons;
	}
	public void setIpersons(List<PersonaIdeal> ipersons) {
		this.ipersons = ipersons;
	}
	public EntityRelationHeader getErelation() {
		return erelation;
	}
	public void setErelation(EntityRelationHeader erelation) {
		this.erelation = erelation;
	}
	public List<EntityRelationHeader> getErelations() {
		return erelations;
	}
	public void setErelations(List<EntityRelationHeader> erelations) {
		this.erelations = erelations;
	}
	public List<EntityRelations> getErelitems() {
		return erelitems;
	}
	public void setErelitems(List<EntityRelations> erelitems) {
		this.erelitems = erelitems;
	}
	
	
	
	
	public PersonDao getPersonDao() {
		if(personDao==null) personDao = new PersonDao();
		return personDao;
	}
	
	
	
	

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public RelationDao getRelDao() {
		if(relDao==null) relDao = new RelationDao();
		return relDao;
	}

	public void setRelDao(RelationDao relDao) {
		this.relDao = relDao;
	}

	public ArticleDao getArticleDao() {
		if (articleDao==null) articleDao = new ArticleDao();
		return articleDao;
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	public PictureDao getPictureDao() {
		if(pictureDao==null) pictureDao = new PictureDao();
		return pictureDao;
	}

	public void setPictureDao(PictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}

	public TaskDao getTaskDao() {
		if(taskDao==null) taskDao = new TaskDao();
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public TaskModel getTaskModel() {
		if(taskModel==null) taskModel=new TaskModel();
		return taskModel;
	}

	public void setTaskModel(TaskModel taskModel) {
		this.taskModel = taskModel;
	}

	public PersonModel getPmodel() {
		if(pmodel==null) pmodel = new PersonModel();
		return pmodel;
	}

	public void setPmodel(PersonModel pmodel) {
		this.pmodel = pmodel;
	}




	public class PicturesRelation{
		String pictureId;
		PictureEy picture;
		String relationPredicate;
		String relationSubject;
		public String getPictureId() {
			return pictureId;
		}
		public void setPictureId(String pictureId) {
			this.pictureId = pictureId;
		}
		public PictureEy getPicture() {
			return picture;
		}
		public void setPicture(PictureEy picture) {
			this.picture = picture;
		}
		public String getRelationPredicate() {
			return relationPredicate;
		}
		public void setRelationPredicate(String relationPredicate) {
			this.relationPredicate = relationPredicate;
		}
		public String getRelationSubject() {
			return relationSubject;
		}
		public void setRelationSubject(String relationSubject) {
			this.relationSubject = relationSubject;
		}
		
	}
	
	
	public class LocationRelation {
		String locationHeading="Locacion relacionada";
		String personName="";
		String locationDescr="";
		String location1="";
		String location2="";
		String contactInfo="";
		String url="";
		Location location;		
		List<Location> locations;

		public String getLocationHeading(){return locationHeading;}
		public String getPersonName() {
			return personName;
		}
		public void setPersonName(String personName) {
			this.personName = personName;
		}
		public String getLocationDescr() {
			return locationDescr;
		}
		public void setLocationDescr(String locationDescr) {
			this.locationDescr = locationDescr;
		}
		public String getLocation1() {
			return location1;
		}
		public void setLocation1(String location1) {
			this.location1 = location1;
		}
		public String getLocation2() {
			return location2;
		}
		public void setLocation2(String location2) {
			this.location2 = location2;
		}
		public String getContactInfo() {
			return contactInfo;
		}
		public void setContactInfo(String contactInfo) {
			this.contactInfo = contactInfo;
		}
		public Location getLocation() {
			return location;
		}
		public void setLocation(Location location) {
			this.location = location;
		}
		public List<Location> getLocations() {
			return locations;
		}
		public void setLocations(List<Location> locations) {
			this.locations = locations;
		}
		public void setLocationHeading(String locationHeading) {
			this.locationHeading = locationHeading;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
	}
	
	public class TaskRelation {
		String heading="Tareas relacionadas";
		String relationheading="";
		String subject="";
		String locator="";
		String text="";
		String duedate="";
		String begindate="";
		String owner="";
		String status="";
		String asignee="";
		
		
		List<Task> tasks;


		public String getHeading() {
			return heading;
		}


		public void setHeading(String heading) {
			this.heading = heading;
		}


		public String getRelationheading() {
			return relationheading;
		}


		public void setRelationheading(String relationheading) {
			this.relationheading = relationheading;
		}


		public String getSubject() {
			return subject;
		}


		public void setSubject(String subject) {
			this.subject = subject;
		}


		public String getText() {
			return text;
		}


		public void setText(String text) {
			this.text = text;
		}


		public String getDuedate() {
			return duedate;
		}


		public void setDuedate(String duedate) {
			this.duedate = duedate;
		}


		public String getOwner() {
			return owner;
		}


		public void setOwner(String owner) {
			this.owner = owner;
		}


		public String getStatus() {
			return status;
		}


		public void setStatus(String status) {
			this.status = status;
		}


		public String getAsignee() {
			return asignee;
		}


		public void setAsignee(String asignee) {
			this.asignee = asignee;
		}


		public List<Task> getTasks() {
			return tasks;
		}


		public void setTasks(List<Task> tasks) {
			this.tasks = tasks;
		}


		public String getLocator() {
			return locator;
		}


		public void setLocator(String locator) {
			this.locator = locator;
		}


		public String getBegindate() {
			return begindate;
		}


		public void setBegindate(String begindate) {
			this.begindate = begindate;
		}
		
	}

	
}
