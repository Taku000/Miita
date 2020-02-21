package model;

import java.util.ArrayList;
import java.util.Collections;

public  class Sort {
	public static ArrayList<Article> SortArticles(ArrayList<Article> listData, String sortWord){
		if (sortWord.equals("new")) {
			Collections.sort(listData, new NewIdComparator());
		}else if (sortWord.equals("old") ) {
			Collections.sort(listData, new OldIdComparator());
		}else if(sortWord.equals("view")) {
			Collections.sort(listData, new ViewComparator());
		}
		return listData;

	}
}
