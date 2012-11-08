<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.session.Session"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="ar.kennedy.is2011.db.entities.User"%>
<%@page import="ar.kennedy.is2011.models.SearchPicturesModel"%>
<%@page import="ar.kennedy.is2011.db.entities.AlbumEy"%>
<%@page import="java.util.Set"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.List"%>
<%@page import="ar.kennedy.is2011.db.entities.PictureEy"%>
<%@page import="java.util.Iterator"%>
<%
	WebUtils.validateMandatoryParameters(request, new String[] { "pictureid" });
	Session userSession = SessionManager.get(request,
			WebUtils.getSessionIdentificator(request));
	User user = (User) SessionManager.getCurrentUser(request);	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="ar.kennedy.is2011.social.Social"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Picture</title>
<meta name="GUI para aplicación is2011" content="">
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

.show-grid [class *="span"] {
	text-align: left;
}
</style>
</head>
<body>
  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">	
        <a class="brand" href="#">Fotobuk</a>
          <ul class="nav">
            <li class="active"><a href="/secure/main.jsp">Inicio</a></li>
            
            <li class="dropdown" >
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Acciones<b class="caret"></b></a>
              <ul class="dropdown-menu" >
                <li><a href="/secure/imageUpload.jsp"><i class="icon-upload"></i>Subir imagen</a></li>
                <li class="divider"></li>
                <li class="nav-header">Visualizar por...</li>
                <li><a href="/secure/albums.jsp"><i class="icon-search"></i>Album</a></li>
                <li><a href="/secure/search.jsp"><i class="icon-search"></i>Buscar</a></li>
              </ul>
            </li>
          </ul>
          <div class="pull-right">
            <ul class="nav">
               <li class="dropdown" >
                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">${usuarioLogeado.userName}<b class="caret"></b></a>
                 <ul class="dropdown-menu" >
                   <li><a href="/userprofile"><i class="icon-user"></i>Editar perfil</a></li>
                   <li class="divider"></li>
                   <li><a href="/logout"><i class="icon-remove"></i>Cerrar sesión</a></li>
                 </ul>
               </li>
            </ul>
          </div>
      </div>   <!-- end container -->
    </div>	 <!--end topbar-inner -->
  </div> <!--end topbr -->
	<div class="container">
		<div class="row">
			<ul class="media-grid">
				<li>
					<img class="span16" src="/image?pictureid=<%=WebUtils.getParameter(request, "pictureid")%>&version=O">
				</li>
				<li><%=Social.addLinks(WebUtils.getCompleteUrlForPicture(request,WebUtils.getParameter(request, "pictureid")), "Picture")%></li>
				<li>
					<button type="button" class="btn btn-link btn-primary" onclick="location.href='/setProfileImage?pictureid=<%=WebUtils.getParameter(request, "pictureid")%>'">establecer como foto del perfil</button>
				</li>
			</ul>
		</div>
	</div>

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="/js/bootstrap-2.1.1.js"></script>

</body>
</html>
