package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMySQL {
	public static void main(String[] args) {
		//DB接続用定数
		 String DATABASE_NAME = "miita";
		 String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
		 String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME+PROPATIES;
		//DB接続用・ユーザ定数
		 String USER = "root";
		 String PASS = "";
			try  {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection(URL,USER,PASS);
      // データベースに対する処理
      System.out.println("イエス");

  } catch (SQLException | ClassNotFoundException e){
     e.printStackTrace();
  }
}
}