package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.PreArticleData;

public class SampleDAO implements Serializable{
	//DB接続用定数
	 static String DATABASE_NAME = "miita";
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
	public static ArrayList<PreArticleData> RequestTable(String catego) {
		//返り値用変数準備
		ArrayList<PreArticleData> preData = new ArrayList<PreArticleData>();


		//SQL文作成
			String sql =" select * from prearticledata where category=?";
			//接続＆return
			try  {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection(URL,USER,PASS);
				PreparedStatement stt = conn.prepareStatement(sql);
				stt.setString(1, catego);
				// データベースに対する処理
				ResultSet rs = stt.executeQuery();
				while(rs.next()) {
					//ヒットした数だけリストに格納
					int id = rs.getInt(1);
					String url = rs.getString(2);
					String category = rs.getString(3);
					int access = rs.getInt(4);
					preData.add(new PreArticleData(id, url, category, access));
				}
				return preData;


				} catch (SQLException | ClassNotFoundException e){
				     e.printStackTrace();
				     return null;
				}
	}
}
