package com.hliedu.chat.client;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * 表情框
 */
public class FaceFrame extends JFrame{

	private static final long serialVersionUID = 8603409755227097846L;
	
	/**
	 * 表情图片摆放
	 * @param textPane
	 */
	public FaceFrame( final JTextPane textPane){//?
		JPanel panel=(JPanel)getContentPane();
		panel.setLayout(null);
		//用双重循环来摆图片
		for (int row = 0; row <10; row++) {
			for (int col = 0; col < 6; col++) {
				//得到图片
				ImageIcon icon=new ImageIcon("src/image/face/"+(6*row+col+1)+".gif");
				//将图片放在JLable里
				JLabel lblIcon=new JLabel(icon);
				lblIcon.setSize(50,50);
				lblIcon.setLocation(0+col*50, 0+row*50);
				//为lbl添加鼠标事件
				lblIcon.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//（jLabel）e.getSourse（）强制转换成JLabel对象
						JLabel jLabel = (JLabel)e.getSource();
						
						Icon icon2 = jLabel.getIcon();
						//插入选中图片
						textPane.insertIcon(icon2);
						//关闭当前图片框
						FaceFrame.this.dispose();
					}
				});
				panel.add(lblIcon);
			}
		}
		setSize(320, 300);
		setLocation(800, 400);
		setTitle("嘻哈猴");
		setVisible(true);
	}
}
