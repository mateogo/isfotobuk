<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.dao.ArticleBean"%>
<%@page import="ar.kennedy.is2011.db.dao.TaskBean"%>
<%@page import="ar.kennedy.is2011.db.entities.EntityRelationHeader"%>


<%
	TaskBean pbean = (TaskBean) request.getAttribute("pbean");
	
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Edicion de Tarea </title>
	<meta name="edicion de tarea " content="">
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
        <a class="brand" href="#">Tarea</a>
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
                <li><a href="/task?action=browseTask">Articulos</a></li>
                <li><a href="/task?action=browseTasks">Tareas</a></li>
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
              <li class="nav-header">Vincular</li>
              <li>
	          </li>

              <li>
              <table id="selectedTable">
              <tbody id="selectedTBody">
              </tbody>
              </table>        
              </li>
 
 
 
              <li class="nav-header">Acciones</li>
				<li><button type="button" class="btn btn-link btn-mini" name="browseTaskBtn" onclick="location.href='/task?action=browseTask'" >explorar tareas</button></li>
            </ul>
          </div><!--/.well -->
        </div><!--/span-->
		
<!-- ************** TASK ************************* -->
  <div class="span9">
	<h2>Tarea: ${pbean.task.locator}</h2>
	<form name="editTask" class="form-inline" action="/editTask?action=${pbean.action}" method="post">
	<fieldset>
			<div class="control-group">
				<label class="control-label" for="taskLocator" >texto localizador de la tarea:</label>
				<div class="controls" >

					<input type="hidden" id="taskId" name="taskId" 
							value="${pbean.task.id}" />

					<input type="text" id="taskLocator" class="span4" name="taskLocator" placeholder="identificador de la tarea"
						value="${pbean.task.locator}" />
					<input type="text" id="taskSubject" class="span5" name="taskSubject" placeholder="asunto"
						value="${pbean.task.subject}" />
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="taskText" >Texto (HTML):</label>
				<div class="controls" >
					<textarea  id="taskText" name="taskText" rows="3" class="span6">${pbean.task.textData}</textarea>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="taskVisibility" >Visibilidad / Estado / Tipo / Sección</label>
				<div class="controls controls-row" >
					<select id="taskVisibility" name="taskVisibility" class="span2">
						<option  value="0"  >privado</option>
						<option  value="1"  >publico</option>
					</select>
					<select id="taskStatus" name="taskStatus" class="span2">
						<option  value="0"  >baja</option>
						<option  value="1"  >planificada</option>
						<option  value="2"  >asignada</option>
						<option  value="2"  >en curso</option>
						<option  value="5"  >cumplida</option>
						<option  value="7"  >demorada</option>
						<option  value="8"  >suspendida</option>
						<option  value="9"  >cancelada</option>
					</select>
					<select id="taskType" name="taskType" class="span2">
						<option  value="PROYECTO"  >proyecto</option>
						<option  value="HITO"      >hito</option>
						<option  value="ACTIVIDAD" >actividad</option>
						<option  value="TAREA"     >tarea</option>
					</select>
					<select id="taskIsActive" name="taskIsActive" class="span2">
						<option  value="0" >Activa</option>
						<option  value="1" >Cerrada</option>
						<option  value="2" >Baja</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="taskDueDate" >Publicación: fecha desde - fecha hasta</label>
				<div class="controls controls-row" >
					<input type="date" id="taskDueDate" class="span3" name="taskDueDate" placeholder="fecha objetivo"
						value="${pbean.task.dueDateAsText}" />
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
			<form id="newRelationForm" name="newRelationForm" class="form-inline" action="/task?action=addRelation" method="post">
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
					<option  value="MIEMBRO_DE"     >miembros de...</option>
					<option  value="RESPONSABLE_DE" >responsable de...</option>
					<option  value="SUBTAREAS_DE"   >tareas relacionadas...</option>
					<option  value="LOCACION_DE"    >locación donde...</option>
					<option  value="PRODUCTOR_DE"   >productores de...</option>
					<option  value="EDITOR_DE"      >editores de...</option>
					<option  value="TITULAR_DE"     >titulares de...</option>
					<option  value="CONOCE_A"       >conocen a...</option>
					<option  value="IMAGEN_DE"      >recurso: imagen</option>
					<option  value="VIDEO_DE"       >recurso: video</option>
					<option  value="AUDIO_DE"       >recurso: audio</option>
					<option  value="DOCUMENTO_DE"   >recurso: documento</option>
					<option  value="RECURSO_DE"     >otro recurso</option>
					<option  value="RENDERIZA_CON"  >publica en...</option>
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
     $('#editTask').on('show', function () {
 

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
		var eType = "TASK"
		var eId   = document.getElementById('taskId').value
		var eName = document.getElementById('taskLocator').value
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
  	 $('#taskVisibility option[value="${pbean.task.visibility}"]').get(0).selected =  true;
	 $('#taskIsActive option[value="${pbean.task.isActive}"]').get(0).selected =  true;
	 $('#taskType option[value="${pbean.task.type}"]').get(0).selected =  true;
	 $('#taskStatus option[value="${pbean.task.status}"]').get(0).selected =  true;
	</script>	
</body>
</html>
