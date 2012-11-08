<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	/*jpd / 15-10-2012 / 
	- Las variables deben ser declaradas e inicializadas sino raise error!
	- Esto se hace cuando el usuario esta loggeado, sino .. raise error! 
	*/

%>

<header>
	<div class="topbar">
		<div class="fill">
 			<div class="container">	
		<h2 class="brand">Bienvenido</h2>

		<nav>
			<ul class="nav">
				<li class="active"><a href="/registracionRapida.jsp">Registrarse</a></li>
				<li class="active"><a href="/restablecerClave.jsp">Olvid&eacute; mi contrase&ntilde;a</a></li>
			</ul>
			<form method="post" action="login" class="pull-right">
				<input class="input-small" type="text"     name="username" placeholder="Usuario"> 
				<input class="input-small" type="password" name="password" placeholder="Contrase&ntilde;a">
				<button class="btn" type="submit">Entrar</button>
			</form>
			</nav>
		</div>   <!-- end container -->
 </div>	 <!--end topbar-inner -->
</div> <!--end topbr -->
</header>