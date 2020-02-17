package model;

import java.io.Serializable;

public  class PreArticleData implements Serializable{
	public  int id;
	public  String url;
	public  String category;
	public  int access;

	public PreArticleData() {}
	public PreArticleData(int id, String url, String category, int access) {
		this.id = id;
		this.url = url;
		this.category = category;
		this.access = access;
	}

}
