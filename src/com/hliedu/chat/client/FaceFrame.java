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
 * �����
 */
public class FaceFrame extends JFrame{

	private static final long serialVersionUID = 8603409755227097846L;
	
	/**
	 * ����ͼƬ�ڷ�
	 * @param textPane
	 */
	public FaceFrame( final JTextPane textPane){//?
		JPanel panel=(JPanel)getContentPane();
		panel.setLayout(null);
		//��˫��ѭ������ͼƬ
		for (int row = 0; row <10; row++) {
			for (int col = 0; col < 6; col++) {
				//�õ�ͼƬ
				ImageIcon icon=new ImageIcon("src/image/face/"+(6*row+col+1)+".gif");
				//��ͼƬ����JLable��
				JLabel lblIcon=new JLabel(icon);
				lblIcon.setSize(50,50);
				lblIcon.setLocation(0+col*50, 0+row*50);
				//Ϊlbl�������¼�
				lblIcon.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//��jLabel��e.getSourse����ǿ��ת����JLabel����
						JLabel jLabel = (JLabel)e.getSource();
						
						Icon icon2 = jLabel.getIcon();
						//����ѡ��ͼƬ
						textPane.insertIcon(icon2);
						//�رյ�ǰͼƬ��
						FaceFrame.this.dispose();
					}
				});
				panel.add(lblIcon);
			}
		}
		setSize(320, 300);
		setLocation(800, 400);
		setTitle("������");
		setVisible(true);
	}
}
