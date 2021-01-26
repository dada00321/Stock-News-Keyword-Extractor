package news_analysis;

import java.io.File;
import java.util.ArrayList;

public class Analysis_Demo {
	public ArrayList<String> getJSONFilePaths(String keyword) {
		/* 找 /res 目錄下，所有依輸入關鍵字建立的的 JSON files 
		 * P.S. 如: 可能有多家網路媒體，分別存成不同 JSON 檔案
		 * */
		ArrayList<String> jsonFilePaths = new ArrayList<String>();
		String jsonFilePath;
		for(String stockDir: new File("res").list()) {
			if(stockDir.equals(keyword)) {
				for(String newsDir: new File(String.format("res/%s", stockDir)).list()) {
					jsonFilePath = String.format("res/%s/%s", stockDir, newsDir);
					for(String jsonFileName: new File(jsonFilePath).list()) {
						jsonFilePath = String.format("%s/%s", jsonFilePath, jsonFileName);
						//System.out.println("jsonFilePath: "+jsonFilePath); // for testing
						jsonFilePaths.add(jsonFilePath);
					}
				}
			}
		}
		int tmpPathsAmount = jsonFilePaths.size();
		if(tmpPathsAmount > 0) {
			System.out.printf("找到 %d 筆 與 \"%s\" 有關的股市新聞 JSON 檔案\n", tmpPathsAmount, keyword);
			return jsonFilePaths;
		} else {
			System.out.printf("很抱歉，目前沒有尋獲與 \"%s\" 有關的股市新聞\n", keyword);
			return null;
		}
	}
	public static void main(String[] args) {
		//String keyword = "聯詠"; //3034
		//String keyword = "南亞科"; //2408
		String keyword = "鴻海"; //2317
		//String keyword = "台積電"; //2330
		
		
		ArrayList<String> jsonFilePaths;
		if((jsonFilePaths= new Analysis_Demo().getJSONFilePaths(keyword))!=null) {
			for(String jsonFilePath: jsonFilePaths) {
				System.out.println("=> " + jsonFilePath);
				int keyWordsAmount = 200;
				new StockNewAnalysis().analyze(jsonFilePath, keyWordsAmount);
			}
		}
		
		/*
		String jsonFilePath = "res\\鴻海\\經濟日報\\鴻海_經濟日報.json";
		new StockNewAnalysis().analyze(jsonFilePath);
		*/
	}
}