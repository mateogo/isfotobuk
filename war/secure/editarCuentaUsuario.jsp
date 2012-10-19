<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Editar Cuenta</title>
<meta name="GUI para aplicación is2011" content="">
<meta name="Grupo 4 - ¿nombre?" content="">
<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="/js/prettify.js"></script>
<script src="/js/bootstrap-modal.js"></script>
<script src="/js/menu.js" type="text/javascript"></script>
<script src="/js/editarCuentaUsuario.js" type="text/javascript"></script>

<link href="/css/bootstrap.css" rel="stylesheet">
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
	<form name="form" action="/editarCuenta" method="post">
		<input type="hidden" name="misFotos" value="misFotos">

		<!-- jpd / 15-10-2012 / llamada al jsp que resuelve la barra de navegacion -->
		<jsp:include page="topbar.jsp?bar=editarCuentaUsuario" flush="true" />

		<div class="container">
			<div class="content">
				<div class="row">
					<div class="clearfix">
						<div class="span16">
							<label for="mediumSelect">Direcci&oacute;n de mail:</label>
							<div class="input">
								<input id="mail" name="mailPrimario" type="text"
									value="${usuarioLogeado.mail}" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="clearfix">
						<div class="span5">
							<label>Direcci&oacute;n de mail alternativo:</label>
							<div class="input">
								<input id="mail2" name="mailSecundaro" type="text"
									value="${usuarioLogeado.mailSecundario}" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="clearfix">
						<div class="span16">
							<label for="mediumSelect">Nombre:</label>
							<div class="input">
								<input id="nomUsr" name="nombreUsr" type="text"
									value="${usuarioLogeado.nombre}" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="clearfix">
						<div class="span16">
							<label for="mediumSelect">Apellido:</label>
							<div class="input">
								<input id="apeUsr" name="apellidoUsr" type="text"
									value="${usuarioLogeado.apellido}" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="clearfix">
						<div class="span16">
							<label for="mediumSelect">Fecha de nacimiento:</label>
							<div class="input">
								<input id="fechaNac" name="fechaNacimiento" type="text"
									value="${usuarioLogeado.fechaNacimiento}" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="clearfix">
						<div class="span16">
							<label for="mediumSelect">Sexo:</label>
							<div class="input">
								<input type="radio" name="tipoSexo" id="masculino" value="M">&nbsp;Hombre&nbsp;</input><br />
								<input type="radio" name="tipoSexo" id="femenino" value="F">&nbsp;Mujer</input>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="clearfix">
						<div class="span16">
							<label for="mediumSelect">Pa&iacute;s o regi&oacute;n:</label>
							<div class="input">
								<select name="pais" id="idPais">
									<option value="">-- Seleccionar un pa&iacute;s --</option>
									<option value="1">Argentina</option>
									<option value="2">Brasil</option>
									<option value="3">Chile</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="clearfix">
						<div class="span16">
							<label for="mediumSelect">Provincia:</label>
							<div class="input">
								<select name="provincia" id="prov">
									<option value="">-- Seleccionar una provincia --</option>
									<option value="1">Buenos Aires</option>
									<option value="2">C&oacute;rdoba</option>
									<option value="3">Santa F&eacute;</option>
									<option value="4">Salta</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="clearfix">
						<div class="span16">
							<label for="mediumSelect">Cambiar Contrase&ntilde;a:</label>
							<div class="input">
								<input type="button" class="btn" name="botonCambiar"
									value="Cambiar">
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="clearfix">
						<div class="span16">
							<label for="mediumSelect">Cambiar pregunta secreta:</label>
							<div class="input">
								<select name="preguntaSecreta" id="pSecreta">
									<option value="">-- Seleccionar una pregunta --</option>
									<option value="1">Cu&aacute;l es el nombre de su
										mascota?</option>
									<option value="2">Nombre de tu abuela materna?</option>
									<option value="3">Apellido de soltera de su madre?</option>
									<option value="4">Cu&aacute;l es tu comida favorita?</option>
									<option value="5">Fecha de casamiento?</option>
									<option value="6">Nombre del primer colegio?</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="clearfix">
						<div class="span16">
							<label for="mediumSelect">Ingresar respuesta:</label>
							<div class="input">
								<input type="text" name="respuestaUsr" size="40" maxlength="42"
									id="respuesta" value="${usuarioLogeado.respuestaPregunta}" />
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="actions">
						<input type="button" class="btn" name="limpiar" value="Limpiar"
							onclick="limpiarCampos();"> <input class="btn primary"
							type="button" name="save" value="Guardar" onclick="guardar();">
					</div>
				</div>
			</div>
		</div>
	</form>

</body>
</html>
