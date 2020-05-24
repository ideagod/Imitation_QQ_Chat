package com.hliedu.chat.server;//服务器启动入口

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.hliedu.chat.Constants.Constants;
import com.hliedu.chat.entity.ServerInfoBean;

public class ChatServer {
	static Map<String,Socket> userSocketMap =new HashMap<String,Socket>();//私聊
	//最常用的Map,它根据键的HashCode 值存储数据,根据键可以直接获取它的值，具有很快的访问速度。
	//HashMap最多只允许一条记录的键为Null(多条会覆盖);允许多条记录的值为 Null。非同步的。
	ServerFrame serverFrame;
	/**
	 * @param args 
	 */
	public ChatServer(){
		try {
			//建立服务器的socket监听
			ServerSocket sso=new ServerSocket(Constants.SERVER_PORT);
			serverFrame =new ServerFrame();
			 
			
			//初始化服务器参数信息
		ServerInfoBean serverInfo = getServerIP();
		loadServerInfo(serverInfo);
			
			//等待一个客户端连接，阻塞实现
			while(true){
				//解决多个客户端
				Socket socket =sso.accept();
				ServerHandler serverHandler=new ServerHandler(socket,serverFrame);
				serverHandler.start();
				System.out.println("服务器接收到客户端连接："+socket);
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

}
	//初始化加载服务器参数
		public void loadServerInfo(ServerInfoBean serverInfo) {
		serverFrame.serverInfoPanel.txtIP.setText(serverInfo.getIp());
		serverFrame.serverInfoPanel.txtServerName.setText(serverInfo.getHostName());
		serverFrame.serverInfoPanel.txtLog.setText("服务器已经启动...");
	}
	
	/**
	 * 获取服务器的主机名和IP地址
	 * @return 返回服务器IP等信息
	 */
	public ServerInfoBean getServerIP() {
		ServerInfoBean sib = null;
		try {
			InetAddress serverAddress = InetAddress.getLocalHost();
			byte[] ipAddress = serverAddress.getAddress();
			sib = new ServerInfoBean();
			sib.setIp(serverAddress.getHostAddress());
			sib.setHostName(serverAddress.getHostName());
			sib.setPort(Constants.SERVER_PORT);
			
			System.out.println("Server IP is:" + (ipAddress[0] & 0xff) + "."
					+ (ipAddress[1] & 0xff) + "." + (ipAddress[2] & 0xff) + "."
					+ (ipAddress[3] & 0xff));
		} catch (Exception e) {
			System.out.println("###Cound not get Server IP." + e);
		}
		return sib;
	}
public static void main(String[] args) {
	// TODO Auto-generated method stub
	new ChatServer();

}
}