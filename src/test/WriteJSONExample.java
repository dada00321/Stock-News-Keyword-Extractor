package test;

import java.io.FileWriter;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
public class WriteJSONExample
{
    @SuppressWarnings("unchecked")
    public static void main( String[] args )
    {
	    // Make content of JSON file
	    JSONArray stockNewsList = new JSONArray();
	    JSONObject news_1 = new JSONObject();
	    //"stock_name, headline, article_link, datetime, article";
	    news_1.put("stock_name", "1");
	    news_1.put("headline", "2");
	    news_1.put("article_link", "3");
	    news_1.put("datetime", "4");
	    news_1.put("article", "5");
	    stockNewsList.add(news_1);
	    
	    // Write JSON file
	    try (FileWriter file = new FileWriter("stock_news.json")) {
	        file.write(stockNewsList.toJSONString());
	        file.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
}
