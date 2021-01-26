package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;

public class JiebaTest {
    public static void main(String[] args) throws IOException {
    	String sentence;
    	String result = "";
    	String userDictPath;
    	String stopWordsPath;
    	List<String> stopWords = new ArrayList<String>();
    	JiebaSegmenter segmenter = new JiebaSegmenter();
    	
    	// 加載使用者自定義的詞庫 (若覺得分詞結果不通順，可透過此文字檔加入自定義的詞彙)
    	userDictPath = "res\\Jieba\\dict.txt";
        WordDictionary.getInstance().loadUserDict(Paths.get(new File((userDictPath)).getAbsolutePath()));
        
        // 加載停用詞 (無意義的詞彙、標點符號)
    	stopWordsPath = "res\\Jieba\\stopWords.txt";
    	BufferedReader br = new BufferedReader(new FileReader(stopWordsPath));
    	String line;
    	while((line=br.readLine())!=null) {
    		stopWords.add(line);
    	}
    	br.close();
        
        sentence = "鴻海電動車大聯盟飆 台半強攻電動車業績看俏";
        //sentence = "變種病毒 加速智慧表出貨暢旺？";
        List<String> textSplitResult = segmenter.sentenceProcess(sentence);//, JiebaSegmenter.SegMode.INDEX).toString();
        for(String word: textSplitResult) {
        	if(!(stopWords.contains(word))) result += word + " ";
        }
        System.out.println("Text splitting result: " + result);
    }
}