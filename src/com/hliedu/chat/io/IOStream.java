package com.hliedu.chat.io;  //IO流工具类

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IOStream {
	//从socket中读取对象
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
		//从socket管道写出消息
		OutputStream os = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(message);
		oos.flush();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}

}
