package news_download;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataStorage {
	/*public void saveCSV(String csvFileName, String outputMsg) throws IOException {
		try {
			OutputStream os = new FileOutputStream(csvFileName);
		    os.write(239);
		    os.write(187);
		    os.write(191);
	
		    PrintWriter w = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
		    w.print(outputMsg);
		    w.flush();
		    w.close();
		    System.out.printf("CSV檔: %s 儲存成功！\n", csvFileName);
		}
	    
	    catch (IOException e) {
	    	System.out.println("[錯誤訊息] 無法儲存CSV檔");
	    }
	}*/
	
	@SuppressWarnings("unchecked")
	public void saveJSON (String newsName, ArrayList<StockNewsModel> stock_news_list) throws IOException {
		String jsonFileName;
		String tmpStockName = stock_news_list.get(0).getStockName();
		// jsonFilePath | example: "res\\鴻海\\經濟日報"
		String jsonFilePath = String.format("res\\%s\\%s", tmpStockName, newsName);
		
		// Create parent directories if not exist
		String tmpPath="";
		String[] tmpPaths = jsonFilePath.split("\\\\");
		for(String path: tmpPaths) {
			tmpPath += path; 
			//System.out.println(tmpPath);
			File tmpDir = new File(tmpPath);
			if(!(tmpDir.exists())) {
				tmpDir.mkdir();
	    	}
			tmpPath += "\\";
		}
		
		// Assign full path name
		jsonFileName = String.format("%s_%s.json", tmpStockName, newsName); // example: 鴻海_經濟日報.json
		jsonFilePath += "\\" + jsonFileName;
		
		// Make content of JSON file
	    JSONArray newsList = new JSONArray();
	    JSONObject news;
	    for(StockNewsModel stock_news: stock_news_list) {
	    	news = new JSONObject();
	    	news.put("stock_name", stock_news.getStockName());
		    news.put("headline", stock_news.getHeadline());
		    news.put("article_link", stock_news.getArticleLink());
		    news.put("datetime", stock_news.getDatetime());
		    news.put("article", stock_news.getArticle());
		    newsList.add(news);
		}
	    
	    // Write JSON file
	    try (FileWriter file = new FileWriter(jsonFilePath)) {
	        file.write(newsList.toJSONString());
	        file.flush();
	        System.out.println("JSON檔儲存成功！");
	        System.out.printf("請至以下路徑查看: %s\n", jsonFilePath);
	    } catch (IOException e) {
	    	System.out.println("[錯誤訊息] 無法儲存JSON檔");
	    }
	}
}
