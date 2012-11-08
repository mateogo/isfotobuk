package ar.kennedy.is2011.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.entities.User;
import ar.kennedy.is2011.db.entities.Account;
import ar.kennedy.is2011.session.Session;
import ar.kennedy.is2011.utils.WebUtils;
import ar.kennedy.is2011.models.AccountModel;
import ar.kennedy.is2011.session.SessionManager;


public class AccountEditController extends AbstractController{

	private static final long serialVersionUID = 1L;
	private User user;
	private Account account;
	private Boolean errorsDetected=false;
	private AccountModel model;
	
	public AccountEditController() {
        super();
    }


	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("****************************************************");
		
		String action = request.getParameter("action");
		log.debug("AccountEditController.action: begin. action:["+action+"]");

		this.user= getUserFromSession(request);

		log.debug("AccountEditController.action: begin. user:["+this.user.getUserName()+"] ["+this.user.getKey().getId()+"]");
		model = new AccountModel(this.user);
		
		if (model.getUserById(this.user.getKey())==null) errorsDetected=true;
		
		log.debug("AccountEditController.action: user retrieve OK user:["+model.getUser().getUserName()+"]");
		
		if(!errorsDetected){
			if(action.equals("update")){
				errorsDetected = updateAccount(request,response,userSession);

			}else if (action.equals("new")){
				errorsDetected = newAccount(request,response,userSession);			
			}			
		}

		if(!errorsDetected){
			
			WebUtils.updateUserSession(request, userSession, model);

			response.sendRedirect("/secure/main.jsp");
			
		} else {
			response.sendRedirect("/index.jsp");
		}

	}

	private Boolean newAccount(HttpServletRequest request, HttpServletResponse response, Session userSession){

		String accountName = request.getParameter("accId");
		
		log.debug("************* NEW account: begin ["+accountName+"]");
		
		if (model.ifExistAccountByName(accountName)) errorsDetected=true;
		
		if(!errorsDetected){
			log.debug("NEW account: ready to insert");
			String mail    	   = request.getParameter("accMail");
			String passwd      = request.getParameter("accPasswd1");
			String provider    = request.getParameter("accProvider");

			errorsDetected= model.initNewAccount(accountName, mail, provider, passwd);

			log.debug("NEW account: new Account initialised");
			
			if(!errorsDetected){
				changePasswd(passwd);

				model.addNewAccountToUser();
				
				model.update();
				
				log.debug("NEW account:  model Updated!");				
			}
		}
		return errorsDetected;
	}
	
	
	private Boolean updateAccount(HttpServletRequest request, HttpServletResponse response, Session userSession){

		String accountName = request.getParameter("accId");
		
		log.debug("********************* UPDATE account: begin ["+accountName+"]");

		if (getAccountToEdit(model.getUser(), accountName)==null) errorsDetected=true;
		
		if(!errorsDetected){
			log.debug("Update account: Account to edit: found! ready to update");
			String mail    	   = request.getParameter("accMail");
			String passwd      = request.getParameter("accPasswd1");
			String provider    = request.getParameter("accProvider");

			model.getAccount().setMail(mail);
			model.getAccount().setAuthProvider(provider);			
			changePasswd(passwd);	
			model.update();
			
			log.debug("Update account:Updated! ["+mail+"] ["+passwd+"] ["+provider+"]");
		}
		
		 // sesion.setAttribute("usuarioLogeado", usuario);
		  
		return errorsDetected;
	}
	

	private User getUserFromSession(HttpServletRequest request){
		return (User) SessionManager.getCurrentUser(request);
	}

	private void changePasswd(String password){
		model.getAccount().setPasswd(WebUtils.encrypt(password));
	}

	private Account getAccountToEdit(User user, String accName){
		log.debug("Update account: AccountToEdit: begin");
		this.account=null;
		this.account=model.chooseAccountFromUser(user, accName);

		return account;
	}

	
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}

}