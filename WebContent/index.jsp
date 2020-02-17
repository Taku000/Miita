<%@ page import="javax.naming.directory.SearchResult"%>
<%@ page contentType="text/html; charset=Shift_JIS" pageEncoding="UTF-8"%>
<%@ page import="model.Article"%>
<%@ page import="servlet.MainServlet"%>
<%@ page import="java.util.ArrayList"%>

<%
	ArrayList<Article> articles = (ArrayList<Article>) request.getAttribute("ARTICLE_LIST");
	int size;
%>


<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/css.css">
<link rel="stylesheet" type="text/css" href="css/ress.css">
<title>テストページ</title>
</head>

<body>
	<header>
		<div id="head_bar">ロゴ</div>
	</header>

	<form method="GET" action="/Sample00/MainServlet">
		<button type="submit" class="java_buttton" name=category value="Java">Java</button>
	</form>
	<div id="contents_area">

		<%
			if (articles == null) {
		%>
		<%
			} else {
		%>
			<%
				for (int i = 0; i < (size = articles.size()); i++) {
			%>
			<div id="contents_box">
				<div id="icon">
				</div>
				<div id="article_base">
					<div id="article_text">
						<div  id="article1">
							<div class="title"><%=articles.get(i).title%>
							</div>
							<div class="caption"><%=articles.get(i).caption%>
							</div>
						</div>
						<div  id="article2">
							<div class="author"><%=articles.get(i).author%>
							</div>
							<div class="view_int">閲覧数:<%=articles.get(i).view%>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%
			}
			%>

		<%
			}
		%>
	</div>
</body>
</html>