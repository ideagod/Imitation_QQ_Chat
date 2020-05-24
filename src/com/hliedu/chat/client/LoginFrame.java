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
import javax.swing.JFrame; //登录界面
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
		this.setTitle("登录QQ");
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		this.setResizable(false);
		//获取屏幕高度和宽度的方法
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int width=screenSize.width;
		int height=screenSize.height;
		setLocation(((width-500)/2),(height-500)/2);
		
		//加载窗体的背景图片
		ImageIcon imageIcon = new ImageIcon("src/image/beijing2.jpg");
		//创建一个标签并将图片添加进去
		JLabel lblBackground=new JLabel(imageIcon);
		
		//设置图片的位置和大小
		lblBackground.setBounds(0,0,500,500);
		//设置布局为空布局
		lblBackground.setLayout(null);
		//添加到当前窗体中
		this.add(lblBackground);
		
		//创建一个标签
		JLabel lblUid=new JLabel("账 号: ");
		//设置位置、大小
		lblUid.setBounds(80, 40, 120, 30);
		lblUid.setFont(new Font("宋体" , 0 , 16));
		//设置标签文本的颜色为白色
		lblUid.setForeground(Color.WHITE);
		//将标签添加到背景图片上
		lblBackground.add(lblUid);
		
		//账号文本框
		final JTextField textUid = new JTextField();
		//设置文本框的位置、大小
		textUid.setBounds(150, 40, 160, 30); //位置和size
		lblBackground.add(textUid);
		
		//创建一个的标签
		JLabel lblPsw=new JLabel("密 码: ");
		//设置标签的位置、大小
		lblPsw.setBounds(80, 80, 120, 30);
		lblPsw.setFont(new Font("宋体" , 0 , 16));
		//设置字体颜色为白色
		lblPsw.setForeground(Color.WHITE);
		//添加到背景图片上
		lblBackground.add(lblPsw);
		
		//创建一个密码框，用于输入用户密码
		final JPasswordField textPsw = new JPasswordField();
		//设置密码框的位置、大小
		textPsw.setBounds(150, 80, 160, 30);
		lblBackground.add(textPsw);
		
		
		//创建一个文字按钮
		JButton enter = new JButton("登 录");
		//设置位置、大小
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
				//本次处理的消息类型为登陆
				tif.setStatusEnum(ChatStatus.LOGIN);
				connectionServer(tif);//调用函数
				
			}
			
		});
		lblBackground.add(enter);
		
		
		//创建一个取消按钮
		JButton cancel=new JButton("取 消");
		//设置按钮的位置、大小
		cancel.setBounds(215, 170, 80, 25);
		//添加到背景图片上
		lblBackground.add(cancel);
		
		
		
		//设置布局为空布局
		this.setLayout(null);
		
		
		
		
		
		this.setVisible(true);
		
	}
	//连接服务器的方法
	public void connectionServer(TransforInfo tif){
		try {
			System.out.println("客户端连接之前");
			Socket socket=new Socket("127.0.0.1",8888);
			
			IOStream.writeMessage(socket,tif);
			
			 ClientHandler clientHandler=new ClientHandler(socket,this);
			 clientHandler.start();//主线程处理窗口，开辟一个新线程处理消息
			 System.out.println("客户端线程启动之后");
			 
			
		
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoginFrame();

	}

}
