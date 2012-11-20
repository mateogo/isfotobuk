package ar.kennedy.is2011.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.db.entities.Person;
import ar.kennedy.is2011.db.dao.AbstractDao;

import ar.kennedy.is2011.db.exception.EntityNotFoundException;
import ar.kennedy.is2011.utils.WebUtils;

public class FindPersonsByNameController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	protected final Logger log = Logger.getLogger(getClass());
	

	private static final String FPERSON_LIKE_NAME ="SELECT e FROM PersonaFisica e WHERE e.nombrePerson LIKE :1";
	private static final String IPERSON_LIKE_NAME = "SELECT e FROM PersonaIdeal  e WHERE e.nombrePerson LIKE :1";

	AbstractDao<PersonaFisica> fpersonDAO = new AbstractDao<PersonaFisica>();
	AbstractDao<PersonaIdeal> ipersonDAO = new AbstractDao<PersonaIdeal>();
	
	String personList = "[";
	

	private List  fpersons;
	private List  ipersons;
	
	
	public FindPersonsByNameController() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("query")+"%";
		personList=" ";
		fpersons=null;
		ipersons=null;
		
		log.debug("******************************************Search Person: ["+name+"]");
		if(WebUtils.isNotNull(name)){
			buildPersonsArray(name);
		}
		
		//personList= "{\n \"options\": [ \n" + personList + " \"teresita\"\n]\n}";
		personList= "[" + personList + " \"teresita\"]";
		log.debug("*******personList:"+personList);

		response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(personList);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action (request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action (request, response);
	}

	public void buildPersonsArray(String name) {
		try {
			ipersons = ipersonDAO.createCollectionQuery(IPERSON_LIKE_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
			fpersons = fpersonDAO.createCollectionQuery(FPERSON_LIKE_NAME, new Vector<Object>(Arrays.asList(new String[] {name})));
			stringList(ipersons);
			stringList(fpersons);			
		} catch(EntityNotFoundException e) {
		}
		
	}
	public void stringList(List lista){
		if(lista==null)return;
		if(lista.isEmpty())return;
		StringBuilder st = new StringBuilder();
		for(Person person: (List<Person>)lista){
			st.append('\"');
			st.append(person.getNombrePerson());
			st.append('\"');
			st.append(", ");
		}
		this.personList=this.personList+st.toString();
	}
}