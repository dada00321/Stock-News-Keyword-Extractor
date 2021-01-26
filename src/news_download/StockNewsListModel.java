/** 新聞模型串列類別: 暫存每次的爬蟲中，多個新聞模型的資訊
 * */
package news_download;

import java.io.IOException;
import java.util.ArrayList;

public class StockNewsListModel {
	private String newsName;
	private ArrayList<StockNewsModel> stock_news_list;
	
	public StockNewsListModel() {
		this.stock_news_list = new ArrayList<StockNewsModel>();
	}
	
	public StockNewsListModel(String newsName) {
		this.setNewsName(newsName);
		this.stock_news_list = new ArrayList<StockNewsModel>();
	}
	
	private void setNewsName(String newsName) {
		this.newsName = newsName;
	}

	public void add_news(StockNewsModel news) {
		this.stock_news_list.add(news);
	}
	
	public int get_newsList_amount() {
		return this.stock_news_list.size();
	}
	
	public StockNewsModel get_news_model(int newsIndex) {
		return this.stock_news_list.get(newsIndex);
	}
	
	public void update_news_model(int newsIndex, StockNewsModel news_model) {
		this.stock_news_list.set(newsIndex, news_model);
	}
	
	public boolean isArticleLinkExists(String articleLink) {
		for(StockNewsModel news: stock_news_list) {
			if(news.getArticleLink() == articleLink) {
				return true;
			}
		}
		return false;
	}
	
	// for testing
	public void printHeadlinesAndLinks() {
		System.out.println("[測試] 印出所有爬到的文章標題和連結");
		for(StockNewsModel news: this.stock_news_list) {
			System.out.println("文章標題: "+news.getHeadline());
			System.out.println("文章連結: "+news.getArticleLink()+"\n");
		}
		System.out.print("\n");
	}
	
	/*public void save_stockNewsList_to_csv() {
		String csvFileName = newsName + ".csv";
		String colNames, outputMsg;
		try {
			int count = 0;
			colNames = "stock_name, headline, article_link, datetime, article";
			outputMsg = colNames + "\n";
			for(StockNewsModel stock_news: stock_news_list) {
				count += 1;
				outputMsg += stock_news.getStockName() + ", ";
				outputMsg += stock_news.getHeadline() + ", ";
				outputMsg += stock_news.getArticleLink() + ", ";
				outputMsg += stock_news.getDatetime() + ", ";
				outputMsg += stock_news.getArticle();
				if(count != stock_news_list.size()) outputMsg += "\n";
			}
			new DataStorage().saveCSV(csvFileName, outputMsg);
			System.out.printf("csv檔: %s 儲存成功！\n", csvFileName);
		} catch(IOException e) {
			System.out.println("[錯誤訊息] 無法儲存csv檔");
		}
	}*/
	
	public void save_stockNewsList_to_json() throws IOException {
		new DataStorage().saveJSON(newsName, stock_news_list);
	}
}
