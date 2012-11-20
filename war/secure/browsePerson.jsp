<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.dao.PersonBean"%>
<%@page import="ar.kennedy.is2011.db.entities.ContactosPerson"%>
<%@page import="ar.kennedy.is2011.db.entities.Location"%>
<%@page import="ar.kennedy.is2011.db.entities.PersonaFisica"%>
<%@page import="ar.kennedy.is2011.db.entities.PersonaIdeal"%>
<%@page import="ar.kennedy.is2011.db.entities.EntityRelationHeader"%>

<%
	PersonBean pbean = (PersonBean) request.getAttribute("pbean");
	
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Explorador: Personas</title>
	<meta name="explorador de personas" content="">
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
        <a class="brand" href="#">Explorador: personas</a>
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
              <li class="nav-header">Favoritos</li>
              <li id="personList"></li>
              
              <li id="selectedPersons" class="nav-header">Vincular</li>
              <li>
	          </li>
              <li>
					<button type="button" class="btn btn-link btn-mini" id="addEntitysBtn" name="addEntitysBtn" onclick="addEntities()" >${eselected} ${etype}</button>
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
				<li><button type="button" class="btn btn-link btn-mini" name="newFPersonBtn" onclick="location.href='/editPerson?action=newFPerson'" >nueva persona fisica</button></li>
				<li><button type="button" class="btn btn-link btn-mini" name="newIPersonBtn" onclick="location.href='/editPerson?action=newIPerson'" >nueva persona ideal</button></li>
				<li><button type="button" class="btn btn-link btn-mini" name="newIPersonBtn" onclick="getPersonsByName('Ca')" >searchPerson</button></li>
            </ul>
          </div><!--/.well -->
        </div><!--/span-->
        
        <!-- START Content  tabs -->
        <div class="span9">
		<div class="tabbable">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#browser" data-toggle="tab">Explorador</a></li>
				<li>               <a href="#editor" data-toggle="tab">Edicion    </a></li>
			</ul>

  		<div class="tab-content">

		<!--   =============  B R O W S E R ======================= -->
		<div class="tab-pane active" id="browser">
       
          <div class="row-fluid">
 			<form class="form-search" method="post" action="/editPerson?action=browsePerson">
				<input id="pDenom" name="pDenom" type="text" class="input-medium search-query ajax-typeahead" data-provide="typeahead" >
				<button type="submit" class="btn">Search</button>
 			</form>
          </div>
          <div class="row-fluid">
			<%
			%>
				<div class="btn-group">
					<button type="button" class="btn btn-info btn-mini" id="selectEntityBtn" name="selectEntityBtn" onclick="selectEntity()" >Seleccionar</button>
					<button type="button" class="btn btn-info btn-mini" name="newRelationBtn" data-toggle="modal" data-target="#newRelation" onclick='initNewRelation()' >nueva Relacion</button>
				</div>
				<table id="personTable" class="table table-striped">
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
					int itemFPerson=0;
					if(!(pbean.getFpersons()==null)){
					if(!pbean.getFpersons().isEmpty()){
					for(PersonaFisica person:pbean.getFpersons()){
					%>
						<tr>
							<td><input type="checkbox" value="true"></td>
							<td><%= person.getId() %></td>
							<td>PF</td>
							<td><%= person.getNombrePerson() %></td>
							<td><%= person.getNombre() %>  <%= person.getApellido() %></td>
							<td>
								<div class="btn-group">
									<button type="button" class="btn btn-link btn-mini" name="editPersonBtn" onclick="location.href='/editPerson?action=updateFPerson&personid=<%=person.getId() %>'" >editar</button>
 									<button type="button" class="btn btn-link btn-mini"  data-toggle="modal"  onclick="viewPerson(<%=person.getId() %>)" data-target="#personViewer" >ver</button>
								</div>
							</td>
						</tr>
		
					<%
						itemFPerson++;					
						}
						}
						}
					%>
		
					<%
						int itemIPerson=0;
						if(!(pbean.getIpersons()==null)){
						if(!pbean.getIpersons().isEmpty()){
						for(PersonaIdeal person:pbean.getIpersons()){
					%>
						<tr>
							<td><input type="checkbox" value="true"></td>
							<td><%= person.getId() %></td>
							<td>PI</td>
							<td><%= person.getNombrePerson() %></td>
							<td><%= person.getComent() %></td>
							<td>
								<div class="btn-group">
									<button type="button" class="btn btn-link btn-mini" name="editPersonBtn" onclick="location.href='/editPerson?action=updateIPerson&personid=<%=person.getId() %>'" >editar</button>
 									<button type="button" class="btn btn-link btn-mini"  data-toggle="modal"  onclick="viewPerson(<%=person.getId() %>)" data-target="#personViewer" >ver</button>
								</div>
							</td>
						</tr>
		
					<%
						itemIPerson++;					
						}
						}
						}
					%>

					<%
						int itemRelation=0;
						if(!(pbean.getErelations()==null)){
						if(!pbean.getErelations().isEmpty()){
							String personHeader="";
						for(EntityRelationHeader relation:pbean.getErelations()){
							personHeader="";
							if(relation.getFpersonkey()!=null) personHeader=personHeader+relation.getFpersonkey()+" ";
							if(relation.getIpersonkey()!=null) personHeader=personHeader+relation.getIpersonkey()+" ";
					%>
						<tr>
							<td><input type="checkbox" value="true"></td>
							<td><%= relation.getId() %></td>
							<td>REL</td>
							<td><%= relation.getSubject() %></td>
							<td><%= personHeader %></td>
							<td>
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
		
          </div><!--/row-->
          
 		</div><!-- end tabpane -->


		<!--   =============  E D I T O R ======================= -->
		<div class="tab-pane" id="editor">
       
          <div class="hero-unit">
            <h1>Hello, world editor!</h1>
            <p>This is a template for a simple marketing or informational website. It includes a large callout called the hero unit and three supporting pieces of content. Use it as a starting point to create something more unique.</p>
            <p><a class="btn btn-primary btn-large">Learn more &raquo;</a></p>
          </div>
 		</div><!-- end tabpane -->


        
        </div><!-- end TAB CONTENT -->
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
<div class="modal hide fade in" id="personViewer"  data-keyboard=false data-backdrop=false tabindex="-1" role="dialog" aria-labelledby="personViewerLabel" aria-hidden="true">
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
		<div class="modal hide fade in" id="newRelation"  data-keyboard=false data-backdrop=false tabindex="-1" role="dialog" aria-labelledby="newEntityRelationLabel" aria-hidden="true">
		<!-- Modal viewer: new Relation -->
			<form id="newRelationForm" name="newRelationForm" class="form-inline" action="/newerelation?action=createRelation" method="post">
			<fieldset>
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
				<h3>nueva relacion</h3>
			</div>
			<div id="relationInput" class="modal-body">
				<label class="control-label" for="personId" >Entidad sobre la que se establecerá la relacion:</label>
				<input type="text" id="personId" name="personId"  class="span1"
						value="" >
				<input type="text" id="personName" name="personName"  class="span7"
						value="" >
				<input type="text" id="relationId" name="relationId"  class="span7"
						value="" >
				<input type="text" id="personType" name="personType"  class="span7"
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
				</select>
			</div>
			<div class="modal-footer form-actions">
				<button type="submit" class="btn btn-primary" name="update" >guardar</button>
				<button type="button" class="btn "            name="cancelar" >cancelarr</button>
			</div>
			</fieldset>
			</form>
		</div>


		

    <!-- Le javascript============================================= -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="/js/bootstrap-2.1.1.js"></script>
    <script src="/js/typeahead.js"></script>

	<script>
	function addEntities() {
		var id  =" ";
		var ids =" ";
		var type="";

		var table = document.getElementById("personTable");
		for (var i=1, row; row = table.rows[i]; i++){
			if(row.cells[0].firstChild.checked){
				id   = row.cells[1].firstChild.nodeValue;
				type = row.cells[2].firstChild.nodeValue;
				ids = ids +type+":"+id+","
			};
		}

        $.ajax({
            url:"/addentities",
            type: 'get',
            data: { listids: ids },
            success: function(data) {
            	if(data=='success') $('#addEntitysBtn').attr( 'class', "btn btn-mini btn-success" );
            	else  $('#addEntitysBtn').attr( 'class', "btn btn-mini btn-danger" );
            }
        });

	}
	</script>	
	
	<script>
	function selectEntity() {
		var html="";
		var id="";
		var type="";
		var name="";		
		var table = document.getElementById("personTable");
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
	function submitExample(personId) {
		var path = "/editPerson?action=browsePerson&pDenom=Sui";
		//path = path + personId
		var method = "post";
		var table = document.getElementById("personTable");
		for (var i=1, row; row = table.rows[i]; i++){
			var cel1= row.cells[0].firstChild.checked;
			var cel2= row.cells[1].firstChild.nodeValue;
			//alert("cel ["+cel1+"] ["+cel2+"]");
		}
		var params = new Array(77,18);
		post_to_url(path,params,method);
	}
	</script>	
	<script>
	function post_to_url(path, params, method) {
   	method = method || "post"; // Set method to post by default, if not specified.

    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);
    //alert("params "+params.length);

    for(var i=0;i<params.length;i++) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", "person");
            hiddenField.setAttribute("value", params[i]);

            form.appendChild(hiddenField);
    }
    document.body.appendChild(form);
    form.submit();
	}
	</script>	
	<script>
	function postToUrl(path, params, method) {
   	method = method || "post"; // Set method to post by default, if not specified.

    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
         }
    }
    document.body.appendChild(form);
    form.submit();
	}
	</script>	

    <script>
	$('#personViewer').on('show', function () {
		var target = "/secure/modalPersonView.jsp?personid=";
		target = target+personViewId;
		
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
	function getPersonsByName(query, process) {
		var target = "/pnamelist";
		$.ajax({
			  url: target,
			  type: 'get',
			  data: {query:query},
              dataType: 'json',
              success: function(json) {
                  process(json.options);
              }
			}).done(function(data) { 
			});
      }
    </script>
    <script>
     $('#pnenom').typeahead({    
     	source: getPersonsByName
     });
 	//source: function (query, process) { return $.get('/pnamelist', { query: query }, function (data) { return process(data.options);});}  
    </script>
 
    <script>
    $('#pmenom').typeahead({    		    
    	source: function(query, process) {
            $.ajax({
                url:"/pnamelist",
                type: 'get',
                data: {query: query},
                dataType: 'json',
                success: function(json) {
                    return typeof json.options == 'undefined' ? false : process(json.options);
                }
            });
        }
    });
    </script>
     
    <script>
    $('#pDenom').typeahead({
			ajax: {
                url:"/pnamelist",
                method: 'get',
                triggerLength: 1,
            }
    });
    </script>

    <script>
	function initNewRelation(){
		var table = document.getElementById("personTable");
		for (var i=1, row; row = table.rows[i]; i++){
			if(row.cells[0].firstChild.checked){
				var pId   = row.cells[1].firstChild.nodeValue;
				var pType = row.cells[2].firstChild.nodeValue;
				var pName = row.cells[3].firstChild.nodeValue;
				document.getElementById('personId').value = pId;
				document.getElementById('personName').value = pName;
				document.getElementById('personType').value = pType;
			};
		}
 		//newRelation="/nuevarelacion?action=new";
 		//newRelation="/nuevarelaconaction=update";
 	}
    </script>

    <script>
	function viewPerson(id){
		personLastViewed = personViewId;
		personViewId=id;		
	}
	var personViewId="0";
	var personLastViewed="0";	
	var personViewHtml="<div>Helouuu</div>";
    </script>

    <script>
	function browse(pId){
		viewPerson(pId);
		$('#personViewer').modal('show');
		
	}
	</script>
    <script>
	function browseLastViewed(){
		if(personLastViewed=="0") return;
		viewPerson(personLastViewed);
		$('#personViewer').modal('show');
		
	}
	</script>




    <script>
	function browsebyname(personName){
	    var form = document.createElement("form");
	   	var method = "post";
	   	var target = "/editPerson";
	   	var params = {
	   			pDenom:personName,
	   			action:'browsePerson'
	   	}
	    
	    form.setAttribute("method", method);
	    form.setAttribute("action", target);
	    //alert("params "+params);

	    for(var key in params) {
	        if(params.hasOwnProperty(key)) {
	            var hiddenField = document.createElement("input");
	            hiddenField.setAttribute("type", "hidden");
	            hiddenField.setAttribute("name", key);
	            hiddenField.setAttribute("value", params[key]);

	            form.appendChild(hiddenField);
	         }
	    }
	    document.body.appendChild(form);
	    form.submit();
	}
    </script>

</body>
</html>
