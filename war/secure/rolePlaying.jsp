<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%


%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Navidad 2012</title>
	<meta name="a quién le regalo?" content="">
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
			padding-top: 10px;
			padding-bottom: 40px;
		}
	</style>
	
</head>
<body>
	<div class="container">
		<div class="hero-unit row" >
		<div class="span12">
			<ul class="thumbnails">
				<li class="span11 thumbnail">
					<div class="span7"><h1>Feliz Navidad 2012 </h1></div>
					<div class="span3"><img src="/image?pictureid=E07jN53fpcji50Q&version=I" height="240" alt="Ultima foto"></div>
				</li>
				<li class="span12">
					<p>Primero seleccioná TU NOMBRE y luego cliqueá el botón para sortear a quién te toca hacerle un regalito</p>
				</li>
				<li class="span12">
					<div class="span6">
					<select id="person" name="person" class="span4" >
							<option value="sd"      >Tu nombre</option>
							<option value="Alfredo" >Alfredo</option>
							<option value="Estela"  >Estela</option>
							<option value="Fidela"  >Fidela</option>
							<option value="Judith"  >Judith</option>
							<option value="Luciano" >Luciano</option>
							<option value="Mateo"   >Mateo</option>
							<option value="Paula"   >Paula</option>
					</select>
					<button id="guessPerson" class="btn btn-primary btn-large"  onclick="selectBenefactor()">Sortear</button>
					</div>
					<div class="span5">
						<h2 id="result" ></h2>
					</div>
			</ul>	
		</div>    <!-- span9 -->
		</div>    <!-- hero unit -->
		

</div>    <!-- container >

    <!-- Le javascript============================================= -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="/js/bootstrap-2.1.1.js"></script>
    <!--   script src="/js/typeahe ad.js"></script    -->

    <script>
    $('#arName').typeahead({    		    
    	source: function(query, process) {
            $.ajax({
                url:"/articlelist",
                type: 'get',
                data: {query: query, type: 'ARTICULO' },
                dataType: 'json',
                success: function(json) {
                    return typeof json.options == 'undefined' ? false : process(json.options);
                }
            });
        }
    });
    </script>

	<script>
	function selectBenefactor(){
		var target = "/roleplaying";
		var person = document.getElementById('person').value;

		$.ajax({
			  url: target,
	          type: 'post',
	            data: {
	            	action: "randomperson",
	            	person: person,
	           	},
              dataType: 'text',
			  success: function(data){
	            	$('#guessPerson').attr( 'hidden'  );
	            	$('#result').html( data );
	            	
	            	
			  }
			}).done(function(data) {
				alert(data);
			});
	}
    </script>

</body>
</html>
