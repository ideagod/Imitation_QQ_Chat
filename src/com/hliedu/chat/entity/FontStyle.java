package com.hliedu.chat.entity;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;

// ���ڴ��ÿһ�����ֵİ�װ����

public class FontStyle implements Serializable{	//���⻯�ӿ�

	private static final long serialVersionUID = -1156702467078049893L;

	/**
	 * �ֵ�����
	 */
	private String content;
	/**
	 * �ֵ�����
	 */
	private String fontFamily;

	/**
	 * �ֵĴ�С
	 */
	private int size;
	/**
	 * �ֵ���ɫ
	 */
	private Color color;
	/**
	 * �ֵ���ʽ
	 */
	private int fontStyle;
	/**
	 * ͼƬ·��
	 */
	private String path;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	public Color getColor() {
		return color;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}
	
	
}
