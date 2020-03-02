package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.SampleDAO;

public class PickoutArticle implements Serializable {
	//成形したjsonデータを一旦収納するインスタンス

	//カテゴリ検索用メソッド
	public static ArrayList<Article> categorySearch(String category) {
		//		記事の情報をDBから貰う
		ArrayList<Article> articleData = SampleDAO.requestTable(category);
		ArrayList<Article> articleList = new ArrayList<Article>();
		articleList = Sort.sortArticles(articleData,"new");
		if (articleList == null) {
			return null;
		}
		return articleList;
	}
	//キーワード検索用メソッド
	public static ArrayList<Article> keyWordSearch(String keyWord){

		ArrayList<Article> articleData = SampleDAO.searchTable(keyWord);
		ArrayList<Article> articleList = new ArrayList<Article>();
		articleList = Sort.sortArticles(articleData,"new");
		if (articleList == null) {
			return null;
		}
		return articleList;
	}
//		//		貰った情報を表示できる形に加工する
//		//記事数カウント変数
//		int size = articleData.size();
//		//predataのListの数だけarticleListにadd
//		for (int i = 0; i < size; i++) {
//			String qiitaId = CutOutURL(articleData.get(i).url);
//			String urlString = "https://qiita.com/api/v2/items/" + qiitaId;
//			Article articleData = new Article();
//			articleData = GetContent(urlString);
//			article.add(articleData);

	//URL切り取るメソッド
	public static String cutOutURL(String url) {
		String fullURL = url;
		String itemsId;
		String id;
		//受け取ったURLをitems以降の文字列に切り取り
		if (fullURL.contains("items/")) {
			itemsId = fullURL.substring(fullURL.indexOf("items/"));
			id = itemsId.substring(6);
		} else {
			itemsId = fullURL.substring(fullURL.indexOf("private/"));
			id = itemsId.substring(8);
		}
		return id;
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
