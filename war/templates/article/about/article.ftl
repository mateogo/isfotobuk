<#ftl strip_whitespace=true>
<#import "/lib/commons/headings.ftl" as h>
<#assign usuarioLogeado="mateogoliche" />
<#escape x as x?html>
<!DOCTYPE html>
<html lang="en">
<head>
	<@h.head title="Acerca de..." mname="datos institucionales del sitio" />
	
	<style type="text/css">
		body {
			padding-top: 50px;
			padding-bottom: 20px;
		}
	</style>
	
</head>
<body>
   <@h.topmenu brand="fokotuk" />
   <!-- Carousel DE FOTOS
         ================================================== -->
<section id="article">

<div class="container">
	<div class="hero-unit" >
 		    <p>Bienvenido a este espacio</p>
		    <h1>Quienes somos</h1>
		    <p>Bajada del titulo para explicar quienes somos</p>
		    <div class="item">
		      <img src="/image?pictureid=aRCnXK1SSQZuWx5&version=O" alt="taller de folklore"  >
		    </div>
	</div> <!-- end herunit -->
   </div><!--  end container -->
 </section> <!--  end section -->


 <section id="article-content">
	<div class="container container-fluid">
	<div class="row-fluid">
        <div class="span5">
          <div class="well sidebar-nav">
			<ul class="nav nav-list"><li>
			<button type="button" class="btn btn-link btn-mini" id="addEntitysBtn" name="addEntitysBtn" onclick="renderData()" >location</button>
			</li></ul>
            <ul id="locationdata" class="nav nav-list">
           	</ul>
          </div>
         </div> <!-- end span4 -->
		<div class="span7">D E S T A C A D O S</div>
    </div> <!-- end row-fluid -->
	</div><!-- container -->
 </section> <!--  end section -->


<div class="container">
	<hr class="featurette-divider">
	<footer>
		<@h.footer />
	</footer>
</div><!-- container -->

    <!-- Le javascript
    ================================================== -->
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
	$(document).ready(function () {
		var target = "/render/location";
		
		$.ajax({
			  url: target,
			  success: function(data){
				 $('#locationdata').html(data);
			  }
			}).done(function(data) { 
			});	

        });
    </script>	
  
  </body>
</html>
</#escape>

