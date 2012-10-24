<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ar.kennedy.is2011.utils.WebUtils"%>
<%@page import="java.util.Date"%>


<%
	Date ts = new Date();
	WebUtils.validateMandatoryParameters(request, new String[] { "pictureid" });
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Picture</title>
</head>
<body>
	<div class="thumbnail">
		Picture: <%=WebUtils.getParameter(request, "pictureid")%>
		date:  <%=ts %>
		<!-- img src="/image?pictureid=<%=WebUtils.getParameter(request, "pictureid")%>&version=O"/> -->
	</div>
</body>
</html>
