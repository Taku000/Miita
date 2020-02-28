package model;

import java.util.Comparator;

public class NewIdComparator implements Comparator<Article>{
	public int compare(Article date1, Article date2) {
		//日付「date2」と日付「date1」を比較して
		//「date2」が「date1」に対して未来なら1、過去なら-1、同日なら0を返す
		return date2.date.compareTo(date1.date);
	}

}
