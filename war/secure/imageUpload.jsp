<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.constants.Constants"%>
<%@page import="ar.kennedy.is2011.session.Session"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.db.dao.AbstractDao"%>
<%@page import="ar.kennedy.is2011.db.entities.Usuario"%>
<%@page import="ar.kennedy.is2011.db.entities.PictureEy"%>
<%@page import="ar.kennedy.is2011.db.entities.AlbumEy"%>
<%@page import="ar.kennedy.is2011.models.SearchPicturesModel"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Set"%>
<%!
	private String getValue(Object object) {
		return object != null ? object.toString() : "";
	}
	private String getAllAlbumsList(List<AlbumEy> albums) {
		StringBuilder list = new StringBuilder();
		for(int i = 0; i < albums.size(); i++) {	
			if(i == (albums.size() - 1)) {
				list.append("'").append(albums.get(i).getAlbumId()).append("'");
			
			} else {
				list.append("'").append(albums.get(i).getAlbumId()).append("', ");
			}
		}
		return list.toString();
	}
	
	private String getAllAlbumsToBeDisplayedByUser(Set<AlbumEy> albums) {
		StringBuilder splitAlbums = new StringBuilder();
		int i = 0;
		for(AlbumEy album : albums) {
			if(i == (albums.size() - 1)) {
				splitAlbums.append("'").append(album.getAlbumId()).append("'");
				
			} else {
				splitAlbums.append("'").append(album.getAlbumId()).append("'").append(", ");
			}
			i++;
		}
		return splitAlbums.toString();
	}
%>
<%
	Session userSession = SessionManager.get(request, WebUtils.getSessionIdentificator(request));
	Map<String, Object> errors = userSession.contains("errors") ? 
			((Map<String, Object>) userSession.getElement("errors")).containsKey("form_errors") ? 
					(Map<String, Object>) ((Map<String, Object>) userSession.getElement("errors")).get("form_errors") : 
					new HashMap<String, Object>() : 
			new HashMap<String, Object>();
	SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
	Usuario user = (Usuario) userSession.getElement("user");
	PictureEy picture = null;

	String pictureId = WebUtils.getParameter(request, "pictureid");
	if (pictureId != null) {
		AbstractDao<PictureEy> pictureDao = new AbstractDao<PictureEy>();

		picture = pictureDao.findById(PictureEy.class, pictureId);

	} else if (userSession.contains("picture")) {
		picture = (PictureEy) userSession.getElement("picture");

	} else {
		picture = new PictureEy();
	}

	if (!"t".equals(WebUtils.getParameter(request, "e"))) {
		errors = new HashMap<String, Object>();
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Sube tus fotos</title>
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

<body onload="cargarAlbums()">

		<!-- jpd / 15-10-2012 / llamada al jsp que resuelve la barra de navegacion -->
		<jsp:include page="topbar.jsp?bar=editarCuentaUsuario" flush="true" />


	<div class="container">
		<form class="form-horizontal" method="post"
				action="/upload?action=<%=pictureId == null ? "add" : "update&pictureid=" + pictureId%>"
				enctype="multipart/form-data">
	
			<div class="control-group">
				<label class="control-label" for="picture_file">Seleccionar de archivo:</label>
				<div class="controls">
					<input  type="file" id="picture_file" class="input-file" name="picture_file"/>
				</div>
				<label class="control-label" for="url">o indique una Url:</label>
				<div class="controls">
					<input type="text" id="url" name="url" class="span5"
					    value="<%=getValue(picture.getUrl())%>" />
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="picture_name">Nombre de la imagen:</label>
				<div class="controls">
					<input id="picture_name" name="picture_name" type="text"
						value="<%=getValue(picture.getPictureName())%>" />
				</div>
			</div>
					<div class="control-group">
						<div class="controls">
						<span class="validator" style="display: <%=errors.containsKey("picture_name") ? "block" : "none"%>"></span>
						<span class="required"><%=errors.containsKey("picture_name") ? errors.get("picture_name") : ""%> </span>
						</div>
					</div>
					
			<div class="control-group">
				<label class="control-label" for="mediumSelect">Seleccion del album:</label>
				<div class="controls">
					<select id="album_id" name="album_id" onchange="llamarPopUp()">
					<%
						if (StringUtils.isNotBlank(getValue(picture.getAlbumId()))) {
					%>
							<option value="<%=getValue(picture.getAlbumId())%>"><%=getValue(picture.getAlbumId())%></option>
							<option value="Elegir">Elegir</option>
					<%
						} else {
					%>
							<option value="Elegir">Elegir</option>
					<%
						}
						Set<AlbumEy> albums = searchPicturesModel.getAlbumsToBeDisplayedByUser(user.getNombreUsr());
						for (AlbumEy album : albums) {
					%>
							<option value="<%=album.getAlbumId()%>"><%=album.getAlbumId()%></option>
					<%
						}
					%>
						<option value="Nuevo">Crear nuevo album...</option>
					</select>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="tags">Tags:</label>
				<div class="controls">
					<input id="tags" name="tags" type="text"
						value="<%=getValue(picture.getTags())%>" />
				</div>
			</div>
	
			<div class="form-actions">
					<button type="submit" class="btn btn-primary">Enviar</button>
			</div>
			
				<div class="control-group">
				<span class="alert-message error" style="display: <%=errors.containsKey("add_url_or_file") ? "block" : "none"%>">
					<span class="required"><%=errors.containsKey("add_url_or_file") ? errors.get("add_url_or_file") : ""%></span>
				</span> 
				<span class="alert-message error"
					style="display: <%=errors.containsKey("album_id") ? "block" : "none"%>">
					<span class="required"><%=errors.containsKey("album_id") ? errors.get("album_id") :""%></span>
				</span>
				<span class="alert-message error"
						style="display: <%=errors.containsKey("tags") ? "block" : "none"%>">
					<span class="required"><%=errors.containsKey("tags") ? errors.get("tags") : ""%></span>
				</span>
				<span class="alert-message error"
						style="display: <%=errors.containsKey("mandatory_parameters") ? "block" : "none"%>">
					<span class="required"><%=errors.containsKey("mandatory_parameters") ? errors.get("mandatory_parameters") : ""%></span>
				</span>
				</div>
				

<script> 
	function cargarAlbums() {
		var albumsToBeDisplayedByUser = [<%= getAllAlbumsToBeDisplayedByUser(searchPicturesModel.getAlbumsToBeDisplayedByUser(user.getNombreUsr())) %>]
		var selectedOption = document.getElementById('album_id').options[document.getElementById('album_id').selectedIndex].value;
		
		var combo = document.getElementById('album_id');
		combo.options.length = 0;

		//Agrero la opcion seleccionada
		combo.options.add(new Option(selectedOption, selectedOption));

		//Agrero la elegir si no es la opcion seleccionada
		if(selectedOption != 'Elegir') {
			combo.options.add(new Option('Elegir', 'Elegir'));
		}
		
		for (i=0; i < albumsToBeDisplayedByUser.length; i++)
		{
			//Añadir los elementos de la lista
		 	combo.options.add(new Option(albumsToBeDisplayedByUser[i], albumsToBeDisplayedByUser[i]));
		}

		//Agrero la opcion Crear nuevo album
    	combo.options.add(new Option('Crear nuevo album...', 'nuevo'));
	}

	function llamarPopUp() {
		 var valor = document.getElementById('album_id').options[document.getElementById('album_id').selectedIndex].value;

		 if (valor == 'nuevo') {
			 var name = prompt("Nombre del album","");
			 var visibility = prompt("Visibilidad (public/private)","");

			 if((name != null && name != "") && (visibility == 'public' || visibility == 'private')) {
				 if(!existeAlbum(name)) {
					//Vuelvo a cargar combo con el contenido de la base de datos
					cargarAlbums();
					//agrego al combo mi nuevo nombre de album
					var option = new Option(name, name + ';' + visibility);
					var combo = document.getElementById('album_id');
     		      	combo.options.add(option);
					combo.options[combo.length - 1].selected =  "1";

				} else {
					alert('Ya existe el album ' + name);
					llamarPopUp();
				 }

			 } else {
				 alert('Datos incorrectos, verifique sus entradas');
				 llamarPopUp();
			 } 
		 }
	}

	function existeAlbum(name) {
		//recorro combo en busca de este valor
		var combo = document.getElementById('album_id');
		var allAlbums = [<%= getAllAlbumsList(searchPicturesModel.getAllAlbums()) %>];
		var exist = false;
		
		for (i=0;i<combo.length;i++) {
			if(name == combo.options[i].value) { 
				exist = true;
			}
		}

		for (i=0;i<allAlbums.length;i++) {
			if(name == allAlbums[i].value) { 
				exist = true;
			}
		}

		return exist;
	}
</script>
			</form>
	</div>


    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="/js/bootstrap-2.1.1.js"></script>
</body>
</html>
