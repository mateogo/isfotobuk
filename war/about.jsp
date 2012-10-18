<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.session.Session"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="ar.kennedy.is2011.db.entities.Usuario"%>
<%@page import="ar.kennedy.is2011.models.SearchPicturesModel"%>
<%@page import="ar.kennedy.is2011.db.entities.AlbumEy"%>
<%@page import="java.util.Set"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.List"%>
<%@page import="ar.kennedy.is2011.db.entities.PictureEy"%>
<%@page import="java.util.Iterator"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Login</title>
<meta name="GUI para aplicaciÃ³n is2011" content="">
<meta name="Grupo 4 - Â¿nombre?" content="">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="/js/prettify.js"></script>
<script src="js/bootstrap-modal.js"></script>
<link href="css/bootstrap.css" rel="stylesheet">
<style type="text/css">

/* Override some defaults */
html,body {
	background-color: #eee;
}

body {
	padding-top: 40px;
	/* 40px to make the container go all the way to the bottom of the topbar */
}

.container>footer p {
	text-align: center; /* center align it with the container */
}

.container {
	/* downsize our container to make the content feel a bit tighter and more cohesive.
				 * NOTE: this removes two full columns from the grid, meaning you only go to 14
				 * columns and not 16.
				 */
	width: 820px;
}
/* The white background content wrapper */
.content {
	background-color: #fff;
	padding: 20px;
	margin: 0 -20px;
	/* negative indent the amount of the padding to maintain the grid system */
	-webkit-border-radius: 0 0 6px 6px;
	-moz-border-radius: 0 0 6px 6px;
	border-radius: 0 0 6px 6px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .15);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .15);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .15);
}
/* Page header tweaks */
.page-header {
	background-color: #f5f5f5;
	padding: 20px 20px 10px;
	margin: -20px -20px 20px;
}
/* Give a quick and non-cross-browser friendly divider */
.content .span10 {
	margin-left: 0;
	padding-left: 19px;
	border-right: 1px solid #eee;
}

.topbar .btn {
	border: 0;
}
</style>
</head>
<body>
	<!-- Barra principal -->
	<!-- jpd / 15-10-2012 / llamada al jsp que resuelve la topbar -->
	<jsp:include page="/secure/topbar.jsp?bar=about" flush="true" />

	<!-- Contenedor -->
	<div class="container">
		<div class="content">
			<div class="page-header">
				<h1>
					Investigaci&oacute;n de Sistemas <small>DiseÃ±o y
						creaci&oacute;n de aplicaci&oacute;n en Google Apps </small>
				</h1>
			</div>
			<!-- /page-header -->
			<div class="row">
				<div class="span10">
					<h2>Grupos</h2>
					<div class="row">
						<div class="span5">
							<h3>Grupo 1</h3>
							<ul>
								<li>Alumno 1</li>
								<li>Alumno 1</li>
								<li>Alumno 1</li>
								<li>Alumno 1</li>
							</ul>
							<h3>Grupo 3</h3>
							<ul>
								<li>Alumno 1</li>
								<li>Alumno 1</li>
								<li>Alumno 1</li>
								<li>Alumno 1</li>
							</ul>
						</div>
						<div class="span5">
							<h3>Grupo 2</h3>
							<ul>
								<li>Alumno 1</li>
								<li>Alumno 1</li>
								<li>Alumno 1</li>
								<li>Alumno 1</li>
							</ul>
							<h3>Grupo 4</h3>
							<ul>
								<li>Alumno 1</li>
								<li>Alumno 1</li>
								<li>Alumno 1</li>
								<li>Alumno 1</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="span4">
					<h3>Tecnolog&iacute;as</h3>
					<ul>
						<li>Ajax</li>
						<li>CSS</li>
						<li>Javascript</li>
						<li>Google AppEngine</li>
						<li>...</li>
					</ul>
				</div>
			</div>
			<!-- row -->
		</div>
		
		<!-- jpd / 15-10-2012 / llamada al jsp que resuelve el footer -->
		<jsp:include page="/secure/footer.jsp" flush="true" />

	</div>
</body>
</html>
