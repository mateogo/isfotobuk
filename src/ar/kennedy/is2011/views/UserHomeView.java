package ar.kennedy.is2011.views;

import javax.servlet.http.HttpServletRequest;
import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.WebUtils;
import ar.kennedy.is2011.models.SearchPicturesModel;
import ar.kennedy.is2011.db.entities.PictureEy;
import org.apache.log4j.Logger;
import java.util.Iterator;
import java.util.List;
import ar.kennedy.is2011.db.dao.PictureDao;



public class UserHomeView {

	//private static final Integer DEFAULT_FECTH = 2;
	private static final Integer PICS_PER_PAGE = 4;
	private static final Integer BUTTONS_PER_SECTION = 4;
	private HttpServletRequest request;
	private User user;
	private PictureEy lastImageUpload;
	private PictureEy profileImage;
	private SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
	protected final Logger log = Logger.getLogger(getClass());
	private List<PictureEy> pictures;
	private int pages=0;
	private int actualPage=1;
	private int fromButton=1;
	private int toButton=3;
	private PictureDao pictureDao= new PictureDao();
	
	public UserHomeView(){
		
	}
	public UserHomeView(HttpServletRequest rq){
		setRequest(rq);
		setUser( (User) SessionManager.getCurrentUser(request));
		setLastImageUpload(searchPicturesModel.getLastPictureUploadByUser(user.getUserName()));
		setProfileImage(searchPicturesModel.getUserProfilePicture(user));
		log.debug("UserHomeView Instanciated");
		
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
	public PictureEy[] getPicBbyPage(){
		List<PictureEy> picList = getPictures();
		Integer picSize = picList.size();
		setPages(picSize);
		Integer page = WebUtils.getParameter(getRequest(), "page") != null ? new Integer(WebUtils.getParameter(request, "page")) : 1;
		setActualPage(page);
		Integer from = firstPicByPage(page,picSize);
		Integer to = lastPicByPage(page,picSize);
		log.debug("Generando pic-Array from:["+from+"]"+" to:["+to+"]");
		PictureEy[] picsArray;
		if(to-from<0){
			picsArray= null;
		}else{
			picsArray = new PictureEy[to-from+1];
			Iterator<PictureEy> it = picList.iterator();
			int fill=0;
			int index=0;
			while (it.hasNext()){
				fill++;
				if(fill>=from && fill<=to){
					picsArray[index]=it.next();
					log.debug("picsArray :["+index+"]"+" ["+picsArray[index].getPictureId()+"]");
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

	public List<PictureEy> fetchAlbumPictures(String albumName) {
		pictures = pictureDao.fetchPicturesByAlbum(albumName);
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
	public String getProfileImage() {
		if(profileImage==null) return getLastImageId();
		else return profileImage.getPictureId();
	}
	public void setProfileImage(PictureEy profileImage) {
		this.profileImage = profileImage;
	}	

}
