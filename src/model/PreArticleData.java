package model;

import java.io.Serializable;

public  class PreArticleData implements Serializable{
	public  int id;
	public  String url;
	public  String category;
	public  int access;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}
	public PreArticleData() {}
	public PreArticleData(int id, String url, String category, int access) {
		this.id = id;
		this.url = url;
		this.category = category;
		this.access = access;
	}

}
