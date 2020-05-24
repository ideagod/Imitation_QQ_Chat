package com.hliedu.chat.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;//服务器端界面
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
	
	//用户列表界面
	public 	OnlineUserPanel onlineUserPanel;
	
	//服务器参数选项卡
	public 	ServerInfoPanel serverInfoPanel;
	/**
	 * @param args
	 */
	public ServerFrame(){
		this.setTitle("网络聊天室服务器");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		//窗体不可扩大
		setResizable(false);
		//获取屏幕
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		//屏幕居中处理
		setLocation((width-FRAME_WIDTH)/2, (height-FRAME_HEIGHT)/2);
		
		//设置窗体关闭，程序退出
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//选项卡
		JTabbedPane tpServer = new JTabbedPane(JTabbedPane.TOP);
		tpServer.setBackground(Color.WHITE);
		tpServer.setFont(new Font("宋体", 0, 18));

		
		serverInfoPanel = new ServerInfoPanel();
		tpServer.add("服务器信息" ,serverInfoPanel.getServerInfoPanel());
		
		 onlineUserPanel = new OnlineUserPanel();	//变成成员
		tpServer.add("用户在线列表" ,onlineUserPanel.getUserPanel());
		
		add(tpServer);
		setVisible(true);	
	}
}
