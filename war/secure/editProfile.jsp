<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.entities.Usuario"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.models.SearchPicturesModel"%>
<%@page import="ar.kennedy.is2011.views.UserHomeView"%>

<%
	UserHomeView userView = new UserHomeView(request);
	SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
	Usuario user = (Usuario) SessionManager.get(request, WebUtils.getSessionIdentificator(request)).getElement("user");
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
            <li class="active"><a href="#">Inicio</a></li>
            
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
                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">${usuarioLogeado.nombreUsr}<b class="caret"></b></a>
                 <ul class="dropdown-menu" >
                   <li><a href="/secure/editarCuentaUsuario.jsp">Editar perfil</a></li>
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
		<h1>Usuario</h1>
		<div class="row">
			<div class="span2">Nombre usuario:</div>
			<div class="span4">mateogo</div>
		</div> 
		<div class="row">
			<div class="span2">e-Mail:</div>
			<div class="span4">mgomezortega@gmail.com</div>
		</div> 
		<div class="row">
			<div class="span2">Fecha de Nacimiento:</div>
			<div class="span4">15-02-1960</div>
		</div> 
		<div class="row">
			<div class="span2">Sexo:</div>
			<div class="span4">Masculino</div>
		</div> 
		<div class="row">
			<div class="span2">Locación:</div>
			<div class="span4">CABA</div>
		</div> 
		<div class="row">
			<div class="span2">Rol:</div>
			<div class="span4">administrador</div>
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
						<div class="span2">Apellido de soltera de tu madre:</div>
						<div class="span4">grego</div>
					</div> 
				</div>
			</div>
		</div>
		</div>
	</div>

	<!--   =============  P E R S O N A ======================= -->
	<div class="tab-pane" id="persona">
		<h1>Persona</h1>
	</div> <!-- end USER -->


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
    
    
	
</body>
</html>
