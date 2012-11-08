<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.db.entities.User"%>
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
	User user = new User();

	//paginas donde el usuario segun nuestro mapa de nevagacion se encuentra logueado
	if(request.getParameter("bar").equals("main") ||
			request.getParameter("bar").equals("search")  ||
			request.getParameter("bar").equals("editarCuentaUsuario")
		)
	{
		userView = new UserHomeView(request);
		searchPicturesModel = new SearchPicturesModel();
		user = (User) SessionManager.getCurrentUser(request);
	}
%>

<!--Header es un TAG NUEVO de HTML5
	* The <header> tag specifies a header for a document or section.
	* The <header> element should be used as a container for introductory content or set of navigational links.
	* You can have several <header> elements in one document. 
-->
<header>
	<div class="topbar">
		<div class="fill">
 			<div class="container">	
<%
	if(request.getParameter("bar").equals("main") ||
		request.getParameter("bar").equals("about") || 
		request.getParameter("bar").equals("index")){
%> 
		<h2 class="brand">Trabajo Pr&aacute;ctico</h2>
<%
	}else if(request.getParameter("bar").equals("search")) {
%>
		<h2 class="brand">Buscar</h2>
<%
	}else if(request.getParameter("bar").equals("editarCuentaUsuario")){
%>
		<h2 class="brand">Editar cuenta de Usuario</h2>
<%
	}
%>

<!--ANALISIS DE CONTENIDO ( A REVISAR ) 
	* Existe una barra de navegacion que varia segun donde estamos posicionados
		<nav>
--> 
		<nav>
			<ul class="nav">
<%
	if(request.getParameter("bar").equals("main")){
%>
				<li class="active"><a href="/secure/main.jsp">Inicio</a></li>
				<li><a href="/secure/albums.jsp">Albums</a></li>
				<li><a href="/secure/search.jsp">Buscar</a></li>
				<li><a href="/secure/imageUpload.jsp">Cargar imagen</a></li>
<%	}else if(request.getParameter("bar").equals("search")  ||
		request.getParameter("bar").equals("editarCuentaUsuario")){
%>
				<li class="active"><a href="/secure/main.jsp">Inicio</a></li>
<%
	}else if(request.getParameter("bar").equals("about") ||
			request.getParameter("bar").equals("index")){
%>
				<li class="active"><a href="/registracionRapida.jsp">Registrarse</a></li>
				<li class="active"><a href="/restablecerClave.jsp">Olvid&eacute; mi contrase&ntilde;a</a></li>
<%		
	}
%>
			</ul>
		
<!--ANALISIS DE CONTENIDO ( A REVISAR ) 
	* Se detectan 2 Situaciones : Se puede esta logueado o No se esta logueado 
	(Si no se esta logueado No hay necesidad de tener un FORM)
-->
<%
	if(request.getParameter("bar").equals("main") ||
		request.getParameter("bar").equals("search") || 
		request.getParameter("bar").equals("editarCuentaUsuario") ){
%>
			<div class="pull-right">
				<ul class="nav">
					<li class="active"><a href="/userprofile">Sesion ${usuarioLogeado.nombreUsr} </a></li>
					<li class="active"><a href="/logout"> Cerrar sesion </a></li>
				</ul>
			</div> 	
<%
	}else if(request.getParameter("bar").equals("about") || 
		request.getParameter("bar").equals("index")){
%>
			<form method="post" action="login" class="pull-right">
				<input class="input-small" type="text"     name="username" placeholder="Usuario"> 
				<input class="input-small" type="password" name="password" placeholder="Contrase&ntilde;a">
				<button class="btn" type="submit">Entrar</button>
			</form>
<%
	} 
%>
			</nav>
		</div>   <!-- end container -->
 </div>	 <!--end topbar-inner -->
</div> <!--end topbr -->
</header>