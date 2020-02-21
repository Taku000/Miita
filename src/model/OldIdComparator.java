package model;

import java.util.Comparator;

public class OldIdComparator implements Comparator<Article>{
	public int compare(Article id1, Article id2) {

		return id1.id < id2.id ? -1 : 1;
	}

}
