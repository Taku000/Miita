<%@page import="model.ArticleAdmin"%>
<%@page import="org.eclipse.jdt.internal.compiler.batch.Main"%>
<%@ page import="javax.naming.directory.SearchResult"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Article"%>
<%@ page import="servlet.Home"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="javax.servlet.RequestDispatcher"%>

<!-- 記事リストを取得 -->
<%
 ArrayList<Article> articleList = (ArrayList<Article>)session.getAttribute("ARTICLE_LIST");
/* 並べ替え機能の条件保持用 */
	String searchKeyWordCondition = (String) request.getAttribute("SEARCH_KEYWORD_CONDITION") ;
	String searchCategoryCondition = (String) request.getAttribute("SEARCH_CATEGORY_CONDITION");
	String sortWord = (String) request.getAttribute("SORT_CONDITION");
	String registerError = (String) request.getAttribute("REGISTER_ERROR");
	int size;
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/css.css">
<link rel="stylesheet" type="text/css" href="css/ress.css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<title>テストページ</title>
</head>

<body>
	<header>
		<div id="head_bar">ロゴ</div>
		<div id="register_area">
			<!-- ここからモーダルウィンドウ -->
			<div id="modal-content">
				<!-- モーダルウィンドウのコンテンツ開始 -->
				<form name="regist_article" method="post" action="RegisterServlet">
				<div class="regist_title">Qiita記事登録画面</div>

				<div class="URL">
				URL:
					<input type="text"  name="regist_url" class="URL_text"  placeholder="登録したい記事のURLを入力"></input>
				</div>
				<p></p>
				<div class="category">
				カテゴリ:
					<select name="category1" class="category1" >
						<option  disabled value=""selected>登録カテゴリを選択</option>
						<option value="kaihatu">開発</option>
						<option value="koutiku">構築</option>
						<option value="DB">DB</option>
						<option value="other">その他</option>
					</select>
					<select name="category2" class="category2">

					</select>
				</div>
				<p></p>
				<div class="pass">
				削除パス:
					<input type="password" name="regist_pass" class="regist_pass" size="3" ></input>
				</div>
				<input type="submit" class="regist_button" value="登録">
				</form>
				<div id="modal-close" class="button-link">閉じる</div>
				<!-- モーダルウィンドウのコンテンツ終了 -->
			</div>
			<button  type="button"  id="modal-open" class="regist_modal">登録</button>
		</div>
	</header>
	<!-- キーワード検索機能 -->

	<form method="GET"  id="search_form" action="Home">
		<input type="text" placeholder="検索キーワードを入力" name="search_keyword" id="search_box">
		<button type="submit" class="search_button" ><i class="fas fa-search"></i></button>
	</form>


		<!-- カテゴリ検索機能 -->
	<!-- valueで検索するカテゴリを送信 -->
	<form method="GET" class="category_button"  action="Home">
		<button type="submit" class="java_buttton" name="search_category" value="Java" onclick= "assignmentCategory(Java)">Java</button>
	</form>
	<form method="GET" class="category_button" action="Home">
		<button type="submit" class="Linux_buttton" name="search_category" value="Linux" onclick= "assignmentCategory(Linux)">Linux</button>
	</form>
	<div id="contents_area">

	<!-- 並べ替え機能 -->
	<!-- セレクトボックスで選択した条件をボタンで送信 -->
		<form method="GET" action="Home">
				<select name ="sort" class="sort_box">
					<option value="新着順"<%= "新着順".equals(sortWord) ? " selected=\"selected\"" : "" %> >新着順</option>
					<option value="投稿順"<%= "投稿順".equals(sortWord) ? " selected=\"selected\"" : "" %>>投稿順</option>
					<option value="閲覧数順"<%= "閲覧数順".equals(sortWord) ? " selected=\"selected\"" : "" %>>閲覧数順</option>
				</select>
				<%
				if(searchCategoryCondition!=null){
					%>
					<select name ="search_category" >
						<option value="<%=searchCategoryCondition %>" selected ></option>
					</select>
					<%
				}if(searchKeyWordCondition!=null){
					%>
					<select name ="search_keyword" >
						<option value="<%=searchKeyWordCondition %>" selected ></option>
					</select>
					<%
					}
					%>
				<button type="submit" class ="sort_button"  name ="">並べ替え</button>
		</form>
		<!-- 記事の表示機能 -->
		<!-- 記事リストのスコープに何か入っていたら、並べて表示 -->
		<%
			if (articleList == null){
			//入っていなかった場合、新着5記事の取得
			ArticleAdmin admin = new ArticleAdmin();
			articleList = admin.categorySearch("all");
			session.setAttribute("ARTICLE_LIST", articleList);
		%>
		<%
			}
		%>
			<%
			for (int i = 0; i < (size = articleList.size()); i++) {
			%>
			<div id="contents_box">
                <img src="./img/<%=articleList.get(i).getCategory()%>.png" id="icon" >
				<div id="article_base">
					<div id="article_text">
						<div  id="article1">
							<div class="title"><%=articleList.get(i).getTitle()%>
								<a href= "<%=articleList.get(i).getUrl()%>"></a>
							</div>
							<div class="caption"><%=articleList.get(i).getCaption()%>
							</div>
						</div>
						<div  id="article2">
							<div class="author"><%=articleList.get(i).getUserName()%>
							</div>
							<div class="view_int">閲覧数:<%=articleList.get(i).getAccess()%>
							</div>
						</div>
                    </div>
                </div>
                <div id ="side_menu">
                    <div class ="date"><%=articleList.get(i).getDate()%>
                    </div>
                    <div class="delete">
                        削除
                    </div>
                </div>
			</div>
			<%
			}
			%>
	</div>
	<!-- 登録時ミスがあれば -->
	<%
			if (registerError != null ){

	%>
	<!--エラーメッセージをjs用の変数に格納  -->
	<script type="text/javascript">
		var error = '<%=registerError%>';
	</script>

				<div class="popup" id="js-popup">
				  <div class="popup-inner">
				    <div class="close-btn" id="js-close-btn"><i class="fas fa-times"></i></div>
				    <div  class="popmessage" id="js-popmessage"></div>
				  </div>
				  <div class="black-background" id="js-black-bg"></div>
				</div>
		<%
			}
		%>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript" src="js/regist.js"></script>
</body>
</html>