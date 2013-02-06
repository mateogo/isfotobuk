<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.entities.User"%>
<%@page import="ar.kennedy.is2011.db.entities.PictureEy"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.models.SearchPicturesModel"%>
<%@page import="ar.kennedy.is2011.views.UserHomeView"%>
<%@page import="ar.kennedy.is2011.db.dao.ArticleBean"%>
<%@page import="ar.kennedy.is2011.db.dao.ArticlePortlet"%>
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
	<title>Fotobuk ::Inicio::</title>
	<meta name="Home del usuario una vez logueado" content="">
	<meta name="fotobuk uk" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">

	<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

	<link href="/css/bootstrap-2.1.1.css" rel="stylesheet">
	<link href="/images/favicon.gif" rel="icon" type="image/gif">
	<style>
	    /* GLOBAL STYLES
	    -------------------------------------------------- */
	    /* Padding below the footer and lighter body text */
	
	    body {
	      padding-bottom: 40px;
	      color: #5a5a5a;
	    }
	
	
	
	    /* CUSTOMIZE THE NAVBAR
	    -------------------------------------------------- */
	
	    /* Special class on .container surrounding .navbar, used for positioning it into place. */
	    .navbar-wrapper {
	      position: relative;
	      z-index: 10;
	      margin-top: 20px;
	      margin-bottom: -90px; /* Negative margin to pull up carousel. 90px is roughly margins and height of navbar. */
	    }
	
	    /* Remove border and change up box shadow for more contrast */
	    .navbar .navbar-inner {
	      border: 0;
	      -webkit-box-shadow: 0 2px 10px rgba(0,0,0,.25);
	         -moz-box-shadow: 0 2px 10px rgba(0,0,0,.25);
	              box-shadow: 0 2px 10px rgba(0,0,0,.25);
	    }
	
	    /* Downsize the brand/project name a bit */
	    .navbar .brand {
	      padding: 14px 20px 16px; /* Increase vertical padding to match navbar links */
	      font-size: 16px;
	      font-weight: bold;
	      text-shadow: 0 -1px 0 rgba(0,0,0,.5);
	    }
	
	    /* Navbar links: increase padding for taller navbar */
	    .navbar .nav > li > a {
	      padding: 15px 20px;
	    }
	
	    /* Offset the responsive button for proper vertical alignment */
	    .navbar .btn-navbar {
	      margin-top: 10px;
	    }
	
	
	
	    /* CUSTOMIZE THE NAVBAR
	    -------------------------------------------------- */
	
	    /* Carousel base class */
	    .carousel {
	      margin-bottom: 60px;
	    }
	
	    .carousel .container {
	      position: absolute;
	      right: 0;
	      bottom: 0;
	      left: 0;
	    }
	
	    .carousel-control {
	      background-color: transparent;
	      border: 0;
	      font-size: 120px;
	      margin-top: 0;
	      text-shadow: 0 1px 1px rgba(0,0,0,.4);
	    }
	
	    .carousel .item {
	      height: 500px;
	    }
	    .carousel img {
	      min-width: 100%;
	      height: 500px;
	    }
	
	    .carousel-caption {
	      background-color: transparent;
	      position: static;
	      max-width: 550px;
	      padding: 0 20px;
	      margin-bottom: 100px;
	    }
	    .carousel-caption h1,
	    .carousel-caption .lead {
	      margin: 0;
	      line-height: 1.25;
	      color: #fff;
	      text-shadow: 0 1px 1px rgba(0,0,0,.4);
	    }
	    .carousel-caption .btn {
	      margin-top: 10px;
	    }
	
	
	
	    /* MARKETING CONTENT
	    -------------------------------------------------- */
	
	    /* Center align the text within the three columns below the carousel */
	    .marketing .span4 {
	      text-align: center;
	    }
	    .marketing h2 {
	      font-weight: normal;
	    }
	    .marketing .span4 p {
	      margin-left: 10px;
	      margin-right: 10px;
	    }
	
	
	    /* Featurettes
	    ------------------------- */
	
	    .featurette-divider {
	      margin: 80px 0; /* Space out the Bootstrap <hr> more */
	    }
	    .featurette {
	      padding-top: 120px; /* Vertically center images part 1: add padding above and below text. */
	      overflow: hidden; /* Vertically center images part 2: clear their floats. */
	    }
	    .featurette-image {
	      margin-top: -120px; /* Vertically center images part 3: negative margin up the image the same amount of the padding to center it. */
	    }
	
	    /* Give some space on the sides of the floated elements so text doesn't run right into it. */
	    .featurette-image.pull-left {
	      margin-right: 40px;
	    }
	    .featurette-image.pull-right {
	      margin-left: 40px;
	    }
	
	    /* Thin out the marketing headings */
	    .featurette-heading {
	      font-size: 50px;
	      font-weight: 300;
	      line-height: 1;
	      letter-spacing: -1px;
	    }
	
	
	
	    /* RESPONSIVE CSS
	    -------------------------------------------------- */
	
	    @media (max-width: 979px) {
	
	      .container.navbar-wrapper {
	        margin-bottom: 0;
	        width: auto;
	      }
	      .navbar-inner {
	        border-radius: 0;
	        margin: -20px 0;
	      }
	
	      .carousel .item {
	        height: 500px;
	      }
	      .carousel img {
	        width: auto;
	        height: 500px;
	      }
	
	      .featurette {
	        height: auto;
	        padding: 0;
	      }
	      .featurette-image.pull-left,
	      .featurette-image.pull-right {
	        display: block;
	        float: none;
	        max-width: 40%;
	        margin: 0 auto 20px;
	      }
	    }
	
	
	    @media (max-width: 767px) {
	
	      .navbar-inner {
	        margin: -20px;
	      }
	
	      .carousel {
	        margin-left: -20px;
	        margin-right: -20px;
	      }
	      .carousel .container {
	
	      }
	      .carousel .item {
	        height: 300px;
	      }
	      .carousel img {
	        height: 300px;
	      }
	      .carousel-caption {
	        width: 65%;
	        padding: 0 70px;
	        margin-bottom: 40px;
	      }
	      .carousel-caption h1 {
	        font-size: 30px;
	      }
	      .carousel-caption .lead,
	      .carousel-caption .btn {
	        font-size: 18px;
	      }
	
	      .marketing .span4 + .span4 {
	        margin-top: 40px;
	      }
	
	      .featurette-heading {
	        font-size: 30px;
	      }
	      .featurette .lead {
	        font-size: 18px;
	        line-height: 1.5;
	      }
	
	    }
    </style>


</head>

<body>
    <!-- NAVBAR
          ================================================== -->

<div class="container navbar-wrapper">
  <div class="navbar navbar-inverse">
    <div class="navbar-inner">
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
     </div>	 <!--end topbar-inner -->
   </div> <!--end topbr -->
  </div><!-- end container -->


    <!-- Carousel DE FOTOS
          ================================================== -->
 	<section id="carousel">
		<%
			String picId;
			String active = "active";
			List<ArticlePortlet> destacados = pbean.getDestacados();
			int item=-1;
			if(destacados !=null){
		%>
		<!-- div  class="span10"   -->
		<div id="myCarousel" class="carousel slide"  >
		  <div class="carousel-inner">
		  
		<%
  			for(ArticlePortlet destacado: destacados){
  				item++;				
  				picId=destacado.getPicture().getPictureId();
  				active = (item==0?"active":"");
		%>
		    <div class="item <%= active %>">
		      <img src="/image?pictureid=<%= picId %>&version=O" alt=""  >
		      <div class="container">
		      <div class="carousel-caption">
		        <h1><%= destacado.getArticle().getMainTitle() %></h1>
		        <p><%= destacado.getArticle().getSecondTitle() %></p>
		        <a class="btn btn-large btn-primary" href="/home?action=article&articleid=<%= destacado.getArticle().getId() %>" >Ver info></a>
		      </div>
		      </div>
		    </div>
		  
		<% 
			}
		%>

		  </div>
		  <a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
		  <a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
		</div>

		 <!--  end div global span 10 -->
		<% 
			}
		%>
	 </section> <!--  end section -->

    <div class="container marketing">
      <!-- Three columns of text below the carousel -->
		<%
			List<ArticlePortlet> tripletes = pbean.getTriplets();
			item=-1;
			if(tripletes !=null){
		%>
	      <div class="row">	  
		<%
  			for(ArticlePortlet destacado: tripletes){
  				item++;				
  				picId=destacado.getPicture().getPictureId();
		%>
        <div class="span4">
          <img class="img-circle" src="/image?pictureid=<%= picId %>&version=O">
          <h3><%= destacado.getArticle().getSlug() %></h3>
          <p><%= destacado.getArticle().getSecondTitle() %></p>
          <p><a class="btn" href="/home?action=article&articleid=<%= destacado.getArticle().getId() %>" >Ver info &raquo;</a></p>
        </div><!-- /.span4 -->
		<% 
			}
		%>
      </div><!-- /.row -->
		<% 
			}
		%>
      <!-- START THE FEATURETTES -->
      <hr class="featurette-divider">
</div>
<div class="container"> </div>

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
    
    <script>
	$('#myCarousel').carousel({
		pause: "hover",
		interval: 3000			
        });
    </script>
    <script>
	//$('.carousel').on('slid', function(car) {
	//	$('.carousel').carousel('pause');
	//	
    //   });
    </script>
    
	
</body>
</html>
