package ar.kennedy.is2011.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.utils.WebUtils;

//import ar.kennedy.is2011.session.Session;

public class AboutSectionController extends AbstractTemplateController {

	private static final long serialVersionUID = 7320911254853012236L;
	
    private Map menu = new HashMap();
  
    
   
	public void action(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("****************About SECTION CONTROLLER *****************");
		log.debug("******* user session: ["+(this.userSession==null?"false":"true")+"] usuario:["+userName+"]");

		if(WebUtils.isNull(this.userName)) this.userName="usuario";
		if (action.equals("default")){
			defaultAction();
		}
	}
		
	private void defaultAction(){
		if(this.userSession==null) buildPublicMenu();
		else buildLoggedMenu();

        page.setTemplate("/article/about/article.ftl");
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildLoggedMenu(){
		ArrayList leftmenu = new ArrayList();
	                     /**  descr     url               icon  class   chld   dvider header  icon  */
		leftmenu.add(new Menu("inicio","/home?action=home","",  "active",false, false, false,  false));
		leftmenu.add(new Menu("clases","#","","",false,false,false,false));
		if(true){
                                /**  descr      url  icon class    chld  dvider header icon  */
			Menu mmedia = new Menu("acciones","#", "",   "",     true, false, false, false);
			ArrayList multimedia = new ArrayList();
                                          /**  descr      url                              icon          class  chld    dvider  header  icon  */
				multimedia.add(new Menu("subir imagen"  ,"secure/imageUpload.jsp"       , "icon-upload" , "" ,  false , false , false , true));
   			    multimedia.add(new Menu(""              ,"#"                            , ""            , "" ,  false , true  , false , false));
   			    multimedia.add(new Menu("visualizar"    ,"#"                            , ""            , "" ,  false , false , true  , false));
   			    multimedia.add(new Menu("album"         ,"/secure/albums.jsp"           , "icon-search" , "" ,  false , false , false , true));
   			    multimedia.add(new Menu("art’culos"     ,"/article?action=browseArticle", "icon-search" , "" ,  false , false , false , true));
   			    mmedia.smenus = multimedia;
			leftmenu.add(mmedia);
		}	
		menu.put("left",  leftmenu);
		
		//========== RIGHT MENU =======================
		ArrayList rightmenu = new ArrayList();
		if(true){
 			            		//  descr   url  icon class    chld  dvider header icon  
			Menu profile = new Menu(this.userName,"#", "",   "",     true, false, false, false);
			ArrayList items = new ArrayList();
			                      /**  descr      url                                icon          class  chld    dvider   header  icon  */
			items.add(new Menu("home"          , "/secure/main.jsp"                , "icon-home"  , ""  , false , false  , false , true));
			items.add(new Menu("editar perfil" , "/userprofile"                    , "icon-user"  , ""  , false , false  , false , true));
			items.add(new Menu("personas"      , "/editPerson?action=browsePerson" , "icon-user"  , ""  , false , false  , false , true));
			items.add(new Menu("divider"       , "#"                               , ""           , ""  , false , true   , false , false));
			items.add(new Menu("logout"        , "/logout"                          , "icon-remove", "" , false , false  , false , true));
			profile.smenus = items;
			rightmenu.add(profile);
		}	
		menu.put("right", rightmenu);

		//========== DATA MODEL =======================
		page.put("menu", menu);
	}

	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildPublicMenu(){
		ArrayList leftmenu = new ArrayList();
	                     /**  descr     url               icon  class   chld   dvider header  icon  */
		leftmenu.add(new Menu("inicio","/home?action=home","",  "active",false, false, false,  false));
		leftmenu.add(new Menu("clases","#","","",false,false,false,false));
		menu.put("left",  leftmenu);
		
		//========== RIGHT MENU =======================
		ArrayList rightmenu = new ArrayList();
		if(true){
 			            		//  descr   url  icon class    chld  dvider header icon  
			Menu profile = new Menu("ingresar","#", "",   "",     true, false, false, false);
			ArrayList items = new ArrayList();
			                      /**  descr      url                                icon          class  chld    dvider   header  icon  */
			items.add(new Menu("ingresar"      , "/index.jsp"                      , "icon-user"  , ""  , false , false  , false , true));
			items.add(new Menu("registrarse"   , "/registracionRapida.jsp"         , "icon-user"  , ""  , false , false  , false , true));
			profile.smenus = items;
			rightmenu.add(profile);
		}	
		menu.put("right", rightmenu);

		//========== DATA MODEL =======================
		page.put("menu", menu);
	}

	
	public static String noNull(String s) {
        return s == null ? "" : s;
    }
    
    public static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0; 
    }
    
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	public Menu menuItem (String descr, String url, String icon,String active){
		Boolean hasicon = icon==null? false:true;
		return new Menu(descr, url, icon, "", false, false, false, hasicon);
	}
	
	public class Menu{
		private String descr="";
		private String url="";
		private String icon="";
		private String active ="";
		private Boolean haschild=false;
		private Boolean isdivider=false;
		private Boolean isheader=false;
		private Boolean hasicon=false;
		private List<Menu> smenus ;

		public Menu(){}

		public Menu(String descr, String url, String icon,String active, Boolean haschild, Boolean isdivider,Boolean isheader, Boolean hasicon){
			this.descr = descr;
			this.url = url;
			this.icon = icon;
			this.active = active;
			this.haschild = haschild;
			this.isdivider = isdivider;
			this.isheader = isheader;
			this.hasicon = hasicon;
		}
				
		public String getDescr() {
			return descr;
		}
		public void setDescr(String descr) {
			this.descr = descr;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public Boolean getIsdivider() {
			return isdivider;
		}
		public void setIsdivider(Boolean isdivider) {
			this.isdivider = isdivider;
		}
		public Boolean getIsheader() {
			return isheader;
		}
		public void setIsheader(Boolean isheader) {
			this.isheader = isheader;
		}


		public String getIcon() {
			return icon;
		}


		public void setIcon(String icon) {
			this.icon = icon;
		}




		public Boolean getHasicon() {
			return hasicon;
		}


		public void setHasicon(Boolean hasicon) {
			this.hasicon = hasicon;
		}


		public Boolean getHaschild() {
			return haschild;
		}


		public void setHaschild(Boolean haschild) {
			this.haschild = haschild;
		}


		public String getActive() {
			return active;
		}


		public void setActive(String active) {
			this.active = active;
		}


		public List<Menu> getSmenus() {
			return smenus;
		}


		public void setSmenus(List<Menu> smenus) {
			this.smenus = smenus;
		}
		
	}
}
