package com.hliedu.chat.entity;

public class ServerInfoBean {

	private String hostName;
	private String ip;
	private Integer port;
	
	public ServerInfoBean(String hostName, String ip, Integer port) {
		super();
		this.hostName = hostName;
		this.ip = ip;
		this.port = port;
	}

	public ServerInfoBean() {
		super();
	}
	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	


	
}
