package model;

import java.io.Serializable;
import java.util.Date;

/**
* 記事データクラス
* @param url URL
* @param category カテゴリ
* @param title タイトル
* @param caption キャプション
* @param userName 作成者
* @param tag タグ
* @param stringDate 作成日(String型)
* @param date 作成日(Date型)
* @param access アクセス数
*/
public class Article implements Serializable{
	private int id;
	private String url;
	private String category;
	private String title;
	private String caption;
	private String userName;
	private String tag;
	private String stringDate;
	private Date date;
	private int access;


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
	public String getStringDate() {
		return stringDate;
	}
	public void setStringDate(String stringDate) {
		this.stringDate = stringDate;
	}
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}



	public Article() {}

	/**
	* 登録用インスタンス作成コンストラクタ
	*/
	public Article(int id, String url,String category,String title, String caption, String userName, String tag, String stringDate, int access ) {
		this.id = id;
		 this.url = url;
		 this.category = category;
		 this.title = title;
		 this.caption = caption;
		 this.userName = userName;
		 this.tag = tag;
		 this.stringDate = stringDate;
		 this.access = access;

	}

	/**
	* 表示用インスタンス作成コンストラクタ
	*/
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