package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.PreArticleData;

public class SampleDAO implements Serializable{
	public static ArrayList<PreArticleData> RequestTable(String categori) {

		PreArticleData predata1 = new PreArticleData();
		PreArticleData predata2 = new PreArticleData();
		PreArticleData predata3 = new PreArticleData();
		PreArticleData predata4 = new PreArticleData();

		predata1.url = "hogehoge";
		predata1.categori = "java";
		predata1.id = 4;
		predata1.access = 7;

		predata2.url = "piyopiyo";
		predata2.categori = "java";
		predata2.id = 5;
		predata2.access = 2;

		predata3.url = "kerokero";
		predata3.categori = "java";
		predata3.id = 6;
		predata3.access = 1;

		predata4.url = "mogumogu";
		predata4.categori = "java";
		predata4.id = 7;
		predata4.access = 10;

		List<PreArticleData> predataList = new ArrayList<PreArticleData>();
		predataList.add(predata1);
		predataList.add(predata2);
		predataList.add(predata3);
		predataList.add(predata4);

		ArrayList<PreArticleData> predata = (ArrayList<PreArticleData>)predataList;
		return predata;

	}

}
