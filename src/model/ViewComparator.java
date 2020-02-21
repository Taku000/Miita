package model;

import java.util.Comparator;

public class ViewComparator implements Comparator<Article>{
	public int compare(Article access1, Article access2) {

		return access1.access < access2.access ? 1 : -1;
	}


}
