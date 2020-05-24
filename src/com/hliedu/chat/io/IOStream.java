package com.hliedu.chat.io;  //IO��������

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IOStream {
	//��socket�ж�ȡ����
	public static Object readMessage(Socket socket){
		Object obj = null;
		try {
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			obj = ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
public static void writeMessage(Socket socket,Object message){
	try {
		//��socket�ܵ�д����Ϣ
		OutputStream os = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(message);
		oos.flush();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}

}
