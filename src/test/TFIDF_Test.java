package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TFIDF_Test {
	// 計算詞頻率(TF)
	// 單詞的TF = occurence / wordCount
	// occurence: 該單詞在文章中的出現次數
	// wordCount: 所有單詞總數
    public static Map<String, Float> calculateTF(String wordAll) {
    	// dict | key: "term"  value: "occurence"
        HashMap<String, Integer> tmpTermOccurences;
        
        // tf | key: "term"  value: "term frequency"
        HashMap<String, Float> tf;
        int wordCount=0;
 
        // 將文章分詞後的各單詞(term)，逐一統計該單詞在文章中出現次數(occurence)，存於 tmpTermOccurences 
        tmpTermOccurences = new HashMap<String, Integer>();
        for(String word: wordAll.split(" ")) {
            wordCount += 1;
            if(!(tmpTermOccurences.containsKey(word))) { 
            	tmpTermOccurences.put(word, 1);
            } else {
            	tmpTermOccurences.put(word, tmpTermOccurences.get(word)+1);
            }
        }
        
        // 利用 tmpTermOccurence，計算詞頻率 tf
        tf = new HashMap<String, Float>();
        for(Map.Entry<String, Integer> termOccurence: tmpTermOccurences.entrySet()) {
            float currentTF = (float) termOccurence.getValue() / wordCount;
            tf.put(termOccurence.getKey(), currentTF);
        }
        return tf;
    }
    

    public static Map<String, Float> tfidfCalculate(int D, Map<String, String> doc_words, Map<String, Float> tf) throws FileNotFoundException, IOException {
        HashMap<String, Float> tfidf = new HashMap<String, Float>();
        for(String term: tf.keySet()){
            int Dt = 0;
            for(Map.Entry<String, String> entry: doc_words.entrySet()) {
                String[] words = entry.getValue().split(" ");
 
                List<String> wordlist=new ArrayList<String>();
                for(int i=0;i<words.length;i++) {
                    wordlist.add(words[i]);                
                }
                if(wordlist.contains(term)) {
                    Dt++;
                }
            }
            float idfvalue = (float) Math.log(Float.valueOf(D)/Dt);
            tfidf.put(term, idfvalue * tf.get(term));
        }
        return tfidf;
  }
}