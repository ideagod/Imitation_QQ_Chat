package com.hliedu.chat.client;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.net.Socket;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.TransferHandler;
import javax.swing.filechooser.FileSystemView;

import com.hliedu.chat.entity.ChatStatus;
import com.hliedu.chat.entity.FontStyle;
import com.hliedu.chat.entity.TransforInfo;
import com.hliedu.chat.io.IOStream;
import com.hliedu.chat.util.FontSupport;


public class ChatFrame extends JFrame {
	String filepath;
	/**
	 * ��ȡСͼ��
	 * @param f
	 * @return
	 */
	private static Icon getSmallIcon(File f) {
		if (f != null && f.exists()) {
			FileSystemView fsv = FileSystemView.getFileSystemView();
			return fsv.getSystemIcon(f);
		}
		return null;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8945833334986986964L;

	/**
	 * @param args
	 */
	public static final Integer FRAME_WIDTH=750;
	public static final Integer FRAME_HEIGHT=600;
	
	//���ܿ�
	public JTextPane acceptPane;
	//��ǰ�����û��б�
	public JList lstUser;
	//����
	JComboBox fontFamilyCmb;

	 JLabel jlb   ;
	
	String userName;
	Socket socket;
	//java�б���������
	JComboBox receiverBox;
	
	public ChatFrame(final String userName,final Socket socket){
		filepath="";
		 jlb=new JLabel();
		this.socket=socket;
		this.userName=userName;
		
		this.setTitle("������������");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		//���岻������
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//��ȡ��Ļ
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		//��Ļ���д���
		setLocation((width-FRAME_WIDTH)/2, (height-FRAME_HEIGHT)/2);
		setVisible(true);
		
		//���ش���ı���ͼƬ
		ImageIcon imageIcon = new ImageIcon("src/image/beijing.jpg");
		//����һ����ǩ����ͼƬ��ӽ�ȥ
		JLabel frameBg = new JLabel(imageIcon);
		//����ͼƬ��λ�úʹ�С
		frameBg.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		this.add(frameBg);
		
		// ���տ�
		acceptPane = new JTextPane();
		acceptPane.setOpaque(false);//����͸��
		acceptPane.setFont(new Font("����", 0, 16));
		
		// ���ý��տ������
		JScrollPane scoPaneOne = new JScrollPane(acceptPane);
		scoPaneOne.setBounds(15, 20, 500, 332);
		//���ñ���͸��
		scoPaneOne.setOpaque(false);//true��ʾ��͸��,false��ʾ͸����
		scoPaneOne.getViewport().setOpaque(false);
		frameBg.add(scoPaneOne);
		


		//��ǰ�����û��б�
		lstUser = new JList();
		lstUser.setFont(new Font("����", 0, 14));
		lstUser.setVisibleRowCount(17);
		lstUser.setFixedCellWidth(180);
		lstUser.setFixedCellHeight(18);
		
		//�����˵�
		final JPopupMenu popupMenu=new JPopupMenu();
		//˽�İ�ť(�˵��
		JMenuItem privateChat=new JMenuItem("˽��");
		privateChat.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//��һϵ�еĶ���
				//α����
				//���߷�����Ϣ�Ľ�������˭��
				Object receiverObj = lstUser.getSelectedValue();
				if(receiverObj !=null){//���ж�
					String receiver = String.valueOf(receiverObj);
					receiverBox.removeAllItems();
					receiverBox .addItem("All");
					receiverBox .addItem(receiver);
					receiverBox.setSelectedItem(receiver);
				}
				
			}
		});
		popupMenu.add(privateChat);
		//��������ť(�˵��  ��
		JMenuItem blackList=new JMenuItem("������");
		blackList.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		popupMenu.add(blackList);
		
		//��ӵ���¼�,ȷ�����Ҽ�
		lstUser.addMouseListener(new MouseAdapter(){//������ģʽ���ӿڲ���new�ġ����ӿڽ���ת��ml
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//������������Ҽ�
				if(e.isMetaDown()){
					//�����˵�
					popupMenu.show(lstUser,e.getX(),e.getY());
				}
			}
		});
		
		
		
		JScrollPane spUser = new JScrollPane(lstUser);
		spUser.setFont(new Font("����", 0, 14));
		spUser.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spUser.setBounds(530, 17, 200, 507);
		frameBg.add(spUser);
		
		
		// �����
		final JTextPane sendPane = new JTextPane();
		sendPane.setOpaque(false);
		sendPane.setFont(new Font("����", 0, 16));
///////////////////////////////////////////
		sendPane.setTransferHandler(new TransferHandler()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean importData(JComponent comp, Transferable t) {
                try {
                    Object o = t.getTransferData(DataFlavor.javaFileListFlavor);
 
                     filepath = o.toString();
                    if (filepath.startsWith("[")) {
                        filepath = filepath.substring(1);
                    }
                    if (filepath.endsWith("]")) {
                        filepath = filepath.substring(0, filepath.length() - 1);
                    }
                    System.out.println(filepath);
              
                    File fl=new File(filepath);
                    //jlb.setIcon(getSmallIcon(fl));
                    //sendPane.setText(filepath);
                    sendPane.insertIcon(new ImageIcon(filepath));//����ͼƬ
                    return true;
                    
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
            @Override
            public boolean canImport(JComponent comp, DataFlavor[] flavors) {
                for (int i = 0; i < flavors.length; i++) {
                    if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
                        return true;
                    }
                }
                return false;
            }
        });
//		sendPane.addMouseMotionListener(new MouseMotionAdapter() {
//			@Override
//			public void mouseDragged(MouseEvent e) {
//			
//				System.out.println("Mouse Dragged");
//			}
// 
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				System.out.println("Mouse mouse Moved");
//			}
//		});
//		sendPane.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				System.out.println("Mouse mouse Pressed");
//			}
// 
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				System.out.println("Mouse mouse  Released");
//			}
// 
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				System.out.println("Mouse mouse Clicked");
//			}
// 
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				System.out.println("Mouse mouse Entered");
//			}
// 
//			@Override
//			public void mouseExited(MouseEvent e) {
//				System.out.println("Mouse mouse Exited");
//			}
//		});
		jlb.setBounds(30, 400, 500, 122);
		sendPane.add(jlb);
		/////////////////////////////////////////////
		
		JScrollPane scoPane = new JScrollPane(sendPane);// ���ù�����
		scoPane.setBounds(15, 400, 500, 122);
		scoPane.setOpaque(false);
		scoPane.getViewport().setOpaque(false);
		frameBg.add(scoPane);
		
		// ��ӱ���ѡ��
		JLabel lblface = new JLabel(new ImageIcon("src/image/face.png"));
		lblface.setBounds(14, 363, 25, 25);
		lblface.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				FaceFrame face=new FaceFrame(sendPane);
			}
		});
			
		frameBg.add(lblface);
		
		// ��Ӷ���Ч��
		JLabel lbldoudong = new JLabel(new ImageIcon("src/image/doudong.png"));
		lbldoudong.setBounds(43, 363, 25, 25);
		frameBg.add(lbldoudong);
		
		// ��������ѡ��
		JLabel lblfontChoose = new JLabel(new ImageIcon("src/image/ziti.png"));
		lblfontChoose.setBounds(44, 363, 80, 25);
		lblfontChoose.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JColorChooser colorChooser = new JColorChooser();
				Color color = colorChooser.showDialog(ChatFrame.this, "������ɫ", Color.BLACK);
				//����ı�
				FontSupport.setFont(sendPane, color, fontFamilyCmb.getSelectedItem().toString(), Font.BOLD, 60);
			}
//			
		});
		frameBg.add(lblfontChoose);
		
		//��������ѡ��
//new7	
		fontFamilyCmb = new JComboBox();
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] str = graphicsEnvironment.getAvailableFontFamilyNames();
		for (String string : str) {
		fontFamilyCmb.addItem(string);
		}
		
		fontFamilyCmb.setSelectedItem("����");
		fontFamilyCmb.setBounds(104, 363, 150, 25);
		frameBg.add(fontFamilyCmb);
		
		//label��ǩ
		JLabel receiverLabel=new JLabel("ѡ�����");
		receiverLabel.setBounds(304,363,150,25);
		frameBg.add(receiverLabel);
		
		//����ѡ���
		receiverBox = new JComboBox();
		receiverBox.setSelectedItem("All");
		receiverBox.addItem("All");
		receiverBox.setBounds(374, 363, 135, 25);
		frameBg.add(receiverBox);
		
//		/*
//		 * ���Ͱ�ť2
//		 */
//		JButton sendp = new JButton("����ͼƬ");
//		sendp.setBounds(300, 533, 125, 25);
//		sendp.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//			//	new sendpt();
//			}
//		});
//		frameBg.add(sendp);
		/*
		 * ���Ͱ�ť
		 */
		JButton send = new JButton("�� ��");
		send.setBounds(15, 533, 125, 25);
		send.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//String content = sendPane.getText();
				TransforInfo tif=new TransforInfo();
				
				//��װ���������ֶ�Ӧ������
				List<FontStyle> fontStyles = FontSupport.fontEncode(sendPane);
				tif.setContent(fontStyles);
				//������
				tif.setSender(userName);
				String receiver="All";
				//��ȡ��ǰ���͸�˭��
				Object receiverObj = receiverBox.getSelectedItem();
				
				if(receiverObj !=null){
					receiver =String.valueOf(receiverObj);
				}
				System.out.println("������Ϣ����:"+receiver);
				//������all
			
				tif.setReciver(receiver);
			
				//���δ������Ϣ����Ϊ��½
				tif.setStatusEnum(ChatStatus.CHAT);
				IOStream.writeMessage(socket, tif);
				sendPane.setText(" ");
			}
		});
		frameBg.add(send);
		
		setVisible(true);
	
	}
}
