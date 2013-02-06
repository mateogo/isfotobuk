package ar.kennedy.is2011.db.dao;

import ar.kennedy.is2011.db.entities.Article;
import ar.kennedy.is2011.db.entities.ContactosPerson;
import ar.kennedy.is2011.db.entities.Location;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.EntityRelationHeader;

import java.util.List;


public class ArticleBean {

	private User user;
	private Article article;
	private Article selectedArticle;
	
	private EntityRelationHeader erelation;
	
	private List<Article> articles;
	private List<EntityRelationHeader> erelations;

	private List<GenericEntity> entities;
	private List<ArticlePortlet> destacados;
	private List<ArticlePortlet> triplets;

	private List<ContactosPerson> contactos;
	private List<Location> locations;

	private String action;
	private String articleToEditId;
	private String browsertab="";
	private String viewertab="";

	public ArticleBean() {
		// TODO Auto-generated constructor stub
	}

	public ArticleBean(User us, String ac) {
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

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public EntityRelationHeader getErelation() {
		return erelation;
	}

	public void setErelation(EntityRelationHeader erelation) {
		this.erelation = erelation;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
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

	public String getArticleToEditId() {
		return articleToEditId;
	}

	public void setArticleToEditId(String articleToEditId) {
		this.articleToEditId = articleToEditId;
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

	public Article getSelectedArticle() {
		return selectedArticle;
	}

	public void setSelectedArticle(Article selectedArticle) {
		this.selectedArticle = selectedArticle;
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

	public List<ArticlePortlet> getDestacados() {
		return destacados;
	}

	public void setDestacados(List<ArticlePortlet> destacados) {
		this.destacados = destacados;
	}

	public List<ArticlePortlet> getTriplets() {
		return triplets;
	}

	public void setTriplets(List<ArticlePortlet> triplets) {
		this.triplets = triplets;
	}


}
