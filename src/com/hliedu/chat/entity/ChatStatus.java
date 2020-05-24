package com.hliedu.chat.entity;

public enum ChatStatus {
	//枚举
	LOGIN(1,"登陆消息"),NOTICE(2,"系统消息"),CHAT(3,"聊天消息"),DD(4,"抖动消息"),ULIST(5,"在线用户列表");
	
	private Integer status;
	private String desc;
	
	//构造方法
	private ChatStatus(int status , String desc){
		this.status = status;
		this.desc = desc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	

}
