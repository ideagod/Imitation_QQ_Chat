package com.hliedu.chat.server;//�������������

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.hliedu.chat.Constants.Constants;
import com.hliedu.chat.entity.ServerInfoBean;

public class ChatServer {
	static Map<String,Socket> userSocketMap =new HashMap<String,Socket>();//˽��
	//��õ�Map,�����ݼ���HashCode ֵ�洢����,���ݼ�����ֱ�ӻ�ȡ����ֵ�����кܿ�ķ����ٶȡ�
	//HashMap���ֻ����һ����¼�ļ�ΪNull(�����Ḳ��);���������¼��ֵΪ Null����ͬ���ġ�
	ServerFrame serverFrame;
	/**
	 * @param args 
	 */
	public ChatServer(){
		try {
			//������������socket����
			ServerSocket sso=new ServerSocket(Constants.SERVER_PORT);
			serverFrame =new ServerFrame();
			 
			
			//��ʼ��������������Ϣ
		ServerInfoBean serverInfo = getServerIP();
		loadServerInfo(serverInfo);
			
			//�ȴ�һ���ͻ������ӣ�����ʵ��
			while(true){
				//�������ͻ���
				Socket socket =sso.accept();
				ServerHandler serverHandler=new ServerHandler(socket,serverFrame);
				serverHandler.start();
				System.out.println("���������յ��ͻ������ӣ�"+socket);
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

}
	//��ʼ�����ط���������
		public void loadServerInfo(ServerInfoBean serverInfo) {
		serverFrame.serverInfoPanel.txtIP.setText(serverInfo.getIp());
		serverFrame.serverInfoPanel.txtServerName.setText(serverInfo.getHostName());
		serverFrame.serverInfoPanel.txtLog.setText("�������Ѿ�����...");
	}
	
	/**
	 * ��ȡ����������������IP��ַ
	 * @return ���ط�����IP����Ϣ
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