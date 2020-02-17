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
	static Article jsonArticle = new Article();

	public static ArrayList<Article> RequestArticle(String category) {
		//		記事の情報をDBから貰う
		ArrayList<PreArticleData> predata = SampleDAO.RequestTable(category);

		//		貰った情報を表示できる形に加工する

		String prearticleId = CutOutURL(predata.get(0).url);
		String urlString = "https://qiita.com/api/v2/items/" + prearticleId;


		Article articledata = new Article();

		articledata = GetContent(urlString);


		//		Article articledata2 = new Article();
		//		Article articledata3 = new Article();
		//		Article articledata4 = new Article();
		//
		//		articledata1.title = "題名１";
		//		articledata1.caption = "キャプション１---------";
		//		articledata1.view = predata.get(0).access;
		//		articledata1.author = "作者１";
		//		articledata1.id= predata.get(0).id;
		//
		//		articledata2.title = "題名2";
		//		articledata2.caption = "キャプション2---------";
		//		articledata2.view = predata.get(1).access;
		//		articledata2.author = "作者2";
		//		articledata2.id= predata.get(1).id;
		//
		//		articledata3.title = "題名3";
		//		articledata3.caption = "キャプション3---------";
		//		articledata3.view = predata.get(2).access;
		//		articledata3.author = "作者3";
		//		articledata3.id= predata.get(2).id;
		//
		//		articledata4.title = "題名4";
		//		articledata4.caption = "キャプション4---------";
		//		articledata4.view = predata.get(3).access;
		//		articledata4.author = "作者4";
		//		articledata4.id= predata.get(3).id;

		ArrayList<Article> article = new ArrayList<Article>();
		article.add(articledata);
		//		article.add(articledata2);
		//		article.add(articledata3);
		//		article.add(articledata4);

		return article;
	}

	//URL切り取るメソッド
	public static String CutOutURL(String url) {
		String fullURL = url;
		String itemsId;
		String id;

		//受け取ったURLをitems以降の文字列に切り取り
		if (fullURL.contains("items/")) {
			itemsId = fullURL.substring(fullURL.indexOf("items/"));
			id = itemsId.substring(6);
		}
		else {
			itemsId = fullURL.substring(fullURL.indexOf("private/"));
			id = itemsId.substring(8);
		}
		return id;
	}

	//記事のリンクを取得するメソッド
	public static Article GetContent(String urlString) {

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
			String caption = (node.get("body").asText()).substring(0, 100).replace("\n", "").
					replace("#", "").replace("*", "").replace("~", "");
			String userIcon = node.get("user").get("profile_image_url").asText();
			String authorName = node.get("user").get("github_login_name").asText();
			String date = (node.get("updated_at").asText()).substring(0,10).replace("\"", "");

			jsonArticle.title = title;
			jsonArticle.caption = caption;
			jsonArticle.userIcon = userIcon;
			jsonArticle.author = authorName;
			jsonArticle.date = date;




		} catch (Exception e) {
			return null;
		}
		return jsonArticle;
	}

}
