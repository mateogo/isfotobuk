<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.models.PersonModel"%>
<%@page import="ar.kennedy.is2011.db.entities.Location"%>
<%@page import="ar.kennedy.is2011.db.entities.ContactosPerson"%>
<%@page import="ar.kennedy.is2011.db.entities.EntityRelationHeader"%>
<%@page import="ar.kennedy.is2011.db.entities.EntityRelations"%>
<%@page import="ar.kennedy.is2011.db.entities.Person"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>

<%
	WebUtils.validateMandatoryParameters(request, new String[] { "personid" });
	String personId = request.getParameter("personid");
	PersonModel pm = new PersonModel();
	pm.searchPersonById(personId);
	pm.getRelationsForPersons();
%>
	<div class="span6">
	<%
		if(pm.getFperson()!=null){
	%>
		<div class="row">
		<div class="span3"> <h3><%=pm.getFperson().getNombrePerson() %></h3><%=pm.getFperson().getComent() %></div>
		<span class="thumbnail span2"><img src='/image?pictureid=<%=pm.getFperson().getDefaultImageId() %>&version=O' height=60 width=90></span>
		</div>
		<p><%=pm.getFperson().getNombre() %> <%=pm.getFperson().getApellido() %></p>
		<p></p>
		<p>Fecha de Nacimiento:<strong> <%=pm.getFperson().getFeNacimAsText() %></strong> Sexo: <strong><%=pm.getFperson().getSexo() %></strong></p>
		<p><strong> Direcciones:</strong></p>
		<%
			if(pm.getFperson().getLocations()!=null){
				List<Location> locs = pm.getFperson().getLocations();
				if(!locs.isEmpty()){
					for(Location loc: locs){
		%>
				<p><%=loc.getCalle1() %> <%=loc.getCalle2() %> <%=loc.getLocalidad() %></p>
				<p>(<%=loc.getCpostal() %>) <%=loc.getProvincia() %> <%=loc.getPais() %> </p>

		<%
				}
				}
			}
		%>
		<p><strong> Datos de contacto:</strong></p>		
		<%
			if(pm.getFperson().getDatosContacto()!=null){
				List<ContactosPerson> contactos = pm.getFperson().getDatosContacto();
				if(!contactos.isEmpty()){
					for(ContactosPerson ctk: contactos){
		%>
		<p>	<span class="span2"><%=ctk.getProvider() %> </span>
			<span class="span3">(<%=ctk.getProtocol() %>) <strong><%=ctk.getValue() %></strong></span>
		</p>

		<%
				}
				}
			}
		%>



	<%
		}
	%>

	<%
		if(pm.getIperson()!=null){
	%>	

		<div class="row">
		<div class="span3"> <h3><%=pm.getIperson().getNombrePerson() %></h3><%=pm.getIperson().getComent() %></div>
		<span class="thumbnail span2"><img src='/image?pictureid=E07jN53fpcji50Q&version=O' height=60 width=90></span>
		</div>

		<p>Fecha creacion: <%=pm.getIperson().getFechaCreacionAsText() %></p>
	<%
		}
	%>
	<%
	int itemRelation=0;
	if(!(pm.getErelations()==null)){
	if(!pm.getErelations().isEmpty()){
		String personHeader="";
		for(EntityRelationHeader relation:pm.getErelations()){
			personHeader="";
			if(relation.getFpersonkey()!=null) personHeader=personHeader+relation.getFpersonkey()+" ";
			if(relation.getIpersonkey()!=null) personHeader=personHeader+relation.getIpersonkey()+" ";
	%>	
			<div class="row">
			<div class="span6"> <h4><%=relation.getPredicate()%>:<%=relation.getSubject()%></h4></div>
			<div class="span6">     <%=relation.getDescription()%></div>
	<%
			pm.setErelitems(relation.getErelations());
			if(pm.getErelitems()!=null){
			if(!pm.getErelitems().isEmpty()){
	%>	
			<div class="span6">
			<ul class="span5">

	<%
			for(EntityRelations ritem:pm.getErelitems()){			
				Person prelated = pm.fetchPersonFromRelationItem(ritem);
	%>
			<li >
			<a href="#" class="span4" onclick='browse("<%=prelated.getId()%>")' ><%=prelated.getNombrePerson()%>: <%=ritem.getComment()%></a>
			</li>
			
	<%
			}
	%>	
			</ul>
			</div>
	<%
			}
			}
	%>	
		</div>
	<%
	itemRelation++;					
	}
	}
	}
	%>
	
	
	</div>
