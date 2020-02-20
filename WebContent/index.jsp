<%@ page import="javax.naming.directory.SearchResult"%>
<%@ page contentType="text/html; charset=Shift_JIS" pageEncoding="UTF-8"%>
<%@ page import="model.Article"%>
<%@ page import="servlet.MainServlet"%>
<%@ page import="java.util.ArrayList"%>

<%
	ArrayList<Article> articleList = (ArrayList<Article>) request.getAttribute("ARTICLE_LIST");
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
	<div class="sort">
			<select name="sort" class="sort_box">
				<option value="1" class ="sort1">新着順</option>
				<option value="2">投稿順</option>
				<option value="3">閲覧数順</option>
			</select>
		</div>

		<%
			if (articleList == null) {
		%>
		<%
			} else {
		%>
			<%
				for (int i = 0; i < (size = articleList.size()); i++) {
			%>
			<div id="contents_box">
                <img src="./img/<%=articleList.get(i).category%>.png" id="icon" >
				<div id="article_base">
					<div id="article_text">
						<div  id="article1">
							<div class="title"><%=articleList.get(i).title%>
							</div>
							<div class="caption"><%=articleList.get(i).caption%>
							</div>
							<a href= "<%=articleList.get(i).url%>"></a>
						</div>
						<div  id="article2">
							<div class="author"><%=articleList.get(i).userName%>
							</div>
							<div class="view_int">閲覧数:<%=articleList.get(i).access%>
							</div>
						</div>
                    </div>
                </div>
                <div id ="side_menu">
                    <div class ="date"><%=articleList.get(i).date%>
                    </div>
                    <div class="delete">
                        削除
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