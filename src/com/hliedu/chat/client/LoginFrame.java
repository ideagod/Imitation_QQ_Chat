package com.hliedu.chat.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame; //��¼����
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.omg.CORBA.portable.InputStream;

import com.hliedu.chat.entity.ChatStatus;
import com.hliedu.chat.entity.TransforInfo;
import com.hliedu.chat.io.IOStream;

public class LoginFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4065664344081690485L;
	
	public LoginFrame(){
		this.setTitle("��¼QQ");
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		this.setResizable(false);
		//��ȡ��Ļ�߶ȺͿ�ȵķ���
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int width=screenSize.width;
		int height=screenSize.height;
		setLocation(((width-500)/2),(height-500)/2);
		
		//���ش���ı���ͼƬ
		ImageIcon imageIcon = new ImageIcon("src/image/beijing2.jpg");
		//����һ����ǩ����ͼƬ��ӽ�ȥ
		JLabel lblBackground=new JLabel(imageIcon);
		
		//����ͼƬ��λ�úʹ�С
		lblBackground.setBounds(0,0,500,500);
		//���ò���Ϊ�ղ���
		lblBackground.setLayout(null);
		//��ӵ���ǰ������
		this.add(lblBackground);
		
		//����һ����ǩ
		JLabel lblUid=new JLabel("�� ��: ");
		//����λ�á���С
		lblUid.setBounds(80, 40, 120, 30);
		lblUid.setFont(new Font("����" , 0 , 16));
		//���ñ�ǩ�ı�����ɫΪ��ɫ
		lblUid.setForeground(Color.WHITE);
		//����ǩ��ӵ�����ͼƬ��
		lblBackground.add(lblUid);
		
		//�˺��ı���
		final JTextField textUid = new JTextField();
		//�����ı����λ�á���С
		textUid.setBounds(150, 40, 160, 30); //λ�ú�size
		lblBackground.add(textUid);
		
		//����һ���ı�ǩ
		JLabel lblPsw=new JLabel("�� ��: ");
		//���ñ�ǩ��λ�á���С
		lblPsw.setBounds(80, 80, 120, 30);
		lblPsw.setFont(new Font("����" , 0 , 16));
		//����������ɫΪ��ɫ
		lblPsw.setForeground(Color.WHITE);
		//��ӵ�����ͼƬ��
		lblBackground.add(lblPsw);
		
		//����һ����������������û�����
		final JPasswordField textPsw = new JPasswordField();
		//����������λ�á���С
		textPsw.setBounds(150, 80, 160, 30);
		lblBackground.add(textPsw);
		
		
		//����һ�����ְ�ť
		JButton enter = new JButton("�� ¼");
		//����λ�á���С
		enter.setBounds(110, 170, 80, 25);
		enter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String userName=textUid.getText();
				String password=textPsw.getText();
				TransforInfo tif=new TransforInfo();
				tif.setUserName(userName);
				tif.setPassword(password);
				//���δ������Ϣ����Ϊ��½
				tif.setStatusEnum(ChatStatus.LOGIN);
				connectionServer(tif);//���ú���
				
			}
			
		});
		lblBackground.add(enter);
		
		
		//����һ��ȡ����ť
		JButton cancel=new JButton("ȡ ��");
		//���ð�ť��λ�á���С
		cancel.setBounds(215, 170, 80, 25);
		//��ӵ�����ͼƬ��
		lblBackground.add(cancel);
		
		
		
		//���ò���Ϊ�ղ���
		this.setLayout(null);
		
		
		
		
		
		this.setVisible(true);
		
	}
	//���ӷ������ķ���
	public void connectionServer(TransforInfo tif){
		try {
			System.out.println("�ͻ�������֮ǰ");
			Socket socket=new Socket("127.0.0.1",8888);
			
			IOStream.writeMessage(socket,tif);
			
			 ClientHandler clientHandler=new ClientHandler(socket,this);
			 clientHandler.start();//���̴߳����ڣ�����һ�����̴߳�����Ϣ
			 System.out.println("�ͻ����߳�����֮��");
			 
			
		
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoginFrame();

	}

}
