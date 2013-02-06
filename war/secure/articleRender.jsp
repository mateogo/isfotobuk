<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.entities.User"%>
<%@page import="ar.kennedy.is2011.db.entities.PictureEy"%>
<%@page import="ar.kennedy.is2011.db.dao.ArticleBean"%>
<%@page import="ar.kennedy.is2011.db.dao.ArticlePortlet"%>
<%@page import="ar.kennedy.is2011.models.RelationModel.LocationRelation"%>

<%@page import="ar.kennedy.is2011.models.SearchPicturesModel"%>
<%@page import="ar.kennedy.is2011.views.UserHomeView"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="java.util.List"%>

<%
ArticleBean pbean = (ArticleBean) request.getAttribute("pbean");
UserHomeView userView = new UserHomeView(request);
SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
User user = (User) SessionManager.getCurrentUser(request);
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Articulo :: Inicio::</title>
	<meta name="vista general de un articulo" content="">
	<meta name="fotobuk uk" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">

	<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

	<link href="/css/bootstrap-2.1.1.css" rel="stylesheet">
	<link href="/images/favicon.gif" rel="icon" type="image/gif">

	<style type="text/css">
		body {
			padding-top: 50px;
			padding-bottom: 20px;
		}
	</style>
	
</head>
<body>
  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">	
        <a class="brand" href="#">Sandra Cirilli</a>
          <ul class="nav">
            <li class="active"><a href="#">Inicio</a></li>
            
            <li class="dropdown" >
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Clases<b class="caret"></b></a>
              <ul class="dropdown-menu" >
				<li><a href="http://www.sandracirilli.com.ar?page_id=34"><i class="icon-search"></i>Clases de folclore</a></li>
				<li><a href="http://www.sandracirilli.com.ar/?page_id=4"><i class="icon-search"></i>Seminarios</a></li>
                <li class="divider"></li>
                <li class="nav-header">Eventos especiales...</li>
                <li><a href="http://www.sandracirilli.com.ar/?page_id=4"><i class="icon-search"></i>Muestra 2012</a></li>
                <li><a href="http://www.sandracirilli.com.ar/?page_id=4"><i class="icon-search"></i>Actividades para docentes</a></li>
              </ul>
            </li>
			<li><a href="http://www.sandracirilli.com.ar">Multimedia</a></li>
          </ul>
          <div class="pull-right">
            <ul class="nav">
				<li><a href="http://www.sandracirilli.com.ar">Acerca de...</a></li>
               <li class="dropdown" >
                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">${usuarioLogeado.userName}<b class="caret"></b></a>
                 <ul class="dropdown-menu" >
                   <li><a href="/userprofile"><i class="icon-user"></i>Editar perfil</a></li>
                   <li><a href="/editPerson?action=browsePerson"><i class="icon-user"></i>Personas</a></li>
	                <li><a href="/article?action=browseArticle">Articulos</a></li>
                   <li class="divider"></li>
                   <li><a href="/logout"><i class="icon-remove"></i>Cerrar sesión</a></li>
                 </ul>
               </li>
            </ul>
          </div>
        </div>  <!--end container -->
     </div>	 <!--end navbar-inner -->
   </div> <!--end navbar -->

   <!-- Carousel DE FOTOS
         ================================================== -->
<section id="article">

<div class="container">
	<div class="hero-unit" >
 		<%
			List<ArticlePortlet> destacados = pbean.getDestacados();
			if(destacados !=null){
		%>
		<%
  			for(ArticlePortlet destacado: destacados){
		%>
		    <p><%= destacado.getArticle().getSection() %></p>
		    <h1><%= destacado.getArticle().getMainTitle() %></h1>
		    <p><%= destacado.getArticle().getSecondTitle() %></p>
		    <div class="item">
		      <img src="/image?pictureid=<%= destacado.getPicture().getPictureId() %>&version=O" alt=""  >
		    </div>
		  
		<% 
			}
		%>
		<% 
			}
		%>
	</div> <!-- end herunit -->
   </div><!--  end container -->
 </section> <!--  end section -->


 <section id="article-content">
	<div class="container container-fluid">
	<div class="row-fluid">
        <div class="span5">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
 		<%
			List<LocationRelation> locations;
			if(!(destacados==null ||destacados.isEmpty())){
				locations = destacados.get(0).getLocations();
				if(!(locations==null || locations.isEmpty())){			
		%>
		<%
  				for(LocationRelation data: locations){
		%>
            	<li class="lead">
					<p><%= data.getLocationHeading() %></p>
				</li>
				<li class="">
					<p><%= data.getLocationDescr() %></p>
					<p><a href="<%= data.getUrl() %>"  ><%= data.getPersonName() %></a></p>
					<p><%= data.getLocation1() %></p>
					<p><%= data.getLocation2() %></p>
					<p><%= data.getContactInfo() %></p>
				</li>
		<%
				}
				}
		%>
		<%
			if(destacados.get(0).getPictureBranding()!=null){
		%>
			<li>
				<img src="/image?pictureid=<%= destacados.get(0).getPictureBranding().getPictureId() %>&version=O" alt=""  >
			</li>
		<%
				}
			}
		%>
           	</ul>
          </div>
         </div> <!-- end span4 -->
 		<%
			if(destacados !=null){ for(ArticlePortlet destacado: destacados){
		%>
		<div class="span7"><%= destacado.getArticle().getTextData() %></div>
		<%
				}}
		%>
    </div> <!-- end row-fluid -->
	</div><!-- container -->
 </section> <!--  end section -->


<div class="container">
	<hr class="featurette-divider">
	 <jsp:include page="footer.jsp" flush="true" />
</div><!-- container -->


<!-- Modal viewer -->
<div class="modal hide fade in" id="picViewer"  data-keyboard=false data-backdrop=false tabindex="-1" role="dialog" aria-labelledby="picViewerLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
		<h3>viewer</h3>
	</div>
	
	<div id="callback123" class="modal-body">
	</div>
	<div class="modal-footer">
		<button class="close" data-dismiss="modal" aria-hidden="true">Close</button>
	</div>
</div>

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
	$('#picViewer').on('show', function () {
		//var el = $(this).attr('id');
		//var newHTML="<p>Alo! el:"+""+el+""+"  id:"+idProperty+"+"</p>";
		//var newHTML="<p>Alo!!!!"+idProperty+"</p>";
		
		var newHTML = "<img src='/image?pictureid="+idProperty+"&version=O'>";		
		document.getElementById('callback123').innerHTML = newHTML;
        });
	
    </script>
    <script>
	function setId(id){
		idProperty=id
	}
	var idProperty="id de la foto a renderizar en la ventana modal";
    </script>
    
    
	
</body>
</html>
