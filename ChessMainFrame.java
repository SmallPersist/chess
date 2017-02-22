package com;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;


/*
 * 棋盘显示，棋子不显示：去掉棋盘，棋子显示，说明是棋盘遮住了棋子，更换一下添加顺序就好了
 */
class ChessMainFrame extends JFrame implements ActionListener, MouseListener, Runnable{
	private static final long serialVersionUID = 1L;
	
	//玩家
	JLabel play[] = new JLabel[32];
	//棋盘
	JLabel image;	
	//窗格
	Container con;
	//工具栏
	JToolBar jmain;	
	//重新开始
	JButton anew;
	//悔棋
	JButton repent;
	//退出
	JButton exit;
	//当前信息
	JLabel text;
	
	// 单击棋子闪烁
	boolean chessManClick;
	static int Man, i;
	Thread tmain;
	
	// 谁走棋
	int chessPlayClick=2;
	
	// 规则
	Rule rule = new Rule();
	
	
	@SuppressWarnings("deprecation")
	public ChessMainFrame(String title) {
		con = this.getContentPane();
		con.setLayout(null);
		
		jmain = new JToolBar();
		text = new JLabel("欢迎使用象棋对弈系统");
		//当鼠标放上显示信息
		text.setToolTipText("信息提示");
		anew = new JButton(" 新 游 戏 ");
		anew.setToolTipText("重新开始新的一局");
		exit = new JButton(" 退  出 ");
		exit.setToolTipText("退出象棋程序程序");
		repent = new JButton(" 悔  棋 ");
		repent.setToolTipText("返回到上次走棋的位置");
		// 将组件添加到工具栏
		jmain.setLayout(new GridLayout(0, 4));
		jmain.add(anew);
		jmain.add(repent);
		jmain.add(exit);
		jmain.add(text);
		jmain.setBounds(0, 0, 558, 30);
		con.add(jmain);
		//注册按扭监听
		anew.addActionListener(this);
		repent.addActionListener(this);
		exit.addActionListener(this);	
		
		//添加棋子标签
		Util.drawChessMan(play);
		//注册棋子移动监听
		for (int i=0;i<32;i++){
			con.add(play[i]);
			play[i].addMouseListener(this);
		}
		
		// 添加棋盘
		con.add(image = new JLabel(new ImageIcon("src\\image\\main.gif")));
		image.setBounds(0, 30, 558, 620);
		image.setName("board");
		image.addMouseListener(this);
		
		
		
		//注册窗体关闭监听
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
		//窗体在电脑屏幕居中
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		
		if (frameSize.height > screenSize.height){
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width){
			frameSize.width = screenSize.width;
		}
		this.setLocation((screenSize.width - frameSize.width) / 2 - 280 ,
						(screenSize.height - frameSize.height ) / 2 - 350);
		
		// 设置
		this.setResizable(false);
		this.setTitle(title);
		this.setSize(558,670);
		this.setVisible(true);
		this.show();
		
	}
	
	
	// 棋子闪烁的线程
	@SuppressWarnings({ "static-access", "static-access" })
	@Override
	public void run() {
		while(true) {
			// 如果单击选中了某个，则闪烁选中的
			if(chessManClick) {
				play[Man].setVisible(false);
				try {
					tmain.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
				play[Man].setVisible(true);
			}
			
			try {
				tmain.sleep(350);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 按钮注册的方法
	@Override
	public void actionPerformed(ActionEvent e) {
		//重新开始按钮
		if(e.getSource().equals(anew)) {
			System.out.println("a new game...");
			Util.newGame(play);
			chessPlayClick = 2;
		}
	}

	// 鼠标监听事件
	@Override
	public void mouseClicked(MouseEvent e) {
		// 启动tmain线程
		if(tmain == null) {
			tmain = new Thread(this);
			tmain.start();
		}
		
		// 符合不符合规则？
		boolean passRule = false;
		
		// 单击的是棋盘，即不吃子，只是走一步
		if(e.getSource().equals(image)) {
			
			// 该红棋
			if(chessPlayClick == 2 && play[Man].getName().charAt(1) == '2') {
				
				if(Man > 15 && Man < 26) {
					// 移动兵
					System.out.println("checking arms rule...");
					passRule = rule.armsRule(Man, play[Man], e);
				} else if(Man > 25 && Man < 30) {
					// 移动炮
					System.out.println("check cannon rule...");
					passRule = rule.cannonRule(play[Man], play, e);
				} else if(Man >= 0 && Man < 4) {
					// 移动车
					System.out.println("check cannon rule...");
					passRule = rule.cannonRule(play[Man], play, e);
				} else if(Man > 3 && Man < 8) {
					// 移动马
					System.out.println("check horse rule...");
					passRule = rule.horseRule(play[Man], play, e);
				} else if(Man > 7 && Man < 12) {
					// 移动象
					System.out.println("check elephant rule...");
					passRule = rule.elephantRule(0, play[Man], play, e);
				} else if(Man > 11 && Man < 16) {
					// 移动士
					System.out.println("check chap rule...");
					passRule = rule.chapRule(0, play[Man], e, play);
				} else if(Man == 30 || Man == 31) {
					// 移动帅
					System.out.println("check will rule...");
					passRule = rule.willRule(0, play[Man], e, play);
				} 
				
				if (passRule)	chessPlayClick = 1;
			}
			
			//该黑棋 
			else if(chessPlayClick == 1 && play[Man].getName().charAt(1) == '1') {
				
				if(Man > 15 && Man < 26) {
					// 移动兵
					System.out.println("checking arms rule...");
					passRule = rule.armsRule(Man, play[Man], e);
				} else if(Man > 25 && Man < 30) {
					// 移动炮
					System.out.println("check cannon rule...");
					passRule = rule.cannonRule(play[Man], play, e);
				} else if(Man >= 0 && Man < 4) {
					// 移动车
					System.out.println("check cannon rule...");
					passRule = rule.cannonRule(play[Man], play, e);
				} else if(Man > 3 && Man < 8) {
					// 移动马
					System.out.println("check horse rule...");
					passRule = rule.horseRule(play[Man], play, e);
				} else if(Man > 7 && Man < 12) {
					// 移动象
					System.out.println("check elephant rule...");
					passRule = rule.elephantRule(1, play[Man], play, e);
				} else if(Man > 11 && Man < 16) {
					// 移动士
					System.out.println("check chap rule...");
					passRule = rule.chapRule(1, play[Man], e, play);
				} else if(Man == 30 || Man == 31) {
					// 移动帅
					System.out.println("check will rule...");
					passRule = rule.willRule(1, play[Man], e, play);
				}

				if (passRule)	chessPlayClick = 2;
			}
			
			// 不管走没有走，因为点了一下，都不闪烁了
			chessManClick = false;
		}
		
		// 单击的是棋子，可能是选中，也可能是吃子
		else {
			//第一次选中棋子(闪烁棋子)，确定单击的是哪个
			if(!chessManClick) {
				for(int i=0; i<32; i++) {
					if(e.getSource().equals(play[i])) {
						Man = i;
						chessManClick = true;
						break;
					}
				}
			}
			
			//吃子
			else if(chessManClick) {
				chessManClick = false;
				
				// 找到被吃掉的棋子
				for(int i=0; i<32; i++) {
					if(e.getSource().equals(play[i])) {
						// 红棋吃棋
						if(chessPlayClick == 2 && play[Man].getName().charAt(1) == '2') {
							if(Man > 15 && Man < 26) {
								System.out.println("check arms eating....");
								passRule = rule.armsRuleEat(Man, play[Man], play[i]);
							} else if(Man > 25 && Man < 30) {
								System.out.println("check cannon eating...");
								passRule = rule.cannonRuleEat(play[Man], play[i], play);
							} else if (Man >=0 && Man < 4){
								System.out.println("check cart eating...");
								passRule = rule.cartRuleEat(play[Man], play[i], play);
							} else if (Man > 3 && Man < 8){
								passRule = rule.horseRuleEat(play[Man], play[i], play);	
							} else if (Man > 7 && Man < 12){
								passRule = rule.elephantRuleEat(0, play[Man], play[i], play);
							} else if (Man > 11 && Man < 16){
								passRule = rule.chapRuleEat(0, play[Man], play[i], play);
							} else if (Man == 30 || Man == 31){
								passRule = rule.willRuleEat(0, play[Man], play[i], play);	
							}
							
							if(passRule)	chessPlayClick = 1;
						}
						
						// 该黑棋
						else {
							if(Man > 15 && Man < 26) {
								System.out.println("check arms eating....");
								passRule = rule.armsRuleEat(Man, play[Man], play[i]);
							} else if(Man > 25 && Man < 30) {
								System.out.println("check cannon eating...");
								passRule = rule.cannonRuleEat(play[Man], play[i], play);
							} else if (Man >=0 && Man < 4){
								System.out.println("check cart eating...");
								passRule = rule.cartRuleEat(play[Man], play[i], play);
							} else if (Man > 3 && Man < 8){
								passRule = rule.horseRuleEat(play[Man], play[i], play);	
							} else if (Man > 7 && Man < 12){
								passRule = rule.elephantRuleEat(0, play[Man], play[i], play);
							} else if (Man > 11 && Man < 16){
								passRule = rule.chapRuleEat(0, play[Man], play[i], play);
							} else if (Man == 30 || Man == 31){
								passRule = rule.willRuleEat(0, play[Man], play[i], play);	
							}
							
							if(passRule)	chessPlayClick = 2;
						}
					}
				}
			}
		}
		
		
		// 游戏是否结束
		if(!play[31].isVisible()) {
			JOptionPane.showConfirmDialog(this, "黑棋win", "paly 1 win", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			// 都不能走棋了
			chessPlayClick = 3;
		} else if(!play[30].isVisible()) {
			JOptionPane.showConfirmDialog(this, "红棋win", "paly 2 win", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			// 都不能走棋了
			chessPlayClick = 3;
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

}