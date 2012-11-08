<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.models.PersonModel"%>
<%@page import="ar.kennedy.is2011.db.dao.PersonBean"%>
<%@page import="ar.kennedy.is2011.db.entities.PersonaFisica"%>
<%@page import="ar.kennedy.is2011.db.entities.ContactosPerson"%>
<%@page import="ar.kennedy.is2011.db.entities.Location"%>

<%
	PersonBean pbean = (PersonBean) request.getAttribute("pbean");
	
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Cuenta::Usuario::Persona</title>
	<meta name="edicion de cuenta - usuario - persona" content="">
	<meta name="fotobuk uk" content="">
	<meta name="description" content="">
	<meta name="author" content="">

	<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

	<link href="/css/bootstrap-2.1.1.css" rel="stylesheet" type="text/css">
	<link href="/images/favicon.gif" rel="icon" type="image/gif">

	<style type="text/css">
		body {
			padding-top: 60px;
			padding-bottom: 40px;
		}
	</style>
	
</head>
<body>
  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">	
        <a class="brand" href="#">Person</a>
          <ul class="nav">
            <li class="active"><a href="/secure/main.jsp">Inicio</a></li>
            
            <li class="dropdown" >
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Acciones<b class="caret"></b></a>
              <ul class="dropdown-menu" >
                <li><a href="/secure/imageUpload.jsp">Subir imagen</a></li>
                <li class="divider"></li>
                <li class="nav-header">Visualizar por...</li>
                <li><a href="/secure/albums.jsp">Album</a></li>
                <li><a href="/secure/search.jsp">Buscar</a></li>
              </ul>
            </li>
          </ul>
          <div class="pull-right">
            <ul class="nav">
               <li class="dropdown" >
                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">${usuarioLogeado.userName}<b class="caret"></b></a>
                 <ul class="dropdown-menu" >
                   <li><a href="/userprofile">Editar perfil</a></li>
                   <li class="divider"></li>
                   <li><a href="/logout">Cerrar sesion</a></li>
                 </ul>
               </li>
            </ul>
          </div> 	
      </div>   <!-- end container -->
    </div>	 <!--end topbar-inner -->
  </div> <!--end topbr -->
<!-- END header -->

<div class="container">
		
<!-- ************** EDIT PERSON FISICA ************************* -->
	<h3>${pbean.fperson.nombrePerson}</h3>
	<form name="editFPerson" class="form-inline" action="/editFPerson?action=${pbean.action}" method="post">
	<fieldset>
					<div class="control-group">
						<label class="control-label" for="pDenom" >Denominación y comentario que caracterizan esta persona:</label>
						<div class="controls" >

							<input type="hidden" id="fpersonId" name="fpersonId" 
									value="${pbean.fperson.id}" />

							<input type="text" id="pDenom" class="span4" name="pDenom" placeholder="Denominacion"
								value="${pbean.fperson.nombrePerson}" />
							<input type="text" id="pComent" class="span6" name="pComent" placeholder="Comentario"
								value="${pbean.fperson.coment}" />
						</div>
					</div>
	
	
					<div class="control-group">
						<label class="control-label" for="pDenom" >Nombre y Apellido:</label>
							<div class="controls" >
							<div class="input-prepend" >
								<span class="add-on"><i class="icon-plus"></i></span>
								<input type="text" id="pName" class="span4" name="pName" placeholder="nombre"
									value="${pbean.fperson.nombre}" />
							</div>
							<div class="input-prepend" >
								<span class="add-on"><i class="icon-plus"></i></span>
								<input type="text" id="pMail" class="span5" name="pApellido" placeholder="apellido"
									value="${pbean.fperson.apellido}" />
							</div>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="pFechaNacim" >Fecha nacimiento:</label>
						<div class="controls controls-row" >
							<input type="date" id="pFechaNacim" class="span3" name="pFechaNacim" placeholder="fecha de nacimiento (mm/dd/aaaa)"
								value="${pbean.fperson.feNacimAsText}" />
							<select id="pGender" name="pGender" class="span3">
								<option value="S/D">Seleccione genero</option>
								<option value="MASCULINO">masculino</option>
								<option value="FEMENINO">femenino</option>
							</select>
						</div>
					</div>
			
			<div class="form-actions">
				<button type="submit" class="btn btn-primary btn-mini" name="save" >actualizar</button>
				<button type="button" class="btn btn-mini"            name="cancelar" >cancelar</button>
			</div>
	</fieldset>
	</form>

<!-- ************** DATOS CONTACTO - LOCACIONES  ************************* -->
		<h3>Datos de contacto y Locaciones</h3>
		<div class="btn-group">
			<button type="button" class="btn btn-info btn-mini" name="newContactBtn" data-toggle="modal" data-target="#newContact" onclick='selectContact(-1)' >nuevo contacto</button>
			<button type="button" class="btn btn-info btn-mini" name="newLocationBtn" data-toggle="modal" data-target="#newLocation" onclick='selectContact(-1)' >nueva locación</button>
			<button type="button" class="btn btn-danger btn-mini" name="removeContactBtn" >eliminar</button>
		</div>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>tipo</th>
					<th>denominación</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			<%
				int citem=0;
				if(pbean.getContactos()!=null){
				for(ContactosPerson contacto:pbean.getContactos()){
			%>
				<tr>
					<td>
						<input type="checkbox">
					</td>
					<td><%= contacto.getConType() %></td>
					<td><%= contacto.toString() %></td>
					<td>
						<div class="btn-group">
							<button type="button" class="btn btn-info btn-mini" name="editContactBtn" data-toggle="modal" data-target="#newContact" onclick='selectContact(<%=citem %>)' >editar</button>
						</div>
					</td>
				</tr>

			<%
				citem++;					
				}
				}
			%>


		<%
				int litem=0;
				if(pbean.getLocations()!=null){
				for(Location location:pbean.getLocations()){
			%>
				<tr>
					<td>
						<input type="checkbox">
					</td>
					<td>LOCACION</td>
					<td><%= location.toString() %></td>
					<td>
						<div class="btn-group">
							<button type="button" class="btn btn-info btn-mini" name="editLocationBtn" data-toggle="modal" data-target="#newLocation" onclick='selectLocation(<%=litem %>)' >editar</button>
						</div>
					</td>
				</tr>

			<%
				litem++;					
				}
				}
			%>



			</tbody>
			
		</table>



<!-- ************** EDIT DATOS CONTACTO ************************* -->
		<!-- Modal viewer -->
		<div class="modal hide fade in" id="newContact"  data-keyboard=false data-backdrop=false tabindex="-1" role="dialog" aria-labelledby="newContactLabel" aria-hidden="true">
			<form id="newContactForm" name="newContactForm" class="form-horizontal" action="/editContact" method="post">			
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
				<h3>administración de datos de contacto</h3>
			</div>
			<div id="ctInput" class="modal-body">

					<input type="hidden" id="ctPersonId" name="ctPersonId" 
						value="${pbean.fperson.id}" />
					<input type="hidden" id="ctContactId" name="ctContactId" 
						value="" />
									
					<div class="control-group" class="span5">
						<label class="control-label" for="ctDescr" >Descripción:</label>
						<div class="controls" >
							<input type="text" id="ctDescr" name="ctDescr" class="span3" 
									value="" />
						</div>
					</div>					
					<div class="control-group" class="span5">
						<label class="control-label" for="ctValue" >Numero/dato:</label>
						<div class="controls" >
								<input type="text" id="ctValue" name="ctValue" class="span3"
									value="" />
						</div>
						<label class="control-label" for="ctProtocol" >Protocolo:</label>
						<div class="controls" >
								<input type="text" id="ctProtocol" name="ctProtocol" class="span3"
									value="" />
						</div>
					</div>

					<div class="control-group" class="span5">
						<label class="control-label" for="ctType" >Tipo:</label>
						<div class="controls" >
							<select id="ctType" name="ctType">
								<option  value="EMAIL" >e-mail</option>
								<option  value="CEL"   >celular</option>
								<option  value="TEL"   >telefono</option>
								<option  value="FAX"   >fax</option>
								<option  value="VOIP"  >voip skype</option>
								<option  value="RSOC"  >red social</option>
								<option  value="OTRO"  >otro</option>
							</select>
						</div>
						<label class="control-label" for="ctUseType" >Uso:</label>
						<div class="controls" >
							<select id="ctUseType" name="ctUseType">
								<option  value="PARTICULAR" >Particular</option>
								<option  value="LABORAL"  >Laboral </option>
								<option  value="OTRA">Otra</option>
							</select>						
						</div>
					</div>
					<div class="control-group" class="span5">
						<label class="control-label" for="ctProvider" >Proveedor:</label>
						<div class="controls" >
							<select id="ctProvider" name="ctProvider">
								<option  value="GOOGLE"   >Google</option>
								<option  value="FACEBOOK" >Facebook</option>
								<option  value="TWITTER"  >Twitter</option>
								<option  value="SKYPE"    >Skype</option>
								<option  value="ARSAT"    >Arsat</option>
								<option  value="METROTEL" >Metrotel</option>
								<option  value="IPLAN"    >Iplan</option>
								<option  value="MOVISTAR" >Movistar</option>
								<option  value="CLARO"    >Claro</option>
								<option  value="PERSONAL" >Personal</option>
								<option  value="OTRA"     >Otra</option>
							</select>						
						</div>
					</div>
			
			</div>
			<div class="modal-footer form-actions">
				<button type="submit" class="btn btn-primary" name="save" >guardar</button>
				<button type="button" class="btn "            name="limpiar" >limpiar</button>
			</div>
			</form>
		</div>




<!-- ************** EDIT LOCATIONS  ************************* -->
		<!-- Modal viewer -->
		<div class="modal hide fade in" id="newLocation"  data-keyboard=false data-backdrop=false tabindex="-1" role="dialog" aria-labelledby="newLocationLabel" aria-hidden="true">
			<form id="newLocatioForm" name="newLocationForm" class="form-inline" action="/editLocation" method="post">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
				<h3>locacion</h3>
			</div>
			<div id="locInput" class="modal-body">
				<input type="hidden" id="locPersonId" name="locPersonId" 
						value="${pbean.fperson.id}" />
				<input type="hidden" id="locLocationId" name="locLocationId" 
						value="" />
									
				<input type="text" id="locCalle1" name="locCalle1" class="span4" 
						placeholder="calle-1" value="" />
				<input type="text" id="locCalle2" name="locCalle2" class="span3"
						placeholder="calle-2" value="" />
				<input type="text" id="locCPostal" name="locCPostal" class="span2"
						placeholder="Código postal" value="" />
				<input type="text" id="locLocalidad" name="locLocalidad" class="span5"
						placeholder="Localidad" value="" />
				<input type="text" id="locProvincia" name="locProvincia" class="span3"
						placeholder="Provincia" value="" />
				<select id="locPais" name="locPais">
					<option  value="ARGENTINA" >Argentina</option>
					<option  value="URUGUAY"   >Uruguay</option>
					<option  value="PARAGUAY"  >Paraguay</option>
					<option  value="CHILE"     >Chile</option>
					<option  value="VENEZUELA" >Venezuela</option>
					<option  value="COLOMBIA"  >Colombia</option>
					<option  value="OTRO"      >otro</option>
				</select>
				<div class="control-group" class="span5">
					<span class="span6 divider">.</span>
					<label class="control-label" for="locDescripcion" >Datos de contexto para esta locación:</label>
					<div class="controls" >
					<input type="text" id="locDescripcion" name="locDescripcion" class="input-block-level"
							placeholder="Descripcion" value="" />
					<label class="control-label" for="locType" >Tipo loc:</label>
					<select id="locSedeType" name="locSedeType" class="span6">
						<option  value="PARTICULAR"    >Particular</option>
						<option  value="EMPRESA"       >Empresa</option>
						<option  value="DEPOSITO"      >Depósito</option>
						<option  value="LUGAR-PUBLICO" >Lugar público</option>
						<option  value="EVENTO"        >Evento</option>
						<option  value="OTRA"          >otra</option>
					</select>
					<label class="control-label" for="locType" >Evento :</label>
					<select id="locEvento" name="locEvento" class="span6">
						<option  value="S/D"         >No representa un evento</option>
						<option  value="NACIMIENTO"  >Lugar de nacimiento</option>
						<option  value="MUERTE"      >Lugar de muerte</option>
						<option  value="EXHIBICION"  >Sala de exhibición</option>
						<option  value="PRODUCCION"  >Sala de producción</option>
					</select>
					</div>
				</div>
			</div>
			<div class="modal-footer form-actions">
				<button type="submit" class="btn btn-primary" name="save" >guardar</button>
				<button type="button" class="btn "            name="limpiar" >limpiar</button>
			</div>
			</form>
		</div>




</div> <!-- end CONTAINER -->


    <!-- Le javascript
    document.getElementById('myLink').onclick = doSomething;
	function doSomething(evt) {
    	evt = evt || window.event; // The event that was fired
    	var targ = evt.target || evt.srcElement; // the element that was clicked
    	var el = this; // the element that fired the event
	}
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="/js/bootstrap-2.1.1.js"></script>

	<script>
     $('#editFPerson').on('show', function () {
	});
     </script>

 	<script>
    function contactData(){
		//var combo = document.getElementById('userGender');
		var contacts= new Array();
		var item =0;
		<%
		if(pbean.getContactos()!=null){
		for(ContactosPerson ctp:pbean.getContactos()){
		%>
			var ctp=new Array();
			ctp[0]='<%= ctp.getDescr()   %>';
			ctp[1]='<%= ctp.getValue()   %>';
			ctp[2]='<%= ctp.getProtocol()%>';
			ctp[3]='<%= ctp.getProvider()%>';	
			ctp[4]='<%= ctp.getUseType() %>';	
			ctp[5]='<%= ctp.getConType() %>';	
			ctp[6]='<%= ctp.getId() %>';	
	
			contacts[item]=ctp;
			item++;	
		<%	
		}
		}
		%>
		return contacts
    }
	</script>

 	<script>
    function locationData(){
		//var combo = document.getElementById('userGender');
		var locations= new Array();
		var item =0;
		<%
		if(pbean.getLocations()!=null){
		for(Location loc:pbean.getLocations()){
		%>
			var loc=new Array();
			loc[0]='<%= loc.getId()        %>';
			loc[1]='<%= loc.getCalle1()    %>';
			loc[2]='<%= loc.getCalle2()    %>';
			loc[3]='<%= loc.getCpostal()   %>';
			loc[4]='<%= loc.getLocalidad() %>';
			loc[5]='<%= loc.getProvincia() %>';
			loc[6]='<%= loc.getPais()      %>';
			loc[7]='<%= loc.getDescr()     %>';
			loc[8]='<%= loc.getSedeType()  %>';
			loc[9]='<%= loc.getEventoType()%>';
	
			locations[item]=loc;
			item++;	
		<%	
		}
		}
		%>
		return locations
    }
	</script>

 	<script>
     $('#newContact').on('show', function () {
 		 //$('#newAccountForm').attr('action', editAccount); //this works
 		//document.getElementById("newAccountForm").setAttribute('action', editAccount);
    	 if(contactIndex!=-1){
        	 var contacts=contactData();
        	 document.getElementById("ctDescr").value=contacts[contactIndex][0];
           	 document.getElementById("ctValue").value=contacts[contactIndex][1];
           	 document.getElementById("ctProtocol").value=contacts[contactIndex][2];
           	 document.getElementById("ctProvider").value=contacts[contactIndex][3];
           	 document.getElementById("ctUseType").value=contacts[contactIndex][4];
           	 document.getElementById("ctType").value=contacts[contactIndex][5];
           	 document.getElementById("ctContactId").value=contacts[contactIndex][6];
    	 }else{
        	 document.getElementById("ctDescr").value="";
           	 document.getElementById("ctValue").value="";
           	 document.getElementById("ctProtocol").value="";
           	 document.getElementById("ctProvider").value="";
           	 document.getElementById("ctUseType").value="";
           	 document.getElementById("ctType").value="";
           	 document.getElementById("ctContactId").value="0";
    	 }
 	});
	</script>

 	<script>
     $('#newLocation').on('show', function () {
    	 if(locationIndex!=-1){
        	 var locations=locationData();
           	 document.getElementById("locLocationId").value=locations[locationIndex][0];
           	 document.getElementById("locCalle1").value=locations[locationIndex][1];
           	 document.getElementById("locCalle2").value=locations[locationIndex][2];
           	 document.getElementById("locCPostal").value=locations[locationIndex][3];
           	 document.getElementById("locLocalidad").value=locations[locationIndex][4];
           	 document.getElementById("locProvincia").value=locations[locationIndex][5];
           	 document.getElementById("locPais").value=locations[locationIndex][6];
           	 document.getElementById("locDescripcion").value=locations[locationIndex][7];
           	 document.getElementById("locSedeType").value=locations[locationIndex][8];
           	 document.getElementById("locEvento").value=locations[locationIndex][9];
    	 }else{
           	 document.getElementById("locLocationId").value="0";
        	 document.getElementById("locCalle1").value=""
           	 document.getElementById("locCalle2").value=""
           	 document.getElementById("locCPostal").value=""
           	 document.getElementById("locLocalidad").value=""
             document.getElementById("locProvincia").value=""
           	 document.getElementById("locPais").value=""
           	 document.getElementById("locDescripcion").value=""
           	 document.getElementById("locSedeType").value=""
           	 document.getElementById("locEvento").value=""
    	 }
 	});
	</script>

	<script>
	function selectContact(index){
 		contactIndex =index
 		if(index==-1) editContact="/editContact?action=new";
 		else editContact="/editContact?action=update";
 		$('#newContactForm').attr('action', editContact);
 	}
	function selectLocation(index){
 		locationIndex =index
 		if(index==-1) editLocation="/editLocation?action=new";
 		else editLocation="/editLocation?action=update";
 		$('#newLocationForm').attr('action', editLocation);
 	}
	var contactIndex=0;
	var locationIndex=0;
    $('#pGender option[value="${pbean.fperson.sexo}"]').get(0).selected =  true;
	</script>	
</body>
</html>
