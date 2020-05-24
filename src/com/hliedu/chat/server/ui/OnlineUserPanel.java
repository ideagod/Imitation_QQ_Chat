package com.hliedu.chat.server.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * ����˵ڶ���ѡ��������û��б�
 *
 */
public class OnlineUserPanel {
	
	//�û��б�
	public JList lstUser;
	
	public JLabel getUserPanel() {
		// �û����
		JPanel pnlUser = new JPanel();
		pnlUser.setLayout(null);
		pnlUser.setBackground(new Color(52, 130, 203));
		pnlUser.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		pnlUser.setBounds(50, 5, 300, 400);
		pnlUser.setOpaque(false);//����͸��
		
		JLabel lblUser = new JLabel("[�����û��б�]");
		lblUser.setFont(new Font("����", 0, 16));
		lblUser.setBounds(50, 10, 200, 30);
		pnlUser.add(lblUser);
		
		//�û��б�
		 lstUser = new JList();
		lstUser.setFont(new Font("����", 0, 14));
		lstUser.setVisibleRowCount(17);
		lstUser.setFixedCellWidth(180);
		lstUser.setFixedCellHeight(18);
		lstUser.setOpaque(false);

		JScrollPane spUser = new JScrollPane(lstUser);
		spUser.setFont(new Font("����", 0, 14));
		spUser.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spUser.setBounds(50, 35, 200, 360);
		spUser.setOpaque(false);
		pnlUser.add(spUser);
		
		//���ش���ı���ͼƬ
		ImageIcon imageIcon = new ImageIcon("src\\image\\beijing.jpg");
		//����һ����ǩ����ͼƬ��ӽ�ȥ
		JLabel lblBackground = new JLabel(imageIcon);
		//����ͼƬ��λ�úʹ�С
		lblBackground.setBounds(0, 200, 300, 300);
		//��ӵ���ǰ������
		lblBackground.add(pnlUser);
		
		return lblBackground;
	}

}

