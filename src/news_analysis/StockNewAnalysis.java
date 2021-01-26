package news_analysis;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StockNewAnalysis {
    @SuppressWarnings("unchecked")
    public String analyze(String jsonFilePath, int keyWordsAmount) throws IOException, ParseException {
    	ArrayList<String> keywordsList = new ArrayList<String>();
        JSONParser jsonParser = new JSONParser();
        // 開啟、讀取一股市資訊 (JSON file)
        FileReader reader = new FileReader(jsonFilePath);
        
        Object obj = jsonParser.parse(reader);
        JSONArray newsList = (JSONArray) obj;
        
        // 迭代每一條股市新聞，取出分詞後的文章 (multiple string，以空格分隔)
        // 並保存到 keywordsList
        newsList.forEach(news -> {
			try {
				keywordsList.add(analyzeSingleNews((JSONObject) news));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        
        // 
        KeywordExtractor calculator = new KeywordExtractor();
        /*
        double tfidf = calculator.calculateTF_IDF(usefulArticle, usefulArticleList, "testing");
        System.out.println("TF-IDF (Has) = " + tfidf); */
        double tfidf;
        HashMap<String, Double> map_word_tfidf = new HashMap<String, Double>();
        for(String keywords: keywordsList) {
        	for(String word: keywords.split(" ")) {
        		tfidf = calculator.calculateTF_IDF(keywords, keywordsList, word);
        		map_word_tfidf.put(word, tfidf);
        	}
        }
        
        // 排序 map_word_tfidf
        List<Map.Entry<String, Double>> list_Data =
                new ArrayList<Map.Entry<String, Double>>(map_word_tfidf.entrySet());
        Collections.sort(list_Data, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2) {
                return (entry1.getKey().compareTo(entry2.getKey()));
            }
        });
        
        int keyWordsCount = 0;
        String candidateWord;
        // keyWord: multiple keywords splitted by space | example: "摩根 美光 台指期 金融 航運 長照 醫療 汽車"
        String keyWord = "";
        for(Map.Entry<String, Double> word_tfidf: list_Data) {
        	if(keyWordsCount < keyWordsAmount) {
        		candidateWord = word_tfidf.getKey();
        		if(candidateWord.contains("."))
        			continue;
            	if(candidateWord.length()>1) {
            		keyWord += candidateWord + " / ";
            		keyWordsCount += 1;
            	}
        	}
        }
        System.out.println("\n\n個股關鍵字:\n" + keyWord);
        return keyWord;
    }
    
    private String analyzeSingleNews(JSONObject news) throws IOException {
    	String article = (String) news.get("article");
    	
    	// for testing 
        /*
        String stock_name = (String) news.get("stock_name");
    	String headline = (String) news.get("headline");
    	String article_link = (String) news.get("article_link");
        String datetime = (String) news.get("datetime");
        
        String msg = "";
        msg += "股票名稱: " + stock_name;
        msg += "\n新聞標題: " + headline;
        msg += "\n新聞連結: " + article_link;
        msg += "\n報導日期: " + datetime;
        */
        //msg += "\n新聞全文:\n" + article;
        //msg += "\n\n";
        //System.out.println(msg);
        //System.out.println(StringUtils.countMatches(article, "\n"));
        
        // 過濾掉文末廣告工商部分
        String usefulArticle = "";
        String adsBeginingWord = "週刊訂購專線";
        //int count = 0;
        for(String slice: article.split("\n")) {
        	// 如出現廣告開頭字眼(已達文末)，則停止收集
        	if(slice.contains(adsBeginingWord)) break;
        	
        	// 尚未出現廣告字眼，收集文章內容
        	//System.out.printf("line [%d]: %s\n", count+1, slice); // for testing
        	usefulArticle += slice;
        	//count += 1;
        }
        //System.out.print("\n");
        //System.out.println("乾淨的文章:\n" + usefulArticle + "\n"); // for testing
        
        // 分詞
        usefulArticle = new TextSplit().splitWords(usefulArticle);
        //System.out.println("分詞後的文章:\n" + usefulArticle + "\n"); // for testing
        return usefulArticle;
    }
}
