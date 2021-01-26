package test;

import java.io.IOException;
import java.util.ArrayList;

import news_analysis.Analysis_Demo;
import news_analysis.StockNewAnalysis;
import news_download.MoneyUdn_Crawler;

public class ImportTest {
	static void test_func_1_news_download(String keyword) throws InterruptedException, IOException {
		// 爬取 "經濟日報" 與輸入關鍵字有關的股市新聞
		// 並儲存成 JSON 檔案至 /res 目錄底下
		new MoneyUdn_Crawler(keyword).crawl_and_save_stockNews();
	}
	
	static void test_func_2_news_analysis(String keyword) throws InterruptedException, IOException {
		ArrayList<String> jsonFilePaths;
		if((jsonFilePaths= new Analysis_Demo().getJSONFilePaths(keyword))!=null) {
			for(String jsonFilePath: jsonFilePaths) {
				System.out.println("=> " + jsonFilePath);
				int keyWordsAmount = 200;
				//new StockNewAnalysis().analyze(jsonFilePath, keyWordsAmount);
			}
		}
		
		/*
		String jsonFilePath = "res\\鴻海\\經濟日報\\鴻海_經濟日報.json";
		new StockNewAnalysis().analyze(jsonFilePath);
		*/
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		//String keyword = "聯詠"; //3034
		//String keyword = "南亞科"; //2408
		String keyword = "鴻海"; //2317
		//String keyword = "台積電"; //2330
		
		/* (1) news_download 功能測試: 爬蟲=>儲存 */
		//test_func_1_news_download(keyword);
		
		/* (2) news_analysis 功能測試: 關鍵字提取 */
		//test_func_2_news_analysis(keyword);
	}
}
