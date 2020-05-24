package com.hliedu.chat.client;//�ͻ��˶˿���һ���߳�ģ��һֱȡ��Ϣ

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
	//��¼����
	LoginFrame loginFrame;
	//����������
	ChatFrame chatFrame;
	public ClientHandler(Socket socket,LoginFrame loginFrame){
		this.socket=socket;	
		this.loginFrame=loginFrame;	
	}
	
	public void run(){
		while(true){
			try {		//��Ϣ����
				//ģ��һֱ����Ϣ����������
				Object obj=IOStream.readMessage(socket);
				if(obj instanceof TransforInfo){
					TransforInfo tfi=(TransforInfo)obj;
					if(tfi.getStatusEnum()==ChatStatus.LOGIN){
						//���ǵ�¼
						loginResult(tfi);	
					}else if(tfi.getStatusEnum()==ChatStatus.CHAT){
						ChatResult(tfi);//����������Ϣ
					}else if(tfi.getStatusEnum()==ChatStatus.NOTICE){
						
								noticeResult(tfi);}
					else if(tfi.getStatusEnum()==ChatStatus.ULIST){
							//ˢ�µ�ǰ�����û��б�
						onlineUsersResult(tfi);
					
				}
					else{
					
				}
			//	System.out.println("�ͻ���"+obj);
			//	if("��¼�ɹ�".equals(obj)){
			//		System.out.println("�ͻ����յ���¼�ɹ�����Ϣ");
			//	}
				Thread.sleep(1000);
			} }catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void loginResult(TransforInfo tfi){
		if(tfi.getLoginSucceessFlag()){
			//����ʵ����õ��û���
			String userName=tfi.getUserName();
			//��¼�ɹ�,
			chatFrame = new ChatFrame(userName,socket);
	
			loginFrame.dispose();//�رյ�¼����
			//�ĸ�������Ҫ�رվ��ĸ�����.dispose()���ڹرյ������촰�壬��ոմ򿪵ģ����ܹ�ֱ�ӹص���Ӧ�ùص���¼����
		}else{
			//��¼ʧ��
			System.out.println("�ͻ��˽��յ���¼ʧ��");
		}
		
	}
	
	//������Ϣ����
	public void ChatResult(TransforInfo tfi){
		String sender = tfi.getSender();
		List<FontStyle> contents=tfi.getContent();	//���������б�
		//���ֽ�������н�������
		FontSupport.fontDecode(chatFrame.acceptPane, contents, sender, "������");
		//String text = chatFrame.acceptPane.getText();
	//	chatFrame.acceptPane.setText(text+"\n"+sender+ " ˵: "+);//����������
	}
	
	//ϵͳ��Ϣ��ʾ
	public void noticeResult(TransforInfo tfi){
		//System.out.println("�ͻ��˽��յ���ϵͳ��Ϣ��"+tfi.getNotice());
		//������Ͷ��ϵͳ��Ϣ
		String text = chatFrame.acceptPane.getText();
		chatFrame.acceptPane.setText(text+"\n"+tfi.getNotice());
	}
	
	//ˢ�µ�ǰ������û��б�
	public void onlineUsersResult(TransforInfo tfi){
		String[] userOnlineArray=tfi.getUserOnlineArray();
		chatFrame.lstUser.setListData(userOnlineArray);
		//
	}
	
}
