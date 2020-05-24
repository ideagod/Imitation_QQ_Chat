package com.hliedu.chat.entity;

import java.io.Serializable;
import java.util.List;

//���ݷ�װ ����д��

public class TransforInfo implements Serializable{//���л����ܵĽӿ�

	private static final long serialVersionUID = 6543722756249559791L;
	
	private String userName;
	private String password;
	
	//������Ϣ����
	private List<FontStyle> content;
	
	//ϵͳ��Ϣ
	private String notice;
	
	//��¼�ɹ���־
	private Boolean loginSucceessFlag = false;
	
	//��Ϣ����ö��
	private ChatStatus statusEnum;
	
	//���ߵ��û��б�
	private String[] userOnlineArray;
	
	//������
	private String sender;
	
	//������
	private String reciver;
	
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReciver() {
		return reciver;
	}
	public void setReciver(String reciver) {
		this.reciver = reciver;
	}
	public String[] getUserOnlineArray() {
		return userOnlineArray;
	}
	public void setUserOnlineArray(String[] userOnlineArray) {
		this.userOnlineArray = userOnlineArray;
	}
	public ChatStatus getStatusEnum() {
		return statusEnum;
	}
	public void setStatusEnum(ChatStatus statusEnum) {
		this.statusEnum = statusEnum;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public Boolean getLoginSucceessFlag() {
		return loginSucceessFlag;
	}
	public void setLoginSucceessFlag(Boolean loginSucceessFlag) {
		this.loginSucceessFlag = loginSucceessFlag;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<FontStyle> getContent() {
		return content;
	}
	public void setContent(List<FontStyle> content) {
		this.content = content;
	}

	
	
	
}
