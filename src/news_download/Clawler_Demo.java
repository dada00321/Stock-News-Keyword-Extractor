/** 爬蟲主程式: 操作 MoneyUdn_Crawler 來爬取股市新聞
 * */
package news_download;

import java.io.IOException;

public class Clawler_Demo {
	public static void main(String[] args) throws InterruptedException, IOException {
    	//String keyword = "聯詠"; //3034
		//String keyword = "南亞科"; //2408
		String keyword = "鴻海"; //2317
		//String keyword = "台積電"; //2330
		
		// 爬取 "經濟日報" 與輸入關鍵字有關的股市新聞
		// 並儲存成 JSON 檔案至 /res 目錄底下
		new MoneyUdn_Crawler(keyword).crawl_and_save_stockNews();
	}
}
