package ar.kennedy.is2011.db.dao;

import ar.kennedy.is2011.db.entities.Article;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.utils.WebUtils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.Key;


public class ArticleDao {

	private static final Logger log = Logger.getLogger(AbstractDao.class);
	private static final String ARTICLES_BY_NAME   = "SELECT e FROM Article e WHERE e.name = :1";
	private static final String ARTICLES_LIKE_NAME = "SELECT e FROM Article  e WHERE e.name LIKE :1";

	private AbstractDao<Article> articDAO = new AbstractDao<Article>();

	private User user;

	private Article article;
	private List<Article> articles;


	public ArticleDao() {
		// TODO Auto-generated constructor stub
	}

	public ArticleDao(User us) {
		// TODO Auto-generated constructor stub
		this();
		setUser(us);
	}

	
	public void update(Article article){
		try{
			log.debug("=== trying update this.article["+article+"]");
			articDAO.persist(article);
			log.debug("****** update article:OK ********");

		} catch (Exception e) {
			//articDAO.rollBackTx();
			log.debug("=== Persistence failed!!! :["+article.getName()+"]");
			log.error(e.getMessage());

		}finally{
		}

	}
	
	public Article fetchArticle(Key key){
		if(key==null) return null;

		try {
			return articDAO.findById(Article.class, key);
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public Article fetchArticle(Long id){
		if(id==null) return null;
		if(id<=0) return null;

		try {
			return articDAO.findById(Article.class, id);
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	public List<Article> fetchArticlesByName(String name) {
		if(WebUtils.isNull(name)) return null;
		
		try {
			return articDAO.createCollectionQuery(ARTICLES_BY_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
		
			
		} catch(EntityNotFoundException e) {
			return new ArrayList<Article>();
		}
	}

	public List<Article> fetchArticlesLikeName(String name) {
		try {
			return articDAO.createCollectionQuery(ARTICLES_LIKE_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
		
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	
	public List<GenericEntity> fetchEntitiesLikeName(String name) {
		List<Article> articles= fetchArticlesLikeName (name);
		
		if(articles==null) return null;
		if(articles.isEmpty()) return null;
	
		return buildEntityList(articles);
	}

	public String fetchArticleNamesLikeName(String name) {
		List<Article> articles= fetchArticlesLikeName (name);
		
		if(articles==null) return null;
		if(articles.isEmpty()) return null;
	
		return buildStringList(articles);
	}
	
	private String buildStringList(List<Article>  articles){
		StringBuilder st = new StringBuilder();
		String response;
		for(Article article: articles){
			st.append('\"');
			st.append(article.getName());
			st.append('\"');
			st.append(", ");
		}
		response = st.toString();
		log.debug("=== responseString:["+response+"] ["+response.substring(0,response.length()-2)+"]");
		return response.substring(0,response.length()-2);
	}
	
	private List<GenericEntity> buildEntityList(List<Article>  articles) {
		List<GenericEntity> entities = new ArrayList<GenericEntity>();
		for(Article ar:articles){
			GenericEntity ge = new GenericEntity();
			ge.setArticle(ar);
			ge.setEntityDescription(ar.getMainTitle());
			ge.setEntityLongId(ar.getKey().getId());
			ge.setEntityName(ar.getName());
			ge.setEntityStringId(ar.getId());
			ge.setEntityType("ARTICLE");
			entities.add(ge);
		}
		return entities;		
	}
	
	
	
	/**  === AUTOGENERATEDE GETTERS - SETTERS: BEGIN ======  */
	public AbstractDao<Article> getArticleDao() {
		return articDAO;
	}

	public void setArticleDao(AbstractDao<Article> articleDao) {
		this.articDAO = articleDao;
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

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	/**  === AUTOGENERATEDE GETTERS - SETTERS: END ========  */

	
}
