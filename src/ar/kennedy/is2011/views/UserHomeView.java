package ar.kennedy.is2011.views;

import javax.servlet.http.HttpServletRequest;
import ar.kennedy.is2011.db.entities.Usuario;
import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.utils.WebUtils;
import ar.kennedy.is2011.models.SearchPicturesModel;
import ar.kennedy.is2011.db.entities.PictureEy;
import org.apache.log4j.Logger;
import java.util.Iterator;
import java.util.List;


public class UserHomeView {

	//private static final Integer DEFAULT_FECTH = 2;
	private static final Integer PICS_PER_PAGE = 4;
	private static final Integer BUTTONS_PER_SECTION = 4;
	private HttpServletRequest request;
	private Usuario user;
	private PictureEy lastImageUpload;
	private SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
	protected final Logger log = Logger.getLogger(getClass());
	private List<PictureEy> pictures;
	private int pages=0;
	private int actualPage=1;
	private int fromButton=1;
	private int toButton=3;
	
	public UserHomeView(){
		
	}
	public UserHomeView(HttpServletRequest rq){
		setRequest(rq);
		setUser( (Usuario) SessionManager.get(request, WebUtils.getSessionIdentificator(request)).getElement("user"));
		setLastImageUpload(searchPicturesModel.getLastPictureUploadByUser(user.getNombreUsr()));
		log.debug("UserHomeView Instanciated");
	}	
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public Usuario getUser() {
		return user;
	}
	public String getUserName() {
		return getUser().getNombre();
	}
	public String getUserDisplayName() {
		return getUser().getDisplayName();
	}
	public void setUser(Usuario user) {
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
		if(pictures==null) pictures = searchPicturesModel.getPicturesToBeDisplayedByUser(user.getNombreUsr());
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

}
