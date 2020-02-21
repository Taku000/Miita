package model;

import java.util.ArrayList;
import java.util.Collections;

public  class Sort {
	//呼び出されたら並べ替え条件によって呼び出すコンストラクタを分岐（一纏めのクラスにする案欲しい）
	public static ArrayList<Article> SortArticles(ArrayList<Article> listData, String sortWord){
		if (sortWord.equals("new")) {
			Collections.sort(listData, new NewIdComparator());
		}else if (sortWord.equals("old") ) {
			Collections.sort(listData, new OldIdComparator());
		}else if(sortWord.equals("view")) {
			Collections.sort(listData, new ViewComparator());
		}
		//並べ替えた記事リストを返却
		return listData;


	}
}
