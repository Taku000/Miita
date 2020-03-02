<%@ page contentType="text/html; charset=Shift_JIS" pageEncoding="UTF-8" %>
<%@ page import = "model.Article"%>
<%@ page import= "servlet.MainServlet"%>
<%@ page import ="java.util.ArrayList"%>

<%
	ArrayList<Article> article = (ArrayList<Article>) session.getAttribute("ARTICLE_LIST");
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
<%String regist_url = (String) request.getAttribute("regist_url"); %>
<%= regist_url %>
<%String category = (String) request.getAttribute("category2"); %>
<%= category %>
<%String pass = (String) request.getAttribute("regist_pass");%>
<%=!(pass.equals("mgt")) %>
</div>
</body>
</html>