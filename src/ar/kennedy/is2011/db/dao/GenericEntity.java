package ar.kennedy.is2011.db.dao;

import ar.kennedy.is2011.db.entities.Article;
import ar.kennedy.is2011.db.entities.Task;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.Person;
import ar.kennedy.is2011.db.entities.PictureEy;
import java.io.Serializable;


public class GenericEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;
	private Article article;
	private Task task;
	private Person person;
	private PictureEy picture;
	

	private String entityType;
	private Long   entityLongId;
	private String entityStringId;
	private String entityName;
	private String entityDescription;
	
	
	
	
	
	public GenericEntity() {
		// TODO Auto-generated constructor stub
	}





	public User getUser() {
		return user;
	}





	public void setUser(User user) {
		this.user = user;
	}





	public Article getArticle() {
		return article;
	}





	public void setArticle(Article article) {
		this.article = article;
	}





	public Person getPerson() {
		return person;
	}





	public void setPerson(Person person) {
		this.person = person;
	}





	public PictureEy getPicture() {
		return picture;
	}





	public void setPicture(PictureEy picture) {
		this.picture = picture;
	}





	public String getEntityType() {
		return entityType;
	}





	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}





	public Long getEntityLongId() {
		return entityLongId;
	}





	public void setEntityLongId(Long entityLongId) {
		this.entityLongId = entityLongId;
	}





	public String getEntityStringId() {
		return entityStringId;
	}





	public void setEntityStringId(String entityStringId) {
		this.entityStringId = entityStringId;
	}





	public String getEntityName() {
		return entityName;
	}





	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}





	public String getEntityDescription() {
		return entityDescription;
	}





	public void setEntityDescription(String entityDescription) {
		this.entityDescription = entityDescription;
	}





	public Task getTask() {
		return task;
	}





	public void setTask(Task task) {
		this.task = task;
	}



}
