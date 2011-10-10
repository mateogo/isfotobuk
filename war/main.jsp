<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.entities.UserEy"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.models.SearchModel"%>
<%
	SearchModel searchModel = new SearchModel();
	UserEy user = (UserEy) SessionManager.get(request, WebUtils.getSessionIdentificator(request)).getElement("user");
%>
<!DOCTYPE html>

<%@page import="ar.kennedy.is2011.db.entities.PictureEy"%><html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Principal</title>
		<meta name="GUI para aplicaciÃ³n is2011" content="">
		<meta name="Grupo 4 - Â¿nombre?" content="">
		<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
		<script src="assets/js/google-code-prettify/prettify.js"></script>
		<script src="js/bootstrap-modal.js"></script>
		<link href="css/bootstrap.css" rel="stylesheet">
		<link href="css/docs.css" rel="stylesheet">
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
		<div class="topbar">
			<div class="topbar-inner">
				<div class="container">
					<a class="brand" href="#">Trabajo Pr&aacute;ctico</a>
					<ul class="nav">
						<li class="active">
							<a href="#">Inicio</a>
						</li>
						<li>
							<a href="#albumes">Albumes</a>
						</li>
						<li>
							<a href="#buscar">Buscar</a>
						</li>
						<li>
							<a href="data.html">Datos</a>
						</li>
						<li>
							<a href="imageUpload.jsp">Cargar imagen</a>
						</li>
					</ul>
					<p class="pull-right">
						Logueado como <a href="#"><%= user.getUsername() %></a>
					</p>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="content">
				<!-- Main hero unit for a primary marketing message or call to action -->
				<div class="hero-unit">
					<ul class="media-grid">
						<li>
							<div class="span4">
								<a href="#"> <img class="thumbnail" src="http://placehold.it/150x150" alt=""> </a>
							</div>
							<div class="span10">
								<h1>Nombre Apellido</h1>
							</div>
						</li>
					</ul>
				</div>
				<h2>&Uacute;ltimas fotos</h2>
				<%
					for(PictureEy picture : searchModel.getPicturesByUsername(user.getUsername())) {
						
				%>
				<div class="well">
					<ul class="media-grid">
						<li>
							<div class="row">
								<div class="span3">
									<a href="#"> <img class="thumbnail" src="image?id=<%= picture.getPictureId() %>" alt="" width="90" height="90"> </a>
								</div>
								<div class="span12">
									<p>
										Metadata de la imagen
									</p>
									<button class="btn danger">
										Eliminar
									</button>
									<button class="btn info">
										Asignar
									</button>
									<button class="btn primary">
										Editar
									</button>
									<a href="data.html" class="btn primary">
										Editar
									</a>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<%
					}
				%>
				<div class="well">
					<ul class="media-grid">
						<li>
							<div class="row">
								<div class="span3">
									<a href="#"> <img class="thumbnail" src="http://placehold.it/90x90" alt=""> </a>
								</div>
								<div class="span12">
									<p></p>
									<button class="btn danger">
										Eliminar
									</button>
									<button class="btn info">
										Asignar
									</button>
									<div id="modal-edit" class="modal hide fade">
										<div class="modal-header">
											<a href="#" class="close">&times;</a>
											<h3>EdiciÃ³n</h3>
										</div>
										<div class="modal-body">
											<p>
												Â¿Campos para modificar?
											</p>
										</div>
										<div class="modal-footer">
											<a href="#" class="btn primary">Aceptar</a>
											<a href="#" class="btn secondary">Cancelar</a>
										</div>
									</div>
									<button data-controls-modal="modal-edit"
									data-backdrop="true" data-keyboard="true" class="btn primary">
										Editar
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="pagination">
					<ul>
						<li class="prev disabled">
							<a href="#">&larr; Previous</a>
						</li>
						<li class="active">
							<a href="#">1</a>
						</li>
						<li>
							<a href="#">2</a>
						</li>
						<li>
							<a href="#">3</a>
						</li>
						<li>
							<a href="#">4</a>
						</li>
						<li>
							<a href="#">5</a>
						</li>
						<li class="next">
							<a href="#">Next &raquo;</a>
						</li>
					</ul>
				</div>
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
		</div>
	</body>
</html>