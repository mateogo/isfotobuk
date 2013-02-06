package ar.kennedy.is2011.controllers;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.db.dao.AbstractDao;
import ar.kennedy.is2011.db.entities.RolePlaying;
import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.session.Session;




public class RolePlayingController extends AbstractController{

	private static final long serialVersionUID = 1L;

	private String action;
	private String person;
	private Boolean success;
	List<RolePlaying> persons;
	RolePlaying player;
	RolePlaying selectedPerson;
	
	private AbstractDao<RolePlaying> rpDAO = new AbstractDao<RolePlaying>();




	public RolePlayingController() {
        super();
    }


	public void action(HttpServletRequest request, HttpServletResponse response, Session userSession) throws Exception {
		log.debug("****************RolePlaying *****************");
		
		this.action = request.getParameter("action");
		this.person = request.getParameter("person");

		log.debug("action:["+action+"]  person:["+person+"]");

		
		if(this.action.equals("randomperson")){
			success = randomPerson();
			String responseText;
			if(success){
				responseText = "and the winner is..."+selectedPerson.getName()+"!";
			} else {
				responseText = "ya votaste ;o( ";
			}
			response.setContentType("text/plain");
	        response.setHeader("Cache-Control", "no-cache");
	        response.getWriter().write(responseText);
		}

		if(this.action.equals("reset")){
			success = reset();
			
			if(success){
				String responseText = "Reset success";
				response.setContentType("text/plain");
		        response.setHeader("Cache-Control", "no-cache");
		        response.getWriter().write(responseText);
			} else {
			}
		}

	}


	private Boolean randomPerson(){
		this.persons = fetchAllPlayers();
		
		this.player = selectPlayer();
		if(this.player==null) return false;
		if(this.player.getHasVoted()) return false;
		
		this.selectedPerson = selectWinner();
		if(this.selectedPerson==null) return false;
		processData();

		return true;
	}
	private void processData(){
		this.player.setHasVoted(true);
		this.player.setVoteDate(new Date());
		this.player.setVoteId(this.selectedPerson.getKey().getId());
		update (this.player);
		
		this.selectedPerson.setHasBeenElected(true);
		update(this.selectedPerson);
	}

	
	private RolePlaying selectWinner(){
		List<RolePlaying> candidates = new ArrayList<RolePlaying>();
		
		for(RolePlaying rp : persons){
			if(!rp.getName().equals(person) && !rp.getHasBeenElected()) candidates.add(rp);
		}
		log.debug("Select Winner: candidates ["+candidates+"]  size:["+candidates.size());
		if(candidates.isEmpty()) return null;
		if(candidates.size()==1) return candidates.get(0);
		return candidates.get(random(candidates.size()));
		
	}
	
	private int random (int n){
		int max = n;
		
		int result = 0 + (int)(Math.random() * ((max - 0) ));
		return result;
	}
	
	private RolePlaying selectPlayer(){
		for(RolePlaying rp : persons){
			if(rp.getName().equals(person)) return rp;
		}
		return null;
	}

	
	private Boolean reset(){
		RolePlaying role;

		log.debug("********************* RESET");
		// ==================================
		role = new RolePlaying("Alfredo");
		initPerson(role);
		update(role);
		// ==================================
		role = new RolePlaying("Estela");
		initPerson(role);
		update(role);
		// ==================================
		role = new RolePlaying("Fidela");
		initPerson(role);
		update(role);
		// ==================================
		role = new RolePlaying("Judith");
		initPerson(role);
		update(role);
		// ==================================
		role = new RolePlaying("Luciano");
		initPerson(role);
		update(role);
		// ==================================
		role = new RolePlaying("Mateo");
		initPerson(role);
		update(role);
		// ==================================
		role = new RolePlaying("Paula");
		initPerson(role);
		update(role);

		return true;
	}

	private void initPerson(RolePlaying person){
		person.setHasBeenElected(false);
		person.setHasVoted(false);
		
	}
	

	public void update(RolePlaying role){
		try{
			log.debug("=== trying update this.rolePlaying["+role+"]");
			rpDAO.persist(role);
			log.debug("****** update role:OK ********");

		} catch (Exception e) {
			//articDAO.rollBackTx();
			log.debug("=== Persistence failed!!! :["+role.getName()+"]");
			log.error(e.getMessage());

		}finally{
		}

	}
	public RolePlaying fetchRole(Long id){
		if(id==null) return null;
		if(id<=0) return null;

		try {
			return rpDAO.findById(RolePlaying.class, id);
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}

	
	public List<RolePlaying> fetchAllPlayers() {
		
		try {
			return rpDAO.select(RolePlaying.class);
			
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	
		
	@Override
	public boolean validateLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
}