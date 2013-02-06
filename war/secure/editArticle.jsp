<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.dao.ArticleBean"%>
<%@page import="ar.kennedy.is2011.db.entities.EntityRelationHeader"%>


<%
	ArticleBean pbean = (ArticleBean) request.getAttribute("pbean");
	
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Edicion de Articlo </title>
	<meta name="edicion de articulo " content="">
	<meta name="fotobuk uk" content="">
	<meta name="description" content="">
	<meta name="author" content="">

	<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

	<link href="/css/bootstrap-2.1.1.css" rel="stylesheet" type="text/css">
	<link href="/images/favicon.gif" rel="icon" type="image/gif">

	<style type="text/css">
		body {
			padding-top: 60px;
			padding-bottom: 40px;
		}
	</style>
	
</head>
<body>
  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">	
        <a class="brand" href="#">Articulo</a>
          <ul class="nav">
            <li class="active"><a href="/secure/main.jsp">Inicio</a></li>
            
            <li class="dropdown" >
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Acciones<b class="caret"></b></a>
              <ul class="dropdown-menu" >
                <li><a href="/secure/imageUpload.jsp">Subir imagen</a></li>
                <li class="divider"></li>
                <li class="nav-header">Visualizar por...</li>
                <li><a href="/secure/albums.jsp">Album</a></li>
                <li><a href="/secure/search.jsp">Buscar</a></li>
                <li><a href="/article?action=browseArticle">Articulos</a></li>
              </ul>
            </li>
          </ul>
          <div class="pull-right">
            <ul class="nav">
               <li class="dropdown" >
                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">${usuarioLogeado.userName}<b class="caret"></b></a>
                 <ul class="dropdown-menu" >
  	               <li><a href="/userprofile">Editar perfil</a></li>
   	               <li><a href="/editPerson?action=browsePerson">Personas</a></li>
                   <li class="divider"></li>
                   <li><a href="/logout">Cerrar sesion</a></li>
                 </ul>
               </li>
            </ul>
          </div> 	
      </div>   <!-- end container -->
    </div>	 <!--end topbar-inner -->
  </div> <!--end topbr -->
<!-- END header -->

<div class="container-fluid">
  <div class="row-fluid">
	  <!--  ====================   SIDE-BAR ==========================  -->
        <div class="span3">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              <li class="nav-header">Favoritos</li>
              <li id="personList"></li>
              
              <li id="selectedPersons" class="nav-header">Vincular</li>
              <li>
	          </li>

              <li>
              <table id="selectedTable">
              <tbody id="selectedTBody">
              </tbody>
              </table>        
              </li>
 
              <li id="selectedPersons" class="nav-header">Grupos</li>
              <li>
              </li>
 
 
 
              <li class="nav-header">Acciones</li>
				<li><button type="button" class="btn btn-link btn-mini" name="browseArticleBtn" onclick="location.href='/article?action=browseArticle'" >explorar articulos</button></li>
            </ul>
          </div><!--/.well -->
        </div><!--/span-->
		
<!-- ************** ARTICLE ************************* -->
  <div class="span9">
	<h2>Artículo: ${pbean.article.name}</h2>
	<form name="editArticle" class="form-inline" action="/editArticle?action=${pbean.action}" method="post">
	<fieldset>
			<div class="control-group">
				<label class="control-label" for="pDenom" >Nombre de uso interno asignado al Articulo:</label>
				<div class="controls" >

					<input type="hidden" id="articleId" name="articleId" 
							value="${pbean.article.id}" />

					<input type="text" id="arName" class="span4" name="arName" placeholder="nombre de uso interno"
						value="${pbean.article.name}" />
					<input type="text" id="arSlug" class="span5" name="arSlug" placeholder="slug"
						value="${pbean.article.slug}" />
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="arMainT" >Título principal y subtítulo:</label>
				<div class="controls" >
					<input type="text" id="arMainT" class="span4" name="arMainT" placeholder="título principal"
						value="${pbean.article.mainTitle}" />
					<input type="text" id="arSectondT" class="span5" name="arSectondT" placeholder="título secundario"
						value="${pbean.article.secondTitle}" />
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="arText" >Texto (HTML):</label>
				<div class="controls" >
					<textarea  id="arText" name="arText" rows="3" class="span6">${pbean.article.textData}</textarea>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="arVisibility" >Visibilidad / Estado / Tipo / Sección</label>
				<div class="controls controls-row" >
					<select id="arVisibility" name="arVisibility" class="span2">
						<option  value="0"  >privado</option>
						<option  value="1"  >publico</option>
					</select>
					<select id="arIsActive" name="arIsActive" class="span2">
						<option  value="0"  >baja</option>
						<option  value="1"  >activo</option>
					</select>
					<select id="arType" name="arType" class="span2">
						<option  value="ARTICULO" >Artículo</option>
						<option  value="AGENDA"   >Agenda</option>
					</select>
					<select id="arSection" name="arSection" class="span2">
						<option  value="GENERICO"   >Generico</option>
						<option  value="NOTICIA"   >Noticia</option>
						<option  value="EVENTO"    >Evento</option>
						<option  value="MUESTRA"    >Muestra</option>
						<option  value="CLASE"     >Clase</option>
						<option  value="SEMINARIO" >Seminario</option>
						<option  value="AGENDA" >Agenda</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="pFechaCreacion" >Publicación: fecha desde - fecha hasta</label>
				<div class="controls controls-row" >
					<input type="date" id="arPublishFD" class="span3" name="arPublishFD" placeholder="fecha desde (mm/dd/aaaa)"
						value="${pbean.article.publishFromDateAsText}" />
					<input type="date" id="arPublishTD" class="span3" name="arPublishTD" placeholder="fecha hasta (mm/dd/aaaa)"
						value="${pbean.article.publishToDateAsText}" />
				</div>
			</div>
			
			<div class="form-actions">
				<button type="submit" class="btn btn-primary" name="save" >actualizar</button>
				<button type="button" class="btn"            name="cancelar" >cancelar</button>
			</div>
	</fieldset>
	</form>

<!-- ************** R E L A C I O N E S  ************************* -->
	<h3>Relaciones</h3>

	<div class="btn-group">
		<button type="button" class="btn btn-info btn-mini" id="selectEntityBtn" name="selectEntityBtn" onclick="selectEntity()" >Seleccionar</button>
		<button type="button" class="btn btn-info btn-mini" name="newRelationBtn" data-toggle="modal" data-target="#newRelation" onclick='initNewRelation()' >nueva Relacion</button>
	</div>
	<table id="relationTable" class="table table-striped">
		<thead>
			<tr>
				<th>#</th>
				<th>Id</th>
				<th>Predicado</th>
				<th>Asunto</th>
				<th>Descripción</th>
			</tr>
		</thead>
		<tbody>


	<%
		int itemRelation=1;
		if(!(pbean.getErelations()==null)){
		if(!pbean.getErelations().isEmpty()){
		for(EntityRelationHeader relation:pbean.getErelations()){
	%>
			<tr>
				<td><input type="checkbox" value="true"></td>
				<td><%= relation.getId()          %></td>
				<td><%= relation.getPredicate()   %></td>
				<td><%= relation.getSubject()     %></td>
				<td><%= relation.getDescription() %></td>
				<td>
					<div class="btn-group">
						<button type="button" class="btn btn-link btn-mini"  data-toggle="modal"  onclick="editRelation(<%=itemRelation%>)" data-target="#newRelation" >editar</button>
					</div>
				</td>
			</tr>
	<%
		itemRelation++;					
		}
		}
		}
	%>

		</tbody>			
	</table>


    </div><!--panel principal-->
  </div><!--/.fluid-container-->

  <!--  ====================   NEW RELATION  ==========================  -->
		<div class="modal hide fade in" id="newRelation"  data-keyboard=false data-backdrop=false tabindex="-1" role="dialog" aria-labelledby="newEntityRelationLabel" aria-hidden="true">
		<!-- Modal viewer: new Relation -->
			<form id="newRelationForm" name="newRelationForm" class="form-inline" action="/article?action=addRelation" method="post">
			<fieldset>
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
				<h3>nueva relacion</h3>
			</div>
			<div id="relationInput" class="modal-body">
				<label class="control-label" for="personId" >Entidad sobre la que se establecerá la relacion:</label>
				<input type="text" id="entityId" name="entityId"  class="span1"
						value="" >
				<input type="text" id="entityName" name="entityName"  class="span7"
						value="" >
				<input type="text" id="relationId" name="relationId"  class="span7"
						value="" >
				<input type="text" id="entityType" name="entityType"  class="span7"
						value="" >
						
				<span class="span6 divider">.</span>
									
				<input type="text" id="relSubject" name="relSubject" class="span6" 
						placeholder="asunto" value="" />
				<textarea  id="relDescr" name="relDescr" class="span6" rows="3"></textarea>
				<span class="span6 divider">.</span>
				<span class="help-block span6" >Tipo de relación a establecer con esta persona:</span>
				<select id="relPredicate" name="relPredicate">
					<option  value="MIEMBRO_DE"   >Miembros de...</option>
					<option  value="PRODUCTOR_DE" >Productores de...</option>
					<option  value="EDITOR_DE"    >Editores de...</option>
					<option  value="TITULAR_DE"   >Titulares de...</option>
					<option  value="CONOCE_A"     >Conocen a...</option>
					<option  value="LOCACION_DE"   >Locación donde...</option>
					<option  value="IMAGEN_DE"    >Recurso: imagen</option>
					<option  value="VIDEO_DE"     >Recurso: video</option>
					<option  value="AUDIO_DE"     >Recurso: audio</option>
					<option  value="DOCUMENTO_DE" >Recurso: documento</option>
					<option  value="RECURSO_DE"   >Otro recurso</option>
					<option  value="RENDERIZA_CON" >Publica en...</option>
					</select>
			</div>
			<div class="modal-footer form-actions">
				<button type="submit" class="btn btn-primary" name="update" >guardar</button>
				<button type="button" class="btn "            name="cancelar" >cancelar</button>
			</div>
			</fieldset>
			</form>
		</div>




</div> <!-- end CONTAINER -->


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
     $('#editArticle').on('show', function () {
 

	});
     </script>


    <script>
	function editRelation(item){
		initNewRelation();

		var table = document.getElementById("relationTable");
		var row   = table.rows[item];
		//for (var i=1, row; row = table.rows[i]; i++){
		//	if(row.cells[0].firstChild.checked){
		document.getElementById('relationId').value   = row.cells[1].firstChild.nodeValue;
		document.getElementById('relPredicate').value = row.cells[2].firstChild.nodeValue;
		document.getElementById('relSubject').value   = row.cells[3].firstChild.nodeValue;
		document.getElementById('relDescr').value     = row.cells[4].firstChild.nodeValue;

	}
    </script>

    <script>
	function initNewRelation(){
		var eType = "ARTICLE"
		var eId   = document.getElementById('articleId').value
		var eName = document.getElementById('arName').value
		document.getElementById('entityId').value   = eId;
		document.getElementById('entityName').value = eName;
		document.getElementById('entityType').value = eType;
 	}
    </script>


	<script>
	function selectEntity() {
		var html="";
		var id="";
		var type="";
		var name="";		
		var table = document.getElementById("relationTable");
		for (var i=1, row; row = table.rows[i]; i++){
			if(row.cells[0].firstChild.checked){
				id   = row.cells[1].firstChild.nodeValue;
				type = row.cells[2].firstChild.nodeValue;
				name = row.cells[3].firstChild.nodeValue;
				html="<tr><td>"+id+"</td><td>"+type+"</td><td>"+name+"</td></tr>";
			};
		}
		//alert(html);
		document.getElementById('selectedTBody').innerHTML = html;

        $.ajax({
            url:"/selectentity",
            type: 'get',
            data: { id: id, type: type, name: name },
            success: function(data) {
            	if(data=='success') $('#selectEntityBtn').attr( 'class', "btn btn-mini btn-success" );
            	else  $('#selectEntityBtn').attr( 'class', "btn btn-mini btn-danger" );
            }
        });

	}
	</script>	


	<script>
	function selectContact(index){
 		contactIndex =index
 		if(index==-1) editContact="/editContact?action=new";
 		else editContact="/editContact?action=update";
 		$('#newContactForm').attr('action', editContact);
 	}
	function selectLocation(index){
 		locationIndex =index
 		if(index==-1) editLocation="/editLocation?action=new";
 		else editLocation="/editLocation?action=update";
 		$('#newLocationForm').attr('action', editLocation);
 	}
	var contactIndex=0;
	var locationIndex=0;
  	 $('#arVisibility option[value="${pbean.article.visibility}"]').get(0).selected =  true;
	 $('#arIsActive option[value="${pbean.article.isActive}"]').get(0).selected =  true;
	 $('#arType option[value="${pbean.article.type}"]').get(0).selected =  true;
	 $('#arSection option[value="${pbean.article.section}"]').get(0).selected =  true;
	</script>	
</body>
</html>
