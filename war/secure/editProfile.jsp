<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.entities.Account"%>
<%@page import="ar.kennedy.is2011.db.entities.PersonaFisica"%>
<%@page import="ar.kennedy.is2011.db.entities.PersonaIdeal"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.models.SearchPicturesModel"%>
<%@page import="ar.kennedy.is2011.views.UserHomeView"%>
<%@page import="ar.kennedy.is2011.views.UserPersonView"%>
<%@page import="ar.kennedy.is2011.db.dao.UserAccount"%>
<%@page import="ar.kennedy.is2011.controllers.UserAccountController"%>

<%
	UserAccount useraccount =  UserPersonView.userAccountFactory(request);
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Cuenta::Usuario::Persona</title>
	<meta name="edicion de cuenta - usuario - persona" content="">
	<meta name="fotobuk uk" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <a class="brand" href="#">Fotobuk</a>
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
                   <li><a href="#">Editar perfil</a></li>
   	               <li><a href="/editPerson?action=browsePerson">Personas</a></li>
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
<!-- START Content  tabs -->
	<div class="tabbable">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#usuario" data-toggle="tab">Usuario</a></li>
			<li><a href="#persona" data-toggle="tab">Persona</a></li>
		</ul>

	<div class="tab-content">

	<!--   =============  U S E R ======================= -->
	<div class="tab-pane active" id="usuario">
		<h2>Usuario</h2>
		<div class="btn-group">
			<button type="button" class="btn btn-info btn-mini" name="editUserBtn"   data-toggle="modal" data-target="#editUser" >editar</button>
			<button type="button" class="btn btn-info btn-mini" name="editPersonBtn" onclick="location.href='/editPerson?action=editUserPerson'" >datos persona</button>
		</div>
		<div class="row">
			<div class="span2">Nombre usuario:</div>
			<div class="span4">${profile.user.userName}</div>
		</div> 
		<div class="row">
			<div class="span2">e-Mail:</div>
			<div class="span4">${profile.user.mail}</div>
		</div> 
		<div class="row">
			<div class="span2">Fecha de Nacimiento:</div>
			<div class="span4">${profile.user.fechaNacimiento}</div>
		</div> 
		<div class="row">
			<div class="span2">Sexo:</div>
			<div class="span4">${profile.user.sexo}</div>
		</div> 
		<div class="row">
			<div class="span2">Locación:</div>
			<div class="span4">${profile.user.locacion}</div>
		</div> 
		<div class="row">
			<div class="span2">Rol:</div>
			<div class="span4">${profile.user.appRole}</div>
		</div> 

		<div class="accordion" id="secretq">
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse" data-parent="#secretq" href="#collapseOne">
				Pregunta secreta
				</a>
			</div>
			<div id="collapseOne" class="accordion-body collapse">
				<div class="accordion-inner">
					<div class="row">
						<div class="span2">Respuesta:</div>
						<div class="span4">${profile.user.respuestaPregunta}</div>
					</div> 
				</div>
			</div>
		</div>
		</div>
<!-- ************** A C C O U N T  ************************* -->
		<h3>Cuentas del usuario</h3>
		<div class="btn-group">
			<button type="button" class="btn btn-info btn-mini" name="newAccountBtn" data-toggle="modal" data-target="#newAccount" onclick='selectAccount(-1)' >nueva cuenta</button>
			<button type="button" class="btn btn-info btn-mini" name="defaultAccount"  >establecer cuenta principal</button>
			<button type="button" class="btn btn-danger btn-mini" name="removeAccountBtn" >eliminar</button>
		</div>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th></th>
					<th>cuenta</th>
					<th>denominación</th>
					<th>autenticación</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			<%
				int item=0;
				if(!useraccount.getAccounts().isEmpty()){
				for(Account acc:useraccount.getAccounts()){
			%>
				<tr>
					<td>
						<input type="checkbox">
					</td>
					<td>
						<%
							if(useraccount.getUser().getDefaultAccount().getAccountName()==acc.getAccountName()){
						%>
							<i class="icon-plus"></i>
						<%
							}
						%>
					</td>
					<td><%= acc.getAccountName() %></td>
					<td><%= acc.getMail() %></td>
					<td><%= acc.getAuthProvider() %></td>
					<td>
						<div class="btn-group">
							<button type="button" class="btn btn-info btn-mini" name="editAccountBtn" data-toggle="modal" data-target="#newAccount" onclick='selectAccount(<%=item %>)' >editar</button>
						</div>
					</td>
				</tr>

			<%
				item++;					
				}
				}
			%>

			</tbody>
			
		</table>
		
<!-- ************** EDIT ACCOUNT ************************* -->
		<!-- Modal viewer -->
		<div class="modal hide fade in" id="newAccount"  data-keyboard=false data-backdrop=false tabindex="-1" role="dialog" aria-labelledby="newAccountLabel" aria-hidden="true">
			<form id="newAccountForm" name="newAccountForm" class="form-horizontal" action="/editAccount" method="post">			
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
				<h3>administración de cuenta</h3>
			</div>
			<div id="accInput" class="modal-body">

					<div class="control-group" class="span5">
						<label class="control-label" for="accId" >id:</label>
						<div class="controls" >
							<div class="input-prepend" >
								<span class="add-on"><i class="icon-plus"></i></span>
								<input type="text" id="accId" class="span3" name="accId" 
									value="" />
							</div>
						</div>
					</div>					
					<div class="control-group" class="span5">
						<label class="control-label" for="accMail" >mail:</label>
						<div class="controls" >
							<div class="input-prepend" >
								<span class="add-on"><i class="icon-envelope"></i></span>
								<input type="email" id="accMail" class="span3" name="accMail" 
									value="" />
							</div>
						</div>
					</div>
					<div class="control-group" class="span5">
						<label class="control-label" for="accPasswd1" >password:</label>
						<div class="controls" >
								<input type="password" id="accPasswd1" class="span3" name="accPasswd1" 
									value="" />
						</div>
					</div>
					<div class="control-group" class="span5">
						<label class="control-label" for="accPasswd2" >repetir password:</label>
						<div class="controls" >
								<input type="password" id="accPasswd2" class="span3" name="accPasswd2" 
									value="" />
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="accProvider">autenticación:</label>
						<div class="controls">
							<select id="accProvider" name="accProvider">
								<option  value="THIS" >fotobuk </option>
								<option  value="GOOGLE"  >google  </option>
								<option  value="FACEBOOK">facebook</option>
								<option  value="TWITTER" >twitter </option>
							</select>
							<span class="help-block">Indique el servicio que provee la autenticación de esta cuenta</span>
						</div>
					</div>

			
			</div>
			<div class="modal-footer form-actions">
				<button type="submit" class="btn btn-primary" name="save" >guardar</button>
				<button type="button" class="btn "            name="limpiar" >limpiar</button>
			</div>
			</form>
		</div>
		
<!-- ************** EDIT USER (MODAL VIEWER) ************************* -->
		<div class="modal hide fade in" id="editUser"  data-keyboard=false data-backdrop=false tabindex="-1" role="dialog" aria-labelledby="editUserLabel" aria-hidden="true">
			<form name="editUserForm" class="form-inline" action="/editUser" method="post">			
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
				<h3>administración de usuario</h3>
			</div>
			<div id="userInput" class="modal-body">

					<div class="control-group">
						<div class="controls" >
							<div class="input-prepend" >
								<span class="add-on"><i class="icon-plus"></i></span>
								<input type="text" id="userId" class="span6" name="userId" placeholder="Identificación"
									value="${profile.user.userName}" />
							</div>
							<div class="input-prepend" >
								<span class="add-on"><i class="icon-envelope"></i></span>
								<input type="email" id="userMail" class="span6" name="userMail" placeholder="e-mail"
									value="${profile.user.mail}" />
							</div>
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls controls-row" >
							<input type="date" id="userFechaNacim" class="span3" name="userFechaNacim" placeholder="fecha de nacimiento"
								value="${profile.user.feNacimAsText}" />
							<select id="userGender" name="userGender" class="span2">
								<option value="S/D">Seleccione</option>
								<option value="MASCULINO">masculino</option>
								<option value="FEMENINO">femenino</option>
							</select>
						</div>
					</div>
					<div class="control-group">
						<div class="controls" >
							<input type="date" id="userPais" class="span3" name="userPais" placeholder="pais de residencia"
								value="${profile.user.locacion.pais}" />
							<input type="date" id="userProvincia" class="span3" name="userProvincia" placeholder="provincia / region" 
								value="${profile.user.locacion.provincia}" />
						</div>
					</div>

					<div class="control-group">
						<div class="controls">
							<select id="userSecretQ" name="userSecretQ" class="span4">
								<option value="">-- Seleccionar una pregunta --</option>
								<option value="1">Cu&aacute;l es el nombre de su mascota?</option>
								<option value="2">Nombre de tu abuela materna?</option>
								<option value="3">Apellido de soltera de su madre?</option>
								<option value="4">Cu&aacute;l es tu comida favorita?</option>
								<option value="5">Fecha de casamiento?</option>
								<option value="6">Nombre del primer colegio?</option>
							</select>
						</div>
						<div class="controls" >
							<input type="date" id="userAnswer" class="span4" name="userAnswer" placeholder="ingrese su respuesta secreta"
								value="${profile.user.respuestaPregunta}" />
						</div>
					</div>
					<div class="control-group">
						<div class="controls controls-row" >
							<select id="userAppRole" name="userAppRole" class="span4">
								<option value="">--seleccione en la aplicaión--</option>
								<option value="ADMINISTRADOR">administrador</option>
								<option value="EDITOR">editor</option>
								<option value="PRODUCTOR">productor</option>
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
		
		
		
		
	</div><!-- end tabpane -->

	<!--   =============  P E R S O N A ======================= -->
	<div class="tab-pane" id="persona">
		<h2>Personas</h2>
		<div class="btn-group">
			<button type="button" class="btn btn-info btn-mini" name="newFPersonBtn" onclick="location.href='/editPerson?action=newFPerson'" >nueva persona fisica</button>
			<button type="button" class="btn btn-info btn-mini" name="newIPersonBtn" onclick="location.href='/editPerson?action=newIPerson'" >nueva persona ideal</button>
		</div>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>Denominación</th>
					<th>Nombre</th>
					<th>Comentario</th>
				</tr>
			</thead>
			<tbody>
			<%
				int itemFPerson=0;
				if(!useraccount.getFpersons().isEmpty()){
				for(PersonaFisica person:useraccount.getFpersons()){
			%>
				<tr>
					<td>
						<input type="checkbox">
					</td>
					<td><%= person.getId() %></td>
					<td><%= person.getNombrePerson() %></td>
					<td><%= person.getNombre() %>  <%= person.getApellido() %></td>
					<td>
						<div class="btn-group">
							<button type="button" class="btn btn-info btn-mini" name="editPersonBtn" onclick="location.href='/editPerson?action=updateFPerson&personid=<%=person.getId() %>'" >editar</button>
						</div>
					</td>
				</tr>

			<%
				itemFPerson++;					
				}
				}
			%>

			<%
				int itemIPerson=0;
				if(!useraccount.getIpersons().isEmpty()){
				for(PersonaIdeal person:useraccount.getIpersons()){
			%>
				<tr>
					<td>
						<input type="checkbox">
					</td>
					<td><%= person.getId() %></td>
					<td><%= person.getNombrePerson() %></td>
					<td><%= person.getComent() %></td>
					<td>
						<div class="btn-group">
							<button type="button" class="btn btn-info btn-mini" name="editPersonBtn" onclick="location.href='/editPerson?action=updateIPerson&personid=<%=person.getId() %>'" >editar</button>
						</div>
					</td>
				</tr>

			<%
				itemIPerson++;					
				}
				}
			%>

			</tbody>
			
		</table>
		
		
		
		
		
		
	</div> <!-- end PERSONA -->


<!-- END Content  tabs -->
	</div><!-- end TAB CONTENT -->
	</div> <!-- end TABABBLE -->
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
    function accountData(){
		//var combo = document.getElementById('userGender');
		var accounts= new Array();
		var item =0;
		<%
		for(Account acc:useraccount.getAccounts()){
		%>
			var acc=new Array();
			acc[0]= <%= acc.getKey().getId() %>;
			acc[1]='<%= acc.getAccountName() %>';
			acc[2]='<%= acc.getMail() %>';
			acc[3]='<%= acc.getAuthProvider() %>';	
	
			accounts[item]=acc;
			item++;	
		<%	
		}
		%>
		return accounts
    }
	</script>

 	<script>
     $('#newAccount').on('show', function () {
    	 if(accountIndex!=-1){
        	 var accounts=accountData();
        	 document.getElementById("accId").value=accounts[accountIndex][1];
        	 document.getElementById("accMail").value=accounts[accountIndex][2];
        	 $('#accProvider option[value=\"'+accounts[accountIndex][3]+'\"]').get(0).selected =  true;
    	 }else{
           	 document.getElementById("accId").value="";
        	 document.getElementById("accMail").value="";
        	 document.getElementById("accProvider").options[0].selected =  true;
    	 }
 	});
	</script>
 	<script>
     $('#editUser').on('show', function () {
    	 //$('#userGender option[value=${profile.user.sexo}]').selected = true;
    	 $('#userGender option[value="${profile.user.sexo}"]').get(0).selected =  true;
    	 $('#userAppRole option[value="${profile.user.appRole}"]').get(0).selected =  true;
    	 $('#userSecretQ option[value="${profile.user.idPreguntaSecreta}"]').get(0).selected =  true;
	});
	</script>
    
 	<script>
    function changeSelected(){
		//var combo = document.getElementById('userGender');
		var combo = $('#userGender').get(0);
    	combo.options[0].selected = true;
    	
      	// $('#userGender').options[2].selected =  true;
   	
    }
	</script>
	<script>
	function selectAccount(index){
 		accountIndex =index
 		if(index==-1) editAccount="/editAccount?action=new";
 		else editAccount="/editAccount?action=update";
 		$('#newAccountForm').attr('action', editAccount); //this works
 	}
	var accountIndex=0;
	var editAccount="updatet";
	</script>
	
</body>
</html>
