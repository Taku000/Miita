package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.MiitaDAO;

public class ArticleRegister {
	//MiitaDAOのインスタンス作成
			MiitaDAO mDao = new MiitaDAO();

	/**
	* 記事登録統括メソッド
	* @param registUrl 登録したい記事のURL
	* @param registCategory 登録したい記事のカテゴリ
	* @return 処理結果のString
	*/
	public  String register(String registUrl, String registCategory) {

		String result;
		boolean successOrFailure = true;


		//URLがQiitaの記事かどうかをチェック
		if (registUrl.contains("qiita.com") &&
				(registUrl.contains("items") ||
						registUrl.contains("private"))) {

			System.out.println("Qiitaの記事だから登録を続行するよ");

			//URL切り取って記事idに成形
			String id = cutOutURL(registUrl);

			//成形したidを使って記事データ取得
			Article articleData = connectionAPI(id);

			if (articleData == null) {
				System.out.println("記事が見つからないよ");
				return result = "notfound";
			}

			//インスタンスにまだ入れていないデータ追加
			articleData.setUrl(registUrl);
			articleData.setCategory(registCategory);

			//アドレスの重複確認
			result = mDao.checkDuplication(articleData);

			if (result.equals("duplication")) {
				return result;

			} else if (result.equals("error")) {
				return result;

			}

			//DAOに登録を依頼する

			successOrFailure = mDao.registDB(articleData);
			if (successOrFailure == false) {
				return result = "registFailure";

			}else {
				System.out.println("記事の登録に成功したよ");
				return result = "success";
			}

		} else {

			System.out.println("Qiitaの記事じゃないからindexに戻すよ");
			return result = "notQiita";

		}
	}

	/**
	* URL→API用ID変換メソッド
	* @param registUrl 登録したい記事のURL
	* @return 成形したAPI用ID
	*/
	public  String cutOutURL(String registURL) {
		String fullUrl = registURL;
		String id;
		//受け取ったURLをAPI用にid部分の文字列に切り取り
		if (fullUrl.contains("items/")) {
			id = fullUrl.substring(fullUrl.indexOf("items/"));
			id = id.substring(5);
		} else {
			id = fullUrl.substring(fullUrl.indexOf("private/"));
			id = id.substring(7);
		}
		if (id.contains("#")) {
			return (id.substring(0, (id.lastIndexOf("#"))));
		}
		return id;
	}

	/**
	* API接続メソッド
	* @param id 取得したい記事のid
	* @return 取得した記事のデータ
	*/
	public  Article connectionAPI(String id) {
		String apiString = "https://qiita.com/api/v2/items/" + (id);
		Article articleData = new Article();
		String script = "";

		try {
			URL url = new java.net.URL(apiString);
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
			if (script.equals("{\"message\":\"Not found\",\"type\":\"not_found\"}")) {
				throw new FileNotFoundException();
			}
			//ObjectMapperオブジェクト宣言
			ObjectMapper mapper = new ObjectMapper();
			//JSON 形式に変換
			JsonNode node = mapper.readTree(script);
			//必要なものを取り出す

			String title = (node.get("title").asText());
			String caption = (node.get("rendered_body").asText()).replaceAll("<.+?>", "").replaceAll("\n", "")
					.substring(0, 200);
			String userName = (node.get("user").get("id")).asText();
			StringBuilder buff = new StringBuilder();
			String tag;
			for (int i = 0; i < (node.get("tags").size()); i++) {
				String individualTag = (node.get("tags").get(i).get("name")).asText();
				buff.append(individualTag + ",");
			}
			tag = buff.toString();
			String date = (node.get("created_at").asText()).substring(0, 10);

			articleData.setTitle(title);
			articleData.setCaption(caption);
			articleData.setUserName(userName);
			articleData.setTag(tag);
			articleData.setStringDate(date);
		} catch (FileNotFoundException e) {
			System.out.println("記事が見つかりませんよ");
			return articleData = null;
		} catch (Exception e) {
		}
		return articleData;
	}


}

