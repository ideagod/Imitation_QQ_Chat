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
	 * 获取小图标
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
	
	//接受框
	public JTextPane acceptPane;
	//当前在线用户列表
	public JList lstUser;
	//字体
	JComboBox fontFamilyCmb;

	 JLabel jlb   ;
	
	String userName;
	Socket socket;
	//java中变量作用域；
	JComboBox receiverBox;
	
	public ChatFrame(final String userName,final Socket socket){
		filepath="";
		 jlb=new JLabel();
		this.socket=socket;
		this.userName=userName;
		
		this.setTitle("聊天室主界面");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		//窗体不可扩大
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//获取屏幕
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		//屏幕居中处理
		setLocation((width-FRAME_WIDTH)/2, (height-FRAME_HEIGHT)/2);
		setVisible(true);
		
		//加载窗体的背景图片
		ImageIcon imageIcon = new ImageIcon("src/image/beijing.jpg");
		//创建一个标签并将图片添加进去
		JLabel frameBg = new JLabel(imageIcon);
		//设置图片的位置和大小
		frameBg.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		this.add(frameBg);
		
		// 接收框
		acceptPane = new JTextPane();
		acceptPane.setOpaque(false);//设置透明
		acceptPane.setFont(new Font("宋体", 0, 16));
		
		// 设置接收框滚动条
		JScrollPane scoPaneOne = new JScrollPane(acceptPane);
		scoPaneOne.setBounds(15, 20, 500, 332);
		//设置背景透明
		scoPaneOne.setOpaque(false);//true表示不透明,false表示透明。
		scoPaneOne.getViewport().setOpaque(false);
		frameBg.add(scoPaneOne);
		


		//当前在线用户列表
		lstUser = new JList();
		lstUser.setFont(new Font("宋体", 0, 14));
		lstUser.setVisibleRowCount(17);
		lstUser.setFixedCellWidth(180);
		lstUser.setFixedCellHeight(18);
		
		//声明菜单
		final JPopupMenu popupMenu=new JPopupMenu();
		//私聊按钮(菜单项）
		JMenuItem privateChat=new JMenuItem("私聊");
		privateChat.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//做一系列的动作
				//伪代码
				//告诉发送消息的接收人是谁？
				Object receiverObj = lstUser.getSelectedValue();
				if(receiverObj !=null){//空判断
					String receiver = String.valueOf(receiverObj);
					receiverBox.removeAllItems();
					receiverBox .addItem("All");
					receiverBox .addItem(receiver);
					receiverBox.setSelectedItem(receiver);
				}
				
			}
		});
		popupMenu.add(privateChat);
		//黑名单按钮(菜单项）  无
		JMenuItem blackList=new JMenuItem("黑名单");
		blackList.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		popupMenu.add(blackList);
		
		//添加点击事件,确认是右键
		lstUser.addMouseListener(new MouseAdapter(){//适配器模式。接口不能new的。将接口进行转换ml
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//监听左键还是右键
				if(e.isMetaDown()){
					//弹出菜单
					popupMenu.show(lstUser,e.getX(),e.getY());
				}
			}
		});
		
		
		
		JScrollPane spUser = new JScrollPane(lstUser);
		spUser.setFont(new Font("宋体", 0, 14));
		spUser.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spUser.setBounds(530, 17, 200, 507);
		frameBg.add(spUser);
		
		
		// 输入框
		final JTextPane sendPane = new JTextPane();
		sendPane.setOpaque(false);
		sendPane.setFont(new Font("宋体", 0, 16));
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
                    sendPane.insertIcon(new ImageIcon(filepath));//插入图片
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
		
		JScrollPane scoPane = new JScrollPane(sendPane);// 设置滚动条
		scoPane.setBounds(15, 400, 500, 122);
		scoPane.setOpaque(false);
		scoPane.getViewport().setOpaque(false);
		frameBg.add(scoPane);
		
		// 添加表情选择
		JLabel lblface = new JLabel(new ImageIcon("src/image/face.png"));
		lblface.setBounds(14, 363, 25, 25);
		lblface.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				FaceFrame face=new FaceFrame(sendPane);
			}
		});
			
		frameBg.add(lblface);
		
		// 添加抖动效果
		JLabel lbldoudong = new JLabel(new ImageIcon("src/image/doudong.png"));
		lbldoudong.setBounds(43, 363, 25, 25);
		frameBg.add(lbldoudong);
		
		// 设置字体选择
		JLabel lblfontChoose = new JLabel(new ImageIcon("src/image/ziti.png"));
		lblfontChoose.setBounds(44, 363, 80, 25);
		lblfontChoose.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JColorChooser colorChooser = new JColorChooser();
				Color color = colorChooser.showDialog(ChatFrame.this, "字体颜色", Color.BLACK);
				//字体改变
				FontSupport.setFont(sendPane, color, fontFamilyCmb.getSelectedItem().toString(), Font.BOLD, 60);
			}
//			
		});
		frameBg.add(lblfontChoose);
		
		//字体下拉选项
//new7	
		fontFamilyCmb = new JComboBox();
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] str = graphicsEnvironment.getAvailableFontFamilyNames();
		for (String string : str) {
		fontFamilyCmb.addItem(string);
		}
		
		fontFamilyCmb.setSelectedItem("楷体");
		fontFamilyCmb.setBounds(104, 363, 150, 25);
		frameBg.add(fontFamilyCmb);
		
		//label标签
		JLabel receiverLabel=new JLabel("选择对象");
		receiverLabel.setBounds(304,363,150,25);
		frameBg.add(receiverLabel);
		
		//下拉选择框
		receiverBox = new JComboBox();
		receiverBox.setSelectedItem("All");
		receiverBox.addItem("All");
		receiverBox.setBounds(374, 363, 135, 25);
		frameBg.add(receiverBox);
		
//		/*
//		 * 发送按钮2
//		 */
//		JButton sendp = new JButton("发送图片");
//		sendp.setBounds(300, 533, 125, 25);
//		sendp.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//			//	new sendpt();
//			}
//		});
//		frameBg.add(sendp);
		/*
		 * 发送按钮
		 */
		JButton send = new JButton("发 送");
		send.setBounds(15, 533, 125, 25);
		send.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//String content = sendPane.getText();
				TransforInfo tif=new TransforInfo();
				
				//包装了所有文字对应的属性
				List<FontStyle> fontStyles = FontSupport.fontEncode(sendPane);
				tif.setContent(fontStyles);
				//发送人
				tif.setSender(userName);
				String receiver="All";
				//获取当前发送给谁？
				Object receiverObj = receiverBox.getSelectedItem();
				
				if(receiverObj !=null){
					receiver =String.valueOf(receiverObj);
				}
				System.out.println("发送消息触发:"+receiver);
				//接受人all
			
				tif.setReciver(receiver);
			
				//本次处理的消息类型为登陆
				tif.setStatusEnum(ChatStatus.CHAT);
				IOStream.writeMessage(socket, tif);
				sendPane.setText(" ");
			}
		});
		frameBg.add(send);
		
		setVisible(true);
	
	}
}
