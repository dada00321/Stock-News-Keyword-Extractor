package test;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class DemoFont {
    public static void main(String[] args) {
        JFrame jf=new JFrame("顯示可用的字體");
        Container container = jf.getContentPane();// 窗体容器
        JTextArea text = new JTextArea();
        Font f = new Font("仿宋", Font.BOLD+ Font.ITALIC,18);
        text.setFont(f);
        text.append("全部可用的字體:\n");
        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fornames=ge.getAvailableFontFamilyNames();
        for (int i = 0; i < fornames.length; i++) 
        {
            text.append(fornames[i]+"\n");
	}
        
        JPanel panel = new JPanel();
        panel.add(text);
        // 下拉式視窗
        JScrollPane scrollPane = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container.add(scrollPane); // 加入下拉式視窗
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.setLocation(400, 300);
        jf.setSize(300, 600);
        jf.setBackground(Color.white);
        jf.setVisible(true);        
    }   
}