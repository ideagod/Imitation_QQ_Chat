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
//����������ҳ��ѡ�
public class ServerInfoPanel {
	//����������
	public JTextField txtServerName ;
	//������ip
	public JTextField txtIP;
	
	public JTextField txtNumber;
	//��־����
	public JTextPane txtLog;
	
	//��һ��ѡ�����Ϣ
	public JPanel getServerInfoPanel(){
		//������һ��ѡ�����壬������־���� һ����������
		
		JPanel pnlServer = new JPanel();
		pnlServer.setOpaque(false);
		pnlServer.setLayout(null);
		pnlServer.setBounds(0, 0, ServerFrame.FRAME_WIDTH, ServerFrame.FRAME_HEIGHT);
		
		//��־����
		JLabel lblLog = new JLabel("[��������־]");
		lblLog.setForeground(Color.BLACK);
		lblLog.setFont(new Font("����", 0, 16));
		lblLog.setBounds(130, 5, 100, 30);
		pnlServer.add(lblLog);
		
		//��־����
		txtLog = new JTextPane();
		txtLog.setOpaque(false);
		txtLog.setFont(new Font("����", 0, 12));
		
		JScrollPane scoPaneOne = new JScrollPane(txtLog);// ���ù�����
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
		serverParamPanel.setFont(new Font("����", 0, 14));
		serverParamPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		
		JLabel lblNumber = new JLabel("��ǰ��������:");
		lblNumber.setFont(new Font("����", 0, 14));
		serverParamPanel.add(lblNumber);
		
		txtNumber = new JTextField("0 ��", 10);
		txtNumber.setFont(new Font("����", 0, 14));
		txtNumber.setEditable(false);
		serverParamPanel.add(txtNumber);
		
		JLabel lblServerName = new JLabel("����������:");
		lblServerName.setFont(new Font("����", 0, 14));
		serverParamPanel.add(lblServerName);
		
		txtServerName = new JTextField(10);
		txtServerName.setFont(new Font("����", 0, 14));
		txtServerName.setEditable(false);
		serverParamPanel.add(txtServerName);
		
		JLabel lblIP = new JLabel("������IP:");
		lblIP.setFont(new Font("����", 0, 14));
		serverParamPanel.add(lblIP);
		
		txtIP = new JTextField(10);
		txtIP.setFont(new Font("����", 0, 14));
		txtIP.setEditable(false);
		serverParamPanel.add(txtIP);
		
		JLabel lblPort = new JLabel("�������˿�:");
		lblPort.setFont(new Font("����", 0, 14));
		serverParamPanel.add(lblPort);
		
		JTextField txtPort = new JTextField(Constants.SERVER_PORT+"" , 10);//ƴ��һ���ַ���
		txtPort.setFont(new Font("����", 0, 14));
		txtPort.setEditable(false);
		serverParamPanel.add(txtPort);
		return serverParamPanel;
	}
	public JButton stopBtn() {
		JButton stopBtn = new JButton("�رշ�����");
		stopBtn.setBackground(Color.WHITE);
		stopBtn.setFont(new Font("����", 0, 14));
		stopBtn.setBounds(200, 400, 110, 30);
		return stopBtn;
	}
	public JButton saveLogBtn() {
		JButton saveLogBtn = new JButton("������־");
		saveLogBtn.setBackground(Color.WHITE);
		saveLogBtn.setFont(new Font("����", 0, 14));
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
			JOptionPane.showMessageDialog(null, "�����¼������message.txt");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	}
	
	

