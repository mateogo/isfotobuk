<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.entities.Usuario"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.models.SearchPicturesModel"%>
<%@page import="ar.kennedy.is2011.views.UserHomeView"%>

<%
	/*jpd / 15-10-2012 / 
	- Las variables deben ser declaradas e inicializadas sino raise error!
	- Esto se hace cuando el usuario esta loggeado, sino .. raise error! 
	*/

	UserHomeView userView = new UserHomeView();
	SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
	Usuario user = new Usuario();

	//paginas donde el usuario segun nuestro mapa de nevagacion se encuentra logueado
	if(request.getParameter("bar").equals("main") ||
			request.getParameter("bar").equals("search")  ||
			request.getParameter("bar").equals("editarCuentaUsuario")
		)
	{
		userView = new UserHomeView(request);
		searchPicturesModel = new SearchPicturesModel();
		user = (Usuario) SessionManager.get(request, WebUtils.getSessionIdentificator(request)).getElement("user");
	}
%>

<!--Header es un TAG NUEVO de HTML5
	* The <header> tag specifies a header for a document or section.
	* The <header> element should be used as a container for introductory content or set of navigational links.
	* You can have several <header> elements in one document. 
-->
<header class="topbar">
<%
//-------------------------------------------------------------------------------------------------------------
if(request.getParameter("bar").equals("main") ||
	request.getParameter("bar").equals("about") || 
	request.getParameter("bar").equals("index"))
{
%> <h1 class="brand">Trabajo Pr&aacute;ctico</h1>
<%
}
else
{	if(request.getParameter("bar").equals("search")) // ------------------------------------------------------
	{
%><h1 class="brand">Buscar</h1>
<%
	}
	else
	{	if(request.getParameter("bar").equals("editarCuentaUsuario")) // --------------------------------------
		{
%><h1 class="brand">Editar cuenta de Usuario</h1>
<%
		}
%>
<%
	}
%>
<%
}
%>

<!--ANALISIS DE CONTENIDO ( A REVISAR ) 
	* Existe una barra de navegacion que varia segun donde estamos posicionados
--> 
	<div class="topbar-inner">
		<section class="container">
			<nav>
			<ul class="nav">
<%
//-------------------------------------------------------------------------------------------------------------
if(request.getParameter("bar").equals("main")) // -------------------------------------------------------------
{
%>
				<li class="active"><a href="/secure/main.jsp">Inicio</a></li>
				<li><a href="/secure/albums.jsp">Albums</a></li>
				<li><a href="/secure/search.jsp">Buscar</a></li>
				<li><a href="/secure/imageUpload.jsp">Cargar imagen</a></li>
<%
}
else
{
	// -------------------------------------------------------------------------------------------------------
	if(request.getParameter("bar").equals("search")  ||
		request.getParameter("bar").equals("editarCuentaUsuario")) // ------------------------------------------------------
	{
%>
				<li class="active"><a href="/secure/main.jsp">Inicio</a></li>
<%
	}
	else
	{
		//--------------------------------------------------------------------------------------------------------------------------
		if(request.getParameter("bar").equals("about") || 
			request.getParameter("bar").equals("index")) // --------------------------
		{
%>
				<li class="active"><a href="/registracionRapida.jsp">Registrarse</a></li>
				<li><a href="/restablecerClave.jsp">Olvid&eacute; mi contrase&ntilde;a</a></li>
<%		
		} // endif "about"
%>
<%		
	} // endif "editarCuentaUsuario"
%>
<%
}
%>
			</ul>
		</nav>
		
<!--ANALISIS DE CONTENIDO ( A REVISAR ) 
	* Se detectan 2 Situaciones : Se puede esta logueado o No se esta logueado 
	(Si no se esta logueado No hay necesidad de tener un FORM)
-->
<%
//-------------------------------------------------------------------------------------------------------------
if(request.getParameter("bar").equals("main") ||
		request.getParameter("bar").equals("search") || 
		request.getParameter("bar").equals("editarCuentaUsuario")
	) // -------------------------------------------------------------
{
%>			
		<p class="pull-right">
			Logueado como <a href="/secure/editarCuentaUsuario.jsp">${usuarioLogeado.nombreUsr}</a><a href="/logout"> Cerrar sesion </a>
		</p>
<%
}
else
{
	//--------------------------------------------------------------------------------------------------------------------------
	if(request.getParameter("bar").equals("about") || // -------------------------- 
		request.getParameter("bar").equals("index"))  // --------------------------
	{
%>			
		<form method="post" action="login" class="pull-right">
			<input class="input-small" type="text" name="username"
						placeholder="Usuario"> <input class="input-small"
						type="password" name="password" placeholder="Contrase&ntilde;a">
			<button class="btn" type="submit">Entrar</button>
		</form>
<%		
	} 
%>
<%
}
%>
		</section>
	</div>
</header>