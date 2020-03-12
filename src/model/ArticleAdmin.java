package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dao.MiitaDAO;

//記事表示関係管理クラス
public class ArticleAdmin implements Serializable {

	MiitaDAO mDao = new MiitaDAO();

	/**
	* カテゴリ検索用メソッド
	* @param category 検索したいカテゴリ名
	* @return 検索結果を格納したリスト
	*/
	public  ArrayList<Article> categorySearch(String category) {
		ArrayList<Article> articleList = new ArrayList<Article>();

		//カテゴリで記事を検索した後、新着順で並べて返却する
		articleList = sortArticles((mDao.searchCategoryDB(category)), "新着順");
		if (articleList == null) {
			return null;
		}
		return articleList;
	}

	/**
	* キーワード検索用メソッド
	* @param keyword 検索したいキーワード
	* @return 検索結果を格納したリスト
	*/
	public  ArrayList<Article> keywordSearch(String keyword) {
		ArrayList<Article> articleList = new ArrayList<Article>();

		//キーワードで記事を検索した後、新着順で並べて返却する
		articleList = sortArticles((mDao.searchKeywordDB(keyword)), "新着順");
		if (articleList == null) {
			return null;
		}
		return articleList;
	}

	/**
	* 並べ替え用メソッド
	* @param articleList 記事のリスト
	* @param sortCondition 並び替えの条件
	* @return 検索結果を格納したリスト
	*/
	public  ArrayList<Article> sortArticles(ArrayList<Article> articleList, String sortCondition) {
		if (sortCondition.equals("新着順")) {
			Collections.sort(articleList, new Comparator<Article>() {
				public int compare(Article date1, Article date2) {
					return date2.getDate().compareTo(date1.getDate());
				}
			});
		} else if (sortCondition.equals("投稿順")) {
			Collections.sort(articleList, new Comparator<Article>() {
				public int compare(Article date1, Article date2) {
					return date1.getDate().compareTo(date2.getDate());
				}
			});
		} else if (sortCondition.equals("閲覧数順")) {
			Collections.sort(articleList, new Comparator<Article>() {
				public int compare(Article access1, Article access2) {
					return access1.getAccess() < access2.getAccess() ? 1 : -1;
				}
			});
		}
		//並べ替えた記事リストを返却
		return articleList;
	}
}