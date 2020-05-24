package com.hliedu.chat.client;//客户端端开辟一个线程模拟一直取消息

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

import com.hliedu.chat.entity.ChatStatus;
import com.hliedu.chat.entity.FontStyle;
import com.hliedu.chat.entity.TransforInfo;
import com.hliedu.chat.io.IOStream;
import com.hliedu.chat.util.FontSupport;

public class ClientHandler extends Thread {
	Socket socket;
	//登录窗体
	LoginFrame loginFrame;
	//聊天主窗体
	ChatFrame chatFrame;
	public ClientHandler(Socket socket,LoginFrame loginFrame){
		this.socket=socket;	
		this.loginFrame=loginFrame;	
	}
	
	public void run(){
		while(true){
			try {		//消息分类
				//模拟一直拿消息，产生阻塞
				Object obj=IOStream.readMessage(socket);
				if(obj instanceof TransforInfo){
					TransforInfo tfi=(TransforInfo)obj;
					if(tfi.getStatusEnum()==ChatStatus.LOGIN){
						//这是登录
						loginResult(tfi);	
					}else if(tfi.getStatusEnum()==ChatStatus.CHAT){
						ChatResult(tfi);//这是聊天消息
					}else if(tfi.getStatusEnum()==ChatStatus.NOTICE){
						
								noticeResult(tfi);}
					else if(tfi.getStatusEnum()==ChatStatus.ULIST){
							//刷新当前在线用户列表
						onlineUsersResult(tfi);
					
				}
					else{
					
				}
			//	System.out.println("客户端"+obj);
			//	if("登录成功".equals(obj)){
			//		System.out.println("客户端收到登录成功的消息");
			//	}
				Thread.sleep(1000);
			} }catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void loginResult(TransforInfo tfi){
		if(tfi.getLoginSucceessFlag()){
			//根据实体类得到用户名
			String userName=tfi.getUserName();
			//登录成功,
			chatFrame = new ChatFrame(userName,socket);
	
			loginFrame.dispose();//关闭登录窗体
			//哪个窗体想要关闭就哪个窗体.dispose()现在关闭的是聊天窗体，你刚刚打开的，不能够直接关掉，应该关掉登录窗体
		}else{
			//登录失败
			System.out.println("客户端接收到登录失败");
		}
		
	}
	
	//聊天消息处理
	public void ChatResult(TransforInfo tfi){
		String sender = tfi.getSender();
		List<FontStyle> contents=tfi.getContent();	//返回文字列表
		//文字解析类进行解析处理
		FontSupport.fontDecode(chatFrame.acceptPane, contents, sender, "所有人");
		//String text = chatFrame.acceptPane.getText();
	//	chatFrame.acceptPane.setText(text+"\n"+sender+ " 说: "+);//函数里面有
	}
	
	//系统消息提示
	public void noticeResult(TransforInfo tfi){
		//System.out.println("客户端接收到的系统消息："+tfi.getNotice());
		//公屏上投射系统消息
		String text = chatFrame.acceptPane.getText();
		chatFrame.acceptPane.setText(text+"\n"+tfi.getNotice());
	}
	
	//刷新当前界面的用户列表
	public void onlineUsersResult(TransforInfo tfi){
		String[] userOnlineArray=tfi.getUserOnlineArray();
		chatFrame.lstUser.setListData(userOnlineArray);
		//
	}
	
}
