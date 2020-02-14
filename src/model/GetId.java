package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetId {
	public static void main(String[] args) {
        // TODO 自動生成されたメソッド・スタブ
        String urlString = "https://qiita.com/api/v2/items/f0e6fb4288705a639f87";
        System.out.println(getContent(urlString));
        System.out.println(getContent(urlString).length());
    }
    public static String getContent(String urlString) {
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
            //必要なものだけ取り出す
            String title = node.get("title").asText();
            String user = node.get("user").get("profile_image_url").asText();
            System.out.println("title:" + title);
            System.out.println("user:" + user);
        } catch (Exception e) {
        }
        return script;
    }
}
