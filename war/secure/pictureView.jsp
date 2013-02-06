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

.thumbnails .control-group {
    border: 1px solid #eee;
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
					<img src="/image?pictureid=<%=WebUtils.getParameter(request, "pictureid")%>&version=O" width=500 height=400>
				</li>
				<li class="thumbnail span12" ><%=Social.addLinks(WebUtils.getCompleteUrlForPicture(request,WebUtils.getParameter(request, "pictureid")), "Picture")%></li>
				
				<li class="thumbnail span12 accordion" id="fototag">
					<div class="accordion-group">
						<span class="accordion-heading">
							<a class="accordion-toggle" data-toggle="collapse" data-parent="#fototag" href="#collapseOne">
							Acciones sobre esta imagen
							</a>
						</span>
						<div id="collapseOne" class="accordion-body collapse">
							<div class="accordion-inner">
								<div class="span12" >
									<button type="button" class="btn btn-link btn-primary" onclick="location.href='/setProfileImage?pictureid=<%=WebUtils.getParameter(request, "pictureid")%>'">establecer como foto para su perfil de usuario</button>
								</div>
							<form class="form-search" method="post" >
							
								<div class="control-group">
									<label class="control-label" for="pDenom" >Seleccione una persona y luego ejecute la acción deseada:</label>
									<div class="controls" >
										<input id="pDenom" name="pDenom" type="text" class="input-medium search-query ajax-typeahead" data-provide="typeahead" >
										<button type="button" class="btn" onClick="selectPerson()">Buscar</button>
										<input  id="pId"   name="pId"   type="hidden"  class="span1" >
										<input  id="pType" name="pType" type="hidden"  class="span1" >
										<input  id="picId" name="picId" type="hidden"  class="span1" value='<%=WebUtils.getParameter(request, "pictureid")%>' >
										<span id="pName" class="input-xlarge uneditable-input"></span>
										
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="altText" >Establezca la imagen como foto-tag de la persona seleccionada:</label>
									<div class="controls" >
										<input  id="altText" name="altText" class="span6" type="text" placeholder="alt-text para la imagen"><button type="button" id="defaultImageBtn" name="defaultImageBtn" class="btn btn-primary" onclick="submitDefaultImage()">Foto-tag</button>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="rDescr" >Relacione a la persona seleccionada con esta foto:</label>
									<div class="controls" >
										<input  id="rDescr" name="rDescr" class="span6" type="text" placeholder="comente la relación"><button type="button" id="addRelationBtn" name="addRelationBtn" class="btn btn-primary" onclick="setRelation()">Relacionar</button>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="defaultImageBtn" >Establezca la imagen como foto-tag para su perfil de usuario:</label>
									<div class="controls" >
										<span id="defaultImageLabel" class="span6 input-xlarge uneditable-input">Al aceptar establecerá esta imagen como foto de su perfil</span><button type="button" id="userProfileBtn" name="userProfileBtn" class="btn btn-primary" onclick="location.href='/setProfileImage?pictureid=<%=WebUtils.getParameter(request, "pictureid")%>'" >Foto de mi perfil</button>
									</div>
								</div>
							
							
							
							</form>
							</div>
						</div>
					</div>
				</li>


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
                triggerLength: 2,
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
	            	if(data=='success') $('#defaultImageBtn').attr( 'class', "btn btn-success" );
	            	else  $('#defaultImageBtn').attr( 'class', "btn btn-danger" );
			  }
			}).done(function(data) { 
				document.getElementById("pId").value   ="";
				document.getElementById("pDenom").value   ="";
				document.getElementById("pType").value ="";
				document.getElementById("pName").innerHTML = "¡foto asignada!" ;
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
					document.getElementById("pName").innerHTML = values['personName'];
					document.getElementById("pId").value   = values['personId'];

			  }
			}).done(function(data) { 
			});	
		
	}
	</script>

	<script>
	function setRelation(){
		var target = "/selectaperson";
		var pId = document.getElementById("pId").value;
		var pType = document.getElementById("pType").value;
		var imageId = document.getElementById("picId").value;
		var descr = document.getElementById("rDescr").value;

		$.ajax({
			  url: target,
	          type: 'post',
	            data: {
	            	action: "addRelationToImage",
	            	personId:   pId,
	            	personType: pType,
	            	imageId:    imageId,
	            	comment:    descr
	           	},
              dataType: 'text',
			  success: function(data){
	            	if(data=='success') $('#addRelationBtn').attr( 'class', "btn btn-success" );
	            	else  $('#addRelationBtn').attr( 'class', "btn btn-danger" );
			  }
			}).done(function(data) {
				document.getElementById("pId").value   ="";
				document.getElementById("pDenom").value   ="";
				document.getElementById("pType").value ="";
				document.getElementById("pName").innerHTML = "¡foto asignada!" ;
				document.getElementById("rDescr").innerHTML = "" ;
			});
	}
    </script>


</body>
</html>
