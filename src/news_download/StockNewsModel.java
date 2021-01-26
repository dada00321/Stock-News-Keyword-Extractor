/** 新聞模型類別: 暫存每一篇文章的資訊
 * */
package news_download;

public class StockNewsModel {
	private String stockName;
	private String headline;
	private String articleLink;
	private String datetime;
	private String article;
	
	public StockNewsModel() {
		clearAll();
	}
	
	public void clearAll() {
		stockName = "";
		headline = "";
		articleLink = "";
		datetime = "";
		article = "";
	}
	
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stock) {
		this.stockName = stock;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getArticleLink() {
		return articleLink;
	}

	public void setArticleLink(String link) {
		this.articleLink = link;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

}
