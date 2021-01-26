package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.simple.parser.ParseException;

import news_download.StockNewsListModel;
import news_download.StockNewsModel;

import java.io.IOException;

public class GUI {
	private static JFrame frame;
    private static JLabel lb1, lb_status3, lb_author;
    private static JTextField tf1, tf_index;
    private static JButton btn1, btn2, btn3;
    private static JTable table;
    private static JScrollPane scrollPane;
    //private static DefaultTableModel tableModel;
    private static JTableButtonModel tableModel;
    //////////////////////////////////
	public static StockNewsListModel stockNewsList;
	private static String keyword;
	private static String[] columns;
	private static Object[][] rows;
	
	public GUI() {
		stockNewsList = new StockNewsListModel();
    	// 定義(建立)各GUI元件
    	createWidgets();
    	// 設定觸發事件
    	setListeners();
        // 設定各元件位置、大小
        setBounds();
        // 設定文字屬性
        setFonts();
        // Close JFrame       
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowHandler(frame));
	}
	
	private static void createWidgets() {
    	// 設定 JFrame
        frame = new JFrame("財經新聞小幫手");
        frame.setLayout(null);
        frame.setSize(770, 580);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 標籤
        lb1 = new JLabel("請輸入欲查詢財經新聞的關鍵字: ");
        frame.add(lb1);
        lb_author = new JLabel("Author: Dada Liu");
        //frame.add(lb_author);
        lb_status3 = new JLabel("0"); // 是/否 已存入芒果DB
        frame.add(lb_status3);
        
        // 文字輸入區
        tf1 = new JTextField("");
        //tf1 = new JTextField("");
        frame.add(tf1);
        tf_index = new JTextField("");
        frame.add(tf_index);
        
        // 按鈕 
        btn1 = new JButton("下載新聞");
        frame.add(btn1);
        btn2 = new JButton("重新輸入");
        frame.add(btn2);
        btn3 = new JButton("提取關鍵字");
        frame.add(btn3);
        
        // 表格
        TableCellRenderer tableRenderer;
        //tableModel = new JTableButtonModel(stockNewsList);
        String[] cols = {"關鍵字", "新聞標題", "報導日期", "查看內文", "造訪連結"};
        columns = cols;
        rows = new Object[0][columns.length];
        tableModel = new JTableButtonModel(rows, columns);
        table = new JTable(tableModel);
        
        tableRenderer = table.getDefaultRenderer(JButton.class);
        table.setDefaultRenderer(JButton.class, new JTableButtonRenderer(tableRenderer));
        
        scrollPane = new JScrollPane(table);
        //tableModel = (DefaultTableModel) table.getModel();
        //tableModel.setRowCount(0);
        frame.add(scrollPane, BorderLayout.CENTER);
    }
	
	private static void setListeners() {
    	// "下載新聞" 按鈕觸發事件
        btn1.addActionListener( new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
            	keyword = tf1.getText();
            	tf1.setEnabled(false);
            	GUI_Util util = new GUI_Util();
            	if(util.isInputValid(keyword)) {
            		System.out.println("正在搜尋與 \"" + keyword + "\" 有關的新聞...");
            		try {
            			// 執行 news_download 功能: 爬蟲=>儲存 
            			stockNewsList = util.run_func_1_news_download(keyword);
            			// 載入新表格
//            	        TableCellRenderer tableRenderer;
//            	        table = new JTable(new JTableButtonModel(stockNewsList));
//            	        tableRenderer = table.getDefaultRenderer(JButton.class);
//            	        table.setDefaultRenderer(JButton.class, new JTableButtonRenderer(tableRenderer));
//            	        scrollPane = new JScrollPane(table);
//            	        frame.add(scrollPane, BorderLayout.CENTER);
            	        //table.repaint();
            	        
            			System.out.println("tmpAmount is not 0");
            	        int tmpAmount = stockNewsList.get_newsList_amount();
            	        System.out.println(stockNewsList.get_news_model(0).getStockName());
            	        System.out.println(stockNewsList.get_news_model(0).getHeadline());
            	        //Object[][] data = new Object[tmpAmount][5];
            	        Object[] data = new Object[5];
            			StockNewsModel news;
            			
            			for(int i=0; i<tmpAmount; i++) {
            				news = stockNewsList.get_news_model(i);
            				data[0] = news.getStockName();  // stock_name
            				String tmpShowHeadline = "";
            				String tmpHeadline = news.getHeadline();
            				for(int j=0;j<tmpHeadline.length();j++) {
            					if(j % 10 == 0)	tmpShowHeadline += "\n";
            					tmpShowHeadline += tmpHeadline.charAt(j);
            				}
            				data[1] = tmpShowHeadline; // headline
            				data[2] = news.getDatetime().split(",")[0]; // datetime
            				//data[3] = news.getArticle();      //article
            				//data[3] = new JButton("點我查看");  // article
            				data[3] = "查看"; // article
            				//data[4] = news.getArticleLink(); // link
            				//data[4] = new JButton("點我造訪"); // link
            				data[4] = "造訪"; // link
            				
            				tableModel.addRow(data);
            			}
//            			System.out.println("data[1]="+data[1]);
//            			System.out.println("data[2]="+data[2]);
//            			System.out.println("data[3]="+data[3]);
            			table.setModel(tableModel);
            			tableModel.fireTableDataChanged();
            			
					} catch (InterruptedException | IOException e1) {
						e1.printStackTrace();
					}
            	}
			}
        });
        
        // "重新輸入" 按鈕觸發事件
        btn2.addActionListener( new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				tf1.setText("");
				tf1.setEnabled(true);
				table.setVisible(false);
				stockNewsList = new StockNewsListModel();
			}
        });
        
        // "提取關鍵字" 按鈕觸發事件
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUI_Util util = new GUI_Util();
            	if(util.isInputValid(keyword)) {
            		try {
            			/* 執行 news_analysis 功能: 關鍵字提取 */
            			String extractedKeywords = util.run_func_2_news_analysis(keyword);
            			String optionContent = extractedKeywords;
                		String optionTitle = "提取後的關鍵字";
                		JOptionPane.showMessageDialog(null, optionContent, optionTitle, JOptionPane.INFORMATION_MESSAGE);
					} catch (InterruptedException | IOException | ParseException e1) {
						e1.printStackTrace();
					}
            	}
            }
        });
        
        // 表格中儲存單擊觸發事件
        table.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		JTable target = (JTable) e.getSource();
		    	int row = target.getSelectedRow();
		      	int col = target.getSelectedColumn();
		      	//System.out.println("r="+row);
		      	//System.out.println("c="+col);
		      	if(col==3) { // 查看新聞
		      		String optionContent = stockNewsList.get_news_model(row).getArticle();
            		String optionTitle = "新聞內容";
            		JOptionPane.showMessageDialog(null, optionContent, optionTitle, JOptionPane.INFORMATION_MESSAGE);
		      	}
		      	else if(col==4) { // 造訪連結
		      		try {
		      			String optionContent = "確定造訪該新聞連結？";
		      			String optionTitle = "小提示";
		      			Object[] options = {"確定", "不，算了"};
		      			int result = JOptionPane.showOptionDialog(null, optionContent, optionTitle, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); 
		      			if(result == 0) { // 確定造訪連結
		      				visitLink(stockNewsList.get_news_model(row).getArticleLink());
		      			}
					} catch (URISyntaxException | IOException e1) {
						e1.printStackTrace();
					}
		      	}
        	}
        });
    }
	
	private static void setFonts() {
    	tf1.setFont(new Font("微軟正黑體", Font.PLAIN, 20)); /// Font.SERIF
    	//tf_index.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
    	//tf_index.setForeground(Color.RED);
    	lb1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
    	btn1.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
    	btn2.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
    	btn3.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
    	lb_status3.setFont(new Font("微軟正黑體", Font.BOLD, 25));
    	lb_author.setFont(new Font("微軟正黑體", Font.PLAIN, 10));
    	table.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
    }
	
	private static void setBounds() {
    	int X_LEFT = 25;
    	int X_ROW1 = 320;
    	int Y_TOP = 15;
    	int Y_MIDDLE = 55;
    	int Y_BOTTOM = 110;
    	int BTN_WEIGHT = 118;
    	int BTN_HEIGHT = 40;
    	
    	lb1.setBounds(X_LEFT, Y_TOP, 300, 30); // "公開資料URL: " 標籤
    	tf1.setBounds(X_ROW1, Y_TOP, 180, 30); // 對應 lb1 之輸入區
    	btn1.setBounds(X_LEFT, Y_MIDDLE, BTN_WEIGHT, BTN_HEIGHT); // "下載新聞" 按鈕 
    	btn2.setBounds(X_LEFT+145, Y_MIDDLE, BTN_WEIGHT, BTN_HEIGHT); // "下載新聞" 按鈕 
    	btn3.setBounds(X_LEFT+280, Y_MIDDLE, BTN_WEIGHT+14, BTN_HEIGHT); // "存入Mongo DB"按鈕
    	//tf_index.setBounds(X_LEFT+220, Y_BOTTOM, 30, 35);
    	lb_author.setBounds(X_LEFT+535, Y_BOTTOM+430, 110, 30);
    	scrollPane.setBounds(X_LEFT, Y_BOTTOM+10, 700, 400);
    	
    	// 設定儲存格置中對齊、欄位寬度
    	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    	centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    	int[] widths = {100,320,190,80,80};
    	for(int i=0;i<widths.length;i++) {
    		TableColumn tc = table.getColumnModel().getColumn(i);
    		tc.setPreferredWidth(widths[i]);
    		tc.setCellRenderer(centerRenderer);
    	}
    }
	
	private static void visitLink(String url_) throws URISyntaxException, IOException {
    	Desktop desktop = Desktop.getDesktop();   
		URI uri_ = new URI(url_); // 建立URI統一資源識別符號
		desktop.browse(uri_); // 使用預設瀏覽器開啟超連結
    }
	
	public void test_funcs() throws InterruptedException, IOException, ParseException {
		//String keyword = "聯詠"; //3034
		//String keyword = "南亞科"; //2408
		String keyword = "鴻海"; //2317
		//String keyword = "台積電"; //2330
		/* (1) news_download 功能測試: 爬蟲=>儲存 */
		//new GUI_Util().run_func_1_news_download(keyword);
		
		/* (2) news_analysis 功能測試: 關鍵字提取 */
		String keyWords = new GUI_Util().run_func_2_news_analysis(keyword);
		System.out.println("提取後的關鍵字: " + keyWords);
	}
	public static void main(String[] args) throws InterruptedException, IOException {
		//new GUI().test_funcs();
		new GUI();
	}
}

class WindowHandler extends WindowAdapter {
	  JFrame f;
	  public WindowHandler(JFrame f) {this.f=f;}
	  public void windowClosing(WindowEvent e) {
		  int result=JOptionPane.showConfirmDialog(f,
				  "確定要結束程式嗎?",
				  "確認訊息",
		          JOptionPane.YES_NO_OPTION,
		          JOptionPane.WARNING_MESSAGE);
		  if (result==JOptionPane.YES_OPTION) {System.exit(0);}
	  }
}

class JTableButtonRenderer implements TableCellRenderer {
   private TableCellRenderer defaultRenderer;
   public JTableButtonRenderer(TableCellRenderer renderer) {
      defaultRenderer = renderer;
   }
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if(value instanceof Component)
         return (Component)value;
         return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
   }
}
class JTableButtonModel extends DefaultTableModel{
	public JTableButtonModel(Object rowData[][], Object columnNames[]) {
		super(rowData, columnNames);
    }
    public Class getColumnClass(int column)
    {
        return getValueAt(0, column).getClass();
    }
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }
};
