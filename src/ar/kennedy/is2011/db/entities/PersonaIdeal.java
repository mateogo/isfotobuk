package ar.kennedy.is2011.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ar.kennedy.is2011.utils.WebUtils;

import java.util.Date;

@Entity
@Table(name = "PERSONA_IDEAL")
public class PersonaIdeal extends Person {
	
	private static final long serialVersionUID = -2548441551324306490L;

	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;

	public PersonaIdeal() {
		// TODO Auto-generated constructor stub
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	
	public String getFechaCreacionAsText() {
		return WebUtils.getFormatedDate(getFechaCreacion());				
	}

	public void setFechaCreacion(Date fechaCreacion) {
		if(fechaCreacion==null) fechaCreacion=new Date();
		this.fechaCreacion = fechaCreacion;
	}
	public String toString(){
		return "PI: ["+getNombrePerson()+"] ["+getId()+"]";
	}

}
