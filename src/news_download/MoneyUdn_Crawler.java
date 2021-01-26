/** 經濟日報-股市新聞爬蟲類別
 *  用於下載「經濟日報」網站中關於某檔股票的近期新聞
 *  包含爬蟲 & 儲存，儲存格式為JSON (因為 csv 較不利於放文字稍多的新聞內容)
 * */
package news_download;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;  
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.JavascriptExecutor;

public class MoneyUdn_Crawler {
	private String keyword;
	private WebDriver driver;
	private StockNewsListModel moneyUdn_news_list;
	private StockNewsModel moneyUdn_news;
	
	/* constructor */
	public MoneyUdn_Crawler(String input) {
		keyword = input;
		driver = getDriver();
	}
	
	/* main routine of scrapying */
	public StockNewsListModel crawl_and_save_stockNews() throws InterruptedException, IOException {
		moneyUdn_news_list = new StockNewsListModel("經濟日報"); // 利用 "新聞名稱" 建立一個 "股市新聞爬蟲串列" (該新聞名稱會用於後續儲存的csv檔 檔名)
		search(keyword); // 先依輸入字串，爬出該檔股票的各個新聞標題
		moneyUdn_news_list.printHeadlinesAndLinks(); // 並印出測試 (利用外部類別方法)
		crawl_articles(); // 再抓出原始文章,日期
		//driver.close(); // 爬蟲完畢，關閉當前頁面
		driver.quit(); // 爬蟲完畢，關閉所有視窗
		/*moneyUdn_news_list.save_stockNewsList_to_csv(); // 儲存為 csv 檔案 (利用外部類別方法)*/
		moneyUdn_news_list.save_stockNewsList_to_json(); // 儲存為 JSON 檔 (利用外部類別方法)
		return moneyUdn_news_list;
	}
	
	private WebDriver getDriver() {
		//System.setProperty("webdriver.chrome.driver", "D:\\geckodriver\\chromedriver.exe");  
    	//return new ChromeDriver();
		System.setProperty("webdriver.gecko.driver", "D:\\geckodriver\\firefox\\geckodriver-v0.28.0-win64\\geckodriver.exe");  
    	return new FirefoxDriver();
	}
	
	/* search for "headline", "link" for articles regarding to input keywords */
	private void search(String keyword) throws InterruptedException {
		System.out.printf("正在爬蟲: 股市新聞標題 & 新聞連結\n\n");
		// 經濟日報搜尋結果網址前綴
		String money_udn_urlPrefix = "https://money.udn.com/search/result/1001/";
		String url = money_udn_urlPrefix + keyword;
		//driver.get("http://www.kangting.tw/2015/03/aspnet-mvc.html");
		driver.get(url);
		driver.manage().window().maximize(); // 最大化 web browser 視窗
		
		// 下拉到最下面(避免只讀到前幾筆) 
		JavascriptExecutor js = (JavascriptExecutor) driver;
		final int delta = 300;
		int moment = 0, iter_times = 5;
		for(int i=0; i<iter_times; i++) { // 4000
			js.executeScript(String.format("window.scrollBy(%d, %d)", moment, moment+delta));
			Thread.sleep(1000);
			moment += delta;
		}
		
		// 抓出: 當前頁面所有符合輸入關鍵字的標題 & 標題連結 
		String xpath = "//a/h3";  //標題
		List<WebElement> tmpList = driver.findElements(By.xpath(xpath));
		xpath = "//a/h3/..";  //標題連結
		List<WebElement> tmpList2 = driver.findElements(By.xpath(xpath));
		xpath = "//dt/a/p"; // 文章摘要
		List<WebElement> tmpList3 = driver.findElements(By.xpath(xpath));
		System.out.printf("目前找到 %d 篇與您的輸入： %s 有關的新聞\n", tmpList.size(), keyword);
		
		int article_idx = -1;
		String tmpHeadline, tmpDesc, tmpLink;
		for(WebElement e: tmpList) {
			article_idx += 1;
			tmpHeadline = e.getText();
			tmpDesc = tmpList3.get(article_idx).getText();
			// 若文章摘要或標題有出現關鍵字(而非廣告)
			if(tmpHeadline.contains(keyword) || tmpDesc.contains(keyword)) {
				tmpLink = tmpList2.get(article_idx).getAttribute("href");
				// 且有抓到文章連結
				if( (tmpLink != null && tmpLink != "") && !(moneyUdn_news_list.isArticleLinkExists(tmpLink)) ) {
					moneyUdn_news = new StockNewsModel();
					//moneyUdn_news.clearAll();
					moneyUdn_news.setStockName(keyword);
					//System.out.printf("keyword: %s\n", keyword);// for testing(1)
					
					moneyUdn_news.setHeadline(tmpHeadline);
					System.out.printf("新聞標題: %s\n", tmpHeadline);// for testing(2)
					
					System.out.printf("新聞連結: %s\n\n", tmpLink);// for testing(3)
					moneyUdn_news.setArticleLink(tmpLink);
					
					// 若確定 "文章標題 與輸入相關(不是廣告) 且 連結有找到"
					// 便把該文章 (1)標題 (2)標題連結 先加入串列
					// 若上述有任何一個抓不到，則 ignore
					moneyUdn_news_list.add_news(moneyUdn_news);
				}
			}
		}
	}
	
	/* crawl "datetime", "article" for each fetched article headline */
	private void crawl_articles() {
		System.out.printf("正在爬蟲: 股市新聞文章 & 發布日期");
		
		int lastNewsIndex = moneyUdn_news_list.get_newsList_amount() - 1;
		StockNewsModel currentNewsModel;
		for(int i=0; i<=lastNewsIndex; i++) { // 迭代每一篇商業報導
		//for(int i=0; i<1; i++) { // for testing
			// 載入已爬取文章標題及連結的 "新聞模型"
			currentNewsModel = moneyUdn_news_list.get_news_model(i);
			String tmpHeadline, tmpArticleLink;
			tmpHeadline = currentNewsModel.getHeadline();
			tmpArticleLink = currentNewsModel.getArticleLink();
			System.out.printf("正在爬 第 %d 篇 商業新聞： \"%s\"\n", i+1, tmpHeadline);
			
			// 造訪該報導文章連結
			driver.get(tmpArticleLink);
			
			// 抓出報導日期
			String xpath = "//div[@class='shareBar__info--author']/span";
			String tmpDatetime = driver.findElement(By.xpath(xpath)).getText();
			//String tmpDatetime = driver.findElement(By.xpath(xpath)).getAttribute("innerHTML"); // if the web element is hidden (probably works) 
			if(tmpDatetime==null || tmpDatetime=="") continue;
			currentNewsModel.setDatetime(tmpDatetime); // "更新" 新聞模型
			
			// 抓出報導內文
			xpath = "//div[@id='article_body']";
			String tmpArticle = driver.findElement(By.xpath(xpath)).getText();
			if(tmpArticle==null || tmpArticle=="") continue;
			currentNewsModel.setArticle(tmpArticle); // "更新" 新聞模型
			
			// 確認: 若報導日期、新聞內文 皆可正確抓取 (上述 if statements 已做過確認)
			// 則 "更新" 本次爬蟲新聞模型串列 的 "最後一項(當前項)" | i.e., 加入(3) 報導日期 (4) 新聞內文
			moneyUdn_news_list.update_news_model(lastNewsIndex, currentNewsModel);
			System.out.printf("成功爬取 第 %d 篇 商業新聞： \"%s\" ！\n\n", i+1, tmpHeadline);
		}
	}
}
