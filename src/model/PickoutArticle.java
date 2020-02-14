package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dao.SampleDAO;

public class PickoutArticle implements Serializable {
	public static ArrayList<Article> RequestArticle(String categori){

		List<PreArticleData> predata = SampleDAO.RequestTable(categori);

		Article articledata1 = new Article();
		Article articledata2 = new Article();
		Article articledata3 = new Article();
		Article articledata4 = new Article();

		articledata1.title = "題名１";
		articledata1.caption = "キャプション１---------";
		articledata1.view = predata.get(0).view;
		articledata1.author = "作者１";
		articledata1.id= predata.get(0).id;

		articledata2.title = "題名2";
		articledata2.caption = "キャプション2---------";
		articledata2.view = predata.get(1).view;
		articledata2.author = "作者2";
		articledata2.id= predata.get(1).id;

		articledata3.title = "題名3";
		articledata3.caption = "キャプション3---------";
		articledata3.view = predata.get(2).view;
		articledata3.author = "作者3";
		articledata3.id= predata.get(2).id;

		articledata4.title = "題名4";
		articledata4.caption = "キャプション4---------";
		articledata4.view = predata.get(3).view;
		articledata4.author = "作者4";
		articledata4.id= predata.get(3).id;

		ArrayList<Article> article = new ArrayList<Article>();
		article.add(articledata1);
		article.add(articledata2);
		article.add(articledata3);
		article.add(articledata4);

		return article;
	}
	
}
