package ar.kennedy.is2011.db.dao;

import ar.kennedy.is2011.db.entities.PersonaFisica;
import ar.kennedy.is2011.db.entities.PersonaIdeal;
import ar.kennedy.is2011.db.entities.Account;
import ar.kennedy.is2011.db.entities.User;

import java.util.List;


public class UserAccount {

	private User user;
	private PersonaFisica person;
	private List<Account> accounts;
	private Account acountDefault;
	private List<PersonaFisica> fpersons;
	private List<PersonaIdeal> ipersons;
	
	public UserAccount() {
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PersonaFisica getPerson() {
		return person;
	}

	public void setPerson(PersonaFisica person) {
		this.person = person;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account getAcountDefault() {
		return acountDefault;
	}

	public void setAcountDefault(Account acountDefault) {
		this.acountDefault = acountDefault;
	}

	public List<PersonaFisica> getFpersons() {
		return fpersons;
	}

	public void setFpersons(List<PersonaFisica> fpersons) {
		this.fpersons = fpersons;
	}

	public List<PersonaIdeal> getIpersons() {
		return ipersons;
	}

	public void setIpersons(List<PersonaIdeal> ipersons) {
		this.ipersons = ipersons;
	}

}
