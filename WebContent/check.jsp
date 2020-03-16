<%@ page contentType="text/html; charset=Shift_JIS" pageEncoding="UTF-8" %>
<%@ page import = "model.Article"%>
<%@ page import ="java.util.ArrayList"%>

<%
	request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div>
<%String result = (String) request.getParameter("delete_pass"); %>
<%String title = (String) request.getParameter("delete_id"); %>
<%= result %>
<%= title %>
</div>
</body>
</html>