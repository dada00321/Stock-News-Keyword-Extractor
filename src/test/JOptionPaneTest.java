package test;
import javax.swing.*;  

public class JOptionPaneTest {
	JFrame f;  
	public JOptionPaneTest() {  
	    f=new JFrame();  
	    String optionContent = "確定造訪該新聞連結？";
		String optionTitle = "小提示";
		Object[] options = {"確定", "不，算了"};
		int result = JOptionPane.showOptionDialog(null, optionContent, optionTitle, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		//.showMessageDialog(null, optionContent, optionTitle, JOptionPane.QUESTION_MESSAGE);
		System.out.println(result);
	}  
	public static void main(String[] args) {  
	    new JOptionPaneTest();  
	} 
}
