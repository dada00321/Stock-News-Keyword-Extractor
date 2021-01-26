package news_analysis;

import java.util.ArrayList;

public class KeywordExtractor {
	// TF(Term Frequency) 表示單詞 t 在 文件 D(i) 中出現的頻率
	// 計算公式: TF(t, D(i)) = count(t, D(i)) / len(D(i))
    private double calculateTF(String doc, String t) {
        double occurence = 0;
        String[] tmpWords = doc.split(" ");
        for(String word: tmpWords) {
        	if(word.equals(t))
        		occurence += 1;
        }
        return occurence / tmpWords.length;
    }

    // IDF(Inverse Document Frequency) 表示單詞 t 在整個文件庫中的區分能力
    // 計算公式: IDF(t) = N / ( 1 + sum(I(t, D(i))) )
    // N: 文件總數
    // t: term 單詞
    // value: 即 公式中的 sum(I(t, D(i)))
    // sum(I(t, D(i))): 單詞 t 在 N 個文件的出現次數
    // P.S. 若此單詞在所有文件皆無出現，分母會為0
    //      因此做簡單的平滑處理，在 sum(I(t, D(i))) 前方加上 1
    private double calculateIDF(ArrayList<String> docs, String t) {
        int N = docs.size();
    	double value = 1;
    	int tmpIncrement;
        for(String doc: docs) { // 迭代所有文件
        	// 該文件 "包含單詞 t 為 1，不含則為 0" | 即 sum(I(t, D(i)))
        	tmpIncrement = doc.contains(t) ? 1:0;
        	value += tmpIncrement;
        }
        return Math.log(N / value);
    }

    public double calculateTF_IDF(String doc, ArrayList<String> docs, String t) {
        return calculateTF(doc, t) * calculateIDF(docs, t);
    }

    public static void main(String[] args) {
        String doc1 = "Lorem ipsum dolor ipsum sit ipsum";
        String doc2 = "Vituperata incorrupte at ipsum pro quo";
        String doc3 = "Has persius disputationi id simul";
        ArrayList<String> documents = new ArrayList<String>();
        documents.add(doc1);
        documents.add(doc2);
        documents.add(doc3);
        
        KeywordExtractor calculator = new KeywordExtractor();
        double tfidf = calculator.calculateTF_IDF(doc1, documents, "Lorem");
        System.out.println("TF-IDF (Has) = " + tfidf);
    }
}
