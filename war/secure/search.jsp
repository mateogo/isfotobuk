<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.session.Session"%>
<%@page import="ar.kennedy.is2011.session.SessionManager"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="ar.kennedy.is2011.db.entities.Usuario"%>
<%@page import="ar.kennedy.is2011.models.SearchPicturesModel"%>
<%@page import="ar.kennedy.is2011.db.entities.AlbumEy"%>
<%@page import="java.util.Set"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.List"%>
<%@page import="ar.kennedy.is2011.db.entities.PictureEy"%>
<%@page import="java.util.Iterator"%>

<%!public PictureEy getPicture(Iterator<PictureEy> iterator) {
		if (iterator.hasNext()) {
			return iterator.next();

		} else {
			return null;
		}
	}%>
<%
	Session userSession = SessionManager.get(request,
			WebUtils.getSessionIdentificator(request));
	Map<String, Object> errors = userSession.contains("errors") ? ((Map<String, Object>) userSession
			.getElement("errors")).containsKey("form_errors") ? (Map<String, Object>) ((Map<String, Object>) userSession
			.getElement("errors")).get("form_errors")
			: new HashMap<String, Object>()
			: new HashMap<String, Object>();
	SearchPicturesModel searchPicturesModel = new SearchPicturesModel();
	Usuario user = (Usuario) userSession.getElement("user");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Buscar</title>
<meta name="GUI para aplicación is2011" content="">
<meta name="Grupo 4 - ¿nombre?" content="">
<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="/js/bootstrap-modal.js"></script>
<link href="/css/bootstrap.css" rel="stylesheet">
<link href="/images/favicon.gif" rel="icon" type="image/gif">

<style type="text/css">
body {
	padding-top: 60px;
}

.show-grid [class*="span"] {
	text-align: left;
}
</style>
</head>
<body>

	<!-- jpd / 15-10-2012 / llamada al jsp que resuelve la barra de navegacion -->
	<jsp:include page="topbar.jsp?bar=search" flush="true" />


	<div class="container">
		<div class="content">
			<div class="row">
				<form id="search_form" name="search_form" method="post"
					action="/secure/search.jsp">
					<div class="clearfix">
						<div class="span16">
							<div class="input">
								<input id="search_value" name="search_value" type="text" /> <input
									class="btn primary" type="submit" name="search" value="Buscar">
							</div>
						</div>
					</div>
				</form>
			</div>
			<%
				if (StringUtils.isNotBlank(WebUtils.getParameter(request,"search_value"))) {
					List<PictureEy> pictures = searchPicturesModel.getPicturesByTags(WebUtils.getParameter(request, "search_value"));
					Iterator<PictureEy> iterator = pictures.iterator();
			%>
			<table>
				<%
					for (PictureEy picture : pictures) {
				%>
				<tr>
					<td>
						<ul class="media-grid">
							<li><a
								href="/secure/pictureView.jsp?pictureid=<%=picture.getPictureId()%>"><img
									class="thumbnail"
									src="/image?pictureid=<%=picture.getPictureId()%>&version=H"
									alt="" width="90" height="90"> </a></li>
						</ul>

					</td>
				</tr>
				<%
					}
				%>
			</table>
			<%
				}
			%>
		</div>
	</div>
</body>
</html>
