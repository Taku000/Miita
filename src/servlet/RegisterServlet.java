package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ArticleRegister;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String registURL = request.getParameter("regist_url");
		String registCategry = request.getParameter("category2");
		String pass = request.getParameter("regist_pass");
		String registerResult = null;


		if(pass.equals("mgt") && registCategry != null) {

			//記事登録クラスを呼びだす
			registerResult = ArticleRegister.register(registURL,registCategry);

			//結果によって分岐

			if (registerResult.equals("success")) {//登録成功
				HttpSession session = request.getSession();
				session.setAttribute("ARTICLE_LIST", null);

			}else {//登録失敗
				request.setAttribute("REGISTER_ERROR", registerResult);

			}

		}else if((!(pass.equals("mgt")))&& registCategry !=null){//パスワードミス
			request.setAttribute("REGISTER_ERROR", "miss");

		}else if (registCategry == null) {//カテゴリ未選択
			request.setAttribute("REGISTER_ERROR", "categoryNull");
		}

		request.removeAttribute("regist_url");
		request.removeAttribute("category2");
		request.removeAttribute("regist_pass");
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

}
