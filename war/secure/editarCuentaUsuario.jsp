<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editar datos del perfil</title>
<meta name="GUI para aplicación isfotobuk" content="">
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

.show-grid [class*="span"] {
	text-align: left;
}

label {
	width: 200px;
}

form .input {
	margin-left: 220px;
}
</style>

</head>

<body>
		<!-- jpd / 15-10-2012 / llamada al jsp que resuelve la barra de navegacion -->
		<jsp:include page="topbar.jsp?bar=editarCuentaUsuario" flush="true" />

	<div class="container">
	<form name="form" class="form-horizontal" action="/editarCuenta" method="post">
		<input type="hidden" name="misFotos" value="misFotos">

		<div class="control-group" class="span11">
			<label class="control-label" for="mail" >Mail:</label>
			<div class="controls" >
				<div class="input-prepend" >
					<span class="add-on"><i class="icon-envelope"></i></span>
					<input type="email" id="mail" class="span3" name="mailPrimario" 
						value="${usuarioLogeado.mail}" />
				</div>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="mail2">Mail alternativo:</label>
			<div class="controls">
				<div class="input-prepend" >
					<span class="add-on"><i class="icon-envelope"></i></span>
					<input type="email" id="mail2" class="span3" name="mailSecundario" 
						value="${usuarioLogeado.mailSecundario}" />
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="nomUsr">Nombre:</label>
			<div class="controls">
				<input id="nomUsr" name="nombreUsr" type="text"
					value="${usuarioLogeado.nombre}" />
			</div>
		</div>
		<div class="control-group">
					<label class="control-label" for="apeUsr">Apellido:</label>
					<div class="controls">
						<input id="apeUsr" name="apellidoUsr" type="text"
							value="${usuarioLogeado.apellido}" />
					</div>
		</div>
		<div class="control-group">
					<label class="control-label" for="fechaNac">Fecha de nacimiento:</label>
					<div class="controls">
						<input type="date" id="fechaNac" name="fechaNacimiento" 
							value="${usuarioLogeado.fechaNacimiento}" />
					</div>
		</div>
		<div class="control-group">
					<div class="controls">
					<span class"help-block">G&eacute;nero: </span>
					<label class="radio" for="masculino">
						<input type="radio" name="tipoSexo" id="masculino" value="M">
						&nbsp;Masculino&nbsp;
					</label>
					<label class="radio" for="femenino">
						<input type="radio" name="tipoSexo" id="femenino" value="F">
						&nbsp;Femenino
					</label>
					</div>
		</div>
		<div class="control-group">
					<label class="control-label" for="pais">Pa&iacute;s o regi&oacute;n:</label>
					<div class="controls">
						<select name="pais" id="idPais">
							<option value="">-- Seleccionar un pa&iacute;s --</option>
							<option value="1">Argentina</option>
							<option value="2">Brasil</option>
							<option value="3">Chile</option>
							<option value="4">Paraguay</option>
							<option value="3">Uruguay</option>
							<option value="3">Bolivia</option>
						</select>
					</div>
		</div>
		<div class="control-group">
					<label class="control-label" for="provincia">Provincia:</label>
					<div class="controls">
						<select name="provincia" id="prov">
							<option value="">-- Seleccionar una provincia --</option>
							<option value="1">CABA</option>
							<option value="2">Buenos Aires</option>
							<option value="3">C&oacute;rdoba</option>
							<option value="4">Santa F&eacute;</option>
							<option value="5">Salta</option>
						</select>
					</div>
		</div>
		<div class="control-group">
					<label class="control-label" for="mediumSelect">Contrase&ntilde;a:</label>
					<div class="controls">
						<input type="button" class="btn" name="botonCambiar"
							value="Cambiar">
					</div>
		</div>
		<div class="control-group">
					<label class="control-label" for="pSecreta">Pregunta secreta:</label>
					<div class="controls">
						<select name="preguntaSecreta" id="pSecreta">
							<option value="">-- Seleccionar una pregunta --</option>
							<option value="1">Cu&aacute;l es el nombre de su mascota?</option>
							<option value="2">Nombre de tu abuela materna?</option>
							<option value="3">Apellido de soltera de su madre?</option>
							<option value="4">Cu&aacute;l es tu comida favorita?</option>
							<option value="5">Fecha de casamiento?</option>
							<option value="6">Nombre del primer colegio?</option>
						</select>
					</div>
		</div>
		<div class="control-group">
					<label class="control-label" for="respuesta">Respuesta:</label>
					<div class="controls">
						<input type="text" name="respuestaUsr" size="40" maxlength="42"
							id="respuesta" value="${usuarioLogeado.respuestaPregunta}" />
					</div>
		</div>

		<div class="form-actions">
				<button type="submit" class="btn btn-primary" name="save" onclick="guardar()">guardar</button>
				<button type="submit" class="btn "            name="limpiar"  onclick="limpiarCampos()">limpiar</button>
		</div>
	</form>
	</div><!-- end container -->
	

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="/js/bootstrap-2.1.1.js"></script>
   <script src="/js/menu.js"></script>

	<script>
		var form = document.getElementsByName("form")[0];
		var mail = document.getElementById("mail");
		var mail2 = document.getElementById("mail2");
		var nombre = document.getElementById("nomUsr");
		var apellido = document.getElementById("apeUsr");
		var fechaNac = document.getElementById("fechaNac");
		var masculino = document.getElementById("masculino");
		var femenino = document.getElementById("femenino");
		var pais = document.getElementById("idPais");
		var prov = document.getElementById("prov");
		var pregunta = document.getElementById("pSecreta");
		var resp = document.getElementById("respuesta");

		cargarCombos();

		function limpiarCampos() {
			mail.value = "";
			mail2.value = "";
			nombre.value = "";
			apellido.value = "";
			fechaNac.value = "";
			//maculino.checked = "false";
			//femenino.checked = "false";
			pais.value = "";
			prov.value = "";
			pregunta.value = "";
			resp.value = "";
		}

		function guardar() {
			if (validarCamposMails()) {
				form.submit();
			}
		}

		function validarCamposMails() {
			if (mail.value == "" && mail2.value == "") {
				$('.alert-message').alert('close')
				alert("Debe de ingresar al menos una direccion de email");
				return false;
			}

			return true;
		}

		function cargarCombos() {
			pais.value = "${usuarioLogeado.pais}";
			prov.value = "${usuarioLogeado.idProvicia}";
			pregunta.value = "${usuarioLogeado.idPreguntaSecreta}";

			if ("${usuarioLogeado.sexo}" != "") {
				if ("${usuarioLogeado.sexo}" == "M") {
					masculino.checked = true;
				} else {
					femenino.checked = true;
				}
			}
		}

		function volverMisFotos() {
			form.action = "/forward";
			form.submit();
		}

		function salir() {
			form.action = "/logout_beta";
			form.submit();
		}

		//Creo una instancia de la clase Menu
		var menu1 = new Desplegable();
		//Creo una propiedad "items", la cual es el array de ítems que tendrá nuestro menu, la creo fuera de la clase ya que nos permite personalizar el menu sin tener que editar la clase.
		menu1.items = new Array([ "item-0", "javascript:;",
				"${usuarioLogeado.nombreUsr}" ], [ "item-1", "javascript:;",
				"Album de Fotos" ]);

		//Creo una propiedad "suitems", la cual es el array de sub ítems que apareceran dentro de cada ítem.
		menu1.subitems = new Array(
				[
						[ "subitem-0", "javascript:editar();", "Editar Cuenta",
								"_self" ],
						[ "subitem-1", "javascript:salir();", "Cerrar Sesion",
								"_self" ] ],
				[
						[ "subitem-0", "javascript:;", "Subir Fotos", "_self" ],
						[ "subitem-1", "javascript:;", "Editar Fotos", "_self" ],
						[ "subitem-2", "javascript:volverMisFotos();",
								"Mis Fotos", "_self" ] ]);
	</script>

</body>
</html>
