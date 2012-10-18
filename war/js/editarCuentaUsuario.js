
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
	