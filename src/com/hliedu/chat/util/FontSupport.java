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

	//��װ����ķ���
	//��������ķ���
	/**
	 * 
	 * @param txtSent ��Ҫ���õ��ı���
	 * @param color   ������ɫ
	 * @param fontFamily ����
	 * @param fontStyle	 ��ʽ
	 * @param fontSize	��С
	 */
	public static void setFont(JTextPane txtSent, Color color, String fontFamily, int fontStyle, int fontSize) {
		// �õ��༭���е��ĵ�
		Document document = txtSent.getDocument();
		try {
			// ���һ������������ʽ����
			StyleContext sc = StyleContext.getDefaultStyleContext();
			// Ϊ����ӵ���ʽ�����������ɫ  ���Լ�
			AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
					StyleConstants.Foreground, color);
			Font font = new Font(fontFamily, fontStyle, fontSize);
			// Ϊ��ӵ���ʽ���������
			aset = sc.addAttribute(aset, StyleConstants.Family, font.getFamily());
			// ��������Ĵ�С
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
			// ����û�ҵ�ֱ��������ѡ�ֵķ�����ֻ�����Ƴ�ԭ�����ַ���
			document.remove(start, end - start);
			// ���²����ַ��������������õ���ʽ���в���
			document.insertString(start, str, aset);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * �����Ϳ�����������ʵ������
	 * @param textPane
	 * @return
	 */           //�ж������
	public static List<FontStyle> fontEncode(JTextPane textPane) {
		Document document = textPane.getDocument();
		List<FontStyle> list = new ArrayList<FontStyle>();
		for (int i = 0; i < document.getLength(); i++) {//ÿ������
			try {
				StyledDocument sd= textPane.getStyledDocument();
				FontStyle font = new FontStyle();
				Element e= sd.getCharacterElement(i);//ÿ���ַ�����
				if(e instanceof LeafElement){
					//ƥ��������������
					if(e.getName().equals("content")){
						AttributeSet as= e.getAttributes();
						font.setContent(sd.getText(i, 1));//��װ
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
						//����ͼƬ·��
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
	 * ���ݽ���
	 * @return
	 */
	public static void fontDecode(JTextPane textPane , List<FontStyle> list , String sender, String receiver) {
		Document doc = contentAppend(textPane , "\n " + sender + " �� " + receiver + " ˵: ");
		sc = StyleContext.getDefaultStyleContext();
		for (FontStyle zi : list) {//��������ÿ�����ֵĶ����ó���
			if (zi != null) {
				// �õ��༭���е��ĵ�
				if (zi.getContent() != null) {
					try {
						// Ϊ����ӵ���ʽ�����������ɫ  ����
						AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground,zi.getColor());
						Font font = new Font(zi.getFontFamily(), zi.getFontStyle(), zi.getSize());
						// Ϊ��ӵ���ʽ���������
						aset = sc.addAttribute(aset,StyleConstants.Family, font.getFamily());
						// ��������Ĵ�С
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
						doc.insertString(doc.getLength(), zi.getContent(),aset);//��ʽ
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					//����ͼƬ�����
					textPane.setCaretPosition(doc.getLength());
					textPane.insertIcon(new ImageIcon(zi.getPath()));
				}
			}
		}
	}
	
	/**
	 * ֱ�Ӹ�ĳ��Text����������
	 * @param textPane
	 * @param content
	 */
	public static Document contentAppend(JTextPane textPane,String content) {
		Document doc = textPane.getDocument();
		// ���һ������������ʽ����
		StyleContext sc = StyleContext.getDefaultStyleContext();
		// Ϊ����ӵ���ʽ�����������ɫ
		AttributeSet asetLine = sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground,Color.black);
		try {
			doc.insertString(doc.getLength(), content , asetLine);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	
}