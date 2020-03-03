package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.SampleDAO;



public class RegistArticle {
	public String  register(String registUrl, String registCategory) {
		String result;
		//URLがQiitaの記事かどうかをチェック
		if (registUrl.contains("qiita.com") &&
				(registUrl.contains("items") || registUrl.contains("private"))
				) {
			System.out.println("Qiitaの記事だから登録を続行するよ");
			//URL切り取って記事idに成形
			 String id = cutOutURL(registUrl);
			//成形したidを使って記事データ取得
			 Article registArticleData = connectionAPI(id);
			 //インスタンスにデータ追加
			 registArticleData.setUrl(registUrl);
			 registArticleData.setCategory(registCategory);
			 //アドレスの重複確認
			 SampleDAO.checkDuplication(registArticleData);
			 //DAOに登録を依頼する
			 SampleDAO.registerTable(registArticleData);
			 return result ="success";

		}else{
			System.out.println("Qiitaの記事じゃないからindexに戻すよ");
			return result ="notQiita";
		}
	}

	//URL切り取るメソッド
	public static String cutOutURL(String registURL) {
		String fullURL = registURL;
		String id;
		//受け取ったURLをconnectionAPI用の文字列に切り取り
		if (fullURL.contains("items/")) {
			id = fullURL.substring(fullURL.indexOf("items/"));
		} else {
			id = fullURL.substring(fullURL.indexOf("private/"));
		}
		if (id.contains("#")) {
			return(id.substring(0,(id.lastIndexOf("#"))));
		}
		return id;
	}

	//切り取り成形されたidを使ってAPI接続、記事のデータを取得するメソッド
	public static Article connectionAPI(String id) {
		String apiString = "https://qiita.com/api/v2/" + (id);
		Article registArticleData = new Article();
		String script ="";
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
            //ObjectMapperオブジェクト宣言
            ObjectMapper mapper = new ObjectMapper();
            //JSON 形式に変換
            JsonNode node = mapper.readTree(script);
            //必要なものを取り出す

            String title = (node.get("title").asText());
            String caption = (node.get("rendered_body").asText()).replaceAll("<.+?>", "").substring(0, 200);
            String userName = (node.get("user").get("id")).asText();
            StringBuilder buff = new StringBuilder();
            String tag;
            for (int i = 0; i < ( node.get("tags").size()); i++) {
            	String individualTag = (node.get("tags").get(i).get("name")).asText();
            	buff.append(individualTag + ",");
			}
            tag = buff.toString();
            String date = (node.get("created_at").asText()).substring(0, 10);


            registArticleData.setTitle(title);
            registArticleData.setCaption(caption);
            registArticleData.setUserName(userName);
            registArticleData.setTag(tag);
            registArticleData.setStringDate(date);


        } catch (Exception e) {

        }
        return registArticleData;
    }

}
