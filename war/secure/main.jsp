<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.entities.User"%>
<%@page import="ar.kennedy.is2011.db.entities.PictureEy"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.models.SearchPicturesModel"%>
<%@page import="ar.kennedy.is2011.views.UserHomeView"%>

<%
	UserHomeView userView = new UserHomeView(request);
	SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
	User user = (User) SessionManager.getCurrentUser(request);
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Fotobuk ::Inicio::</title>
	<meta name="Home del usuario una vez logueado" content="">
	<meta name="fotobuk uk" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">

	<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

	<link href="/css/bootstrap-2.1.1.css" rel="stylesheet">
	<link href="/images/favicon.gif" rel="icon" type="image/gif">

	<style type="text/css">
		body {
			padding-top: 60px;
			padding-bottom: 40px;
		}
	</style>
	
</head>
<body>


<!--Header es un TAG NUEVO de HTML5
	* The <header> tag specifies a header for a document or section.
	* The <header> element should be used as a container for introductory content or set of navigational links.
	* You can have several <header> elements in one document. 
-->
  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">	
        <a class="brand" href="#">Fotobuk</a>
          <ul class="nav">
            <li class="active"><a href="#">Inicio</a></li>
            
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
		<div class="hero-unit row" >
   		<div class="span12">
			<div class="row">
   			<div class="span2 thumbnail">
			<%
				if(userView.userHaveImages()) {
			%>
				<a href="/secure/pictureView.jsp?pictureid=<%= userView.getProfileImage() %>">
				<img
					src="/image?pictureid=<%= userView.getProfileImage() %>&version=I"
					height="280" alt="Ultima foto"></a>
			<%
				} else {
			%>
				<a href="#"> <img class="thumbnail"
					src="http://placehold.it/150x150" alt="no image available">
				</a>
			<%
				}
			%>
			</div>
			<div class="span9">
				<h1>
			<%
					//out.print(userView.getProfileImage());
					out.print(userView.getUserDisplayName());
			%>
				</h1>
			</div>
			</div>
		</div>
		</div>
		
	<ul class="thumbnails">
		<%
			PictureEy[] pics = userView.getPicBbyPage();
			String picId;
			if(pics !=null){
			for(PictureEy picture: pics){
				picId=picture.getPictureId();
		%>
   		<li class="span11 thumbnail">
   				<div class="span3 offset2">
   					<div class="row">
					<img src="/image?pictureid=<%= picId %>&version=I"
						alt="" height=120 width=160 onclick="location.href='/secure/pictureView.jsp?pictureid=<%= picId %>'">
					</div>
  					<div class="row">
					<span class="label label-info"><%= picture.getPictureName() %></span>
					<a class="label label-info" href="/user/<%= picture.getUsername() %>/<%= picture.getAlbumId() %>"><%= picture.getAlbumId() %></a>
					<a class="label label-info" href="/user/<%= picture.getUsername() %>"><%= picture.getUsername() %></a>
					</div>
				</div>
 					<div class="btn-group-vertical">
						<!-- href="/secure/modalPictureView.jsp?pictureid=<%= picId %>" -->
 						<button type="button" class="btn btn-link btn-large" onclick='setId("<%= picId %>")' data-toggle="modal" data-target="#picViewer" >ver</button>
						<button type="button" class="btn btn-link btn-large" onclick="location.href='/secure/imageUpload.jsp?pictureid=<%= picId %>'">editar</button>
						<button type="button" class="btn btn-link btn-large" onclick="location.href='/upload?action=delete&pictureid=<%= picId %>'">eliminar</button>
					</div>
		</li>
		<% 
			}
			}
		%>
	</ul>
			<%
					if(true) {
			%>
			<div class="pagination">
				<ul>
					<!-- =========== Boton Previous ===============================  -->
					<%
							if(userView.getActualPage()==1 || userView.getPages()==0){
						%>
					<li class="prev disabled"><a href="main.jsp?page=<%= 1%>">&larr;
							Previous</a></li>
					<%
							}else{
						%>
					<li class="prev active"><a
						href="main.jsp?page=<%= userView.getActualPage()-1%>">&larr;
							Previous</a></li>
					<%
							}//end-if
						%>
					<!-- =========== Botones 1,2,3 ===============================  -->
					<%
						userView.setNavButtons();
						for(int but=userView.getFromButton();but<=userView.getToButton();but++) {
						%>
					<%
							if(but==userView.getActualPage() ) {
						%>
					<li class="disabled"><a href="main.jsp?page=<%= but  %>"><%= but %></a>
					</li>
					<%
							}else{
						%>
					<li class="active"><a href="main.jsp?page=<%= but  %>"><%= but %></a>
					</li>
					<%
							}
						%>
					<%
						}//endfor
						%>
					<!-- =========== Boton next ===============================  -->
					<%
							if(userView.getActualPage() == userView.getPages()){
						%>
					<li class="next disabled"><a
						href="main.jsp?page=<%=userView.getPages()  %>">Next &raquo;</a></li>
					<%
							}else{
						%>
					<li class="next"><a
						href="main.jsp?page=<%=userView.getActualPage()+1  %>">Next
							&raquo;</a></li>
					<%
							}
						%>
				</ul>
			</div>
			<%
					}//end-if bloque pagination
				%>


			<!-- jpd / 15-10-2012 / llamada al jsp que resuelve el footer -->
			<jsp:include page="footer.jsp" flush="true" />

	</div>

<!-- Modal viewer -->
<div class="modal hide fade in" id="picViewer"  data-keyboard=false data-backdrop=false tabindex="-1" role="dialog" aria-labelledby="picViewerLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
		<h3>viewer</h3>
	</div>
	
	<div id="callback123" class="modal-body">
	</div>
	<div class="modal-footer">
		<button class="close" data-dismiss="modal" aria-hidden="true">Close</button>
	</div>
</div>

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
	$('#picViewer').on('show', function () {
		//var el = $(this).attr('id');
		//var newHTML="<p>Alo! el:"+""+el+""+"  id:"+idProperty+"+"</p>";
		//var newHTML="<p>Alo!!!!"+idProperty+"</p>";
		
		var newHTML = "<img src='/image?pictureid="+idProperty+"&version=O'>";		
		document.getElementById('callback123').innerHTML = newHTML;
        });
	
    </script>
    <script>
	function setId(id){
		idProperty=id
	}
	var idProperty="id de la foto a renderizar en la ventana modal";
    </script>
    
    
	
</body>
</html>
