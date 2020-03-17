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

import dao.MiitaDAO;
import model.Article;
import model.ArticleAdmin;
import model.ArticleRegister;

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
			throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		//登録用パラメータ
		String registURL = request.getParameter("regist_url");
		String registCategry = request.getParameter("category2");
		String registPass = request.getParameter("regist_pass");

		//削除用パラメータ
		String deleteId = request.getParameter("delete_id");
		String deletePass = request.getParameter("delete_pass");

		String deleteResult = null;
		String registerResult = null;


		ArticleRegister aRegister = new ArticleRegister();
		ArticleAdmin admin = new ArticleAdmin();
		MiitaDAO mDao = new MiitaDAO();

		if (deletePass != null) {
			//削除用パラメータがあれば

			if (mDao.passwordCompare(deletePass) && deleteId !=null) {
				//記事削除クラス呼び出し
				deleteResult = admin.deleteArticles(deleteId);

				//結果によって分岐
				if (deleteResult.equals("deleteSuccess")) {//削除成功

					request.setAttribute("RESULT_STATUS", deleteResult);
					HttpSession session = request.getSession();
					session.setAttribute("ARTICLE_LIST", null);

				}else {//削除失敗
					request.setAttribute("RESULT_STATUS", deleteResult);
				}

			}else if (!(mDao.passwordCompare(deletePass))) {//パスワードミス
				request.setAttribute("RESULT_STATUS", "miss");
			}

		}else if (registPass != null) {
			//登録用パラメータがあれば
			if(mDao.passwordCompare(registPass) && registCategry != null) {

				//記事登録クラスを呼びだす
				registerResult = aRegister.register(registURL,registCategry);

				//結果によって分岐

				if (registerResult.equals("success")) {//登録成功
					HttpSession session = request.getSession();
					session.setAttribute("ARTICLE_LIST", null);

				}else {//登録失敗
					request.setAttribute("RESULT_STATUS", registerResult);

				}

			}else if((!(mDao.passwordCompare(registPass)))&& registCategry !=null){//パスワードミス
				request.setAttribute("RESULT_STATUS", "miss");

			}else if (registCategry == null) {//カテゴリ未選択
				request.setAttribute("RESULT_STATUS", "categoryNull");
			}

		}
		request.removeAttribute("regist_url");
		request.removeAttribute("category2");
		request.removeAttribute("regist_pass");
		request.removeAttribute("delete_id");
		request.removeAttribute("delete_pass");
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

}
