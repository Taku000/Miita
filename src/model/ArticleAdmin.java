package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.MiitaDAO;

//記事表示関係管理クラス
public class ArticleAdmin implements Serializable {
	static ArrayList<Article> articleList = new ArrayList<Article>();

	//カテゴリ検索用メソッド
	public static ArrayList<Article> categorySearch(String category) {

		//記事の情報をDBから貰う
		articleList = ArticleAdmin.sortArticles((MiitaDAO.tableSearchCategory(category)),"新着順");
		if (articleList == null) {
			return null;
		}
		return articleList;
	}

	//キーワード検索用メソッド
	public static ArrayList<Article> keywordSearch(String keyWord){

		//記事の情報をDBから貰う
		articleList = ArticleAdmin.sortArticles((MiitaDAO.tableSearchKeyword(keyWord)),"新着順");
		if (articleList == null) {
			return null;
		}
		return articleList;
	}


	public static ArrayList<Article> sortArticles(ArrayList<Article> listData, String sortWord){
		if (sortWord.equals("新着順")) {
			Collections.sort(listData, new NewIdComparator());
		}else if (sortWord.equals("投稿順") ) {
			Collections.sort(listData, new OldIdComparator());
		}else if(sortWord.equals("閲覧数順")) {
			Collections.sort(listData, new ViewComparator());
		}
		//並べ替えた記事リストを返却
		return listData;
	}

	//記事のリンクを取得するメソッド
	public static Article getContent(String urlString) {
		Article jsonArticle = new Article();

		String script = "";
		try {
			URL url = new java.net.URL(urlString);
			//HttpURLConnection型にキャスト
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			//URL要求のメソッドをGET
			con.setRequestMethod("GET");
			//通信リンクを確立
			con.connect();
			//入力ストリーム
			InputStream stream = con.getInputStream();
			//文字列のバッファの構築
			StringBuffer sb = new StringBuffer();
			//文字列入力型ストリームを作成
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			stream.close();
			script = sb.toString();
			//ObjectMapperオブジェクト宣言
			ObjectMapper mapper = new ObjectMapper();
			//JSON 形式に変換
			JsonNode node = mapper.readTree(script);
			//必要なものを取り出す
			String title = node.get("title").asText();
			String caption = (node.get("body").asText()).substring(0, 100).replace("\n", "").replace("#", "")
					.replace("*", "").replace("~", "");
			String userIcon = node.get("user").get("profile_image_url").asText();
			String authorName = node.get("user").get("github_login_name").asText();
			String date = (node.get("updated_at").asText()).substring(0, 10).replace("\"", "");
			String urlLink = node.get("url").asText();
//
//			jsonArticle.title = title;
//			jsonArticle.caption = caption;
//			jsonArticle.userIcon = userIcon;
//			jsonArticle.author = authorName;
//			jsonArticle.date = date;
//			jsonArticle.url = urlLink;

		} catch (Exception e) {
			return null;
		}
		return jsonArticle;
	}

}
