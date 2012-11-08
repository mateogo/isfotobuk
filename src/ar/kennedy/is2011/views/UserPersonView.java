package ar.kennedy.is2011.views;

import javax.servlet.http.HttpServletRequest;

import ar.kennedy.is2011.db.dao.PersonBean;
import ar.kennedy.is2011.db.dao.UserAccount;
import ar.kennedy.is2011.db.entities.User;
import org.apache.log4j.Logger;

import ar.kennedy.is2011.models.PersonModel;
//import ar.kennedy.is2011.session.SessionManager;
import ar.kennedy.is2011.session.SessionManager;


public class UserPersonView {

	protected final Logger log = Logger.getLogger(getClass());
	

	public UserPersonView() {
		// TODO Auto-generated constructor stub
	}

	public static UserAccount userAccountFactory(HttpServletRequest request){

		UserAccount userprofile = new UserAccount();
		
		User user = (User) SessionManager.getCurrentUser(request);
		
		if (user!=null){
			userprofile.setUser(user);
			userprofile.setAccounts(user.getAccounts());
			userprofile.setAcountDefault(user.getDefaultAccount());
			
			PersonModel personModel=new PersonModel(user);
			userprofile.setPerson(personModel.getPersonFromUser());
			userprofile.setFpersons(personModel.getFpersonsOwnedByUser());
		}
		
		return userprofile;
	}
	
	
	public static PersonBean personBeanFactory (HttpServletRequest request){
		PersonBean personBean = new PersonBean();
		User user = (User) SessionManager.getCurrentUser(request);
		
		if (user!=null){
			PersonModel personModel=new PersonModel(user);
			personBean = new PersonBean(user,"view");
			personBean.setFperson(personModel.getPersonFromUser());
			personBean.setContactos(personModel.getContactList());
			personBean.setLocations(personModel.getLocationList());
		}

		return personBean;
	}	
}
