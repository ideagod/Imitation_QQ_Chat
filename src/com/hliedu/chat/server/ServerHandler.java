package com.hliedu.chat.server;//�������˿���һ���߳�ģ��һֱ����Ϣ

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
	
	static List<String> onlineUsers=new ArrayList();//�߳�  onlineuser���ǵ�����һ��������һ��
	static List<Socket> onlineSockets=new ArrayList();//�ܵ�
	//ÿ���˳��е������ڷ������˶��Ǵ��ڵģ���Ҫ���ݽ�������������Ϣ
	public void run(){
		while(true){
			System.out.println("�������˵�ѭ��");
			try {
				//ģ��һֱ����Ϣ������������
				Object obj = IOStream.readMessage(socket);
			//	System.out.println("����ˣ�" +obj);
				if(obj instanceof TransforInfo){
					TransforInfo tfi=(TransforInfo) obj;//����ǿ��ת��
					if(tfi.getStatusEnum()==ChatStatus.LOGIN){
						//���ǵ�¼
						loginHandler(tfi);
						
						
					}else if(tfi.getStatusEnum()==ChatStatus.CHAT){
						//����������Ϣ
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
	//����ͻ��˵���������
	private void chatHandler(TransforInfo tfi){//���
		//ת���������û�
		String reciver = tfi.getReciver();
		if("All".equals(reciver)){
			//all
			sendAll(tfi);
			List<FontStyle> contects =tfi.getContent();
			FontSupport.fontDecode(serverFrame.serverInfoPanel.txtLog,contects,tfi.getSender(),"������");
			//��¼��־
	//		log(tfi.getSender()+"�����˷���Ϣ�������� "+tfi.getContent());
		}else{
			send(tfi);
		}
	}
	
	
	
	//����ͻ��˵ĵ�¼����
	private void loginHandler(TransforInfo tfi){
		boolean flag = checkUserLogin(tfi);
		tfi.setLoginSucceessFlag(false);
		if(flag){
			//���ص�¼��¼�ɹ����ͻ���
			tfi.setLoginSucceessFlag(true);
			tfi.setStatusEnum(ChatStatus.LOGIN);
			IOStream.writeMessage(socket, tfi);
			String userName=tfi.getUserName();
			
			onlineUsers.add(userName);//����ͳ������
			onlineSockets.add(socket);//socket����û��ֵ
			
			//�����û��͹ܵ��Ķ�Ӧ��ϵ
			ChatServer.userSocketMap.put(userName,socket);
			
			
			//��ϵͳ��Ϣ���ͻ��ˣ����û�������
			tfi=new TransforInfo();
			tfi.setStatusEnum(ChatStatus.NOTICE );

			String notice=">>> ��ӭ"+userName +"����Ⱥ��";
			tfi.setNotice(notice);
			sendAll(tfi);
			
	//׼�����µ��û��б����ǰ�Ŀͻ���
			tfi=new TransforInfo();
			tfi.setUserOnlineArray(onlineUsers.toArray(new String[onlineUsers.size()]));
			tfi.setStatusEnum(ChatStatus.ULIST );	
			sendAll(tfi);
			
			//ˢ�������û��б�
			flushOnlineUserList();
			//��¼��־
			log(notice);
		
		}else{
			System.out.println("��¼ʧ��");
			//���ص�¼��¼ʧ�ܸ��ͻ���
			IOStream.writeMessage(socket,tfi);
			//��¼��־
			log(tfi.getUserName()+"��¼ʧ��");
		}
	}
	
	//��¼��־�ķ���
	private void log(String log){
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestr=sdf.format(date);//��ʽ��
		JTextPane txtlog=serverFrame.serverInfoPanel.txtLog;
		String oldlog=txtlog.getText();
		txtlog.setText(oldlog+"\n"+datestr+"  "+log);
	}
	
	//ˢ���û��б�
	public void flushOnlineUserList(){
		JList lstUser=serverFrame.onlineUserPanel.lstUser;//ȡ���û��б��չʾ��
		String[] userArray=onlineUsers.toArray(new String[onlineUsers.size()]);//�Ѽ���ת������
		lstUser.setListData(userArray);//���ý�ȥ
		serverFrame.serverInfoPanel.txtNumber.setText(userArray.length+"");
	}
	//������Ϣ��������
	public void sendAll(TransforInfo tfi){
		for(int i=0;i<onlineSockets.size();i++)
		{
			Socket tempSocket=onlineSockets.get(i);
			 IOStream.writeMessage(tempSocket ,tfi);//ѭ�����ͳ�ȥ
		}
	}
	
	//������Ϣ����˽�ĵĽ�����
	public void send(TransforInfo tfi){
		String reciver = tfi.getReciver();
		String sender = tfi.getSender();
		//����receiver�õ�Socket�ܵ�
		
		//ͨ���û���Ϊ�����ܵ�Ϊֵ��һ��map
		Socket socket1 = ChatServer.userSocketMap.get(reciver);
		IOStream.writeMessage(socket1, tfi);
		
		Socket socket2 = ChatServer.userSocketMap.get(sender);
		IOStream.writeMessage(socket2, tfi);
	//	map.get();
	}
	//��¼����ʵ��
	public boolean checkUserLogin(TransforInfo tfi){
		try {
			String userName = tfi.getUserName();
			String password = tfi.getPassword();
			FileInputStream fis = new FileInputStream(new File("src/user.txt"));
			DataInputStream dis = new DataInputStream(fis);
			String row=null;
			
			
			while((row=dis.readLine())!=null){
				//���ļ��ж�ȡ����
				if((userName+"|"+password).equals(row)) {
					//System.out.println("����ˣ��û�����������ȷ");
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		}
		
	}
	
