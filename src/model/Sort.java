package model;

import java.util.ArrayList;
import java.util.Collections;

public  class Sort {
	//呼び出されたら並べ替え条件によって呼び出すクラスを分岐
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
}
