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

	<div class="container">
		<div class="hero-unit row" >
   		<div class="span12">
			<div class="row">
   			<div class="span2 thumbnail">
			<%
				if(userView.userHaveImages()) {
			%>
				<a href="/secure/pictureView.jsp?pictureid=<%= userView.getLastImageId() %>">
				<img
					src="/image?pictureid=<%= userView.getLastImageId() %> &version=I"
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
					out.print(userView.getUserDisplayName());
			%>
				</h1>
			</div>
			</div>
		</div>
		</div>
		
	<ul class="thumbnails">
		<%
			String[] pics = userView.getPicBbyPage();
			if(pics !=null){
			for(String picId: pics){
		%>
   		<li class="span11 thumbnail">
   				<div class="span3 offset2">
					<img src="/image?pictureid=<%= picId %>&version=H"
						alt="" height=120 width=160 onclick="location.href='/secure/pictureView.jsp?pictureid=<%= picId %>'">
				</div>
 					<div class="btn-group-vertical">
						<button type="button" class="btn btn-link btn-large" onclick="location.href='/secure/pictureView.jsp?pictureid=<%= picId %>'">ver</button>
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


    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="/js/bootstrap-2.1.1.js"></script>
    <script src="/js/bootstrap-dropdown.js"></script>
    <script src="/js/bootstrap-button.js"></script>
    <script src="/js/bootstrap-tooltip.js"></script>
    <script>
 	   $('.dropdown-toggle').dropdown()
	</script>
    
	
</body>
</html>
