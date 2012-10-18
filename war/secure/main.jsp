<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.entities.Usuario"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.models.SearchPicturesModel"%>
<%@page import="ar.kennedy.is2011.db.entities.PictureEy"%>
<%@page import="ar.kennedy.is2011.views.UserHomeView"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%!
	private static final Integer DEFAULT_FECTH = 2;
%>

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

<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
<!--script src="/js/prettify.js"></script  -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="/js/bootstrap-modal.js"></script>

<link href="/css/bootstrap.css" rel="stylesheet">
<link href="/css/docs.css" rel="stylesheet">
<link href="/favicon.gif" rel="icon" type="image/gif">

<style type="text/css">
body {
	padding-top: 60px;
}

.show-grid [class*="span"] {
	text-align: left;
}
</style>

</head>
<body>

	<!-- jpd / 15-10-2012 / llamada al jsp que resuelve la barra de navegacion -->
	<jsp:include page="topbar.jsp?bar=main" flush="true" />

	<div class="container">
		<div class="content">
			<!-- Main hero unit for a primary marketing message or call to action -->
			<div class="hero-unit">
				<ul class="media-grid">
					<li>
						<div class="span4">
							<%
									if(userView.userHaveImages()) {
								%>
							<a
								href="/secure/pictureView.jsp?pictureid=<%= userView.getLastImageId() %>"><img
								class="thumbnail"
								src="/image?pictureid=<%= userView.getLastImageId() %>&version=I"
								width="150" height="150" alt=""></a>
							<%
									} else {
								%>
							<a href="#"> <img class="thumbnail"
								src="http://placehold.it/150x150" alt="">
							</a>
							<%
									}
								%>
						</div>
						<div class="span10">
							<h1>
								<%
										out.print(userView.getUserDisplayName());
									%>
							</h1>
						</div>
					</li>
				</ul>
			</div>
			<!-- REVISION MGO  -->
			<h2>&Uacute;ltimas fotos</h2>
			<%
					String[] pics = userView.getPicBbyPage();
					if(pics !=null){
					for(String picId: pics){
				%>
			<div class="well">
				<ul class="media-grid">
					<li>
						<div class="row">
							<div class="span3">
								<a href="/secure/pictureView.jsp?pictureid=<%= picId %>"><img
									class="thumbnail" src="/image?pictureid=<%= picId %>&version=H"
									alt="" width="90" height="90"> </a>
							</div>
							<p>Acciones</p>
							<div class="span12">
								<a href="/secure/imageUpload.jsp?pictureid=<%= picId %>">
									<button class="btn primary">Editar</button>
								</a> <a href="/upload?action=delete&pictureid=<%= picId %>">
									<button class="btn danger">Eliminar</button>
								</a>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<%
					}//end-for
					}//end-if
				%>
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
	</div>
</body>
</html>
