package gui;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import news_analysis.Analysis_Demo;
import news_analysis.StockNewAnalysis;
import news_download.MoneyUdn_Crawler;
import news_download.StockNewsListModel;

public class GUI_Util {
	public boolean isInputValid(String input) {
		if(input.equals("")) {
    		System.out.println("輸入不可為空");
    		return false;
    	}
    	else {
    		return true;
    	}
	}
	
	public StockNewsListModel run_func_1_news_download(String keyword) throws InterruptedException, IOException {
		// 爬取 "經濟日報" 與輸入關鍵字有關的股市新聞
		// 並儲存成 JSON 檔案至 /res 目錄底下
		return new MoneyUdn_Crawler(keyword).crawl_and_save_stockNews();
	}
	
	public String run_func_2_news_analysis(String keyword) throws InterruptedException, IOException, ParseException {
		ArrayList<String> jsonFilePaths;
		if((jsonFilePaths= new Analysis_Demo().getJSONFilePaths(keyword))!=null) {
			String resultKeyWord = ""; // if there's many json files
			String keyWord; // for single json file
			for(String jsonFilePath: jsonFilePaths) {
				System.out.println("=> " + jsonFilePath);
				int keyWordsAmount = 200;
				keyWord = new StockNewAnalysis().analyze(jsonFilePath, keyWordsAmount);
				resultKeyWord += keyWord;
			}
			return resultKeyWord;
		}
		return "";
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
		//new GUI_Util().run_func_1_news_download(keyword);
		
		/* (2) news_analysis 功能測試: 關鍵字提取 */
		//new GUI_Util().run_func_2_news_analysis(keyword);
		
		/*Object[] list = new Object[3];
		list[0] = "123";
		for(int i=0;i<list.length;i++)
			System.out.println(list[i]);*/
		
		/*
		Object[][] rows = new Object[1][5];
		for(int i=0;i<rows.length;i++)
			for(int j=0; j<rows[0].length; j++)
				System.out.println("rows["+i+"]["+j+"] = "+rows[i][j]);
			System.out.println();
			
		rows = new Object[2][5];
		rows[0][0] = "1";
		rows[0][1] = 2;
		rows[0][2] = 3.3;
		
		for(int i=0;i<rows.length;i++)
			for(int j=0; j<rows[0].length; j++)
				System.out.println("rows["+i+"]["+j+"] = "+rows[i][j]);
			System.out.println();*/
		
		String tmpShowHeadline = "";
		String tmpHeadline = "一二三四五六七八九十石伊十二一二三四五六七八九十石伊十二一二三四五六七八九十石伊十二";
		for(int j=0;j<tmpHeadline.length();j++) {
			if(j % 10 == 0)	tmpShowHeadline += "\n";
			tmpShowHeadline += tmpHeadline.charAt(j);
		}
		System.out.println(tmpShowHeadline);
	}
}
