package ar.kennedy.is2011.views;

import javax.servlet.http.HttpServletRequest;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.session.Session;

import ar.kennedy.is2011.utils.WebUtils;
import ar.kennedy.is2011.models.SearchPicturesModel;
import ar.kennedy.is2011.models.AlbumModel;
import ar.kennedy.is2011.db.entities.PictureEy;
import ar.kennedy.is2011.db.entities.AlbumEy;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;


public class ImageUploadView {

	protected final Logger log = Logger.getLogger(getClass());

	//private static final Integer DEFAULT_FECTH = 2;
	private static final Integer PICS_PER_PAGE = 4;
	private static final Integer BUTTONS_PER_SECTION = 4;
	private HttpServletRequest request;
	private User user;
	private Session userSession ;
	private PictureEy lastImageUpload;
	private PictureEy pictureToEdit;
	private Map<String, Object> errors;
	private SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
	private List<PictureEy> pictures;
	private AlbumModel albumModel;

	private int pages=0;
	private int actualPage=1;
	private int fromButton=1;
	private int toButton=3;
	
	public ImageUploadView(){
		
	}
	public ImageUploadView(HttpServletRequest rq){
		setRequest(rq);
		init();
	}	
	@SuppressWarnings("unchecked")
	private void init(){
		this.userSession = SessionManager.get(request, WebUtils.getSessionIdentificator(request));
		this.errors = userSession.contains("errors") ? 
				((Map<String, Object>) userSession.getElement("errors")).containsKey("form_errors") ? 
						(Map<String, Object>) ((Map<String, Object>) userSession.getElement("errors")).get("form_errors") : 
						new HashMap<String, Object>() : 
				new HashMap<String, Object>();
		setUser( (User) userSession.getElement("user"));
		setLastImageUpload(searchPicturesModel.getLastPictureUploadByUser(user.getUserName()));
		log.debug("UserHomeView Instanciated");
		albumModel= new AlbumModel();
		setPictureToEdit();
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public User getUser() {
		return user;
	}
	public String getUserName() {
		return getUser().getUserName();
	}
	public String getUserDisplayName() {
		return getUser().getDisplayName();
	}
	public void setUser(User user) {
		this.user = user;
	}
	public PictureEy getLastImageUpload() {
		return lastImageUpload;
	}
	public Boolean userHaveImages(){
		if(getLastImageUpload()!=null) return true; else return false;
	}
	public void setLastImageUpload(PictureEy lastImageUpload) {
		this.lastImageUpload = lastImageUpload;
	}	
	public String getLastImageId() {
		return getLastImageUpload().getPictureId();
	}
	public Iterator<PictureEy> getPicByUserIterator(){
		Iterator<PictureEy> it = getPictures().iterator();
		//testIterator(it);
		return it;		
	}
	public List<PictureEy> getPicByUserList(){
		return getPictures();
	}
	public String[] getPicBbyPage(){
		List<PictureEy> picList = getPictures();
		Integer picSize = picList.size();
		setPages(picSize);
		Integer page = WebUtils.getParameter(getRequest(), "page") != null ? new Integer(WebUtils.getParameter(request, "page")) : 1;
		setActualPage(page);
		Integer from = firstPicByPage(page,picSize);
		Integer to = lastPicByPage(page,picSize);
		log.debug("Generando pic-Array from:["+from+"]"+" to:["+to+"]");
		String[] picsArray;
		if(to-from<0){
			picsArray= null;
		}else{
			picsArray = new String[to-from+1];
			Iterator<PictureEy> it = picList.iterator();
			int fill=0;
			int index=0;
			while (it.hasNext()){
				fill++;
				if(fill>=from && fill<=to){
					picsArray[index]=it.next().getPictureId();
					log.debug("picsArray :["+index+"]"+" ["+picsArray[index]+"]");
					index++;					
				}else{
					it.next();					
				}
				log.debug("Iterando index:["+index+"]"+" fill:["+fill+"]");
			}			
		}
		return picsArray;
	}
	private Integer firstPicByPage(Integer page, Integer listSize){
		int from=1;
		if(((page-1)*PICS_PER_PAGE)+1<=listSize) from=((page-1)*PICS_PER_PAGE)+1;
		else from=listSize+1;
		return from;
	}
	private Integer lastPicByPage(Integer page, Integer listSize){
		int last=0;
		if(page*PICS_PER_PAGE<=listSize) last=page*PICS_PER_PAGE;
		else last=listSize;
		return last;
	}
	public List<PictureEy> getPictures() {
		if(pictures==null) pictures = searchPicturesModel.getPicturesToBeDisplayedByUser(user.getUserName());
		return pictures;
	}
	public void setPictures(List<PictureEy> pictures) {
		this.pictures = pictures;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int listSize) {
		int p = listSize/PICS_PER_PAGE + ((listSize % PICS_PER_PAGE) == 0? 0 : 1);
		log.debug("-------------------------");
		log.debug("Pages: p:["+p+"] listSize:["+listSize+"]");
		this.pages = p;
	}
	public int getActualPage() {
		return actualPage;
	}
	public void setActualPage(int actualPage) {
		this.actualPage = actualPage;
	}
	public void testIterator(Iterator<PictureEy> it){
		while (it.hasNext()){
			PictureEy pic= it.next();
			log.debug("listando: ["+pic.getPictureId()+"]");
		}
	}
	public void setNavButtons(){
		int navSection=(this.getActualPage()-1)/BUTTONS_PER_SECTION;
		this.fromButton=navSection*BUTTONS_PER_SECTION+1;
		this.toButton=(fromButton+BUTTONS_PER_SECTION-1)>this.getPages()?this.getPages():fromButton+BUTTONS_PER_SECTION-1;
		log.debug("-------------------------");
		log.debug("getPages:["+getPages()+"]  navSection:["+navSection+"]  fromButton:["+fromButton+"]  toButton:["+toButton+"] ");
	}
	public int getFromButton() {
		return fromButton;
	}
	public void setFromButton(int fromButton) {
		this.fromButton = fromButton;
	}
	public int getToButton() {
		return toButton;
	}
	public void setToButton(int toButton) {
		this.toButton = toButton;
	}
	public String objectToString(Object obj){
		return obj != null ? obj.toString() : "";
	}

	public AlbumModel getAlbumModel() {
		if(albumModel==null) albumModel=new AlbumModel();
		return albumModel;
	}
	public void setAlbumModel(AlbumModel albumModel) {
		this.albumModel = albumModel;
	}

	
	private String buildAlbumString(List<AlbumEy> albums){
		StringBuilder list = new StringBuilder();
		for(int i = 0; i < albums.size(); i++) {	
			if(i == (albums.size() - 1)) {
				list.append("'").append(albums.get(i).getAlbumId()).append("'");
			
			} else {
				list.append("'").append(albums.get(i).getAlbumId()).append("', ");
			}
		}
		String str=list.toString();
		log.debug("buildAlbumString :["+str+"]");
		
		return str;
	}
	public String getAlbumsCommaSeparated() {
		List<AlbumEy> albums = getAlbumModel().getAllAlbumes();
		return buildAlbumString(albums);
	}

	public String getAlbumsByUserCommaSeparated() {
		Set<AlbumEy> albums = getAlbumsToBeDisplayedByUser();
		List<AlbumEy> albumsByOwner = new ArrayList<AlbumEy>();
		albumsByOwner.addAll(albums);		
		return buildAlbumString(albumsByOwner);
	}

	public Set<AlbumEy> getAlbumsToBeDisplayedByUser() {
		List<AlbumEy> albumsByVisibility = null;
		List<AlbumEy> albumsByOwner = null;
		Set<AlbumEy> albums = new HashSet<AlbumEy>();
		
		try {
			albumsByVisibility = getAlbumModel().getAlbumesByVisibility("public");
			albumsByOwner = getAlbumModel().getAlbumesByOwner(user.getUserName());
			
			albums.addAll(albumsByVisibility);
			albums.addAll(albumsByOwner);
			
			return albums;
			
		} catch(Exception e) {
			return new HashSet<AlbumEy>();
		}
	}
	public Map<String, Object> getErrors() {
		if(errors==null) errors = new HashMap<String,Object>();
		return errors;
	}
	public void setErrors(Map<String, Object> errors) {
		this.errors = errors;
	}
	public Session getUserSession() {
		return userSession;
	}
	public void setUserSession(Session userSession) {
		this.userSession = userSession;
	}
	public PictureEy getPictureToEdit() {
		if(pictureToEdit==null) pictureToEdit = new PictureEy();
		return pictureToEdit;
	}
	public void setPictureToEdit() {
		PictureEy picture = null;
		
		String pictureId = WebUtils.getParameter(request, "pictureid");
		if (pictureId != null) {
			picture = searchPicturesModel.getPicture(pictureId);
		
		} else if (userSession.contains("picture")) {
			picture = (PictureEy) userSession.getElement("picture");
		
		} else {
			picture = new PictureEy();
		}
		
		this.pictureToEdit=picture;
	}
}
