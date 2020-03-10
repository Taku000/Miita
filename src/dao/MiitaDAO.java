package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;

import model.Article;

public class MiitaDAO implements Serializable {

	public String sql;

	public Connection conn = null;
	//DB接続用定数
	private String DATABASE_NAME = "miita_proto";
	private String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
	private String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME + PROPATIES;
	//DB接続用・ユーザ定数
	private String USER = "root";
	private String PASS = "";

	//DBカテゴリ検索用メソッド
	public  ArrayList<Article> tableSearchCategory(String categoryWord) {
		ArrayList<Article> articleList = new ArrayList<Article>();

		//検索内容がallの場合、新着５記事を取り出す
		if (categoryWord.equals("all")) {
			sql = " select * from articles order by date desc limit 5;";
		} else {
			sql = " select * from articles where category=?;";
			//接続＆return
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASS);
			PreparedStatement stt = conn.prepareStatement(sql);
			if (sql.contains("?")) {
				stt.setString(1, categoryWord);
			}
			// データベースに対する処理
			ResultSet rs = stt.executeQuery();

			//ヒットした数だけリストに格納
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

				articleList.add(new Article(id, url, category, title, caption, userName, tag, date, access));
			}
			return articleList;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	//DBキーワード検索用メソッド
	public  ArrayList<Article> tableSearchKeyword(String searchWord) {
		ArrayList<Article> articleList = new ArrayList<Article>();

		//重複記事を格納しないために用意
		boolean result = true;
		LinkedHashSet<Integer> hs = new LinkedHashSet<Integer>();

		//接続＆return
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASS);

			//検索語句の作成
			//全角スペースを半角スペースに変換、半角スペースで単語を区切る
			ArrayList<String> wordList = new ArrayList<String>
			(Arrays.asList(searchWord.replaceAll("　", " ").split(" ",0)));

			sql = "select * from articles WHERE (category LIKE ?) "
					+ "OR (title LIKE ?)"
					+ "OR (user_name LIKE ?)"
					+ "OR(caption LIKE ?)"
					+ "OR(tag LIKE ?);";

			PreparedStatement pStatement = conn.prepareStatement(sql);

			//区切った単語の数だけ回す
			for (int i = 0; i < wordList.size(); i++) {
				// パラメータの数分回す(5つ)
				for (int j = 0; j < 5; j++) {
					int num = j + 1;
					pStatement.setString(num, "%" + wordList.get(i) + "%");
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
						articleList.add(article);
						//falseならcontinue
					} else {
						continue;
					}
				}
			}
			return articleList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return articleList;
	}

	//登録時重複記事チェックメソッド
	public  String checkDuplication(Article registData) {
		//データベースの中に既に同じURLが存在してないかチェックターン
		String checkResult;

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
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("MySQLとのやり取りでエラーが出たよ");
			return checkResult = "error";
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return checkResult = "error";
			}
		}
	}

	//記事登録メソッド
	public  boolean registerTable(Article registData) {

		//SQL文作成
		sql = " INSERT INTO ARTICLES(url,category,title,caption,user_name,tag,date)"
				+ "VALUES" + "(" + "'" + registData.getUrl()
				+ "','" + registData.getCategory() + "','" + registData.getTitle()
				+ "','" + registData.getCaption() + "','" + registData.getUserName()
				+ "','" + registData.getTag() + "','" + registData.getStringDate() + "'" + ");";
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
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
