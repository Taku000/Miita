package model;

import java.util.Comparator;

public class OldIdComparator implements Comparator<Article>{
	public int compare(Article date1, Article date2) {
		//日付「date1」と日付「date2」を比較して
		//「date1」が「date2」に対して未来なら1、過去なら-1、同日なら0を返す
		return date1.date.compareTo(date2.date);
	}

}
