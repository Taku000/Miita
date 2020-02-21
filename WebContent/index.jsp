<%@ page import="javax.naming.directory.SearchResult"%>
<%@ page contentType="text/html; charset=Shift_JIS" pageEncoding="UTF-8"%>
<%@ page import="model.Article"%>
<%@ page import="servlet.MainServlet"%>
<%@ page import="java.util.ArrayList"%>

<!-- 記事リストを取得、nullかどうかで表示分岐 -->
<%
	ArrayList<Article> articleList = (ArrayList<Article>) session.getAttribute("ARTICLE_LIST");
/* 並べ替え機能の条件保持用 */
	String sortWord = (String) request.getAttribute("SORT_CONDITION");

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
	<!-- カテゴリ検索機能 -->
	<!-- valueで検索するカテゴリを送信 -->
	<form method="GET" id="category_button"  action="/Sample00/MainServlet">
		<button type="submit" class="java_buttton" name=category value="Java">Java</button>
	</form>
	<form method="GET" id="category_button" action="/Sample00/MainServlet">
		<button type="submit" class="Linux_buttton" name=category value="Linux">Linux</button>
	</form>
	<div id="contents_area">
	<!-- 並べ替え機能 -->
	<!-- セレクトボックスで選択した条件をボタンで送信 -->
		<form method="POST" action="/Sample00/MainServlet">
				<select name ="sort" class="sort_box">
					<option value="new"<%= "new".equals(sortWord) ? " selected=\"selected\"" : "" %> >新着順</option>
					<option value="old"<%= "old".equals(sortWord) ? " selected=\"selected\"" : "" %>>投稿順</option>
					<option value="view"<%= "view".equals(sortWord) ? " selected=\"selected\"" : "" %>>閲覧数順</option>
				</select>
			<button type="submit" class ="sort_button" name ="sort" >並べ替え
			</button>
		</form>
		<!-- 記事の表示機能 -->
		<!-- 記事リストのスコープに何か入っていたら、並べて表示 -->
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