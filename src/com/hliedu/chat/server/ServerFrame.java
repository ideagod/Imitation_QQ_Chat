package com.hliedu.chat.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;//�������˽���
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.hliedu.chat.server.ui.OnlineUserPanel;
import com.hliedu.chat.server.ui.ServerInfoPanel;

public class ServerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8152378291726535742L;
	public static final Integer FRAME_WIDTH=550;
	public static final Integer FRAME_HEIGHT=500;
	
	//�û��б����
	public 	OnlineUserPanel onlineUserPanel;
	
	//����������ѡ�
	public 	ServerInfoPanel serverInfoPanel;
	/**
	 * @param args
	 */
	public ServerFrame(){
		this.setTitle("���������ҷ�����");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		//���岻������
		setResizable(false);
		//��ȡ��Ļ
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		//��Ļ���д���
		setLocation((width-FRAME_WIDTH)/2, (height-FRAME_HEIGHT)/2);
		
		//���ô���رգ������˳�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//ѡ�
		JTabbedPane tpServer = new JTabbedPane(JTabbedPane.TOP);
		tpServer.setBackground(Color.WHITE);
		tpServer.setFont(new Font("����", 0, 18));

		
		serverInfoPanel = new ServerInfoPanel();
		tpServer.add("��������Ϣ" ,serverInfoPanel.getServerInfoPanel());
		
		 onlineUserPanel = new OnlineUserPanel();	//��ɳ�Ա
		tpServer.add("�û������б�" ,onlineUserPanel.getUserPanel());
		
		add(tpServer);
		setVisible(true);	
	}
}
