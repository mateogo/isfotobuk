<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Ajax Example</title>

	<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
			<![endif]-->


	<link href="/images/favicon.gif" rel="icon" type="image/gif">
	<link href="/css/bootstrap-2.1.1.css" rel="stylesheet">

	<style type="text/css">	
		body {
			padding-top: 60px;
			/* 40px to make the container go all the way to the bottom of the topbar */
		}
	</style>

	</head>
	
	<body>
		<!-- Barra principal -->
		<div class="navbar navbar-inverse navbar-fixed-top">
		    <div class="navbar-inner">
		      <div class="container">		
		      <h3>Prueba de ajax</h3>
		      </div>   <!-- end container -->
		    </div>	 <!--end topbar-inner -->
	 	 </div> <!--end topbar -->
	
		<div class="container">
			<div class="content">
				<h3>Testeo de funciones asincronicas</h3>
				<div id="btn1" class="row">
					<div class="btn-group">
						<button type="button" class="btn btn-info btn-mini" name="editUserBtn"  onclick="getClic()" >send Ajax</button>
					</div>
				</div>
				<div id="texto1" class="row">
				</div>
				<div id="texto2" class="row">
				</div>

			</div>
		</div>
	
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	<script src="/js/bootstrap-2.1.1.js"></script>
	
	<script>
		function getClic(){
			$.ajax({
				  url: "/secure/modalPersonView.jsp?personid=37",
				  //context: document.body,
				  dataType: "text",
				  success: function(data){
					  document.getElementById('texto1').innerHTML = data;
				  }
				}).done(function(data) { 
				  //$(this).addClass("done");
					//var newHTML = "<p>Helloooo World</p>";		
					//document.getElementById('texto2').innerHTML = newHTML;
					//$('#texto2').append(data);
					alert(data)
				});	
		}
   </script>
     	
	</body>
</html>
