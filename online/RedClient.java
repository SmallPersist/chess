package merge2;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;


class RedClient extends JFrame implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	public static int server_port = 8000;
	
	JLabel play[] = new JLabel[32];
	JLabel image;	
	Container con;
	JToolBar jmain;	
	JButton anew;
	JButton repent;
	JButton exit;
	JLabel text;
	
	static int Man, i;
	
	// 谁走棋
	int chessPlayClick=2;
	
	// 规则
	Rule rule = new Rule();
	
	
	// 这一步是不是走对了？
	int[] movePass = null;
	boolean eatPass = false;
	
	// 返回的棋子移动信息，标准如下(服务端按照规则解析就是)：
	// 如果是移动：move,i,coordinateX,coordinateY
	// 如果是吃子：eat,i,j
	// 如果是查询:query
	// 修改Rule的返回值是int[]，如果不pass就返回null，pass就返回落子的坐标，stepPass = (return null)
	String stepInfo = "";
	
	// 有没有选择棋子
	boolean chessManClick = false;
	
	@SuppressWarnings("deprecation")
	public RedClient(String title) throws Exception {
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
		
		// 设置
		this.setResizable(false);
		this.setTitle(title);
		this.setSize(558,670);
		this.setVisible(true);
		this.show();
		
		gameStart();
	}
	
	
	private void gameStart() throws Exception {
		
		while(true) {
			// 游戏开始了，就一直死循环
			
			while(movePass == null && !eatPass) {
				
				// 不能一直死循环，先让出CPU处理鼠标等事件
				Thread.sleep(200);
			}
			
			movePass = null;
			eatPass = false;
			
			System.out.println("sending info to black...:" + stepInfo);
			// 这步pass了之后，把移动的是什么发给server
			Socket s = new Socket("127.0.0.1", 8000);
			OutputStream os = s.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			
			bw.write(stepInfo + "\n");
			bw.flush();
			stepInfo = null;
			s.close();
			
			// 等黑方先把数据query掉
			Thread.sleep(300);
			
			// 另外请求：对方有没有走棋
			boolean flag = false;
			while(!flag) {
				Socket ss = new Socket("127.0.0.1", 8000);
				OutputStream oss = ss.getOutputStream();
				InputStream iss = ss.getInputStream();
				BufferedWriter bws = new BufferedWriter(new OutputStreamWriter(oss));
				BufferedReader brs = new BufferedReader(new InputStreamReader(iss));
				bws.write("query\n");
				bws.flush();
				
				// 另外一个人走棋的数据
				String msg = brs.readLine();
				if(!"no".equals(msg)) {
					flag = true;
					update(msg);
				}
				
				ss.close();
				Thread.sleep(100);
			}
			
			
			
			// 如果服务器发来数据，说不用继续了，就break
//			if(gameOver)	break;
		}
	}
	
	
	public void update(String msg) {
		String[] ss = msg.split(",");
		if(ss[0].equals("move")) {
			int i  = Integer.valueOf(ss[1]);
			int coordX = Integer.valueOf(ss[2]);
			int coordY = Integer.valueOf(ss[3]);
			play[i].setBounds(coordX, coordY, 55, 55);
		} else {
			int i  = Integer.valueOf(ss[1]);
			int j  = Integer.valueOf(ss[2]);
			play[i].setBounds(play[j].getBounds());
			play[j].setVisible(false);
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
		
		// 单击的是棋盘，即不吃子，只是走一步
		if(e.getSource().equals(image)) {
			
			// 该红棋
			if(chessPlayClick == 2 && play[Man].getName().charAt(1) == '2') {
				
				if(Man > 15 && Man < 26) {
					// 移动兵
					movePass = rule.armsRule(Man, play[Man], e);
				} else if(Man > 25 && Man < 30) {
					// 移动炮
					movePass = rule.cannonRule(play[Man], play, e);
				} else if(Man >= 0 && Man < 4) {
					// 移动车
					movePass = rule.cannonRule(play[Man], play, e);
				} else if(Man > 3 && Man < 8) {
					// 移动马
					movePass = rule.horseRule(play[Man], play, e);
				} else if(Man > 7 && Man < 12) {
					// 移动象
					movePass = rule.elephantRule(0, play[Man], play, e);
				} else if(Man > 11 && Man < 16) {
					// 移动士
					movePass = rule.chapRule(0, play[Man], e, play);
				} else if(Man == 30 || Man == 31) {
					// 移动帅
					movePass = rule.willRule(0, play[Man], e, play);
				} 
				
				if (movePass != null)	chessPlayClick = 1;
			}
			
			//该黑棋 
			else if(chessPlayClick == 1 && play[Man].getName().charAt(1) == '1') {
				
				if(Man > 15 && Man < 26) {
					// 移动兵
					movePass = rule.armsRule(Man, play[Man], e);
				} else if(Man > 25 && Man < 30) {
					// 移动炮
					movePass = rule.cannonRule(play[Man], play, e);
				} else if(Man >= 0 && Man < 4) {
					// 移动车
					movePass = rule.cannonRule(play[Man], play, e);
				} else if(Man > 3 && Man < 8) {
					// 移动马
					movePass = rule.horseRule(play[Man], play, e);
				} else if(Man > 7 && Man < 12) {
					// 移动象
					movePass = rule.elephantRule(1, play[Man], play, e);
				} else if(Man > 11 && Man < 16) {
					// 移动士
					movePass = rule.chapRule(1, play[Man], e, play);
				} else if(Man == 30 || Man == 31) {
					// 移动帅
					movePass = rule.willRule(1, play[Man], e, play);
				}

				if (movePass != null)	chessPlayClick = 2;
			}
			
			if(movePass != null)		stepInfo = "move," + Man + "," + movePass[0] + "," + movePass[1];
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
								eatPass = rule.armsRuleEat(Man, play[Man], play[i]);
							} else if(Man > 25 && Man < 30) {
								eatPass = rule.cannonRuleEat(play[Man], play[i], play);
							} else if (Man >=0 && Man < 4){
								eatPass = rule.cartRuleEat(play[Man], play[i], play);
							} else if (Man > 3 && Man < 8){
								eatPass = rule.horseRuleEat(play[Man], play[i], play);	
							} else if (Man > 7 && Man < 12){
								eatPass = rule.elephantRuleEat(0, play[Man], play[i], play);
							} else if (Man > 11 && Man < 16){
								eatPass = rule.chapRuleEat(0, play[Man], play[i], play);
							} else if (Man == 30 || Man == 31){
								eatPass = rule.willRuleEat(0, play[Man], play[i], play);	
							}
							
							if(eatPass)	chessPlayClick = 1;
						}
						
						// 该黑棋
						else {
							if(Man > 15 && Man < 26) {
								eatPass = rule.armsRuleEat(Man, play[Man], play[i]);
							} else if(Man > 25 && Man < 30) {
								eatPass = rule.cannonRuleEat(play[Man], play[i], play);
							} else if (Man >=0 && Man < 4){
								eatPass = rule.cartRuleEat(play[Man], play[i], play);
							} else if (Man > 3 && Man < 8){
								eatPass = rule.horseRuleEat(play[Man], play[i], play);	
							} else if (Man > 7 && Man < 12){
								eatPass = rule.elephantRuleEat(0, play[Man], play[i], play);
							} else if (Man > 11 && Man < 16){
								eatPass = rule.chapRuleEat(0, play[Man], play[i], play);
							} else if (Man == 30 || Man == 31){
								eatPass = rule.willRuleEat(0, play[Man], play[i], play);	
							}
							
							if(eatPass)	chessPlayClick = 2;
						}
						
						if(eatPass)		stepInfo = "eat," + Man + "," + i;
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