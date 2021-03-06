
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.dao.TaskBean"%>
<%@page import="ar.kennedy.is2011.db.dao.GenericEntity"%>
<%@page import="ar.kennedy.is2011.db.entities.Task"%>
<%@page import="ar.kennedy.is2011.db.entities.EntityRelationHeader"%>
<%@page import="ar.kennedy.is2011.db.dao.ArticlePortlet"%>
<%@page import="ar.kennedy.is2011.models.RelationModel.LocationRelation"%>
<%@page import="ar.kennedy.is2011.models.RelationModel.TaskRelation"%>
<%@page import="java.util.List"%>


<%
	List<LocationRelation> locations = null;
	List<TaskRelation> taskRelations = null;
	
	TaskBean pbean = (TaskBean) request.getAttribute("pbean");
	//pbean.setSelectedTask((Task) request.getAttribute("taskselected"));
	if(pbean.getArticlePortlet()!=null){
		locations = pbean.getArticlePortlet().getLocations();
		taskRelations = pbean.getArticlePortlet().getTaskrelations();
	}

	
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Explorador: Tareas</title>
	<meta name="explorador de tareas" content="">
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
  <!--  ====================   TOPBAR ==========================  -->
  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">	
        <a class="brand" href="#">Explorador: Tareas</a>
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
              </ul>
            </li>
          </ul>
          <div class="pull-right">
            <ul class="nav">
               <li class="dropdown" >
                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">${usuarioLogeado.userName}<b class="caret"></b></a>
                 <ul class="dropdown-menu" >
                   <li><a href="/userprofile">Editar perfil</a></li>
                  <li><a href="/editPerson?action=newFPerson"><i class="icon-user"></i>Persona Fisica</a></li>
                   <li><a href="/editPerson?action=newIPerson"><i class="icon-user"></i>Persona Ideal</a></li>
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

<!-- START CONTENT   -->
    <div class="container-fluid">

	  <!--  ====================   SIDE-BAR ==========================  -->
      <div class="row-fluid">
        <div class="span3">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              
              <li class="nav-header">seleccionado</li>
              <li>
					<button type="button" class="btn btn-link btn-mini" id="addEntitysBtn" name="addEntitysBtn" onclick="location.href='/task?action=updateTask&taskid=${taskselected.id}'" >${etype}: ${taskselected} </button>
              </li>
					<%
						int itemRelation=0;
						if(!(pbean.getErelations()==null)){
						if(!pbean.getErelations().isEmpty()){
					%>
				<li id="selectedTasks" class="nav-header">Relaciones</li>
				<li>
				<table id="relationTable" class="table table-condensed">
					<tbody>
					<%
						for(EntityRelationHeader relation:pbean.getErelations()){
					%>
						<tr class="success">
							<td><input type="checkbox" value="true"></td>
							<td hidden=true><%= relation.getId() %></td>
							<td hidden=true><%= relation.getPredicate() %></td>
							<td hidden=true><%= relation.getDescription() %></td>
							<td hidden=true><%= relation.getEntityId() %></td>
							<td hidden=true><%= relation.getEntityType() %></td>
							<td><a href="#"  data-toggle="modal"  onclick="editRelation(<%=itemRelation %>)" data-target="#updateRelation" ><%= relation.getSubject() %></a></td>
						</tr>
					<%
						itemRelation++;					
						}
					%>
					</tbody>			
				</table>
				</li>
					<%
						}
						}
					%>
              <li>
              <table id="selectedTable">
              <tbody id="selectedTBody">
              </tbody>
              </table>        
              </li>
              <li class="nav-header">Acciones</li>
			<li><button type="button" class="btn btn-link btn-mini" name="newTaskBtn" onclick="location.href='/task?action=newTask'" >nueva tarea</button></li>
            </ul>
          </div><!--/.well -->
        </div><!--/span-->
        
        <!-- START     MAIN CONTENT  tabs -->
        <div class="span9">
		<div class="tabbable">
			<ul class="nav nav-tabs">
				<li class="${pbean.browsertab}"><a href="#browser"    data-toggle="tab">explorador </a></li>
				<li                            ><a href="#render"     data-toggle="tab">visor      </a></li>
				<li class="${pbean.viewertab}" ><a href="#relacionar" data-toggle="tab">relacionar </a></li>
			</ul>

  		<div class="tab-content">
		<!--   =============  B R O W S E R ======================= -->
		<div class="tab-pane ${pbean.browsertab}" id="browser">
       
          <div class="row-fluid">
 			<form class="form-search" method="post" action="/task?action=browseTask">
				<input id="taskLocator" name="taskLocator" type="text" class="input-medium search-query ajax-typeahead" data-provide="typeahead" >
				<button type="submit" class="btn">Search</button>
 			</form>
          </div>
          <div class="row-fluid">
				<div class="btn-group">
					<button type="button" class="btn btn-info btn-mini" id="selectEntityBtn" name="selectEntityBtn" onclick="selectEntity()" >Seleccionar</button>
					<button type="button" class="btn btn-info btn-mini" name="newRelationBtn" data-toggle="modal" data-target="#updateRelation" onclick='initNewRelation()' >nueva Relacion</button>
				</div>
				<table id="taskTable" class="table table-striped">
					<thead>
						<tr>
							<th>#</th>
							<th>Id</th>
							<th>Tipo</th>
							<th>Localizador</th>
							<th>Asunto</th>
						</tr>
					</thead>
					<tbody>
					<%
					int itemTask=0;
					if(!(pbean.getTasks()==null)){
					if(!pbean.getTasks().isEmpty()){
					for(Task task:pbean.getTasks()){
					%>
						<tr>
							<td><input type="checkbox" value="true"></td>
							<td><%= task.getId() %></td>
							<td>TASK</td>
							<td><%= task.getLocator() %></td>
							<td><%= task.getSubject() %></td>
							<td>
								<div class="btn-group">
									<button type="button" class="btn btn-link btn-mini" name="editTaskBtn" onclick="location.href='/task?action=updateTask&taskid=<%=task.getId() %>'" >editar</button>
 									<button type="button" class="btn btn-link btn-mini"  data-toggle="modal"  onclick="viewTask(<%=task.getId() %>)" data-target="#taskViewer" >ver</button>
								</div>
							</td>
						</tr>
		
					<%
						itemTask++;					
						}
						}
						}
					%>
		
					</tbody>			
				</table>
		
          </div><!--/row-->
 		</div><!-- end tabpane  BROWSER  -->


		<!--   =============  V I E W E R  ======================= -->
		<div class="tab-pane " id="render">
       
          <div class="row-fluid">
            <p>${taskselected.locator}</p>
            <p>Fecha límite cumplimiento:<strong> ${taskselected.dueDateAsText}</strong></p>
            <h3>${taskselected.subject}</h3>
            <p>${taskselected.textData}<p>
          </div>
          <div class="row-fluid">
          </div>
          <div class="row-fluid">
            <h4>Datos técnicos</h4>
            <div>${pbean.selectedTaskTechData}</div>
          </div>
          
		<div class="row-fluid">
	 		<%
				if(!(locations==null || locations.isEmpty())){
			%>
			<%
	  				for(LocationRelation data: locations){
			%>
				<h4><%= data.getLocationHeading() %></h4>
				<ul class="nav nav-list"><li >
				<div><%= data.getLocationDescr() %></div>
				<div><a href="<%= data.getUrl() %>"  ><%= data.getPersonName() %></a></div>
				<div><%= data.getLocation2() %></div>
				</li></ul>

			<%
					}
				}
			%>
	
	    </div> <!-- end row-fluid -->
          

		<div class="row-fluid">
	 		<%
				if(!(taskRelations==null || taskRelations.isEmpty())){
			%>
				<h4>Tareas Relacionadas</h4>
			<%
	  				for(TaskRelation data: taskRelations){
			%>
				<h4><%= data.getRelationheading() %></h4>
				<ul class="nav nav-list"><li >
				<div><b><%= data.getSubject() %></b></div>
				<div>(<%= data.getLocator() %>)</div>
				<div><b>Fecha límite: </b><%= data.getDuedate() %></div>
				<div><%= data.getText() %></div>
				<div><%= data.getOwner() %></div>
				</li></ul>

			<%
					}
				}
			%>
	
	    </div> <!-- end row-fluid -->
          
          
      	</div><!-- end tabpane VIEWER -->
          

		<!--   =============  R E L A C I O N A R   ======================= -->
		<div class="tab-pane ${pbean.viewertab}" id="relacionar">
       
          <div class="row-fluid">
 			<form class="form-search" method="post" action="/task?action=browseEntity">
				<div class="input-prepend">
					<div class="btn-group">
						<button class="btn dropdown-toggle" data-toggle="dropdown">tipo<span class="caret"></span></button>
							<ul class="dropdown-menu">
								<li><a href="#" onclick="choose('ARTICLE')">artículo</a></li>
								<li><a href="#" onclick="choose('PF')"      >persona</a></li>
								<li><a href="#" onclick="choose('PI')"      >persona ideal</a></li>
								<li><a href="#" onclick="choose('PICTURE')" >imagen</a></li>
								<li><a href="#" onclick="choose('REL')"     >relación</a></li>
								<li><a href="#" onclick="choose('TASK')"   >tarea</a></li>
							</ul>
					</div>
					<input id="enType" name="enType" type="text" class="span3">
					<input id="enName" name="enName" type="text" class="input-medium search-query ajax-typeahead" data-provide="typeahead">
					<button type="submit" class="btn" >Buscar</button>
 				</div>
			</form>
          </div>
          <div class="row-fluid">
			<%
			%>
				<div class="btn-group">
					<input id="relItemComent" name="relItemComent" type="text" class="input-medium search-query" >
					<button type="button" class="btn btn-info btn-mini" name="newRelationItemBtn"  onclick='setRelationItem()' >vincular</button>
				</div>
				<table id="entityTable" class="table table-striped">
					<thead>
						<tr>
							<th>#</th>
							<th>Id</th>
							<th>Denominación</th>
							<th>Comentario</th>
						</tr>
					</thead>
					<tbody>
					<%
					int itemEntity=0;
					if(!(pbean.getEntities()==null)){
					if(!pbean.getEntities().isEmpty()){
					for(GenericEntity entity:pbean.getEntities()){
					%>
						<tr>
							<td><input type="checkbox" value="true"></td>
							<td><%= entity.getEntityStringId() %></td>
							<td><%= entity.getEntityType() %></td>
							<td><%= entity.getEntityName() %></td>
							<td><%= entity.getEntityDescription() %></td>
							<td>
								<div class="btn-group">
									<button type="button" class="btn btn-link btn-mini" name="editTaskBtn" onclick="location.href='/task?action=updateTask&taskid=<%=entity.getEntityStringId() %>'" >editar</button>
 									<button type="button" class="btn btn-link btn-mini"  data-toggle="modal"  onclick="viewTask(<%=entity.getEntityStringId() %>)" data-target="#taskViewer" >ver</button>
								</div>
							</td>
						</tr>
		
					<%
						itemEntity++;					
						}
						}
						}
					%>
		
					</tbody>			
				</table>
		
          </div><!--/row-->
          
 		</div><!-- end tabpane ENTITY RELATIONER -->
 	</div><!-- end TAB CONTENT -->

<!--   =============  TAB PANE CONTENT END  =========== -->
 
	</div> <!-- end TABABBLE -->
    </div><!--/span9-->

   </div><!--/row fluid-->

      <hr>

      <footer>
        <p>&copy; Company 2012</p>
      </footer>

 </div><!--/.fluid-container-->


 <!--  ====================   VIEWER  ==========================  -->

<!-- Modal viewer -->
<div class="modal hide fade in" id="taskViewer"  data-keyboard=false data-backdrop=false tabindex="-1" role="dialog" aria-labelledby="taskViewerLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
		<h3>viewer</h3>
	</div>
	
	<div id="callback123" class="modal-body">
	</div>
	<div class="modal-footer">
		<button class="btn btn-large"  onclick="browseLastViewed()">Anterior</button>
		<button class="btn btn-large btn-primary"  data-dismiss="modal"  aria-hidden="true">Cerrar</button>
	</div>
</div>

  <!--  ====================   NEW RELATION  ==========================  -->
		<div class="modal hide fade in" id="updateRelation"  data-keyboard=false data-backdrop=false tabindex="-1" role="dialog" aria-labelledby="newEntityRelationLabel" aria-hidden="true">
		<!-- Modal viewer: new Relation -->
			<form id="newRelationForm" name="newRelationForm" class="form-inline" action="/task?action=addRelation" method="post">
			<fieldset>
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
				<h3>editar relacion</h3>
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
					<option  value="MIEMBRO_DE"    >Miembros de...</option>
					<option  value="PRODUCTOR_DE"  >Productores de...</option>
					<option  value="EDITOR_DE"     >Editores de...</option>
					<option  value="TITULAR_DE"    >Titulares de...</option>
					<option  value="CONOCE_A"      >Conocen a...</option>
					<option  value="LOCACION_DE"   >Locación donde...</option>
					<option  value="IMAGEN_DE"     >Recurso: imagen</option>
					<option  value="VIDEO_DE"      >Recurso: video</option>
					<option  value="AUDIO_DE"      >Recurso: audio</option>
					<option  value="DOCUMENTO_DE"  >Recurso: documento</option>
					<option  value="RECURSO_DE"    >Otro recurso</option>
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



    <!-- Le javascript============================================= -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="/js/bootstrap-2.1.1.js"></script>
    <!--   script src="/js/typeahe ad.js"></script    -->

    <script>
	var taskViewId="0";
	var taskLastViewed="0";	
	var taskViewHtml="<div>Helouuu</div>";
	var eTarget="/taskl";
	</script>

	
	<script>
	function selectEntity() {
		var id="";
		var type="";
		var name="";		
		var table = document.getElementById("taskTable");
		for (var i=1, row; row = table.rows[i]; i++){
			if(row.cells[0].firstChild.checked){
				id   = row.cells[1].firstChild.nodeValue;
				type = row.cells[2].firstChild.nodeValue;
				name = row.cells[3].firstChild.nodeValue;
			};
		}
        location.href='/selectentity?id='+id+'&type='+type+'&name='+name+'&target=taskbrowser'
	}
	</script>	

	<script>
	function selectDeprecatedEntity() {
		var html="";
		var id="";
		var type="";
		var name="";		
		var table = document.getElementById("taskTable");
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
        	}).done(function(data) {
        		//location.reload();
			});
        //$.ajax({url: "", context: document.body, success: function(s,x){ $(this).html(s);}});
        //location.href='/task?action=browseTask&taskLocator='+name
	}
	</script>	


    <script>
	$('#taskViewer').on('show', function () {
		var target = "/secure/modalTaskView.jsp?taskid=";
		target = target+taskViewId;
		
		$.ajax({
			  url: target,
			  dataType: "text",
			  success: function(data){
				 document.getElementById('callback123').innerHTML = data;
			  }
			}).done(function(data) { 
			});	

        });
    </script>	

     
    <script>
    $('#taskLocator').typeahead({    		    
    	source: function(query, process) {
            $.ajax({
                url:"/tasklist",
                type: 'get',
                data: {query: query, type: 'TASK' },
                dataType: 'json',
                success: function(json) {
                    return typeof json.options == 'undefined' ? false : process(json.options);
                }
            });
        }
    });
    </script>


    <script>
	function viewTask(id){
		taskLastViewed = taskViewId;
		taskViewId=id;		
	}
    </script>

    <script>
	function browse(pId){
		viewTask(pId);
		$('#taskViewer').modal('show');
		
	}
	</script>
	
    <script>
	function browseLastViewed(){
		if(taskLastViewed=="0") return;
		viewTask(taskLastViewed);
		$('#taskViewer').modal('show');
		
	}

	function choose(entityType){
		document.getElementById('enType').value = entityType;		
		eTarget = entityType;
	}
	</script>


    <script>
    $('#enName').typeahead({    		    
    	source: function(query, process) {
            $.ajax({
                url:"/tasklist",
                type: 'get',
                data: {query: query, type: eTarget },
                dataType: 'json',
                success: function(json) {
                    return typeof json.options == 'undefined' ? false : process(json.options);
                }
            });
        }
    });
    </script>


    <script>
    $('#nnName').typeahead({
			ajax: {
                url: eTarget,
                method: 'get',
                triggerLength: 2
            }
    });
    </script>

    <script>
	function initNewRelation(){
		var table = document.getElementById("taskTable");
		for (var i=1, row; row = table.rows[i]; i++){
			if(row.cells[0].firstChild.checked){
				var pId   = row.cells[1].firstChild.nodeValue;
				var pType = row.cells[2].firstChild.nodeValue;
				var pName = row.cells[3].firstChild.nodeValue;
				document.getElementById('entityId').value = pId;
				document.getElementById('entityName').value = pName;
				document.getElementById('entityType').value = pType;
			};
		}
 		//newRelation="/nuevarelacion?action=new";
 		//newRelation="/nuevarelaconaction=update";
 	}
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
		document.getElementById('relDescr').value     = row.cells[3].firstChild.nodeValue;
		document.getElementById('entityId').value     = row.cells[4].firstChild.nodeValue;
		document.getElementById('entityType').value   = row.cells[5].firstChild.nodeValue;
		document.getElementById('relSubject').value   = row.cells[6].firstChild.firstChild.nodeValue;

	}
    </script>

	<script>
	function setRelationItem(){
		var target = "/task";
		var entityObj = selectedEntity();
		var relObj = selectedRelation()
		var relComent = document.getElementById('relItemComent').value;

		$.ajax({
			  url: target,
	          type: 'post',
	            data: {
	            	action: "addRelationItem",
	            	relationId: relObj.id,
	            	entityType: entityObj.type,
	            	entityId:   entityObj.id,
	            	entityName: entityObj.name,
	            	itemDescr: relComent
	           	},
              dataType: 'text',
			  success: function(data){
	            	if(data=='success') $('#addRelationBtn').attr( 'class', "btn btn-success" );
	            	else  $('#addRelationBtn').attr( 'class', "btn btn-danger" );
			  }
			}).done(function(data) {
			});
	}
    </script>

    <script>
	function selectedEntity(){
		var entityObj={};
		var table = document.getElementById("entityTable");
		for (var i=0, row; row = table.rows[i]; i++){
			if(row.cells[0].firstChild.checked){
				entityObj.id   = row.cells[1].firstChild.nodeValue;
				entityObj.type = row.cells[2].firstChild.nodeValue;
				entityObj.name = row.cells[3].firstChild.nodeValue;
			};
		}
		return entityObj;
 	}
    </script>
    <script>
	function selectedRelation(){
		var relationObj={};
		var table = document.getElementById("relationTable");
		for (var i=0, row; row = table.rows[i]; i++){
			if(row.cells[0].firstChild.checked){
				relationObj.id         = row.cells[1].firstChild.nodeValue;
				relationObj.predicate  = row.cells[2].firstChild.nodeValue;
				relationObj.entityId   = row.cells[4].firstChild.nodeValue;
				relationObj.entityType = row.cells[5].firstChild.nodeValue;
			};
		}
		return relationObj;
 	}
    </script>

</body>
</html>
