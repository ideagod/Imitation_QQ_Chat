package com.hliedu.chat.server.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.hliedu.chat.Constants.Constants;
import com.hliedu.chat.server.ServerFrame;
//服务器参数页面选项卡
public class ServerInfoPanel {
	//服务器名称
	public JTextField txtServerName ;
	//服务器ip
	public JTextField txtIP;
	
	public JTextField txtNumber;
	//日志区域
	public JTextPane txtLog;
	
	//第一个选项卡的信息
	public JPanel getServerInfoPanel(){
		//整个第一个选项服务板，包括日志区域 一整个大的面板
		
		JPanel pnlServer = new JPanel();
		pnlServer.setOpaque(false);
		pnlServer.setLayout(null);
		pnlServer.setBounds(0, 0, ServerFrame.FRAME_WIDTH, ServerFrame.FRAME_HEIGHT);
		
		//日志区域
		JLabel lblLog = new JLabel("[服务器日志]");
		lblLog.setForeground(Color.BLACK);
		lblLog.setFont(new Font("宋体", 0, 16));
		lblLog.setBounds(130, 5, 100, 30);
		pnlServer.add(lblLog);
		
		//日志区域
		txtLog = new JTextPane();
		txtLog.setOpaque(false);
		txtLog.setFont(new Font("宋体", 0, 12));
		
		JScrollPane scoPaneOne = new JScrollPane(txtLog);// 设置滚动条
		scoPaneOne.setBounds(130, 35, 340, 360);
		scoPaneOne.setOpaque(false);
		scoPaneOne.getViewport().setOpaque(false);
		pnlServer.add(scoPaneOne);
		
		pnlServer.add(stopBtn());
		
		pnlServer.add(saveLogBtn());
		
		
		pnlServer.add(getServerParam());
		
		//pnlServer.add(serverParamPanel);
		return pnlServer;
		
		
	}
	public JPanel getServerParam(){
		JPanel serverParamPanel = new JPanel();
		serverParamPanel.setOpaque(false);
		serverParamPanel.setBounds(5, 5, 100, 400);
		serverParamPanel.setFont(new Font("宋体", 0, 14));
		serverParamPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		
		JLabel lblNumber = new JLabel("当前在线人数:");
		lblNumber.setFont(new Font("宋体", 0, 14));
		serverParamPanel.add(lblNumber);
		
		txtNumber = new JTextField("0 人", 10);
		txtNumber.setFont(new Font("宋体", 0, 14));
		txtNumber.setEditable(false);
		serverParamPanel.add(txtNumber);
		
		JLabel lblServerName = new JLabel("服务器名称:");
		lblServerName.setFont(new Font("宋体", 0, 14));
		serverParamPanel.add(lblServerName);
		
		txtServerName = new JTextField(10);
		txtServerName.setFont(new Font("宋体", 0, 14));
		txtServerName.setEditable(false);
		serverParamPanel.add(txtServerName);
		
		JLabel lblIP = new JLabel("服务器IP:");
		lblIP.setFont(new Font("宋体", 0, 14));
		serverParamPanel.add(lblIP);
		
		txtIP = new JTextField(10);
		txtIP.setFont(new Font("宋体", 0, 14));
		txtIP.setEditable(false);
		serverParamPanel.add(txtIP);
		
		JLabel lblPort = new JLabel("服务器端口:");
		lblPort.setFont(new Font("宋体", 0, 14));
		serverParamPanel.add(lblPort);
		
		JTextField txtPort = new JTextField(Constants.SERVER_PORT+"" , 10);//拼接一个字符串
		txtPort.setFont(new Font("宋体", 0, 14));
		txtPort.setEditable(false);
		serverParamPanel.add(txtPort);
		return serverParamPanel;
	}
	public JButton stopBtn() {
		JButton stopBtn = new JButton("关闭服务器");
		stopBtn.setBackground(Color.WHITE);
		stopBtn.setFont(new Font("宋体", 0, 14));
		stopBtn.setBounds(200, 400, 110, 30);
		return stopBtn;
	}
	public JButton saveLogBtn() {
		JButton saveLogBtn = new JButton("保存日志");
		saveLogBtn.setBackground(Color.WHITE);
		saveLogBtn.setFont(new Font("宋体", 0, 14));
		saveLogBtn.setBounds(320, 400, 110, 30);
		saveLogBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
		}
		});
		return saveLogBtn;
	}
	public void save(){
		try {
			FileOutputStream fileoutput = new FileOutputStream("message.txt", true);
			int a = Integer.parseInt(txtLog.getText());
			fileoutput.write(a);
			fileoutput.close();
			JOptionPane.showMessageDialog(null, "聊天记录保存在message.txt");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	}
	
	

