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
		request.setCharacterEncoding("UTF-8");
		String keyWord = request.getParameter("search_keyword");
		String category = request.getParameter("search_category");
		//どちらの検索リクエストがきたかチェック
		if (category != null) {
			//受け取ったカテゴリに該当する記事データを探してもらう
			ArrayList<Article> articleList = PickoutArticle.categorySearch(category);
			HttpSession session = request.getSession();
			session.setAttribute("ARTICLE_LIST", articleList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}else if (keyWord != null) {
			//受け取ったキーワードを含む記事データを探してもらう
			ArrayList<Article> articleList = PickoutArticle.keyWordSearch(keyWord);
			HttpSession session = request.getSession();
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
		HttpSession session = request.getSession();
		String sortWord = request.getParameter("sort");
		ArrayList<Article> articleList = (ArrayList<Article>) session.getAttribute("ARTICLE_LIST");
		//条件と記事のリストを載せてSortArticlesメソッドを呼び出す
		articleList = Sort.sortArticles(articleList, sortWord);
		request.setAttribute("SORT_CONDITION", sortWord);
		//並べ替えたリストをセッションスコープに上書きしてindex,jspに
		session.setAttribute("ARTICLE_LIST", articleList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

}
