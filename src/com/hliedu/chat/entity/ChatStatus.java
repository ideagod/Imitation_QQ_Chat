package com.hliedu.chat.entity;

public enum ChatStatus {
	//ö��
	LOGIN(1,"��½��Ϣ"),NOTICE(2,"ϵͳ��Ϣ"),CHAT(3,"������Ϣ"),DD(4,"������Ϣ"),ULIST(5,"�����û��б�");
	
	private Integer status;
	private String desc;
	
	//���췽��
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
