<%@ page contentType="text/html; charset=Shift_JIS" pageEncoding="UTF-8" %>
<%@ page import = "model.Article"%>
<%@ page import= "servlet.MainServlet"%>
<%@ page import ="java.util.ArrayList"%>

<%
  ArrayList<Article> article = (ArrayList<Article>) session.getAttribute("ARTICLE_LIST");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div>
移動したよ
<%  if (article !=null)%>
</div>
</body>
</html>