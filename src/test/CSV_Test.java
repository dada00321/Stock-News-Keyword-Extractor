package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/** [測試] csv模組
 * */
import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TestCsvHelper {
	public String escapeSpecialCharacters(String data) {
	    String escapedData = data.replaceAll("\\R", " ");
	    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
	        data = data.replace("\"", "\"\"");
	        escapedData = "\"" + data + "\"";
	    }
	    return escapedData;
	}
	public String convertToCSV(String[] data) {
	    return Stream.of(data)
	      .map(this::escapeSpecialCharacters)
	      .collect(Collectors.joining(","));
	}
	public void save_records_to_csv(ArrayList<String[]> records, String csv_file_name) throws IOException {
	    File csvOutputFile = new File(csv_file_name);
	    //try (PrintWriter pw = new PrintWriter(csvOutputFile, "UTF-8")) {
	    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
	    	records.stream()
	          .map(this::convertToCSV)
	          .forEach(pw::println);
	    }
	}
}

public class CSV_Test {
	static void test_csv_1() throws IOException {
		//String line = "שלום, hello, привет";
		String line = "שלום, hello, 中文測試\n";
		line += "八, 七, 五十六\n";
		line += "百, 合, 大法好！！";
	    OutputStream os = new FileOutputStream("csv_test.csv");
	    os.write(239);
	    os.write(187);
	    os.write(191);

	    PrintWriter w = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));

	    w.print(line);
	    w.flush();
	    w.close();
	}
	
	static void test_csv_2() throws IOException {
		String csv_file_name = "test_csv.csv";
		System.out.printf("正在儲存csv檔: %s...\n", csv_file_name);
		ArrayList<String[]> records = new ArrayList<String[]>();
		records.add( new String[] {
				   "stock_name", 
				   "headline",
				   "link",
				   "datetime",
				   "article"} );
		records.add( new String[] {
				   "這是", 
				   "一個",
				   "簡單的",
				   "csv檔案",
				   "中文編碼儲存測試"} );
		new TestCsvHelper().save_records_to_csv(records, csv_file_name);
	}
	
	public static void main(String[] args) throws IOException {
		test_csv_1();
	}
}
