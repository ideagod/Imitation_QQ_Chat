package com.hliedu.chat.util;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.AbstractDocument.LeafElement;

import com.hliedu.chat.entity.FontStyle;

public class FontSupport {

	//包装字体的方法
	//解析字体的方法
	/**
	 * 
	 * @param txtSent 需要作用的文本框
	 * @param color   字体颜色
	 * @param fontFamily 字体
	 * @param fontStyle	 样式
	 * @param fontSize	大小
	 */
	public static void setFont(JTextPane txtSent, Color color, String fontFamily, int fontStyle, int fontSize) {
		// 得到编辑器中的文档
		Document document = txtSent.getDocument();
		try {
			// 添加一个可以设置样式的类
			StyleContext sc = StyleContext.getDefaultStyleContext();
			// 为所添加的样式类添加字体颜色  属性集
			AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
					StyleConstants.Foreground, color);
			Font font = new Font(fontFamily, fontStyle, fontSize);
			// 为添加的样式类添加字体
			aset = sc.addAttribute(aset, StyleConstants.Family, font.getFamily());
			// 设置字体的大小
			aset = sc.addAttribute(aset, StyleConstants.FontSize, fontSize);
			if(fontStyle==Font.BOLD){
				aset=sc.addAttribute(aset, StyleConstants.Bold, true);
			}
			if(fontStyle==Font.ITALIC){
				aset=sc.addAttribute(aset, StyleConstants.Italic, true);
			}
			if(fontStyle==Font.PLAIN){
				aset=sc.addAttribute(aset, StyleConstants.Bold, false);
				aset=sc.addAttribute(aset, StyleConstants.Italic, false);
			}
			int start = txtSent.getSelectionStart();
			int end = txtSent.getSelectionEnd();
			String str = document.getText(start, end - start);
			// 由于没找到直接设置所选字的方法，只有先移除原来的字符串
			document.remove(start, end - start);
			// 重新插入字符串，并按新设置的样式进行插入
			document.insertString(start, str, aset);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * 将发送框的字体解析到实体类中
	 * @param textPane
	 * @return
	 */           //有多个文字
	public static List<FontStyle> fontEncode(JTextPane textPane) {
		Document document = textPane.getDocument();
		List<FontStyle> list = new ArrayList<FontStyle>();
		for (int i = 0; i < document.getLength(); i++) {//每个文字
			try {
				StyledDocument sd= textPane.getStyledDocument();
				FontStyle font = new FontStyle();
				Element e= sd.getCharacterElement(i);//每个字符属性
				if(e instanceof LeafElement){
					//匹配内容文字区域
					if(e.getName().equals("content")){
						AttributeSet as= e.getAttributes();
						font.setContent(sd.getText(i, 1));//封装
						font.setFontFamily(as.getAttribute(StyleConstants.Family).toString());
						font.setSize((Integer)as.getAttribute(StyleConstants.FontSize));
						font.setFontStyle((Integer)as.getAttribute(StyleConstants.FontSize));
						font.setColor((Color)as.getAttribute(StyleConstants.Foreground));
						if(StyleConstants.isBold(as)){
							font.setFontStyle(Font.BOLD);
						}else if(StyleConstants.isItalic(as)){
							font.setFontStyle(Font.ITALIC);
						}else{
							font.setFontStyle(Font.PLAIN);
						}
					} else if(e.getName().equals("icon")) {
						//设置图片路径
						font.setPath(e.getAttributes().getAttribute(StyleConstants.IconAttribute).toString());
					}
				}
				list.add(font);
			}catch (Exception e) {
		
			}
		}
		return list;
	}
	
	
	static StyleContext sc = null;
	
	/**
	 * 内容解码
	 * @return
	 */
	public static void fontDecode(JTextPane textPane , List<FontStyle> list , String sender, String receiver) {
		Document doc = contentAppend(textPane , "\n " + sender + " 对 " + receiver + " 说: ");
		sc = StyleContext.getDefaultStyleContext();
		for (FontStyle zi : list) {//遍历，将每个文字的对象拿出来
			if (zi != null) {
				// 得到编辑器中的文档
				if (zi.getContent() != null) {
					try {
						// 为所添加的样式类添加字体颜色  解析
						AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground,zi.getColor());
						Font font = new Font(zi.getFontFamily(), zi.getFontStyle(), zi.getSize());
						// 为添加的样式类添加字体
						aset = sc.addAttribute(aset,StyleConstants.Family, font.getFamily());
						// 设置字体的大小
						aset = sc.addAttribute(aset,StyleConstants.FontSize, zi.getSize());
						System.out.println(zi.getSize());
						if (zi.getFontStyle() == Font.BOLD) {
							aset = sc.addAttribute(aset,StyleConstants.Bold, true);
						}
						if (zi.getFontStyle() == Font.ITALIC) {
							aset = sc.addAttribute(aset,StyleConstants.Italic, true);
						}
						if (zi.getFontStyle() == Font.PLAIN) {
							aset = sc.addAttribute(aset,StyleConstants.Bold, false);
							aset = sc.addAttribute(aset,StyleConstants.Italic, false);
						}
						doc.insertString(doc.getLength(), zi.getContent(),aset);//样式
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					//解析图片至框框
					textPane.setCaretPosition(doc.getLength());
					textPane.insertIcon(new ImageIcon(zi.getPath()));
				}
			}
		}
	}
	
	/**
	 * 直接给某个Text面板添加文字
	 * @param textPane
	 * @param content
	 */
	public static Document contentAppend(JTextPane textPane,String content) {
		Document doc = textPane.getDocument();
		// 添加一个可以设置样式的类
		StyleContext sc = StyleContext.getDefaultStyleContext();
		// 为所添加的样式类添加字体颜色
		AttributeSet asetLine = sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground,Color.black);
		try {
			doc.insertString(doc.getLength(), content , asetLine);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	
}