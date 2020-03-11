package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Article;
import model.ArticleAdmin;

@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Home() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//受け取る情報の入れ物用意
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		//記事表示用リスト
		ArrayList<Article> articleList = (ArrayList<Article>) session.getAttribute("ARTICLE_LIST");
		ArticleAdmin admin = new ArticleAdmin();

		String sortWord = request.getParameter("sort"); //並べ替え条件
		String category = request.getParameter("search_category"); //カテゴリ検索
		String keyWord = request.getParameter("search_keyword"); //キーワード検索

		//パラメータで処理分岐
		if (keyWord != null) {//キーワード検索のみ
			articleList = admin.keywordSearch(keyWord);
			request.setAttribute("SEARCH_KEYWORD_CONDITION", keyWord);//URLリクエストパラメーター用

		} else if (category != null) {//カテゴリ検索のみ
			articleList = admin.categorySearch(category);
			request.setAttribute("SEARCH_CATEGORY_CONDITION", category);

		}

		if (sortWord != null) {//並べ替え処理
			articleList = admin.sortArticles(articleList, sortWord);
			request.setAttribute("SORT_CONDITION", sortWord);
		}

		//記事リストを上書きしてフォワード

		session.setAttribute("ARTICLE_LIST", articleList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
