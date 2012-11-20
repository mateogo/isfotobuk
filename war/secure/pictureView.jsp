<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.session.Session"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.db.entities.User"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%
	WebUtils.validateMandatoryParameters(request, new String[] { "pictureid" });
	Session userSession = SessionManager.get(request,
			WebUtils.getSessionIdentificator(request));
	User user = (User) SessionManager.getCurrentUser(request);	
%>
<!DOCTYPE html>

<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.social.Social"%><html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Visor de Imagenes</title>
<meta name="GUI para aplicación is2011" content="">
<meta name="Grupo 4 - ¿nombre?" content="">

<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

<link href="/css/bootstrap-2.1.1.css" rel="stylesheet">
<link href="/images/favicon.gif" rel="icon" type="image/gif">

<style type="text/css">
body {
	padding-top: 60px;
}

.show-grid [class *="span"] {
	text-align: left;
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
                <li><a href="/secure/imageUpload.jsp"><i class="icon-upload"></i>Subir imagen</a></li>
                <li class="divider"></li>
                <li class="nav-header">Visualizar por...</li>
                <li><a href="/secure/albums.jsp"><i class="icon-search"></i>Album</a></li>
                <li><a href="/secure/search.jsp"><i class="icon-search"></i>Buscar</a></li>
              </ul>
            </li>
          </ul>
          <div class="pull-right">
            <ul class="nav">
               <li class="dropdown" >
                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">${usuarioLogeado.userName}<b class="caret"></b></a>
                 <ul class="dropdown-menu" >
                   <li><a href="/userprofile"><i class="icon-user"></i>Editar perfil</a></li>
                   <li><a href="/editPerson?action=browsePerson"><i class="icon-user"></i>Personas</a></li>
                   <li class="divider"></li>
                   <li><a href="/logout"><i class="icon-remove"></i>Cerrar sesión</a></li>
                 </ul>
               </li>
            </ul>
          </div>
      </div>   <!-- end container -->
    </div>	 <!--end topbar-inner -->
  </div> <!--end topbr -->
	<div class="container">
			<ul class="thumbnails">
				<li class="thumbnail span12">
					<img src="/image?pictureid=<%=WebUtils.getParameter(request, "pictureid")%>&version=O">
				</li>
				<li class="thumbnail span12" ><%=Social.addLinks(WebUtils.getCompleteUrlForPicture(request,WebUtils.getParameter(request, "pictureid")), "Picture")%></li>
				<li class="thumbnail span12" >
					<button type="button" class="btn btn-link btn-primary" onclick="location.href='/setProfileImage?pictureid=<%=WebUtils.getParameter(request, "pictureid")%>'">establecer como foto del perfil</button>
				</li>
				
				
				<li class="thumbnail span12" ><form class="form-search" method="post" action="/selectaperson">
						<span class=span10>Seleccionar una persona para asociar la imagen</span>
						<input id="pDenom" name="pDenom" type="text" class="input-medium search-query ajax-typeahead" data-provide="typeahead" >
						<button type="button" class="btn" onClick="selectPerson()">Buscar</button>
						<input  id="pId"   name="pId"   type="text"  class="span1">
						<input  id="pType" name="pType" type="text"  class="span1">
						<input  id="picId" name="picId" type="text"  class="span1" value='<%=WebUtils.getParameter(request, "pictureid")%>' >
						<input  id="pName" name="pName" type="text" >
						<input  id="rDescr" name="rDescr" type="text" placeholder="ingrese una descripcion">
						<button type="button" class="btn btn-primary" onclick="submitDefaultImage()">Aceptar</button>
	 			</form></li>				
			</ul>
	</div>

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="/js/bootstrap-2.1.1.js"></script>
	<script src="/js/typeahead.js"></script>

   <script>
    $('#pDenom').typeahead({
			ajax: {
                url:"/pnamelist",
                method: 'get',
                triggerLength: 1,
            }
    });
    </script>

	<script>
	function submitDefaultImage(){
		var target = "/selectaperson";
		var pId = document.getElementById("pId").value;
		var pType = document.getElementById("pType").value;
		var imageId = document.getElementById("picId").value;
		

		$.ajax({
			  url: target,
	          type: 'post',
	            data: {
	            	action: "setdefaultimage",
	            	personId:   pId,
	            	personType: pType,
	            	imageId:    imageId
	           	},
              dataType: 'text',
			  success: function(data){
				  	//alert(data);
			  }
			}).done(function(data) { 
			});	

	}
    </script>
	
	<script>
	function selectPerson(){
		var target = "/selectaperson";
		var person = document.getElementById("pDenom").value;

		$.ajax({
			  url: target,
	          type: 'get',
	            data: { 
	            	action: "fetchPerson",
	            	qperson: person
	            },
              dataType: 'text',
			  success: function(jsonString){
				  	//alert(jsonString);
				  	var values = jQuery.parseJSON( jsonString );
					document.getElementById("pType").value = values['personType'];
					document.getElementById("pName").value = values['personName'];
					document.getElementById("pId").value   = values['personId'];

			  }
			}).done(function(data) { 
			});	
		
	}
	</script>

</body>
</html>
