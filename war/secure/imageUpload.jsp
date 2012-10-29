<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.session.Session"%>
<%@page import="ar.kennedy.is2011.db.entities.Usuario"%>
<%@page import="ar.kennedy.is2011.db.entities.PictureEy"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.views.ImageUploadView"%>
<%@page import="java.util.HashMap"%>

<%
	ImageUploadView imageUpload = new ImageUploadView(request);
	Usuario user = imageUpload.getUser();
	Session userSession = imageUpload.getUserSession();
	String pictureId = WebUtils.getParameter(request, "pictureid");
	PictureEy picture = imageUpload.getPictureToEdit();
	if (!"t".equals(WebUtils.getParameter(request, "e"))) {
		imageUpload.setErrors(new HashMap<String, Object>());	
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
<script> 
	function cargarAlbums() {
		var albumSelect = populateAlbumsComboBox();
		var actualAlbum = "<%= imageUpload.objectToString(picture.getAlbumId()) %>"
	
		var actualIndex = albumIndex(albumSelect,actualAlbum);
		if(actualIndex!=-1) albumSelect.options[actualIndex].selected =  true;
		else albumSelect.options[0].selected =  true;
		
	}
	function populateAlbumsComboBox() {
		var albumsToBeDisplayedByUser = [ <%= imageUpload.getAlbumsByUserCommaSeparated() %>];

		var combo = document.getElementById('album_id');
		//var selectedOption = combo.options[combo.selectedIndex].value;
		//reset	
		combo.options.length = 0;
		combo.options.add(new Option('Elegir', 'Elegir'));
		
		for (i=0; i < albumsToBeDisplayedByUser.length; i++){
			//Aadir los elementos de la list
		 	combo.options.add(new Option(albumsToBeDisplayedByUser[i], albumsToBeDisplayedByUser[i]));
		}
		return combo;
	}
	function albumIndex(combo,name) {
		//recorro combo en busca de este valor
		var comboIndex = -1;

		for (i=0;i<combo.length;i++) {
			if(name == combo.options[i].value) return i;
		}
		return comboIndex;
	}
</script> 
	
</head>

<body onload=cargarAlbums()>

	<!-- jpd / 15-10-2012 / llamada al jsp que resuelve la barra de navegacion -->
	<jsp:include page="topbar.jsp" flush="true" />

	<div class="container">	
		<form name="uploadimage" class="form-horizontal" method="post"
				action="/upload?action=<%=pictureId == null ? "add" : "update&pictureid=" + pictureId%>"
				enctype="multipart/form-data">
	
			<div class="control-group">
				<label class="control-label" for="picture_file">Seleccionar de archivo:</label>
				<div class="controls">
					<input type="file" id="picture_file" name="picture_file" class="input-file"/>
				</div>
				<label class="control-label" for="url">o indique una Url:</label>
				<div class="controls">
					<input type="text" id="url" name="url" class="span5"
					    value="<%=imageUpload.objectToString(picture.getUrl())%>" />
						<span class="help-block">Indique solo una de las opciones precedentes. O busca la imagen de archivo o la referencia a través de una URL</span>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="picture_name">Nombre de la imagen:</label>
				<div class="controls">
					<input type="text" id="picture_name" name="picture_name"
						value="<%=imageUpload.objectToString(picture.getPictureName())%>" />
				</div>
			</div>
					<div class="control-group">
						<div class="controls">
						<span class="validator" style="display: <%=imageUpload.getErrors().containsKey("picture_name") ? "block" : "none"%>"></span>
						<span class="required"><%=imageUpload.getErrors().containsKey("picture_name") ? imageUpload.getErrors().get("picture_name") : ""%> </span>
						</div>
					</div>
					
			<div class="control-group">
				<label class="control-label" for="album_id">Seleccione un album:</label>
				<div class="controls">
					<select id="album_id" name="album_id">
							<option value="Elegir">Elegir</option>
					</select>
 					<button type="button" class="btn btn-link btn-large" name="newalbum" data-toggle="modal" data-target="#nuevoAlbum" >nuevo album</button>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="tags">Tags:</label>
				<div class="controls">
					<input id="tags" name="tags" type="text"
						value="<%=imageUpload.objectToString(picture.getTags())%>" />
				</div>
			</div>
	
			
				<div class="control-group">
				<span class="alert-message error" style="display: <%=imageUpload.getErrors().containsKey("add_url_or_file") ? "block" : "none"%>">
					<span class="required"><%=imageUpload.getErrors().containsKey("add_url_or_file") ? imageUpload.getErrors().get("add_url_or_file") : ""%></span>
				</span> 
				<span class="alert-message error"
					style="display: <%=imageUpload.getErrors().containsKey("album_id") ? "block" : "none"%>">
					<span class="required"><%=imageUpload.getErrors().containsKey("album_id") ? imageUpload.getErrors().get("album_id") :""%></span>
				</span>
				<span class="alert-message error"
						style="display: <%=imageUpload.getErrors().containsKey("tags") ? "block" : "none"%>">
					<span class="required"><%=imageUpload.getErrors().containsKey("tags") ? imageUpload.getErrors().get("tags") : ""%></span>
				</span>
				<span class="alert-message error"
						style="display: <%=imageUpload.getErrors().containsKey("mandatory_parameters") ? "block" : "none"%>">
					<span class="required"><%=imageUpload.getErrors().containsKey("mandatory_parameters") ? imageUpload.getErrors().get("mandatory_parameters") : ""%></span>
				</span>
				</div>

				
		<!-- Modal viewer -->
		<div class="modal hide fade in" id="nuevoAlbum"  data-keyboard=false data-backdrop=false tabindex="-1" role="dialog" aria-labelledby="nuevoAlbumLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
				<h3>nuevo album</h3>
			</div>
			
			<div id="callback123" class="modal-body">
				<div class="control-group">
					<label class="control-label" for="newalbumid">Identificador:</label>
					<div class="controls">
						<input type="text" id="newalbumid" name="newalbumid" />
						<span class="help-block">Ingrese un identificador (o descripción corta) sin acentos, espacios o enies</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="newalbumname">Nombre del album:</label>
					<div class="controls">
						<input type="text" id="newalbumname" name="newalbumname" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="newalbumscope">Privacidad:</label>
					<div class="controls">
						<select id="newalbumscope" name="newalbumscope">
							<option                    value="publico">Publico</option>
							<option class="text-error" value="privado">Privado</option>
						</select>
						<span class="help-block">Un album <strong>Publico</strong> puede ser visto por otros usuarios.</span>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="close" data-dismiss="modal" aria-hidden="true">Cerrar</button>
			</div>
		</div>
			<div class="form-actions">
					<button type="submit" class="btn btn-primary">Enviar</button>
			</div>

	</form>
</div>


    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="/js/bootstrap-2.1.1.js"></script>
    <script>
	$('#nuevoAlbum').on('show', function () {
		//algo para hcaer...
        });
	
    </script>
    <script>
	$('#nuevoAlbum').on('hide', function () {
	    var albumSelect = populateAlbumsComboBox();
	    var nuevoAlbum = document.getElementById('newalbumid');
		//Agrero la opcion Crear nuevo album
    	albumSelect.options.add(new Option(nuevoAlbum.value, nuevoAlbum.value));
		albumSelect.options[albumSelect.length - 1].selected =  true;
	});
    </script>

</body>
</html>
