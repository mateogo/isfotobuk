<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.views.PublicProfileView"%>
<%
	PublicProfileView miVista = new PublicProfileView(request);
%>					


<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>Perfil publico</title>
		<meta name="GUI para aplicación is2011" content="">
		<meta name="Grupo 4 - ¿nombre?" content="">
		<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
		<link href="/images/favicon.gif" rel="icon" type="image/gif">
		<link href="/css/bootstrap-2.1.1.css" rel="stylesheet">
		<style type="text/css">
			/* Override some defaults */
			body {
				padding-top: 40px;
				/* 40px to make the container go all the way to the bottom of the topbar */
			}

		</style>
	</head>
	
	<body>
		<!-- Barra principal -->
		<div class="navbar navbar-inverse navbar-fixed-top">
		    <div class="navbar-inner">
		      <div class="container">	
		            <%
		            	out.print(miVista.getHTMLContent_TopBarFormat());
		            %>
		      </div>   <!-- end container -->
		    </div>	 <!--end topbar-inner -->
	  	</div> <!--end topbar -->
		
		<!-- Contenedor -->
		<div class="container">
				<div class="hero-unit">
					<div class="row">
					<div class="span3">
						<%
							out.print(miVista.getHTMLContent_FotoDePerfil());				
						%>
					</div>
					<div class="span6">		
							<%
								out.print(miVista.getHTMLContent_Encabezado());									
							%>					
					</div>
					</div>
				</div>
				<!-- /page-header -->
				
				<div class="row">
					<div class="span4">
					<%
						out.print(miVista.getHTMLContent_Albums());
					%>	
					</div>
				</div>
				<div class="row">
						<%
							out.print(miVista.getHTMLContent_Datos());	
						%>
				</div>
				
				<!-- row -->
			<footer>
				<div id="modal-from-dom" class="modal hide fade">
					<div class="modal-header">
						<a href="#" class="close">&times;</a>
						<h3>Condiciones Legales</h3>
					</div>
					<div class="modal-body">
						<p>
							Etiam porta sem malesuada magna mollis euismod. Integer posuere erat a ante venenatis dapibus posuere velit aliquet. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum. Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit.
						</p>
					</div>
				</div>
				<p>
					<a href="#" data-controls-modal="modal-from-dom"
					data-backdrop="true" data-keyboard="true"> Condiciones Legales </a>
				</p>
			</footer>
		</div>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="/js/bootstrap-2.1.1.js"></script>
	</body>
</html>
