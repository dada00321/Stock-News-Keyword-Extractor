package test;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class ButtonColumnTest extends JFrame {
   private JTable table;
   private JScrollPane scrollPane;
   public ButtonColumnTest() {
      setTitle("JTableButton Test");
      TableCellRenderer tableRenderer;
      table = new JTable(new JTableButtonModel());
      tableRenderer = table.getDefaultRenderer(JButton.class);
      table.setDefaultRenderer(JButton.class, new JTableButtonRenderer(tableRenderer));
      scrollPane = new JScrollPane(table);
      add(scrollPane, BorderLayout.CENTER);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setSize(400, 300);
      setVisible(true);
   }
   public static void main(String[] args) {
      new ButtonColumnTest();
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
@SuppressWarnings("serial")
class JTableButtonModel extends AbstractTableModel {
   private Object[][] rows = {{"stock name", "headline", "datetime", new JButton("✔"), new JButton("✔")},{"stock name", "headline", "datetime", new JButton("✔"), new JButton("✔")}};
   private String[] columns = {"股票名稱", "新聞標題", "報導日期", "查看內文", "造訪連結"};
   public String getColumnName(int column) {
      return columns[column];
   }
   public int getRowCount() {
      return rows.length;
   }
   public int getColumnCount() {
      return columns.length;
   }
   public Object getValueAt(int row, int column) {
      return rows[row][column];
   }
   public boolean isCellEditable(int row, int column) {
      return false;
   }
   @SuppressWarnings({ "unchecked", "rawtypes" })
   public Class getColumnClass(int column) {
      return getValueAt(0, column).getClass();
   }
}