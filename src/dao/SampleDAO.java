package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;

import model.Article;

public class SampleDAO implements Serializable{
	static Connection conn = null;
	//DB接続用定数
	 static String DATABASE_NAME = "miita_proto";
	 static String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
	 static String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME+PROPATIES;
	//DB接続用・ユーザ定数
	 static String USER = "root";
	 static String PASS = "";

//	public static void connectDAO() {
//		DB接続一連の流れ
		//DB接続用定数
//		 String DATABASE_NAME = "miita";
//		 String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
//		 String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME+PROPATIES;
//		//DB接続用・ユーザ定数
//		 String USER = "root";
//		 String PASS = "";
//			try  {
//				Class.forName("com.mysql.cj.jdbc.Driver");
//				Connection conn = DriverManager.getConnection(URL,USER,PASS);
//				// データベースに対する処理
//				System.out.println("イエス");
//
//
// } catch (SQLException | ClassNotFoundException e){
//    e.printStackTrace();
// }
//	}
	public static ArrayList<Article> requestTable(String catego) {
		String sql;
		//返り値用変数準備
		ArrayList<Article> articleData = new ArrayList<Article>();

		//SQL文作成
		//検索内容がallの場合、新着５記事を取り出す
		if (catego.equals("all")) {
			sql = " select * from articles order by date limit 5";
		} else {
			sql = " select * from articles where category=?";

			//接続＆return
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASS);
			PreparedStatement stt = conn.prepareStatement(sql);
			if (sql.contains("?")) {
				stt.setString(1, catego);
			}
			// データベースに対する処理
			ResultSet rs = stt.executeQuery();
			while (rs.next()) {
				//ヒットした数だけリストに格納

				int id = rs.getInt(1);
				String url = rs.getString(2);
				String category = rs.getString(3);
				String title = rs.getString(4);
				String caption = rs.getString(5);
				String userName = rs.getString(6);
				String tag = rs.getString(7);
				Date date = rs.getDate(8);
				int access = rs.getInt(9);
				articleData.add(new Article(id, url, category, title, caption, userName, tag, date, access));
			}
			return articleData;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}finally {
			try{
			    if (conn != null){
			      conn.close();
			    }
			  }catch (SQLException e){
				  e.printStackTrace();
			  }
		}

	}
	public static ArrayList<Article> searchTable(String searchWord) {
		//返り値用変数 用意
		ArrayList<Article> articleData = new ArrayList<Article>();
		//重複記事を格納しないために用意
		boolean result = true;
		LinkedHashSet<Integer> hs = new LinkedHashSet<Integer>();
		//接続＆return
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASS);

			//検索語句の作成
			//全角スペースを半角スペースに変換、半角スペースで単語を区切る
			String[] separateWord = searchWord.replaceAll("　", " ").split(" ", 0);
			String sql = "select * from articles WHERE (category LIKE ?) "
					+ "OR (title LIKE ?)"
					+ "OR (user_name LIKE ?)"
					+ "OR(caption LIKE ?)"
					+ "OR(tag LIKE ?);";

			PreparedStatement pStatement = conn.prepareStatement(sql);

			//区切った単語の数だけ回す
			for (int i = 0; i < separateWord.length; i++) {
				// パラメータの数分回す(5つ)
				for (int j = 0; j < 5; j++) {
					int num = j + 1;
					pStatement.setString(num, "%" + separateWord[i] + "%");
				}
				ResultSet rs = pStatement.executeQuery();
				while (rs.next()) {

					int id = rs.getInt(1);
					String url = rs.getString(2);
					String category = rs.getString(3);
					String title = rs.getString(4);
					String caption = rs.getString(5);
					String userName = rs.getString(6);
					String tag = rs.getString(7);
					Date date = rs.getDate(8);
					int access = rs.getInt(9);
					Article article = new Article(id, url, category, title, caption, userName, tag, date, access);

					//セットにarticleクラスのIDを格納する
					//setに重複するIDがない→true
					//重複するIDがある→false
					result = hs.add(article.getId());
					//※ 確認用コンソール出力 System.out.println(result);

					//trueならそのまま記事をリストに格納
					if (result == true) {
						articleData.add(article);
						//falseならcontinue
					} else {
						continue;
					}
				}
			}
			return articleData;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try{
			    if (conn != null){
			      conn.close();
			    }
			  }catch (SQLException e){
				  e.printStackTrace();
			  }
		}
		return articleData;

	}
	//登録時重複記事チェックメソッド
	public static String checkDuplication(Article registData) {
		//データベースの中に既に同じURLが存在してないかチェックターン
		String checkResult;
		String sql;
				//SQL文作成
				sql = "select url  from  articles ;";

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection(URL, USER, PASS);
					PreparedStatement stt = conn.prepareStatement(sql);
					// データベースに対する処理
					ResultSet rs = stt.executeQuery();
					while (rs.next()) {
						//getString(1)==URL
						String url = rs.getString(1);
						if (registData.getUrl().equals(url)) {
							System.out.println("URLが重複してるよ");
							return checkResult = "duplication";
						}
					}
					System.out.println("URLは重複してないよ");
					return checkResult = "noDuplication";
				}catch(SQLException | ClassNotFoundException e) {
					e.printStackTrace();
					System.out.println("MySQLとのやり取りでエラーが出たよ");
					return checkResult = "error";
				}finally {
					try{
					    if (conn != null){
					      conn.close();
					    }
					  }catch (SQLException e){
						  e.printStackTrace();
						  return checkResult = "error";
					  }
				}
	}


	//記事登録メソッド
	public static boolean registerTable (Article registData){

		String sql;
		//SQL文作成
		sql = " INSERT INTO ARTICLES(url,category,title,caption,user_name,tag,date)"
				+ "VALUES" + "(" + "'" + registData.getUrl()
				+ "','" + registData.getCategory() + "','" + registData.getTitle()
				+ "','" + registData.getCaption() + "','" + registData.getUserName()
				+ "','" + registData.getTag() + "','" + registData.getStringDate() + "'" + ");";
		System.out.println();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASS);
			Statement statement = conn.createStatement();
			// データベースに対する処理
			statement.executeUpdate(sql);
			return true;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}finally {
			try{
			    if (conn != null){
			      conn.close();
			    }
			  }catch (SQLException e){
				  e.printStackTrace();
			  }
		}
	}
}
