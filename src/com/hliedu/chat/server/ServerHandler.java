package com.hliedu.chat.server;//服务器端开辟一个线程模拟一直读消息

import java.awt.Color;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JList;
import javax.swing.JTextPane;

import com.hliedu.chat.entity.ChatStatus;
import com.hliedu.chat.entity.FontStyle;
import com.hliedu.chat.entity.TransforInfo;
import com.hliedu.chat.io.IOStream;
import com.hliedu.chat.util.FontSupport;

public class ServerHandler extends Thread {
	 private DataInputStream dis;  
	    private Graphics g;  
	Socket socket;
	ServerFrame serverFrame;
	public ServerHandler (Socket socket,ServerFrame serverFrame){
		this.socket=socket;
		this.serverFrame=serverFrame;
	}
	
	static List<String> onlineUsers=new ArrayList();//线程  onlineuser是是单独的一个，对象不一样
	static List<Socket> onlineSockets=new ArrayList();//管道
	//每个人持有的连接在服务器端都是存在的，需要根据接收人来发送消息
	public void run(){
		while(true){
			System.out.println("服务器端的循环");
			try {
				//模拟一直拿消息，产生阻塞。
				Object obj = IOStream.readMessage(socket);
			//	System.out.println("服务端：" +obj);
				if(obj instanceof TransforInfo){
					TransforInfo tfi=(TransforInfo) obj;//类型强行转换
					if(tfi.getStatusEnum()==ChatStatus.LOGIN){
						//这是登录
						loginHandler(tfi);
						
						
					}else if(tfi.getStatusEnum()==ChatStatus.CHAT){
						//这是聊天消息
					chatHandler(tfi);
					}
					
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
		}
		
	}

}
	//picture
	public void readPic(){  
        int rgb;  
    try {  
        rgb = dis.readInt();      
        int i=dis.readInt();  
        int j=dis.readInt();          
        Color color =new Color(rgb);  
        g.setColor(color);  
        g.drawLine(i,j,i,j);  
  
    } catch (IOException e) {  
  
        e.printStackTrace();  
    }  
    } 
	//处理客户端的聊天请求
	private void chatHandler(TransforInfo tfi){//类参
		//转发给其他用户
		String reciver = tfi.getReciver();
		if("All".equals(reciver)){
			//all
			sendAll(tfi);
			List<FontStyle> contects =tfi.getContent();
			FontSupport.fontDecode(serverFrame.serverInfoPanel.txtLog,contects,tfi.getSender(),"所有人");
			//记录日志
	//		log(tfi.getSender()+"所有人发消息，内容是 "+tfi.getContent());
		}else{
			send(tfi);
		}
	}
	
	
	
	//处理客户端的登录请求
	private void loginHandler(TransforInfo tfi){
		boolean flag = checkUserLogin(tfi);
		tfi.setLoginSucceessFlag(false);
		if(flag){
			//返回登录登录成功给客户端
			tfi.setLoginSucceessFlag(true);
			tfi.setStatusEnum(ChatStatus.LOGIN);
			IOStream.writeMessage(socket, tfi);
			String userName=tfi.getUserName();
			
			onlineUsers.add(userName);//在线统计人数
			onlineSockets.add(socket);//socket本身没有值
			
			//在线用户和管道的对应关系
			ChatServer.userSocketMap.put(userName,socket);
			
			
			//发系统消息给客户端，该用户已上线
			tfi=new TransforInfo();
			tfi.setStatusEnum(ChatStatus.NOTICE );

			String notice=">>> 欢迎"+userName +"来到群聊";
			tfi.setNotice(notice);
			sendAll(tfi);
			
	//准备最新的用户列表给当前的客户端
			tfi=new TransforInfo();
			tfi.setUserOnlineArray(onlineUsers.toArray(new String[onlineUsers.size()]));
			tfi.setStatusEnum(ChatStatus.ULIST );	
			sendAll(tfi);
			
			//刷新在线用户列表
			flushOnlineUserList();
			//记录日志
			log(notice);
		
		}else{
			System.out.println("登录失败");
			//返回登录登录失败给客户端
			IOStream.writeMessage(socket,tfi);
			//记录日志
			log(tfi.getUserName()+"登录失败");
		}
	}
	
	//记录日志的方法
	private void log(String log){
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestr=sdf.format(date);//格式化
		JTextPane txtlog=serverFrame.serverInfoPanel.txtLog;
		String oldlog=txtlog.getText();
		txtlog.setText(oldlog+"\n"+datestr+"  "+log);
	}
	
	//刷新用户列表
	public void flushOnlineUserList(){
		JList lstUser=serverFrame.onlineUserPanel.lstUser;//取到用户列表的展示框，
		String[] userArray=onlineUsers.toArray(new String[onlineUsers.size()]);//把集合转成数组
		lstUser.setListData(userArray);//设置进去
		serverFrame.serverInfoPanel.txtNumber.setText(userArray.length+"");
	}
	//发送消息给所有人
	public void sendAll(TransforInfo tfi){
		for(int i=0;i<onlineSockets.size();i++)
		{
			Socket tempSocket=onlineSockets.get(i);
			 IOStream.writeMessage(tempSocket ,tfi);//循环发送出去
		}
	}
	
	//发送消息给人私聊的接收者
	public void send(TransforInfo tfi){
		String reciver = tfi.getReciver();
		String sender = tfi.getSender();
		//根据receiver拿到Socket管道
		
		//通过用户名为键，管道为值做一个map
		Socket socket1 = ChatServer.userSocketMap.get(reciver);
		IOStream.writeMessage(socket1, tfi);
		
		Socket socket2 = ChatServer.userSocketMap.get(sender);
		IOStream.writeMessage(socket2, tfi);
	//	map.get();
	}
	//登录功能实现
	public boolean checkUserLogin(TransforInfo tfi){
		try {
			String userName = tfi.getUserName();
			String password = tfi.getPassword();
			FileInputStream fis = new FileInputStream(new File("src/user.txt"));
			DataInputStream dis = new DataInputStream(fis);
			String row=null;
			
			
			while((row=dis.readLine())!=null){
				//从文件中读取的行
				if((userName+"|"+password).equals(row)) {
					//System.out.println("服务端：用户名，密码正确");
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		}
		
	}
	
