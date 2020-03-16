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
import java.util.Iterator;
import java.util.LinkedHashSet;

import model.Article;

public class MiitaDAO implements Serializable {
	//SQL文の変数用意
	public String sql;
	public ResultSet rs;


	//DB接続用定数
	private final String DATABASE_NAME = "miita_proto";
	private final String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
	private final String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME + PROPATIES;
	//DB接続用・ユーザ定数
	private final String USER = "root";
	private final String PASS = "";


	 /**
	   * ドライバ登録コンストラクタ
	   * Class.forName
	   */
	public MiitaDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	* カテゴリ検索用DB処理メソッド
	* @param categoryWord 検索したいカテゴリ名
	* @return 検索結果を格納したリスト
	*/
	public  ArrayList<Article> searchCategoryDB(String categoryWord) {
		ArrayList<Article> articleList = new ArrayList<Article>();

		//検索内容がallの場合、新着５記事を取り出す
		if (categoryWord.equals("all")) {
			sql = " select * from articles order by date desc limit 5;";
		} else {
			sql = " select * from articles where category=?;";
			//接続＆return
		}
		try(Connection conn = DriverManager.getConnection(this.URL, this.USER, this.PASS);
				PreparedStatement stt = conn.prepareStatement(sql);)
		{
			if (sql.contains("?")) {
				stt.setString(1, categoryWord);
			}
			// データベースに対する処理
			rs = stt.executeQuery();

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

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	* キーワード検索用DB処理メソッド
	* @param searchWord 検索したいカテゴリ名
	* @return 検索結果を格納したリスト
	*/
	public ArrayList<Article> searchKeywordDB(String searchWord) {
		ArrayList<Article> articleList = new ArrayList<Article>();

		//重複記事を格納しないために用意
		boolean result = true;
		LinkedHashSet<Integer> hs = new LinkedHashSet<Integer>();

		//検索語句の作成
		//全角スペースを半角スペースに変換、半角スペースで単語を区切る
		ArrayList<String> wordList = new ArrayList<String>(
				Arrays.asList(searchWord.replaceAll("　", " ").split(" ", 0)));

		//空文字要素を排除
		Iterator<String> reword = wordList.iterator();
		while (reword.hasNext()) {
			String str = reword.next();
			if (str.equals("")) {
				reword.remove();
			}
		}
		if (wordList.size()==0) {
			return articleList;
		}

		//SQL文パーツ準備
		String sql = "select * from articles WHERE (category LIKE ? "
				+ "or title LIKE ? "
				+ "or user_name LIKE ? "
				+ "or caption LIKE ? "
				+ "or tag LIKE ? )";
		String sql2 ="AND (category LIKE ? "
				+ "or title LIKE ? "
				+ "or user_name LIKE ? "
				+ "or caption LIKE ? "
				+ "or tag LIKE ? )";
		String end = ";";

		//検索語句の数によってSQL文の下地を成形
		if (wordList.size()<2) {

		}else{
			for(int i =1; i < wordList.size(); i++) {
				sql = sql + sql2;
			}
		}
		sql = sql + end;

		//接続＆return
		try (Connection conn = DriverManager.getConnection(this.URL, this.USER, this.PASS);
				PreparedStatement stt = conn.prepareStatement(sql);)
		{
			//SQL文内に検索語句を配置
			if (wordList.size()<2) {
				for(int i = 0; i < 5; i++) {
					int num = i + 1;
					stt.setString(num, "%" +wordList.get(0) + "%");
				}
			}else {
				for(int i = 0; i < wordList.size(); i++) {
					for (int j = 0; j < 5; j++) {
						int num = j+1;
						stt.setString(num+(5*i), "%" +wordList.get(i) + "%");
					}
				}
			}
			rs = stt.executeQuery();
			while (rs.next()){

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
			return articleList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	* 登録時重複記事チェックメソッド
	* @param registData 登録したい記事のデータ
	* @return String 重複の有無
	*/
	public  String checkDuplication(Article registData) {
		//データベースの中に既に同じURLが存在してないかチェックターン
		String checkResult;

		//SQL文作成
		sql = "select url  from  articles ;";

		try (Connection conn = DriverManager.getConnection(this.URL, this.USER, this.PASS);
				PreparedStatement stt = conn.prepareStatement(sql);)
		{
			// データベースに対する処理
			rs = stt.executeQuery();
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
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("MySQLとのやり取りでエラーが出たよ");
			return checkResult = "error";
		}
	}

	/**
	* 記事登録メソッド
	* @param registData 登録したい記事のデータ
	* @return boolean 処理の結果
	*/
	public  boolean registDB(Article registData) {

		//SQL文作成
		sql = " INSERT INTO ARTICLES(url,category,title,caption,user_name,tag,date)"
				+ "VALUES" + "(" + "'" + registData.getUrl()
				+ "','" + registData.getCategory() + "','" + registData.getTitle()
				+ "','" + registData.getCaption() + "','" + registData.getUserName()
				+ "','" + registData.getTag() + "','" + registData.getStringDate() + "'" + ");";
		try(Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement statement = conn.createStatement();)
		{
			// データベースに対する処理
			statement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	* パスワードDB比較用メソッド
	* @param inputPass 入力されたパスワード
	* @return 一致の是非
	*/
	public boolean passwordCompare(String inputPass) {
		//SQL文作成
		sql = "select pass from pass_tables ;";
		String basePass = null;

		try(Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement stt = conn.prepareStatement(sql);)
		{
			// データベースに対する処理
			rs = stt.executeQuery();
			while (rs.next()) {
			basePass = rs.getString(1);
			}
			if (inputPass.equals(basePass)) {
				 return true;
				}else {
					return false;
				}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	* 記事DB削除用メソッド
	* @param articleId 削除したい記事のID
	* @return 削除の成否
	*/
	public boolean deleteArticleDB(int articleId) {
		//sql文作成
		sql = "DELETE FROM articles WHERE id = "+ articleId +";" ;

		try(Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement statement = conn.createStatement();)
		{
			// データベースに対する処理
			statement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
