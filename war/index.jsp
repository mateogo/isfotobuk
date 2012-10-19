<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Fotobuk - bienvenido</title>
<meta name="GUI para aplicación is2011" content="">
<meta name="Investigacion de sistemas" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link rel="icon" href="/images/favicon.gif" type="image/gif">

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
	<jsp:include page="/secure/topbar.jsp?bar=index" flush="true" />
	
	<div class="container">
		<div class="hero-unit">
			<h1>FotobUK</h1>
			<!-- nombre feo si los hay... -->
			<p>Aplicaci&oacute;n creada en Google AppEngine utilizando varias tecnolog&iacute;as</p>
			<p>
				<a class="btn primary large" href="/about.jsp">Acerca de. . . &raquo;</a>
			</p>
		</div>
	</div>
</body>
</html>
