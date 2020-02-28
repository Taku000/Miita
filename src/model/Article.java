package model;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable{
	public int id;
	public String url;
	public String category;
	public String title;
	public String caption;
	public String userName;
	public String tag;
	public Date date;
	public int access;


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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}

	public Article() {}
	public Article(int id, String url,String category,String title, String caption, String userName, String tag, Date date, int access ) {
		this.id = id;
		 this.url = url;
		 this.category = category;
		 this.title = title;
		 this.caption = caption;
		 this.userName = userName;
		 this.tag = tag;
		 this.date = date;
		 this.access = access;

	}
	public int compareTo(Article date2) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

}