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
import model.PickoutArticle;
import model.Sort;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//受け取る情報の入れ物用意
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ArrayList<Article> articleList = (ArrayList<Article>) session.getAttribute("ARTICLE_LIST");
		String keyWord = request.getParameter("search_keyword");
		String category = request.getParameter("search_category");
		String sortWord = request.getParameter("sort");
		//どういう検索リクエストがきたかチェック
		if (category != null && sortWord != null) {
			//カテゴリ検索の後、並び替え依頼
			articleList = PickoutArticle.categorySearch(category);
			articleList = Sort.sortArticles(articleList, sortWord);
			//並べ替えたリストをセッションスコープに上書きしてindex,jspに
			request.setAttribute("NOW_SEARCH_CATEGORY", category);
			request.setAttribute("SORT_CONDITION", sortWord);
			session.setAttribute("ARTICLE_LIST", articleList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}else if (category != null) {
			//受け取ったカテゴリに該当する記事データを探してもらう
			articleList = PickoutArticle.categorySearch(category);
			request.setAttribute("NOW_SEARCH_CATEGORY", category);
			request.setAttribute("SORT_CONDITION", sortWord);
			session.setAttribute("ARTICLE_LIST", articleList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}else if (keyWord != null) {
			//受け取ったキーワードを含む記事データを探してもらう
			articleList = PickoutArticle.keyWordSearch(keyWord);
			session.setAttribute("ARTICLE_LIST", articleList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}else if (sortWord != null){
		//条件と記事のリストを載せてSortArticlesメソッドを呼び出す
		articleList = Sort.sortArticles(articleList, sortWord);
		request.setAttribute("SORT_CONDITION", sortWord);
		//並べ替えたリストをセッションスコープに上書きしてindex,jspに
		session.setAttribute("ARTICLE_LIST", articleList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//index.jspのソートボタンから送られてきた、並べ替え条件と記事リストの取得
	}

}
